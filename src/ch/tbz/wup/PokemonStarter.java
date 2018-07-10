package ch.tbz.wup;

/**
 * Entry point for the application.
 */
public class PokemonStarter {

	/**
	 * Entry point for the application. Initiates loading the game by calling StartUp.configure().
	 * 
	 * @param args  command line arguments.
	 */
	public static void main(String[] args) {
		StartUp.configure();
		
		/*Map<Integer, PokemonSpecies> all_species = context.getAllPokemon();
		for (int id : all_species.keySet()) {
			PokemonSpecies species = all_species.get(id);
			if (species.getType2() == null) {
				System.out.println(id + ": " + species.getName() + ", " + species.getType1().getName());
			} else {
				System.out.println(id + ": " + species.getName() + ", " + species.getType1().getName() + ", " + species.getType2().getName());
			}
		}*/
	}
}
