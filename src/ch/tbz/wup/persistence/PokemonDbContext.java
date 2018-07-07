package ch.tbz.wup.persistence;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import ch.tbz.wup.models.ElementalType;
import ch.tbz.wup.models.PokemonSpecies;

public class PokemonDbContext {
	private static final String pokemonDbLocation = "\\files\\data\\pokedex.sqlite";
	
	public List<PokemonSpecies> getPokemonFromDatabase() {
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
	
	private List<PokemonSpecies> readPokemon(Connection connection) {
		
		List<PokemonSpecies> pokemon_list = new ArrayList<PokemonSpecies>();
		String sql = SqlStatements.GET_POKEMON;
        
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
