package ch.tbz.wup.ui;

import java.awt.Component;

import ch.tbz.wup.Player;
import ch.tbz.wup.Region;

public interface IUserInterface {
	public void init(Player player, Region region);
	public void show();
	public Component getWindow();
	public void moveView(int dX, int dY);
}
