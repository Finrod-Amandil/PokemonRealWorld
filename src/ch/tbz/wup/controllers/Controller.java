package ch.tbz.wup.controllers;

import java.awt.Point;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import ch.tbz.wup.models.Player;
import ch.tbz.wup.models.Region;
import ch.tbz.wup.views.IUserInterface;
import viewmodels.MainViewModel;

public class Controller implements KeyListener {
	private IUserInterface _userInterface;
	private Player _player;
	
	public Controller(IUserInterface userInterface, Player player) {
		_userInterface = userInterface;
		_player = player;
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
	}
	
	@Override
	public void keyTyped(KeyEvent e) {}

	@Override
	public void keyPressed(KeyEvent e) {
		switch (e.getKeyCode()) {
		case KeyEvent.VK_UP:
			System.out.println("Pressed up!");
			_userInterface.moveView(0, 10);
			break;
		case KeyEvent.VK_DOWN:
			System.out.println("Pressed down!");
			_userInterface.moveView(0, -10);
			break;
		case KeyEvent.VK_RIGHT:
			System.out.println("Pressed right!");
			_userInterface.moveView(10, 0);
			break;
		case KeyEvent.VK_LEFT:
			System.out.println("Pressed left!");
			_userInterface.moveView(-10, 0);
			break;
		default:
			System.out.println("Pressed something else...");
		}
	}

	@Override
	public void keyReleased(KeyEvent e) {}

}
