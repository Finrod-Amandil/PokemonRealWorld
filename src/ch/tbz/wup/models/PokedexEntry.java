package ch.tbz.wup.models;

/**
 * Container class for a single entry in a Pokédex. Groups a Pokémon species with
 * the information whether it was discovered yet.
 */
public class PokedexEntry {
	private PokemonSpecies _species;
	private boolean _isDiscovered;
	
	/**
	 * Initiates a new Entry with the species and status Not Discovered.
	 * 
	 * @param species  The species of this entry.
	 */
	public PokedexEntry(PokemonSpecies species) {
		_species = species;
		_isDiscovered = false;
	}
	
	/**
	 * @return  The species of this entry.
	 */
	public PokemonSpecies getSpecies() {
		return _species;
	}
	
	/**
	 * @return  Whether the species has been discovered.
	 */
	public boolean isDiscovered() {
		return _isDiscovered;
	}
	
	/**
	 * Marks the species as discovered.
	 */
	public void discover() {
		_isDiscovered = true;
	}
}
