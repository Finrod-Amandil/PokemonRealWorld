package ch.tbz.wup.controllers;

import ch.tbz.wup.models.Player;
import ch.tbz.wup.views.IUserInterface;

public class MainController {
	public static final long TICKS_PER_SECOND = 20;
	
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
			try {
				Thread.sleep(1000 / TICKS_PER_SECOND);
				_gameTicks++;
				_userInputController.tick();
				_spawnController.tick();
				
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

}
