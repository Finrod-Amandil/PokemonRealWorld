package ch.tbz.wup;

/**
 * Interface for classes that want to observe an observable object and get notified
 * when the object changes state. Use the addObserver-method to subscribe.
 */
public interface IObserver {
	
	/**
	 * Method which is called by the observable when it changes state. Defines
	 * the Observer-specific reaction to the change.
	 * 
	 * @param observable  The object that changed state.
	 */
	void onObservableChanged(IObservable observable);
}
