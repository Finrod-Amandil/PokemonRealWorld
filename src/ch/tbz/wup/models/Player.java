package ch.tbz.wup.models;

import java.awt.Point;
import java.util.ArrayList;
import java.util.List;

import ch.tbz.wup.IObservable;
import ch.tbz.wup.IObserver;

public class Player implements IObservable {
	private static Player _instance;
	
	public static Player getInstance() {
		if (_instance == null) {
			_instance = new Player();
		}
		return _instance;
	}
	
	private List<IObserver> _observers = new ArrayList<IObserver>();
	private Point _location = new Point(0, 0);
	
	private Player() {}
	
	public void setLocation(Point location) {
		_location = location;
		notifyObservers();
	}
	
	public Point getLocation() {
		return _location;
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
	public void notifyObservers() {
		for (IObserver observer : _observers) {
			observer.onObservableChanged(this);
		}
	}
}
