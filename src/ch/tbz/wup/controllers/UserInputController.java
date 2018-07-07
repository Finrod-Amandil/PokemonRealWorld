package ch.tbz.wup.controllers;

import java.awt.Point;
import java.awt.Rectangle;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import ch.tbz.wup.models.Player;
import ch.tbz.wup.models.Region;
import ch.tbz.wup.viewmodels.MainViewModel;
import ch.tbz.wup.views.IUserInterface;
import ch.tbz.wup.views.Key;

public class UserInputController implements KeyListener {
	private Player _player;
	private IUserInterface _userInterface;
	private Map<Key, Boolean> _pressedKeys = new HashMap<Key, Boolean>();
	
	public UserInputController(Player player, IUserInterface userInterface) {
		_player = player;
		_userInterface = userInterface;
	}
	
	public void init() {
		_player.setLocation(new Point(683570, 246830));
		
		Region region = new Region("zurich", null);
		
		MainViewModel viewModel = new MainViewModel();
		viewModel.playerLocation = _player.getLocation();
		viewModel.regionBounds = region.getBounds();
		viewModel.regionName = region.getName();
		
		_userInterface.init(viewModel);
		_userInterface.getWindow().addKeyListener(this);
		_userInterface.show();
		
		_pressedKeys.put(Key.UP, false);
		_pressedKeys.put(Key.DOWN, false);
		_pressedKeys.put(Key.RIGHT, false);
		_pressedKeys.put(Key.LEFT, false);
	}
	
	@Override
	public void keyTyped(KeyEvent e) {}

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
		default:
			System.out.println("Pressed something else...");
		}
	}
	
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
		}
	}
	
	public void movePlayer() {
		if (_pressedKeys.get(Key.UP)) {
			_userInterface.moveView(0, 1);
		}
		if (_pressedKeys.get(Key.DOWN)) {
			_userInterface.moveView(0, -1);
		}
		if (_pressedKeys.get(Key.RIGHT)) {
			_userInterface.moveView(1, 0);
		}
		if (_pressedKeys.get(Key.LEFT)) {
			_userInterface.moveView(-1, 0);
		}
	}
}
