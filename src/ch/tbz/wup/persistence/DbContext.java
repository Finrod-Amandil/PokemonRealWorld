package ch.tbz.wup.persistence;

import java.util.List;

import ch.tbz.wup.models.Area;
import ch.tbz.wup.models.PokemonSpecies;

public class DbContext implements IDbContext {
	PokemonDbContext pokemonDbContext = new PokemonDbContext();
	GameDataDbContext gameDataDbContext = new GameDataDbContext();
	
	@Override
	public List<PokemonSpecies> getAllPokemon() {
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
}
