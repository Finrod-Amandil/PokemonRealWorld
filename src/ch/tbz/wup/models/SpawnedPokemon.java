package ch.tbz.wup.models;

import java.awt.Point;
import java.time.LocalDateTime;

public class SpawnedPokemon {
	private PokemonSpecies _species;
	private Point _rc_location;
	private LocalDateTime _spawnTime;
	
	public SpawnedPokemon(PokemonSpecies species, Point spawnPoint) {
		_species = species;
		_rc_location = spawnPoint;
		_spawnTime = LocalDateTime.now();
	}
	
	public PokemonSpecies getSpecies() {
		return _species;
	}

	public PokemonSpecies get_species() {
		return _species;
	}

	public Point get_rc_location() {
		return _rc_location;
	}

	public LocalDateTime get_spawnTime() {
		return _spawnTime;
	}
}
