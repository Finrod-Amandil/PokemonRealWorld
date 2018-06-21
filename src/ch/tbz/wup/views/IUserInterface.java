package ch.tbz.wup.views;

import java.awt.Component;
import java.awt.Point;
import java.awt.Rectangle;

import ch.tbz.wup.viewmodels.MainViewModel;

public interface IUserInterface {
	public void init(MainViewModel viewModel);
	public void show();
	public Component getWindow();
	public void moveView(int dX, int dY);
	public void showImage(String filePath, Rectangle dimensions, Point rc_point, Point rc_center);
}
