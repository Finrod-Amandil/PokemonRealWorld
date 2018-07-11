package ch.tbz.wup.models;

import java.awt.Point;
import java.time.LocalDateTime;

import javax.swing.JLabel;

/**
 * Represents a concrete Pok�mon which is currently present on the map.
 */
public class SpawnedPokemon {
	private PokemonSpecies _species;
	private Point _rc_location;
	private LocalDateTime _spawnTime;
	private JLabel _sprite;
	
	/**
	 * Creates a new Pok�mon.
	 * 
	 * @param species  The species the Pok�mon is of.
	 * @param spawnPoint  Where the Pok�mon appeared.
	 * @param sprite  The Swing Component for the graphical representation.
	 */
	public SpawnedPokemon(PokemonSpecies species, Point spawnPoint, JLabel sprite) {
		_species = species;
		_rc_location = spawnPoint;
		_spawnTime = LocalDateTime.now();
		_sprite = sprite;
	}

	/**
	 * @return  Of which species this Pok�mon is.
	 */
	public PokemonSpecies getSpecies() {
		return _species;
	}

	/**
	 * @return  Where the Pok�mon is currently located.
	 */
	public Point getLocation() {
		return _rc_location;
	}

	/**
	 * @return  When the Pok�mon has appeared, as LocalDateTime.
	 */
	public LocalDateTime getSpawnTime() {
		return _spawnTime;
	}
	
	/**
	 * @return  The Swing Component representing the Pok�mon graphically.
	 */
	public JLabel getSprite() {
		return _sprite;
	}
}
