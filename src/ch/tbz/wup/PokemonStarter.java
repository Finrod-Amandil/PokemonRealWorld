package ch.tbz.wup;

import java.util.List;

import ch.tbz.wup.models.Area;
import ch.tbz.wup.models.PokemonSpecies;
import ch.tbz.wup.persistence.DbContext;
import ch.tbz.wup.persistence.IDbContext;

public class PokemonStarter {

	public static void main(String[] args) {
		IDbContext context = new DbContext();
		List<Area> areas = context.getAllAreas();
		for (Area area : areas) {
			System.out.println("ID = " + area.getId() + " Name = " + area.getName() + " Type = " + area.getType().toString());
		}
		
		StartUp.configure();
		
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
