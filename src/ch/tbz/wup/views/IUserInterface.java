package ch.tbz.wup.views;

import java.awt.Component;
import java.awt.Point;
import java.awt.Rectangle;

import javax.swing.JLabel;

import ch.tbz.wup.viewmodels.MainViewModel;
import ch.tbz.wup.viewmodels.PokedexViewModel;

public interface IUserInterface {
	public void init(MainViewModel viewModel);
	public void show();
	public Component getWindow();
	public void moveView(int dX, int dY);
	public JLabel showImage(String filePath, Rectangle dimensions, Point rc_point, Point rc_center);
	public void hideImage(JLabel label);
	public void showPokedex(PokedexViewModel pokedex);
	public void hidePokedex();
}
