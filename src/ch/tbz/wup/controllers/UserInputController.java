package ch.tbz.wup.controllers;

import java.awt.Point;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import ch.tbz.wup.IObservable;
import ch.tbz.wup.IObserver;
import ch.tbz.wup.models.Player;
import ch.tbz.wup.models.PokedexEntry;
import ch.tbz.wup.models.Region;
import ch.tbz.wup.viewmodels.MainViewModel;
import ch.tbz.wup.viewmodels.PokedexEntryViewModel;
import ch.tbz.wup.viewmodels.PokedexViewModel;
import ch.tbz.wup.views.IUserInterface;
import ch.tbz.wup.views.Key;

/**
 * Controller that handles all actions related to user inputs. Implements KeyListener interface
 * to receive KeyEvents.
 */
public class UserInputController implements KeyListener, IObservable {
	private Player _player;
	private IUserInterface _userInterface;
	
	private boolean _isPokedexOpen = false;
	
	private List<IObserver> _observers = new ArrayList<IObserver>();
	
	//Overview over all directional keys and whether they are currently pressed.
	private Map<Key, Boolean> _pressedKeys = new HashMap<Key, Boolean>();
	
	/**
	 * Instantiates the controller with the passed objects.
	 * 
	 * @param player  The Singleton player.
	 * @param userInterface  The Singleton user interface.
	 */
	public UserInputController(Player player, IUserInterface userInterface) {
		_player = player;
		_userInterface = userInterface;
	}
	
	/**
	 * Initiates the controller. Sets up the view and adds itself as KeyListener.
	 */
	public void init() {
		_player.setLocation(new Point(683570, 246830));
		
		Region region = new Region("zurich", null);
		
		//Set up UI
		MainViewModel viewModel = new MainViewModel();
		viewModel.playerLocation = _player.getLocation();
		viewModel.regionBounds = region.getBounds();
		viewModel.regionName = region.getName();
		
		_userInterface.init(viewModel);
		_userInterface.getWindow().addKeyListener(this);
		_userInterface.show();
		
		//Initialise keys
		_pressedKeys.put(Key.UP, false);
		_pressedKeys.put(Key.DOWN, false);
		_pressedKeys.put(Key.RIGHT, false);
		_pressedKeys.put(Key.LEFT, false);
		
		//Move player to start position.
		movePlayer();
	}
	
	/**
	 * Moves the player every tick, if a key is pressed.
	 * 
	 * @param totalTicks
	 */
	public void tick(long totalTicks) {
		movePlayer();
	}
	
	/**
	 * (No Action on keyTyped)
	 */
	@Override
	public void keyTyped(KeyEvent e) {}

	/**
	 * If a directional key is pressed, updates the corresponding entry
	 * in the key tracking Map to pressed.
	 */
	@Override
	public void keyPressed(KeyEvent e) {
		switch (e.getKeyCode()) {
		case KeyEvent.VK_UP:
			_pressedKeys.put(Key.UP, true);
			break;
		case KeyEvent.VK_DOWN:
			_pressedKeys.put(Key.DOWN, true);
			break;
		case KeyEvent.VK_RIGHT:
			_pressedKeys.put(Key.RIGHT, true);
			break;
		case KeyEvent.VK_LEFT:
			_pressedKeys.put(Key.LEFT, true);
			break;
		}
	}
	
	/**
	 * If a directional key is released, updates the corresponding entry
	 * in the key tracking Map to released.
	 */
	@Override
	public void keyReleased(KeyEvent e) {
		switch (e.getKeyCode()) {
		case KeyEvent.VK_UP:
			_pressedKeys.put(Key.UP, false);
			break;
		case KeyEvent.VK_DOWN:
			_pressedKeys.put(Key.DOWN, false);
			break;
		case KeyEvent.VK_RIGHT:
			_pressedKeys.put(Key.RIGHT, false);
			break;
		case KeyEvent.VK_LEFT:
			_pressedKeys.put(Key.LEFT, false);
			break;
		case KeyEvent.VK_P:
			togglePokedex();
			break;
		}
	}
	
	//Checks which directional keys are pressed and moves the player one unit into the corresponding
	//directions.
	private void movePlayer() {
		if (_pressedKeys.get(Key.UP)) {
			_userInterface.moveView(0, 1);
			_player.setLocation(new Point(_player.getLocation().x, _player.getLocation().y + 1));
		}
		if (_pressedKeys.get(Key.DOWN)) {
			_userInterface.moveView(0, -1);
			_player.setLocation(new Point(_player.getLocation().x, _player.getLocation().y - 1));
		}
		if (_pressedKeys.get(Key.RIGHT)) {
			_userInterface.moveView(1, 0);
			_player.setLocation(new Point(_player.getLocation().x + 1, _player.getLocation().y));
		}
		if (_pressedKeys.get(Key.LEFT)) {
			_userInterface.moveView(-1, 0);
			_player.setLocation(new Point(_player.getLocation().x - 1, _player.getLocation().y));
		}
	}
	
	private void togglePokedex() {
		if (_isPokedexOpen) {
			_userInterface.hidePokedex();
			notifyObservers(UserInputControllerStateChange.GAME_UNPAUSED.ordinal());
			_isPokedexOpen = false;
			return;
		}
		
		notifyObservers(UserInputControllerStateChange.GAME_PAUSED.ordinal());
		
		PokedexViewModel viewModel = new PokedexViewModel();
		viewModel.entryCount = _player.getPokedex().getPokemon().size();
		
		for (PokedexEntry entry : _player.getPokedex().getPokemon().values()) {
			PokedexEntryViewModel entryViewModel = new PokedexEntryViewModel();
			entryViewModel.id = entry.getSpecies().getId();
			entryViewModel.isDiscovered = entry.isDiscovered();
			entryViewModel.speciesName = entry.getSpecies().getName();
			entryViewModel.type1 = entry.getSpecies().getType1().getName();
			
			if (entry.getSpecies().getType2() != null) {
				entryViewModel.type2 = entry.getSpecies().getType2().getName();
			}
			else {
				entryViewModel.type2 = "";
			}
			
			viewModel.entries.add(entryViewModel);
		}
		
		_userInterface.showPokedex(viewModel);
		_isPokedexOpen = true;
	}

	@Override
	public void addObserver(IObserver observer) {
		_observers.add(observer);
	}

	@Override
	public void removeObserver(IObserver observer) {
		_observers.remove(observer);
	}

	@Override
	public void notifyObservers(int changeId) {
		for (IObserver observer : _observers) {
			observer.onObservableChanged(this, changeId);
		}
	}
}
