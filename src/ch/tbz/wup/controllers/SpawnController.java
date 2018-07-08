package ch.tbz.wup.controllers;

import java.awt.Point;
import java.awt.Rectangle;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Random;

import ch.tbz.wup.models.Area;
import ch.tbz.wup.models.AreaType;
import ch.tbz.wup.models.Player;
import ch.tbz.wup.models.PokemonSpecies;
import ch.tbz.wup.models.Spawn;
import ch.tbz.wup.views.IUserInterface;

public class SpawnController {
	
	private static final int MAX_SECONDS_UNTIL_NEXT_SPAWN = 20;
	private static final int MINIMUM_SPAWN_RADIUS = 50;
	private static final int MAXIMUM_SPAWN_RADIUS = 150;
	private static final int SPRITE_SIZE = 48;
	private static final String SPRITE_FILEPATH = "./files/graphics/sprites/pokemon/";
	private static final String SPRITE_FILE_EXTENSION = ".png";
	
	private Player _player;
	private IUserInterface _userInterface;
	private List<Area> _allAreas;
	private Map<Integer, PokemonSpecies> _allPokemon;
	
	private long _nextSpawnCountdownTicks = 20 * MainController.TICKS_PER_SECOND;
	private Random _random = new Random();
	
	public SpawnController(Player player, IUserInterface userInterface, List<Area> allAreas, Map<Integer, PokemonSpecies> allPokemon) {
		_player = player;
		_userInterface = userInterface;
		_allAreas = allAreas;
		_allPokemon = allPokemon;
	}
	
	public void tick() {
		if (_nextSpawnCountdownTicks <= 0) {
			attemptSpawn();
			_nextSpawnCountdownTicks = _random.nextInt((int)(MAX_SECONDS_UNTIL_NEXT_SPAWN * MainController.TICKS_PER_SECOND));
		}
		else {
			_nextSpawnCountdownTicks--;
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
		
		_userInterface.showImage(
				SPRITE_FILEPATH + pokemonNumber + SPRITE_FILE_EXTENSION, 
				new Rectangle(0, 0, SPRITE_SIZE, SPRITE_SIZE),
				rc_spawnPoint,
				_player.getLocation());
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
		
		return allSpawns.get(allSpawns.size() - 1);
	}
}
