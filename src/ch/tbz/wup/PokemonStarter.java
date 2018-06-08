package ch.tbz.wup;

public class PokemonStarter {

	public static void main(String[] args) {
		//CoordinateConverter.convertPointFromWGS84toLV03(8, 20);
		
		//KmlParser.readAreas("C:\\Users\\severin.zahler\\Desktop\\test.kml");
		
		StartUp.configure();
		
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
