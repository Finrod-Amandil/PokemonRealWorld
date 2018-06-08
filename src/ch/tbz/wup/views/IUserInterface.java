package ch.tbz.wup.views;

import java.awt.Component;

import viewmodels.MainViewModel;

public interface IUserInterface {
	public void init(MainViewModel viewModel);
	public void show();
	public Component getWindow();
	public void moveView(int dX, int dY);
}
