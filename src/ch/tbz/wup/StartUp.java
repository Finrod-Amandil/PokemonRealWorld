package ch.tbz.wup;

import ch.tbz.wup.controllers.MainController;
import ch.tbz.wup.models.Player;
import ch.tbz.wup.views.IUserInterface;
import ch.tbz.wup.views.MainView;

public class StartUp {
	public static void configure() {
		IUserInterface userInterface = MainView.getInstance();
		Player player = Player.getInstance();
		
		MainController controller = new MainController(player, userInterface);
		controller.init();
	}
	
}