package ch.tbz.wup;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import ch.tbz.wup.ui.IUserInterface;

public class UserInputController implements KeyListener {
	private IUserInterface _userInterface;
	
	public UserInputController(IUserInterface userInterface) {
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
			break;
		case KeyEvent.VK_DOWN:
			System.out.println("Pressed down!");
			break;
		case KeyEvent.VK_RIGHT:
			System.out.println("Pressed right!");
			break;
		case KeyEvent.VK_LEFT:
			System.out.println("Pressed left!");
			break;
		default:
			System.out.println("Pressed something else...");
		}
	}

	@Override
	public void keyReleased(KeyEvent e) {}

}
