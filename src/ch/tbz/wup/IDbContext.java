package ch.tbz.wup;

import java.util.List;

import ch.tbz.wup.models.PokemonSpecies;

public interface IDbContext {
	
	public List<PokemonSpecies> getAllPokemon();

}
