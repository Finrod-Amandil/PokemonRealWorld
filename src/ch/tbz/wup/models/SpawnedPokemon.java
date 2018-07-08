package ch.tbz.wup.models;

import java.awt.Point;
import java.time.LocalDateTime;

import javax.swing.JLabel;

public class SpawnedPokemon {
	private PokemonSpecies _species;
	private Point _rc_location;
	private LocalDateTime _spawnTime;
	private JLabel _sprite;
	
	public SpawnedPokemon(PokemonSpecies species, Point spawnPoint, JLabel sprite) {
		_species = species;
		_rc_location = spawnPoint;
		_spawnTime = LocalDateTime.now();
		_sprite = sprite;
	}

	public PokemonSpecies getSpecies() {
		return _species;
	}

	public Point getLocation() {
		return _rc_location;
	}

	public LocalDateTime getSpawnTime() {
		return _spawnTime;
	}
	
	public JLabel getSprite() {
		return _sprite;
	}
}
