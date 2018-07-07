package ch.tbz.wup.controllers;

import java.awt.Point;
import java.awt.Rectangle;
import java.util.Random;

import ch.tbz.wup.models.Player;
import ch.tbz.wup.views.IUserInterface;

public class SpawnController {
	private IUserInterface _userInterface;
	private Player _player;
	
	public SpawnController(Player player, IUserInterface userInterface) {
		_player = player;
		_userInterface = userInterface;
	}
	
	public void attemptSpawn() {
		Random random = new Random();
		int pokemonNumber = random.nextInt(801) + 1;
		
		_userInterface.showImage(
				"./files/graphics/sprites/pokemon/" + pokemonNumber + ".png", 
				new Rectangle(0, 0, 96, 96),
				new Point(_player.getLocation().x, _player.getLocation().y + 100), 
				_player.getLocation());
	}
}
