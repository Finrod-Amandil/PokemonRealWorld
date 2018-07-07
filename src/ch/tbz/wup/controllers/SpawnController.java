package ch.tbz.wup.controllers;

import java.awt.Point;
import java.awt.Rectangle;
import java.util.Random;

import ch.tbz.wup.models.Player;
import ch.tbz.wup.views.IUserInterface;

public class SpawnController {
	private IUserInterface _userInterface;
	private Player _player;
	private long _nextSpawnCountdownTicks = 20 * MainController.TICKS_PER_SECOND;
	private Random _random = new Random();
	
	public SpawnController(Player player, IUserInterface userInterface) {
		_player = player;
		_userInterface = userInterface;
	}
	
	public void tick() {
		if (_nextSpawnCountdownTicks <= 0) {
			attemptSpawn();
			_nextSpawnCountdownTicks = _random.nextInt((int)(1 * MainController.TICKS_PER_SECOND));
		}
		else {
			_nextSpawnCountdownTicks--;
		}
	}
	
	private void attemptSpawn() {
		Point spawnPoint = getSpawnPoint();
		
		int pokemonNumber = _random.nextInt(801) + 1;
		
		_userInterface.showImage(
				"./files/graphics/sprites/pokemon/" + pokemonNumber + ".png", 
				new Rectangle(0, 0, 48, 48),
				new Point(
					_player.getLocation().x + spawnPoint.x, 
					_player.getLocation().y + spawnPoint.y), 
				_player.getLocation());
	}
	
	private Point getSpawnPoint() {
		double minimumDistance = 50;
		double maximumDistance = 150;
		double angle = _random.nextDouble() * 2 * Math.PI;
		double distance = (maximumDistance - minimumDistance) * Math.sqrt(_random.nextDouble()) + minimumDistance;
		
		int x = (int)(distance * Math.cos(angle));
		int y = (int)(distance * Math.sin(angle));
		
		System.out.println("Spawn point: (" + x + ", " + y + ")");
		
		return new Point(x, y);
	}
}
