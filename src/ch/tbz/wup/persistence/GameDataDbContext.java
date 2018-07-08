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
import ch.tbz.wup.models.PokemonSpecies;
import ch.tbz.wup.models.Spawn;

public class GameDataDbContext {
	private static final String gameDataDbLocation = "\\files\\data\\game_data.db";
	
	public void writeAreasToDatabase(List<Area> areas) {
		List<Area> existingAreas = getAreasFromDatabase();
		
		Connection connection = null;
		try{
			connection = DriverManager.getConnection(getGameDataDbUrl());

	        for (Area area : areas) {
	        	ByteArrayOutputStream bos = new ByteArrayOutputStream();
		        ObjectOutputStream oos = new ObjectOutputStream(bos);
	        	
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
	
	public List<Area> getAreasFromDatabase() {
		List<Area> areas = new ArrayList<Area>();
		
        String sql = "SELECT * FROM Area";
        Connection connection = null;
        ResultSet rs;
		try {
			connection = DriverManager.getConnection(getGameDataDbUrl());
			PreparedStatement ps = connection.prepareStatement(sql);
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
	
	public List<Spawn> getSpawnsFromDatabase(Map<Integer, PokemonSpecies> pokemon) {
		List<Spawn> spawns = new ArrayList<Spawn>();
		
		String sql = "SELECT * FROM Spawns";
		Connection connection = null;
		ResultSet rs;
		
		try {
			connection = DriverManager.getConnection(getGameDataDbUrl());
			PreparedStatement ps = connection.prepareStatement(sql);
			rs = ps.executeQuery();
			spawns = parseSpawns(rs, pokemon);
		}
		catch (SQLException sqle) {
			sqle.printStackTrace();
		}
		
		return spawns;
	}
	
	private String getGameDataDbUrl() {
		String db_directory = gameDataDbLocation;
    	String complete_path = System.getProperty("user.dir") + db_directory;
        return "jdbc:sqlite:" + complete_path;
	}
	
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
