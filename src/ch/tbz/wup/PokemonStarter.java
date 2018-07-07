package ch.tbz.wup;

import java.util.List;

import ch.tbz.wup.models.PokemonSpecies;
import ch.tbz.wup.persistence.DbContext;

public class PokemonStarter {

	public static void main(String[] args) {
		StartUp.configure();
		
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
