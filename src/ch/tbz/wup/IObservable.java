package ch.tbz.wup;

/**
 * Interface for classes that can be observed using the Observer pattern.
 * Specifies methods to add, remove and notify Observers.
 */
public interface IObservable {
	/**
	 * Adds an observer that is to be notified on change.
	 * 
	 * @param observer  An instance of a class implementing IObserver.
	 */
	public void addObserver(IObserver observer);
	
	/**
	 * Removes an observer so that it is no longer notified.
	 * 
	 * @param observer  An instance of a class implementing IObserver.
	 */
	public void removeObserver(IObserver observer);
	
	/**
	 * Calls the onObservableChanged() method of each active observer on this object.
	 */
	public void notifyObservers(int changeIndex);
}
