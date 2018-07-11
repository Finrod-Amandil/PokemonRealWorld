package ch.tbz.wup.models;

import java.awt.Point;
import java.util.ArrayList;
import java.util.List;

import ch.tbz.wup.IObservable;
import ch.tbz.wup.IObserver;

/**
 * The Player Singleton is the protagonist of the game. Communicates changes on
 * himself through the Observer pattern.
 */
public class Player implements IObservable {
	private static Player _instance; //Instance holder for Singleton-Pattern
	
	/**
	 * If the player singleton was not yet created, creates it.
	 * 
	 * @return  The Player Singleton.
	 */
	public static Player getInstance() {
		if (_instance == null) {
			_instance = new Player();
		}
		return _instance;
	}
	
	private List<IObserver> _observers = new ArrayList<IObserver>();
	private Point _location = new Point(0, 0);
	private Pokedex _pokedex;
	
	//Private constructor for Singleton pattern
	private Player() {}
	
	/**
	 * Associates a Pokédex with this player.
	 * 
	 * @param pokedex  The pre-filled Pokédex.
	 */
	public void setPokedex(Pokedex pokedex) {
		_pokedex = pokedex;
	}
	
	/**
	 * @return  The Pokédex associated with this instance. May be null if not set yet.
	 */
	public Pokedex getPokedex() {
		return _pokedex;
	}
	
	/**
	 * Marks the given species as "discovered" in the Pokédex, unless it was discovered before.
	 * Triggers notifications to all observers.
	 * 
	 * @param species  The discovered species.
	 */
	public void addPokemonToPokedex(PokemonSpecies species) {
		if (!_pokedex.isDiscovered(species)) {
			_pokedex.discover(species);
			notifyObservers(PlayerStateChange.POKEDEX_CHANGED.ordinal());
		}
	}
	
	/**
	 * @return Whether the Pokédex is complete, i.e. all Pokémon species have been encountered.
	 */
	public boolean isPokedexComplete() {
		return _pokedex.isComplete();
	}
	
	/**
	 * Updates the player's location. Triggers notifications to all observers.
	 * 
	 * @param location  The new location.
	 */
	public void setLocation(Point location) {
		_location = location;
		notifyObservers(PlayerStateChange.LOCATION_CHANGED.ordinal());
	}
	
	/**
	 * @return The player's location.
	 */
	public Point getLocation() {
		return _location;
	}

	/**
	 * Adds an observer to the list of objects to notify on state change.
	 * 
	 * @param observer  The Observer to add.
	 */
	@Override
	public void addObserver(IObserver observer) {
		_observers.add(observer);
	}

	/**
	 * Unsubscribes an observer so that no more notifications are sent to it.
	 * 
	 * @param observer  The Observer to remove.
	 */
	@Override
	public void removeObserver(IObserver observer) {
		_observers.remove(observer);
	}

	/**
	 * Notifies all subscribed observers about a state change.
	 * 
	 * @param changeId  An index indicating which state has changed.
	 */
	@Override
	public void notifyObservers(int changeId) {
		for (IObserver observer : _observers) {
			observer.onObservableChanged(this, changeId);
		}
	}
}
