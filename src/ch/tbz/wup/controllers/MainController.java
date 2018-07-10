package ch.tbz.wup.controllers;

import java.util.List;

import ch.tbz.wup.IObservable;
import ch.tbz.wup.IObserver;
import ch.tbz.wup.models.Area;
import ch.tbz.wup.models.Player;
import ch.tbz.wup.models.PlayerStateChange;
import ch.tbz.wup.views.IUserInterface;

/**
 * Controller class which delegates global responsibilities and information.
 * Especially contains a ticker method which is the global game pacer.
 */
public class MainController implements IObserver {
	/**
	 * How many ticks are emitted each second.
	 */
	public static final long TICKS_PER_SECOND = 20;
	
	private UserInputController _userInputController;
	private SpawnController _spawnController;
	
	private long _gameTicks = 0; //The total elapsed game ticks since the game started.
	private boolean _isPaused = false;
	private boolean _isGameWon = false;
	
	private Player _player;
	private IUserInterface _userInterface;
	private List<Area> _allAreas;
	
	/**
	 * Instantiates the MainController. Requires a set of parameters injected
	 * through inversion of control.
	 * 
	 * @param player  The Singleton player of the game.
	 * @param userInterface  The Singleton user interface.
	 * @param allAreas  A list of all areas of the current region.
	 */
	public MainController(Player player, IUserInterface userInterface, List<Area> allAreas) {
		_player = player;
		_userInterface = userInterface;
		_allAreas = allAreas;
		_userInputController = new UserInputController(_player, _userInterface);
		_spawnController = new SpawnController(_player, _userInterface, _allAreas);
	}
	
	/**
	 * Initialises the controller and the underlying specialised controllers.
	 * Starts the game ticker and thus the game.
	 */
	public void init() {
		_userInputController.init();
		_player.addObserver(this);
		_userInputController.addObserver(this);
		startTicker();
	}
	
	@Override
	public void onObservableChanged(IObservable observable, int changeId) {
		if (observable instanceof Player 
				&& changeId == PlayerStateChange.POKEDEX_CHANGED.ordinal() 
				&& !_isGameWon) {
			if (_player.isPokedexComplete()) {
				_isGameWon = true;
				System.out.println("Congratulations, you win!");
			}
		}
		
		else if (observable instanceof UserInputController) {
			if (changeId == UserInputControllerStateChange.GAME_PAUSED.ordinal()) {
				_isPaused = true;
			}
			else if (changeId == UserInputControllerStateChange.GAME_UNPAUSED.ordinal()) {
				_isPaused = false;
			}
		}
	}
	
	//Ticker method that runs throughout the game. Calls the tick method of the
	//specialised controllers.
	private void startTicker() {
		int tickDelay = (int)(1000 / TICKS_PER_SECOND);
		while (true) {
			try {
				Thread.sleep(tickDelay);
				if (!_isPaused) {
					_gameTicks++;
					_userInputController.tick(_gameTicks);
					_spawnController.tick(_gameTicks);
				}
				
			} catch (InterruptedException e) {
				System.out.println("An unknown error occurred: " + e.getMessage() + "\nPlease try to restart the game.");
				e.printStackTrace();
			}
		}
	}
}
