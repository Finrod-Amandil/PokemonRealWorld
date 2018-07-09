package ch.tbz.wup.models;

import java.util.ArrayList;
import java.util.List;

public class Pokedex {
	private List<PokedexEntry> _entries;
	
	public Pokedex(List<PokemonSpecies> pokemonSpecies) {
		_entries = new ArrayList<PokedexEntry>();
		
		for (PokemonSpecies pokemon : pokemonSpecies) {
			_entries.add(new PokedexEntry(pokemon));
		}
	}
	
	public List<PokedexEntry> getPokemon() {
		return new ArrayList<PokedexEntry>(_entries);
	}
	
	public void discover(PokemonSpecies species) {
		for (PokedexEntry entry : _entries) {
			if (entry.getSpecies().getId() == species.getId()) {
				entry.discover();
				break;
			}
		}
	}
}
