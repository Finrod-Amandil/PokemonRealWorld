package ch.tbz.wup;

public interface IObservable {
	public void addObserver(IObserver observer);
	public void removeObserver(IObserver observer);
	public void notifyObservers();
}
