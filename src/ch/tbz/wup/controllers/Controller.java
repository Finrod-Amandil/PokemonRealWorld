package ch.tbz.wup.controllers;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import ch.tbz.wup.views.IUserInterface;

public class Controller implements KeyListener {
	private IUserInterface _userInterface;
	
	public Controller(IUserInterface userInterface) {
		_userInterface = userInterface;
		_userInterface.getWindow().addKeyListener(this);
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
