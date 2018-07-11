package ch.tbz.wup.persistence;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import ch.tbz.wup.models.Area;
import ch.tbz.wup.models.AreaType;
import ch.tbz.wup.models.Pokedex;
import ch.tbz.wup.models.PokemonSpecies;
import ch.tbz.wup.models.Spawn;

/**
 * Performs all database queries related to the custom game_data.db database.
 */
public class GameDataDbContext {
	private static final String gameDataDbLocation = "\\files\\data\\game_data.db";
	
	/**
	 * Serialises and saves Areas to DB.
	 * 
	 * @param areas  Areas including nested objects.
	 */
	public void writeAreasToDatabase(List<Area> areas) {
		List<Area> existingAreas = getAreasFromDatabase();
		
		Connection connection = null;
		try{
			//Open connection
			connection = DriverManager.getConnection(getGameDataDbUrl());

			//Save every area seperately.
	        for (Area area : areas) {
	        	
	        	//Streams to convert object to bytes and then output those.
	        	ByteArrayOutputStream bos = new ByteArrayOutputStream();
		        ObjectOutputStream oos = new ObjectOutputStream(bos);
	        	
		        //Serialise area
	        	oos.writeObject(area);
		        oos.flush();

		        byte[] data = bos.toByteArray();
		        
		        //Check if id already exists
		        boolean doesAreaAlreadyExist = false;
		        for (Area existingArea : existingAreas) {
		        	if (existingArea.getId() == area.getId()) {
		        		doesAreaAlreadyExist = true;
		        		break;
		        	}
		        }
		        
		        //If area does exist already, overwrite Area-data of existing entry. Else add new entry.
		        String sql = "";
		        if (doesAreaAlreadyExist) {
		        	sql = "UPDATE Area SET area_serialized = ? WHERE id = ?";
		        	PreparedStatement query = connection.prepareStatement(sql);
			        query.setObject(1, data);
			        query.setInt(2, area.getId());
			        query.executeUpdate();
		        }
		        else {
		        	sql = "INSERT INTO Area (id, area_serialized) VALUES(?, ?)";
		        	PreparedStatement query = connection.prepareStatement(sql);
			        query.setInt(1, area.getId());
			        query.setObject(2, data);
			        query.executeUpdate();
		        }
		        
		        oos.close();
		        bos.close();
	        }
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
	}
	
	/**
	 * @return  All areas including nested objects.
	 */
	public List<Area> getAreasFromDatabase() {
		List<Area> areas = new ArrayList<Area>();
		
        String sql = "SELECT * FROM Area";
        Connection connection = null;
        ResultSet rs;
		try {
			//Build up connection and query
			connection = DriverManager.getConnection(getGameDataDbUrl());
			PreparedStatement ps = connection.prepareStatement(sql);
			
			//execute query and parse data
			rs = ps.executeQuery();
			areas = parseAreas(rs);
		} 
		catch (SQLException e) {
			e.printStackTrace();
		} 
		catch (IOException e) {
			e.printStackTrace();
		}
		finally {
			try {
				connection.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
        return areas;
	}
	
	/**
	 * Loads all Spawns from the database. Requires Pokémon data to build species from ID.
	 * 
	 * @param pokemon  List of all Pokémon species.
	 * @return  All Spawn possibilities with an areaId each.
	 */
	public List<Spawn> getSpawnsFromDatabase(Map<Integer, PokemonSpecies> pokemon) {
		List<Spawn> spawns = new ArrayList<Spawn>();
		
		String sql = "SELECT * FROM Spawns";
		Connection connection = null;
		ResultSet rs;
		
		try {
			//Set up connection and query
			connection = DriverManager.getConnection(getGameDataDbUrl());
			PreparedStatement ps = connection.prepareStatement(sql);
			
			//Execute query and build spawns.
			rs = ps.executeQuery();
			spawns = parseSpawns(rs, pokemon);
		}
		catch (SQLException sqle) {
			sqle.printStackTrace();
		}
		
		return spawns;
	}
	
	/**
	 * Load the contents of the Pokédex of the specified region.
	 * 
	 * @param regionId  The Id of the region to load the Pokédex from.
	 * @param pokemon  List of all Pokémon species to build nested objects.
	 * @return  Pokédex with all nested objects and all Pokémon not yet discovered.
	 */
	public Pokedex getPokedexFromDatabase(int regionId, Map<Integer, PokemonSpecies> pokemon) {
		List<Integer> pokemonIds = new ArrayList<Integer>();
		List<PokemonSpecies> species = new ArrayList<PokemonSpecies>();
		
		String sql = "SELECT * FROM Pokedex WHERE fk_region = ?";
		Connection connection = null;
		ResultSet rs;
		
		try {
			connection = DriverManager.getConnection(getGameDataDbUrl());
			PreparedStatement query = connection.prepareStatement(sql);
			query.setInt(1, regionId);
			rs = query.executeQuery();
			
			while (rs.next()) {
				pokemonIds.add(rs.getInt("fk_species"));
			}
			
		}
		catch (SQLException sqle) {
			sqle.printStackTrace();
		}
		
		//Add species by species-ID.
		for (Integer pokemonId : pokemonIds) {
			species.add(pokemon.get(pokemonId));
		}
		
		return new Pokedex(species);
	}
	
	//Connection information used by all queries.
	private String getGameDataDbUrl() {
		String db_directory = gameDataDbLocation;
    	String complete_path = System.getProperty("user.dir") + db_directory;
        return "jdbc:sqlite:" + complete_path;
	}
	
	//Deserialises loaded areas.
	private List<Area> parseAreas(ResultSet rs) throws SQLException, IOException {
		List<Area> areas = new ArrayList<Area>();
		ByteArrayInputStream bais = null;
        ObjectInputStream ins = null;
		
		while(rs.next())
        {
            try {
            	byte[] data = rs.getBytes("area_serialized");
            	if (data == null) {
            		areas.add(Area.add(rs.getInt("id"), null, null, AreaType.NONE));
            		continue;
            	}
            	
	            bais = new ByteArrayInputStream(data);
	            ins = new ObjectInputStream(bais);
	
	            Area area = (Area)ins.readObject();
	            areas.add(area);
	
	            ins.close();
            }
            catch (ClassCastException cce) {
            	System.out.println(cce.getMessage());
            } 
            catch (ClassNotFoundException e) {
				e.printStackTrace();
			}
            finally {
            	if (bais != null) {
            		bais.close();
            	}
            	if (ins != null) {
            		ins.close();
            	}
            }
        }
		
		return areas;
	}
	
	//Builds Spawn objects from DB data.
	private List<Spawn> parseSpawns(ResultSet rs, Map<Integer, PokemonSpecies> pokemon) throws SQLException {
		List<Spawn> spawns = new ArrayList<Spawn>();
		
		while(rs.next()) {
			int speciesId = rs.getInt("species_id");
			int areaId = rs.getInt("area_id");
			int weight = rs.getInt("weight");
			
			spawns.add(new Spawn(pokemon.get(speciesId), weight, areaId));
		}
		
		return spawns;
	}
}
