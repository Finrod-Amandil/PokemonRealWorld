package ch.tbz.wup.persistence;

import java.util.List;
import java.util.Map;

import ch.tbz.wup.models.Area;
import ch.tbz.wup.models.Pokedex;
import ch.tbz.wup.models.PokemonSpecies;
import ch.tbz.wup.models.Spawn;

public class DbContext implements IDbContext {
	PokemonDbContext pokemonDbContext = new PokemonDbContext();
	GameDataDbContext gameDataDbContext = new GameDataDbContext();
	
	@Override
	public Map<Integer, PokemonSpecies> getAllPokemon() {
		return pokemonDbContext.getPokemonFromDatabase();
	}
	
	@Override
	public void saveAreas(List<Area> areas) {
		gameDataDbContext.writeAreasToDatabase(areas);
	}
	
	@Override
	public List<Area> getAllAreas() {
		return gameDataDbContext.getAreasFromDatabase();
	}

	@Override
	public List<Spawn> getAllSpawns() {
		return gameDataDbContext.getSpawnsFromDatabase(pokemonDbContext.getPokemonFromDatabase());
	}

	@Override
	public Pokedex getPokedex(int regionId) {
		return gameDataDbContext.getPokedexFromDatabase(regionId, pokemonDbContext.getPokemonFromDatabase());
	}
}
