package ch.tbz.wup;

import java.awt.Point;

public class Player {
	private static Player _instance;
	
	public static Player getInstance() {
		return _instance == null ? new Player() : _instance;
	}
	
	private Point _location = new Point(0, 0);
	
	private Player() {}
	
	public void setLocation(Point location) {
		_location = location;
	}
	
	public Point getLocation() {
		return _location;
	}

}
