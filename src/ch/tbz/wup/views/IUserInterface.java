package ch.tbz.wup.views;

import java.awt.Component;
import java.awt.Point;
import java.awt.Rectangle;

import javax.swing.JLabel;

import ch.tbz.wup.viewmodels.MainViewModel;
import ch.tbz.wup.viewmodels.PokedexViewModel;

/**
 * UserInterface requirements
 */
public interface IUserInterface {
	/**
	 * Initiate the UI with the data given in the viewModel
	 * 
	 * @param viewModel  Data needed for UI initialization.
	 */
	public void init(MainViewModel viewModel);
	
	/**
	 * Show the UI.
	 */
	public void show();
	
	/**
	 * @return  the topmost content container.
	 */
	public Component getWindow();
	
	/**
	 * Display a different location relative to the previous location.
	 * 
	 * @param dX  Location change along x-axis (horizontal)
	 * @param dY  Location change along y-axis (vertical)
	 */
	public void moveView(int dX, int dY);
	
	/**
	 * Displays the specified image file.
	 * 
	 * @param filePath  Path to the image file.
	 * @param dimensions  Dimensions of the image file.
	 * @param rc_point  Where to show image (centered).
	 * @param rc_center  Relative center point.
	 * @return  The Component containing the image.
	 */
	public JLabel showImage(String filePath, Rectangle dimensions, Point rc_point, Point rc_center);
	
	/**
	 * Hide the given component.
	 * 
	 * @param label  Component to hide,
	 */
	public void hideImage(JLabel label);
	
	/**
	 * Display the contents of the given Pokedex.
	 * 
	 * @param pokedex  Pokedex with underlying species.
	 */
	public void showPokedex(PokedexViewModel pokedex);
	
	/**
	 * Hide the pokedex information.
	 */
	public void hidePokedex();
}
