package ch.tbz.wup.models;

public class Spawn {
	
	private PokemonSpecies _species;
	private int _weight;
	
	public Spawn(PokemonSpecies species, int weight) {
		_species = species;
		_weight = weight;
	}
	
	public PokemonSpecies getSpecies() {
		return _species;
	}
	public void setSpecies(PokemonSpecies species) {
		this._species = species;
	}
	public int getWeight() {
		return _weight;
	}
	public void setWeight(int weight) {
		this._weight = weight;
	}
}
