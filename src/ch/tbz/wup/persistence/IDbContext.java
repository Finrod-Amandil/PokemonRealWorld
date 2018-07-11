package ch.tbz.wup.persistence;

import java.util.List;
import java.util.Map;

import ch.tbz.wup.models.Area;
import ch.tbz.wup.models.Pokedex;
import ch.tbz.wup.models.PokemonSpecies;
import ch.tbz.wup.models.Spawn;

/**
 * Interface containing all methods required to fetch DB data for the Pokemon Real World project
 */
public interface IDbContext {
	/**
	 * @return  All Pok�mon species with basic information each
	 */
	public Map<Integer, PokemonSpecies> getAllPokemon();
	
	/**
	 * @return  All spawn areas including default areas.
	 */
	public List<Area> getAllAreas();
	
	/**
	 * @return  All Spawns (disconnected, but with areaId set to corresponding Area)
	 */
	public List<Spawn> getAllSpawns();
	
	/**
	 * @param regionId  The region Id for which the Pok�dex should be loaded
	 * @return  The Pok�dex with underlying Pok�mon species for the specified region.
	 */
	public Pokedex getPokedex(int regionId);
	
	/**
	 * Stores Area-datA to DB
	 * 
	 * @param areas  The areas to save.
	 */
	public void saveAreas(List<Area> areas);

}
