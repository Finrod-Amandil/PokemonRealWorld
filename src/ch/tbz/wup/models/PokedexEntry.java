package ch.tbz.wup.models;

public class PokedexEntry {
	private PokemonSpecies _species;
	private boolean _isDiscovered;
	
	public PokedexEntry(PokemonSpecies species) {
		_species = species;
		_isDiscovered = false;
	}
	
	public PokemonSpecies getSpecies() {
		return _species;
	}
	
	public boolean isDiscovered() {
		return _isDiscovered;
	}
	
	public void discover() {
		_isDiscovered = true;
	}
}
