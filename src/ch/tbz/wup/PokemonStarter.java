package ch.tbz.wup;

import java.awt.Point;

import ch.tbz.wup.ui.MainUi;

public class PokemonStarter {

	public static void main(String[] args) {
		//CoordinateConverter.convertPointFromWGS84toLV03(8, 20);
		
		//KmlParser.readAreas("C:\\Users\\severin.zahler\\Desktop\\test.kml");
		
		Player player = Player.getInstance();
		player.setLocation(new Point(683570, 246830));
		Region region = new Region("zurich", null);
		
		MainUi ui = MainUi.getInstance();
		ui.init(player, region);
		ui.show();
		
		UserInputController controller = new UserInputController(ui);
		
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
