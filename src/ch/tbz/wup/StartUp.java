package ch.tbz.wup;

import java.util.List;

import ch.tbz.wup.controllers.MainController;
import ch.tbz.wup.models.Area;
import ch.tbz.wup.models.Player;
import ch.tbz.wup.models.Pokedex;
import ch.tbz.wup.persistence.DbContext;
import ch.tbz.wup.persistence.IDbContext;
import ch.tbz.wup.views.IUserInterface;
import ch.tbz.wup.views.MainView;

/**
 * Sets up the game. Loads necessary data from Database, instantiates
 * segregated classes and passes them to the controller. Then initializes
 * the Controller.
 */
public class StartUp {
	public static void configure() {
		
		//Instantiate segregated classes
		IUserInterface userInterface = MainView.getInstance();
		IDbContext dbContext = new DbContext();
		Player player = Player.getInstance();
		
		//Load data from DB
		List<Area> allAreas = dbContext.getAllAreas();
		Pokedex pokedex = dbContext.getPokedex(1);
		player.setPokedex(pokedex);
		
		//Create and initialize controller
		MainController controller = new MainController(player, userInterface, allAreas);
		controller.init();
	}
	
}