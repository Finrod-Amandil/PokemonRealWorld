package ch.tbz.wup.models;

import java.io.Serializable;

/**
 * A Spawn represents a possibility of a PokémonSpecies to appear within the associated area
 * with a given probability.
 */
public class Spawn implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	private PokemonSpecies _species; //Which species of Pokémon this Spawn can bring forth.
	private int _weight; //The probability of the Spawn happening. Value usually between 1 (rare) and 10 (common), but could be higher
	private int _areaId; //The ID of the single area this Spawn is associated with.
	
	/**
	 * Instantiates a new Spawn object.
	 * 
	 * @param species  The species this Spawn can bring forth.
	 * @param weight  The probability as numerical value relative to other Spawns. 1 = rare, 10 = common. Value can exceed 10 though.
	 * @param areaId  The Id of the single associated Area.
	 */
	public Spawn(PokemonSpecies species, int weight, int areaId) {
		_species = species;
		_weight = weight;
		_areaId = areaId;
	}
	
	/**
	 * @return  The species this Spawn can bring forth.
	 */
	public PokemonSpecies getSpecies() {
		return _species;
	}
	
	/**
	 * @return  The probability of this Spawn.
	 */
	public int getWeight() {
		return _weight;
	}

	/**
	 * @return  The Id of the associated Area.
	 */
	public int getAreaId() {
		return _areaId;
	}
}
