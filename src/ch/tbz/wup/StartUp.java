package ch.tbz.wup;

import ch.tbz.wup.controllers.Controller;
import ch.tbz.wup.models.Player;
import ch.tbz.wup.views.IUserInterface;
import ch.tbz.wup.views.MainView;

public class StartUp {
	public static void configure() {
		IUserInterface userInterface = MainView.getInstance();
		Player player = Player.getInstance();
		
		Controller controller = new Controller(userInterface, player);
		controller.init();
	}
	
}