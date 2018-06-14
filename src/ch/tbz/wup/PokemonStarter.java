package ch.tbz.wup;

public class PokemonStarter {

	public static void main(String[] args) {
		StartUp.configure();
		
		/*ICoordinateConverter converter = new WGS84ToLV03Converter();
		IAreaParser parser = new KmlParser(converter);
		
		List<Area> areas = parser.readAreas("C:\\Users\\severin.zahler\\Desktop\\zurich.kml");
		for (Area area : areas) {
			System.out.println("Name = " + area.getName() + " Type = " + area.getType().toString());
		}*/
		
		
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
