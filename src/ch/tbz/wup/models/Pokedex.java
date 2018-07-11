package ch.tbz.wup.models;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * A Pok�dex is a collection book for Pok�mon species.
 */
public class Pokedex {
	private Map<Integer, PokedexEntry> _entries; //List of all species of this Pok�dex, indexed by the species' ID.
	
	/**
	 * Instantiates and initialises a new Pok�dex with the given species. The order
	 * of the species will be preserved from the given source.
	 * 
	 * @param pokemonSpecies  A sorted list of all species to be added to the Pok�dex
	 */
	public Pokedex(List<PokemonSpecies> pokemonSpecies) {
		_entries = new LinkedHashMap<Integer, PokedexEntry>();
		
		for (PokemonSpecies pokemon : pokemonSpecies) {
			_entries.put(pokemon.getId(), new PokedexEntry(pokemon));
		}
	}
	
	/**
	 * @return  All entries of this Pok�dex as fixed-order LinkedHashMap.
	 */
	public Map<Integer, PokedexEntry> getPokemon() {
		return new LinkedHashMap<Integer, PokedexEntry>(_entries);
	}
	
	/**
	 * Marks the given species as discovered in the Pok�dex.
	 * 
	 * @param species  The discovered species.
	 */
	public void discover(PokemonSpecies species) {
		if (_entries.containsKey(species.getId())) {
			_entries.get(species.getId()).discover();
		}
	}
	
	/**
	 * @param species  The species of interest.
	 * @return  Whether the given species has already been discovered.
	 */
	public boolean isDiscovered(PokemonSpecies species) {
		if (_entries.containsKey(species.getId())) {
			return _entries.get(species.getId()).isDiscovered();
		}
		return false;
	}
	
	
	/**
	 * Checks all entries of the Pok�dex for whether they have been discovered.
	 * 
	 * @return  Whether all entries in the Pok�dex have been discovered.
	 */
	public boolean isComplete() {
		for (PokedexEntry entry : _entries.values()) {
			if (!entry.isDiscovered()) {
				return false;
			}
		}
		return true;
	}
}
