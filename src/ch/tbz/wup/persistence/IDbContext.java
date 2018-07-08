package ch.tbz.wup.persistence;

import java.util.List;
import java.util.Map;

import ch.tbz.wup.models.Area;
import ch.tbz.wup.models.PokemonSpecies;
import ch.tbz.wup.models.Spawn;

public interface IDbContext {
	
	public Map<Integer, PokemonSpecies> getAllPokemon();
	public List<Area> getAllAreas();
	public List<Spawn> getAllSpawns();
	public void saveAreas(List<Area> areas);

}
