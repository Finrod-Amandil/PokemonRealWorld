package ch.tbz.wup;

public class PokemonStarter {

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
