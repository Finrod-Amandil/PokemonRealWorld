package ch.tbz.wup;

import java.awt.Point;

import ch.tbz.wup.controllers.Controller;
import ch.tbz.wup.models.Player;
import ch.tbz.wup.models.Region;
import ch.tbz.wup.views.MainView;
import viewmodels.MainViewModel;

public class PokemonStarter {

	public static void main(String[] args) {
		//CoordinateConverter.convertPointFromWGS84toLV03(8, 20);
		
		//KmlParser.readAreas("C:\\Users\\severin.zahler\\Desktop\\test.kml");
		
		Player player = Player.getInstance();
		player.setLocation(new Point(683570, 246830));
		Region region = new Region("zurich", null);
		
		MainView ui = MainView.getInstance();
		MainViewModel vm = new MainViewModel();
		vm.playerLocation = player.getLocation();
		vm.regionBounds = region.getBounds();
		vm.regionName = region.getName();
		ui.init(vm);
		ui.show();
		
		Controller controller = new Controller(ui);
		
		/*DbContext context = new DbContext();
		List<PokemonSpecies> all_species = context.getAllPokemon();
		for (PokemonSpecies species: all_species) {
			if (species.getType2() == null) {
				System.out.println(species.getName() + ", " + species.getType1().getName());
			} else {
				System.out.println(species.getName() + ", " + species.getType1().getName() + ", " + species.getType2().getName());
			}
		}*/
	}
}
