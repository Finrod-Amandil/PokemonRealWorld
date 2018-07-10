package ch.tbz.wup.controllers;

import java.awt.Point;
import java.awt.Rectangle;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import javax.swing.JLabel;

import ch.tbz.wup.IObservable;
import ch.tbz.wup.IObserver;
import ch.tbz.wup.models.Area;
import ch.tbz.wup.models.AreaType;
import ch.tbz.wup.models.Player;
import ch.tbz.wup.models.PlayerStateChange;
import ch.tbz.wup.models.Spawn;
import ch.tbz.wup.models.SpawnedPokemon;
import ch.tbz.wup.views.IUserInterface;

/**
 * Specialised controller that deals with all actions that are related
 * to Pokémon Spawning and Despawning. Implements IObserver in order
 * to observe the Player class.
 */
public class SpawnController implements IObserver {
	
	private static final int MAX_SECONDS_UNTIL_NEXT_SPAWN = 20;
	private static final int SECONDS_UNTIL_DESPAWN = 60;
	private static final int MINIMUM_SPAWN_RADIUS = 50; //in meters
	private static final int MAXIMUM_SPAWN_RADIUS = 150; //in meters
	private static final double MIN_ENCOUNTER_DISTANCE = 30; //in meters
	private static final int SPRITE_SIZE = 48;
	private static final String SPRITE_FILEPATH = "./files/graphics/sprites/pokemon/";
	private static final String SPRITE_FILE_EXTENSION = ".png";
	
	private Player _player;
	private IUserInterface _userInterface;
	private List<Area> _allAreas;
	
	//Counter (countdown) until next spawn occurs. The initial value given specifies,
	//After which (fixed) time the first Pokémon spawns.
	private long _nextSpawnCountdownTicks = 20 * MainController.TICKS_PER_SECOND;
	private Random _random = new Random();
	
	//The Pokémon that are currently spawned / on the map.
	private List<SpawnedPokemon> _activePokemon = new ArrayList<SpawnedPokemon>();
	
	/**
	 * Instantiates a new SpawnController. Subscribes to the Player Observable.
	 * 
	 * @param player  The Singleton player.
	 * @param userInterface  The Singleton user interface.
	 * @param allAreas  List of all areas of the current region.
	 */
	public SpawnController(Player player, IUserInterface userInterface, List<Area> allAreas) {
		_player = player;
		_player.addObserver(this);
		_userInterface = userInterface;
		_allAreas = allAreas;
	}
	
	/**
	 * Reacts to a change of an observable. Is called, when the player changed his
	 * position, i.e. moved. This may lead to an encounter, thus check if an
	 * encounter happened.
	 * 
	 * @param observable  The object that changed state and raised the notification.
	 */
	@Override
	public void onObservableChanged(IObservable observable, int changeId) {
		if (observable instanceof Player && changeId == PlayerStateChange.LOCATION_CHANGED.ordinal()) {
			checkEncounter();
		}
	}
	
	/**
	 * Counts down the counter until next spawn. If the counter reaches 0, spawns a
	 * Pokémon and resets the countdown. Every few ticks check, if a Pokémon should
	 * despawn due to it being present for too long already.
	 * 
	 * @param totalTicks
	 */
	public void tick(long totalTicks) {
		if (_nextSpawnCountdownTicks <= 0) {
			attemptSpawn();
			//Maximum Ticks = (Ticks/Seconds) * Seconds until spawn.
			_nextSpawnCountdownTicks = _random.nextInt((int)(MAX_SECONDS_UNTIL_NEXT_SPAWN * MainController.TICKS_PER_SECOND));
		}
		else {
			_nextSpawnCountdownTicks--;
		}
		
		//Every 10 ticks, check if Pokémon should despawn.
		if (totalTicks % 10 == 0) {
			updateSpawnedPokemon();
		}
	}
	
	//Attempts to spawn a Pokémon
	private void attemptSpawn() {
		
		//Obtain a random spawnpoint near player. Coordinates are relative to player.
		Point rel_spawnPoint = getSpawnPoint();
		
		//Convert relative spawnpoint to absolute coordinates.
		Point rc_spawnPoint = new Point(
				_player.getLocation().x + rel_spawnPoint.x, 
				_player.getLocation().y + rel_spawnPoint.y);
				
		//Obtain all areas in which the point lies.
		List<Area> spawnAreas = getSpawnAreas(rc_spawnPoint);
		
		//Add or remove some special areas.
		spawnAreas = filterAreas(spawnAreas);
		
		//Obtain a random spawn from the available areas.
		Spawn spawn = getSpawn(spawnAreas);
		if (spawn == null) {
			return;
		}
		
		//Spawn the Pokémon.
		int pokemonNumber = spawn.getSpecies().getId();
		JLabel sprite = _userInterface.showImage(
			SPRITE_FILEPATH + pokemonNumber + SPRITE_FILE_EXTENSION, 
			new Rectangle(0, 0, SPRITE_SIZE, SPRITE_SIZE),
			rc_spawnPoint,
			_player.getLocation());
		_activePokemon.add(new SpawnedPokemon(spawn.getSpecies(), rc_spawnPoint, sprite));
	}
	
	//Finds a random spawnpoint within the area that lies between a minimum and maximum radius
	//around the player.
	private Point getSpawnPoint() {
		double minimumDistance = MINIMUM_SPAWN_RADIUS;
		double maximumDistance = MAXIMUM_SPAWN_RADIUS;
		
		//Get a random point in polar coordinates.
		//SquareRoot in distance formula is to ensure even distribution of spawns.
		double angle = _random.nextDouble() * 2 * Math.PI;
		double distance = (maximumDistance - minimumDistance) * Math.sqrt(_random.nextDouble()) + minimumDistance;
		
		//Convert point to cartesian coordinates.
		int x = (int)(distance * Math.cos(angle));
		int y = (int)(distance * Math.sin(angle));
		
		return new Point(x, y);
	}
	
	//Determines, in which ares the chosen spawnpoint lies.
	private List<Area> getSpawnAreas(Point spawnPoint) {
		List<Area> spawnAreas = new ArrayList<Area>();
		for (Area area : _allAreas) {
			if (area.contains(spawnPoint)) {
				spawnAreas.add(area);
			}
		}
		
		return spawnAreas;
	}
	
	//Checks for all active Pokémon, whether they are spawned for longer than the specified despawn-time.
	//Despawns them if needed.
	private void updateSpawnedPokemon() {
		List<SpawnedPokemon> tempList = new ArrayList<SpawnedPokemon>(_activePokemon);
		for (SpawnedPokemon pokemon : tempList) {
			if (pokemon.getSpawnTime().plusSeconds(SECONDS_UNTIL_DESPAWN).isBefore(LocalDateTime.now())) {
				_userInterface.hideImage(pokemon.getSprite());
				_activePokemon.remove(pokemon);
			}
		}
	}
	
	//Checks whether the distance between the player and any Pokémon is small
	//enough to trigger an encounter.
	private void checkEncounter() {
		boolean isEncounter = false;
		double distance = 0;
		SpawnedPokemon encounteredPokemon = null;
		
		//Check all Pokémon, even if a Pokémon in range was found.
		//In case there are multiple Pokémon in range, choose the closest one.
		for (SpawnedPokemon pokemon : _activePokemon) {
			Point rc_pokemon = pokemon.getLocation();
			Point rc_player = _player.getLocation();
			
			//Calculate distance between Pokémon and Player.
			int dX = rc_pokemon.x - rc_player.x;
			int dY = rc_pokemon.y - rc_player.y;
			double newDistance = Math.sqrt((dX * dX) + (dY * dY));
			
			if (newDistance <= MIN_ENCOUNTER_DISTANCE) {
				//Override encounteredPokémon if there's another one who is even closer.
				if ((isEncounter && newDistance < distance) || !isEncounter) {
					isEncounter = true;
					distance = newDistance;
					encounteredPokemon = pokemon;
				}
			}
		}
		
		if (isEncounter) {
			_userInterface.hideImage(encounteredPokemon.getSprite());
			_activePokemon.remove(encounteredPokemon);
			_player.addPokemonToPokedex(encounteredPokemon.getSpecies());
		}
	}
	
	//Adds and removes some areas based on various criteria.
	private List<Area> filterAreas(List<Area> spawnAreas) {
		
		//If there is a 'special' area (with no type), disregard all others.
		//Such areas are 'isolated', and no other Pokémon can spawn there.
		boolean containsSpecial = false;
		for (Area spawnArea : spawnAreas) {
			if (spawnArea.getType() == AreaType.NONE) {
				containsSpecial = true;
				break;
			}
		}
		
		if (containsSpecial) {
			List<Area> tempList = new ArrayList<Area>(spawnAreas);
			spawnAreas = new ArrayList<Area>();
			
			for (Area area : tempList) {
				if (area.getType() == AreaType.NONE) {
					spawnAreas.add(area);
				}
			}
		}
		
		//Add default areas (areas with same types as current spawn areas but no special name)
		List<Area> tempList = new ArrayList<Area>(spawnAreas);
		for (Area spawnArea : tempList) {
			
			//If current area is a default area already no action needs to be done.
			if (spawnArea.getName().length() == 0) {
				continue;
			}
			
			AreaType currentType = spawnArea.getType();
			
			//Find default area
			for (Area area : _allAreas) {
				if (area.getType() == currentType && area.getName().length() == 0 && !spawnAreas.contains(area)) {
					spawnAreas.add(area);
				}
			}
		}
		
		return spawnAreas;		
	}
	
	//Choose a random spawn from the available areas, while regarding the different spawn weights.
	private Spawn getSpawn(List<Area> spawnAreas) {
		
		//Gather all spawns from all areas.
		List<Spawn> allSpawns = new ArrayList<Spawn>();
		for (Area spawnArea : spawnAreas) {
			allSpawns.addAll(spawnArea.getSpawns());
		}
		
		//Calculate sum of all spawn weights.
		int totalSpawnWeight = 0;
		for (Spawn spawn : allSpawns) {
			totalSpawnWeight += spawn.getWeight();
		}
		
		//No spawns available for this point.
		if (totalSpawnWeight == 0) {
			return null;
		}
		
		//Determine Pokémon by continually subtracting spawn weights
		//until result reaches 0 or lower.
		int spawnIndex = _random.nextInt(totalSpawnWeight);
		for (Spawn spawn : allSpawns) {
			spawnIndex -= spawn.getWeight();
			if (spawnIndex <= 0) {
				return spawn;
			}
		}
		
		return null;
	}
}
