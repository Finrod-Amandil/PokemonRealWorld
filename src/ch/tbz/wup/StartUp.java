package ch.tbz.wup;

import java.util.List;
import java.util.Map;

import ch.tbz.wup.controllers.MainController;
import ch.tbz.wup.models.Area;
import ch.tbz.wup.models.Player;
import ch.tbz.wup.models.PokemonSpecies;
import ch.tbz.wup.persistence.DbContext;
import ch.tbz.wup.persistence.IDbContext;
import ch.tbz.wup.views.IUserInterface;
import ch.tbz.wup.views.MainView;

public class StartUp {
	public static void configure() {
		IUserInterface userInterface = MainView.getInstance();
		IDbContext dbContext = new DbContext();
		Player player = Player.getInstance();
		
		//Load data from DB
		List<Area> allAreas = dbContext.getAllAreas();
		Map<Integer, PokemonSpecies> allPokemon = dbContext.getAllPokemon();
		
		MainController controller = new MainController(player, userInterface, allAreas, allPokemon);
		controller.init();
	}
	
}