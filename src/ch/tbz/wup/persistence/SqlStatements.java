package ch.tbz.wup.persistence;

/**
 * Helper class to store larger SQL queries.
 */
public class SqlStatements {
	
	/**
	 * SQL for loading all Pokémon species with their GERMAN name, id and types.
	 */
	public static final String GET_POKEMON = "SELECT pokemon_species.id AS 'id', pokemon_species_names.name AS 'name', type1.type_name AS 'type1', type2.type_name AS 'type2'" +
			"FROM pokemon_species " + 
			"JOIN pokemon_species_names ON pokemon_species_names.pokemon_species_id = pokemon_species.id " + 
			"JOIN language_names lang1 ON lang1.language_id = pokemon_species_names.local_language_id " +  
			"JOIN " + 
			"(SELECT pokemon_species.id AS pokemon_id, type_names.name AS type_name FROM pokemon_species " + 
			"JOIN pokemon_types ON pokemon_types.pokemon_id = pokemon_species.id " + 
			"JOIN type_names ON type_names.type_id = pokemon_types.type_id " + 
			"JOIN language_names lang2 ON lang2.language_id = type_names.local_language_id " + 
			"WHERE pokemon_types.slot = 1 " + 
			"AND lang2.name = 'German') AS type1 " + 
			"ON pokemon_species.id = type1.pokemon_id " + 
			"LEFT JOIN " + 
			"(SELECT pokemon_species.id AS pokemon_id, type_names.name AS type_name FROM pokemon_species " + 
			"JOIN pokemon_types ON pokemon_types.pokemon_id = pokemon_species.id " + 
			"JOIN type_names ON type_names.type_id = pokemon_types.type_id " + 
			"JOIN language_names lang3 ON lang3.language_id = type_names.local_language_id " + 
			"WHERE pokemon_types.slot = 2 " + 
			"AND lang3.name = 'German') AS type2 " + 
			"ON pokemon_species.id = type2.pokemon_id " + 
			"WHERE lang1.name = 'German'";
}
