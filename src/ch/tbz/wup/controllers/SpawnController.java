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
import ch.tbz.wup.models.Spawn;
import ch.tbz.wup.models.SpawnedPokemon;
import ch.tbz.wup.views.IUserInterface;

public class SpawnController implements IObserver {
	
	private static final int MAX_SECONDS_UNTIL_NEXT_SPAWN = 20;
	private static final int SECONDS_UNTIL_DESPAWN = 60;
	private static final int MINIMUM_SPAWN_RADIUS = 50;
	private static final int MAXIMUM_SPAWN_RADIUS = 150;
	private static final double MIN_ENCOUNTER_DISTANCE = 30;
	private static final int SPRITE_SIZE = 48;
	private static final String SPRITE_FILEPATH = "./files/graphics/sprites/pokemon/";
	private static final String SPRITE_FILE_EXTENSION = ".png";
	
	private Player _player;
	private IUserInterface _userInterface;
	private List<Area> _allAreas;
	
	private long _nextSpawnCountdownTicks = 20 * MainController.TICKS_PER_SECOND;
	private Random _random = new Random();
	
	private List<SpawnedPokemon> _activePokemon = new ArrayList<SpawnedPokemon>();
	
	public SpawnController(Player player, IUserInterface userInterface, List<Area> allAreas) {
		_player = player;
		_player.addObserver(this);
		_userInterface = userInterface;
		_allAreas = allAreas;
	}
	
	@Override
	public void onObservableChanged(IObservable observable) {
		if (observable instanceof Player) {
			checkEncounter();
		}
	}
	
	public void tick(long totalTicks) {
		if (_nextSpawnCountdownTicks <= 0) {
			attemptSpawn();
			_nextSpawnCountdownTicks = _random.nextInt((int)(MAX_SECONDS_UNTIL_NEXT_SPAWN * MainController.TICKS_PER_SECOND));
		}
		else {
			_nextSpawnCountdownTicks--;
		}
		
		if (totalTicks % 10 == 0) {
			updateSpawnedPokemon();
		}
	}
	
	private void attemptSpawn() {
		Point rel_spawnPoint = getSpawnPoint();
		Point rc_spawnPoint = new Point(
				_player.getLocation().x + rel_spawnPoint.x, 
				_player.getLocation().y + rel_spawnPoint.y);
				
		List<Area> spawnAreas = getSpawnAreas(rc_spawnPoint);
		spawnAreas = filterAreas(spawnAreas);
		
		Spawn spawn = getSpawn(spawnAreas);
		if (spawn == null) {
			return;
		}
		
		int pokemonNumber = spawn.getSpecies().getId();
		JLabel sprite = _userInterface.showImage(
			SPRITE_FILEPATH + pokemonNumber + SPRITE_FILE_EXTENSION, 
			new Rectangle(0, 0, SPRITE_SIZE, SPRITE_SIZE),
			rc_spawnPoint,
			_player.getLocation());
		
		_activePokemon.add(new SpawnedPokemon(spawn.getSpecies(), rc_spawnPoint, sprite));
	}
	
	private Point getSpawnPoint() {
		double minimumDistance = MINIMUM_SPAWN_RADIUS;
		double maximumDistance = MAXIMUM_SPAWN_RADIUS;
		double angle = _random.nextDouble() * 2 * Math.PI;
		double distance = (maximumDistance - minimumDistance) * Math.sqrt(_random.nextDouble()) + minimumDistance;
		
		int x = (int)(distance * Math.cos(angle));
		int y = (int)(distance * Math.sin(angle));
		
		return new Point(x, y);
	}
	
	private List<Area> getSpawnAreas(Point spawnPoint) {
		List<Area> spawnAreas = new ArrayList<Area>();
		for (Area area : _allAreas) {
			if (area.contains(spawnPoint)) {
				spawnAreas.add(area);
			}
		}
		
		return spawnAreas;
	}
	
	private void updateSpawnedPokemon() {
		List<SpawnedPokemon> tempList = new ArrayList<SpawnedPokemon>(_activePokemon);
		for (SpawnedPokemon pokemon : tempList) {
			if (pokemon.getSpawnTime().plusSeconds(SECONDS_UNTIL_DESPAWN).isBefore(LocalDateTime.now())) {
				_userInterface.hideImage(pokemon.getSprite());
				_activePokemon.remove(pokemon);
			}
		}
	}
	
	private void checkEncounter() {
		boolean isEncounter = false;
		double distance = 0;
		SpawnedPokemon encounteredPokemon = null;
		for (SpawnedPokemon pokemon : _activePokemon) {
			Point rc_pokemon = pokemon.getLocation();
			Point rc_player = _player.getLocation();
			
			int dX = rc_pokemon.x - rc_player.x;
			int dY = rc_pokemon.y - rc_player.y;
			
			double newDistance = Math.sqrt((dX * dX) + (dY * dY));
			
			if (newDistance <= MIN_ENCOUNTER_DISTANCE) {
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
		}
	}
	
	private List<Area> filterAreas(List<Area> spawnAreas) {
		//If there is a 'special' area (with no type), only regard these
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
	
	private Spawn getSpawn(List<Area> spawnAreas) {
		List<Spawn> allSpawns = new ArrayList<Spawn>();
		for (Area spawnArea : spawnAreas) {
			allSpawns.addAll(spawnArea.getSpawns());
		}
		
		int totalSpawnWeight = 0;
		for (Spawn spawn : allSpawns) {
			totalSpawnWeight += spawn.getWeight();
		}
		
		//No spawns available for this point.
		if (totalSpawnWeight == 0) {
			return null;
		}
		
		int spawnIndex = _random.nextInt(totalSpawnWeight);
		
		for (Spawn spawn : allSpawns) {
			spawnIndex -= spawn.getWeight();
			if (spawnIndex <= 0) {
				return spawn;
			}
		}
		
		return allSpawns.get(0);
	}
}
