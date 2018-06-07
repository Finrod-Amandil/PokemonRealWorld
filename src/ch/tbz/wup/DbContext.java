package ch.tbz.wup;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class DbContext implements IDbContext {
	
	public List<PokemonSpecies> getAllPokemon() {
		return getPokemonFromDatabase();
	}
	
	private List<PokemonSpecies> getPokemonFromDatabase() {
		// create connection for DB access
        Connection connection = null;
        
        try {
        	String db_directory = "\\files\\pokemon_data\\pokedex.sqlite";
        	String complete_path = System.getProperty("user.dir") + db_directory;
            String url = "jdbc:sqlite:" + complete_path;
            connection = DriverManager.getConnection(url);
            return readPokemon(connection);
            
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            
        } finally {
            try {
                if (connection != null) {
                    connection.close();
                }
            } catch (SQLException ex) {
                System.out.println(ex.getMessage());
            }
        }
        return null;
    }
	
	private List<PokemonSpecies> readPokemon(Connection connection) {
		
		List<PokemonSpecies> pokemon_list = new ArrayList<PokemonSpecies>();
		String sql = "SELECT pokemon_species_names.name AS 'name', type1.type_name AS 'type1', type2.type_name AS 'type2'" +
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
        
        try (Statement statement = connection.createStatement();
             ResultSet result = statement.executeQuery(sql)){
            
            // loop through the result set
            while (result.next()) {
            	PokemonSpecies species = PokemonSpecies.builder()
            			.name(result.getString("name"))
            			.type1(ElementalType.getInstance(result.getString("type1")))
            			.type2(ElementalType.getInstance(result.getString("type2")))
            			.build();
                pokemon_list.add(species);
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        
        return pokemon_list;
	}
}
