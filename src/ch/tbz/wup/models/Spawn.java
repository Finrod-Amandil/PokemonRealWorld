package ch.tbz.wup.models;

import java.io.Serializable;

public class Spawn implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	private PokemonSpecies _species;
	private int _weight;
	private int _areaId;
	
	public Spawn(PokemonSpecies species, int weight, int areaId) {
		_species = species;
		_weight = weight;
		_areaId = areaId;
	}
	
	public PokemonSpecies getSpecies() {
		return _species;
	}
	
	public int getWeight() {
		return _weight;
	}

	public int getAreaId() {
		return _areaId;
	}
}
