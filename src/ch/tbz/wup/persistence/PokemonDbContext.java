package ch.tbz.wup.persistence;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.LinkedHashMap;
import java.util.Map;

import ch.tbz.wup.models.ElementalType;
import ch.tbz.wup.models.PokemonSpecies;

/**
 * Class responsible for all DB queries to the Pokémon data DB.
 */
public class PokemonDbContext {
	private static final String pokemonDbLocation = "\\files\\data\\pokedex.sqlite";
	
	/**
	 * @return All Pokémon species as Map with National Pokédex Numbers as keys.
	 */
	public Map<Integer, PokemonSpecies> getPokemonFromDatabase() {
        Connection connection = null;
        
        try {
        	// create connection for DB access
            connection = DriverManager.getConnection(getPokemonDbUrl());
            
            //Parse pokemon
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
	
	//DB access information
	private String getPokemonDbUrl() {
		String db_directory = pokemonDbLocation;
    	String complete_path = System.getProperty("user.dir") + db_directory;
        return "jdbc:sqlite:" + complete_path;
	}
	
	//Reads out the query results and builds PokemonSpecies instances from the data
	private Map<Integer, PokemonSpecies> readPokemon(Connection connection) {
		
		//LinkedHashMap is used to keep collection ordered.
		Map<Integer, PokemonSpecies> pokemon_list = new LinkedHashMap<Integer, PokemonSpecies>();
		
		//Get big query from helper class.
		String sql = SqlStatements.GET_POKEMON;
        
        try (Statement statement = connection.createStatement();
             ResultSet result = statement.executeQuery(sql)){
            
            // loop through the result set
            while (result.next()) {
            	int id = result.getInt("id");

            	//Build Pokémon
            	PokemonSpecies species = PokemonSpecies.builder()
            			.id(id)
            			.name(result.getString("name"))
            			.type1(ElementalType.getInstance(result.getString("type1")))
            			.type2(ElementalType.getInstance(result.getString("type2")))
            			.build();
                pokemon_list.put(id, species);
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        
        return pokemon_list;
	}
}
