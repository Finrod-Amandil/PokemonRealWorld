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

public class PokemonDbContext {
	private static final String pokemonDbLocation = "\\files\\data\\pokedex.sqlite";
	
	public Map<Integer, PokemonSpecies> getPokemonFromDatabase() {
		// create connection for DB access
        Connection connection = null;
        
        try {
            connection = DriverManager.getConnection(getPokemonDbUrl());
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
	
	private String getPokemonDbUrl() {
		String db_directory = pokemonDbLocation;
    	String complete_path = System.getProperty("user.dir") + db_directory;
        return "jdbc:sqlite:" + complete_path;
	}
	
	private Map<Integer, PokemonSpecies> readPokemon(Connection connection) {
		
		Map<Integer, PokemonSpecies> pokemon_list = new LinkedHashMap<Integer, PokemonSpecies>();
		String sql = SqlStatements.GET_POKEMON;
        
        try (Statement statement = connection.createStatement();
             ResultSet result = statement.executeQuery(sql)){
            
            // loop through the result set
            while (result.next()) {
            	int id = result.getInt("id");
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
