package ch.tbz.wup.persistence;

import java.util.List;
import java.util.Map;

import ch.tbz.wup.models.Area;
import ch.tbz.wup.models.Pokedex;
import ch.tbz.wup.models.PokemonSpecies;
import ch.tbz.wup.models.Spawn;

/**
 * Access point for all database queries. Delegates the various queries
 * to specialised, db-specific classes.
 */
public class DbContext implements IDbContext {
	PokemonDbContext pokemonDbContext = new PokemonDbContext();
	GameDataDbContext gameDataDbContext = new GameDataDbContext();
	
	/**
	 * @return  all Pokémon (up until 7th generation) as Map with
	 * National Pokédex Number as key.
	 */
	@Override
	public Map<Integer, PokemonSpecies> getAllPokemon() {
		return pokemonDbContext.getPokemonFromDatabase();
	}
	
	/**
	 * Serialises and stores the given areas to DB.
	 * 
	 * @param areas  Areas including nested objects.
	 */
	@Override
	public void saveAreas(List<Area> areas) {
		gameDataDbContext.writeAreasToDatabase(areas);
	}
	
	/**
	 * @return  De-serialised, pre-built Areas from DB, including nested objects.
	 */
	@Override
	public List<Area> getAllAreas() {
		//TODO region filter
		return gameDataDbContext.getAreasFromDatabase();
	}

	/**
	 * @return  Spawn data from DB.
	 */
	@Override
	public List<Spawn> getAllSpawns() {
		return gameDataDbContext.getSpawnsFromDatabase(pokemonDbContext.getPokemonFromDatabase());
	}

	/**
	 * @return  the Pokédex contents of the given region.
	 */
	@Override
	public Pokedex getPokedex(int regionId) {
		return gameDataDbContext.getPokedexFromDatabase(regionId, pokemonDbContext.getPokemonFromDatabase());
	}
}
