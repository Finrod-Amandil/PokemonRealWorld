package ch.tbz.wup.controllers;

import ch.tbz.wup.models.Player;
import ch.tbz.wup.views.IUserInterface;

public class MainController {
	private UserInputController _userInputController;
	private SpawnController _spawnController;
	private long _gameTicks = 0;
	private Player _player;
	private IUserInterface _userInterface;
	
	public MainController(Player player, IUserInterface userInterface) {
		_player = player;
		_userInterface = userInterface;
		_userInputController = new UserInputController(_player, _userInterface);
		_spawnController = new SpawnController(_player, _userInterface);
	}
	
	public void init() {
		_userInputController.init();
		startTicker();
	}
	
	private void startTicker() {
		while (true) {
			_userInputController.movePlayer();
			try {
				Thread.sleep(20);
				_gameTicks++;
				
				if (_gameTicks % 1 == 0) {
					_userInputController.movePlayer();
				}
				
				if (_gameTicks % 100 == 0) {
					_spawnController.attemptSpawn();
				}
				
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

}
