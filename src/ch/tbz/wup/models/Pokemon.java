package ch.tbz.wup.models;

public class Pokemon {
	private PokemonSpecies _species;
	
	public Pokemon(PokemonSpecies species) {
		_species = species;
	}
	
	public PokemonSpecies getSpecies() {
		return _species;
	}
}