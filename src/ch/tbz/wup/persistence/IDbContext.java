package ch.tbz.wup.persistence;

import java.util.List;

import ch.tbz.wup.models.Area;
import ch.tbz.wup.models.PokemonSpecies;

public interface IDbContext {
	
	public List<PokemonSpecies> getAllPokemon();
	public List<Area> getAllAreas();
	public void saveAreas(List<Area> areas);

}
