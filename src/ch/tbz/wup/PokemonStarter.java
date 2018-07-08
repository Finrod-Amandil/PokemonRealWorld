package ch.tbz.wup;

import java.util.List;
import java.util.Map;

import ch.tbz.wup.models.Area;
import ch.tbz.wup.models.PokemonSpecies;
import ch.tbz.wup.persistence.DbContext;
import ch.tbz.wup.persistence.IDbContext;

public class PokemonStarter {

	public static void main(String[] args) {
		IDbContext context = new DbContext();
		List<Area> areas = context.getAllAreas();
		
		//StartUp.configure();
		
		Map<Integer, PokemonSpecies> all_species = context.getAllPokemon();
		for (int id : all_species.keySet()) {
			PokemonSpecies species = all_species.get(id);
			if (species.getType2() == null) {
				System.out.println(id + ": " + species.getName() + ", " + species.getType1().getName());
			} else {
				System.out.println(id + ": " + species.getName() + ", " + species.getType1().getName() + ", " + species.getType2().getName());
			}
		}
	}
}
