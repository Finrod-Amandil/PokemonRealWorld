package ch.tbz.wup;

import java.awt.Point;
import java.util.List;

public class PokemonStarter {

	public static void main(String[] args) {
		//CoordinateConverter.convertPointFromWGS84toLV03(8, 20);
		
		//KmlParser.readAreas("C:\\Users\\severin.zahler\\Desktop\\test.kml");
		
		Player player = Player.getInstance();
		player.setLocation(new Point(683570, 246830));
		Region region = new Region("zurich", null);
		
		//MainUi ui = new MainUi(player, region);
		//ui.show();
		
		DbContext context = new DbContext();
		List<PokemonSpecies> all_species = context.getAllPokemon();
		for (PokemonSpecies species: all_species) {
			if (species.getType2() == null) {
				System.out.println(species.getName() + ", " + species.getType1().getName());
			} else {
				System.out.println(species.getName() + ", " + species.getType1().getName() + ", " + species.getType2().getName());
			}
		}
	}
}
