package ch.tbz.wup.models;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class Pokedex {
	private Map<Integer, PokedexEntry> _entries;
	
	public Pokedex(List<PokemonSpecies> pokemonSpecies) {
		_entries = new LinkedHashMap<Integer, PokedexEntry>();
		
		for (PokemonSpecies pokemon : pokemonSpecies) {
			_entries.put(pokemon.getId(), new PokedexEntry(pokemon));
		}
	}
	
	public Map<Integer, PokedexEntry> getPokemon() {
		return new LinkedHashMap<Integer, PokedexEntry>(_entries);
	}
	
	public void discover(PokemonSpecies species) {
		if (_entries.containsKey(species.getId())) {
			_entries.get(species.getId()).discover();
		}
	}
	
	public boolean isDiscovered(PokemonSpecies species) {
		if (_entries.containsKey(species.getId())) {
			return _entries.get(species.getId()).isDiscovered();
		}
		return false;
	}
	
	public boolean isComplete() {
		for (PokedexEntry entry : _entries.values()) {
			if (!entry.isDiscovered()) {
				return false;
			}
		}
		return true;
	}
}
