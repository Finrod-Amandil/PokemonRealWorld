package ch.tbz.wup.models;

import java.awt.Point;
import java.time.LocalDateTime;

import javax.swing.JLabel;

/**
 * Represents a concrete Pokémon which is currently present on the map.
 */
public class SpawnedPokemon {
	private PokemonSpecies _species;
	private Point _rc_location;
	private LocalDateTime _spawnTime;
	private JLabel _sprite;
	
	/**
	 * Creates a new Pokémon.
	 * 
	 * @param species  The species the Pokémon is of.
	 * @param spawnPoint  Where the Pokémon appeared.
	 * @param sprite  The Swing Component for the graphical representation.
	 */
	public SpawnedPokemon(PokemonSpecies species, Point spawnPoint, JLabel sprite) {
		_species = species;
		_rc_location = spawnPoint;
		_spawnTime = LocalDateTime.now();
		_sprite = sprite;
	}

	/**
	 * @return  Of which species this Pokémon is.
	 */
	public PokemonSpecies getSpecies() {
		return _species;
	}

	/**
	 * @return  Where the Pokémon is currently located.
	 */
	public Point getLocation() {
		return _rc_location;
	}

	/**
	 * @return  When the Pokémon has appeared, as LocalDateTime.
	 */
	public LocalDateTime getSpawnTime() {
		return _spawnTime;
	}
	
	/**
	 * @return  The Swing Component representing the Pokémon graphically.
	 */
	public JLabel getSprite() {
		return _sprite;
	}
}
