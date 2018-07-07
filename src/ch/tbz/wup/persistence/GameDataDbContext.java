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

import ch.tbz.wup.models.Area;

public class GameDataDbContext {
	private static final String gameDataDbLocation = "\\files\\data\\game_data.db";
	
	public void writeAreasToDatabase(List<Area> areas) {
		Connection connection = null;
		try{
			connection = DriverManager.getConnection(getGameDataDbUrl());

	        for (Area area : areas) {
	        	ByteArrayOutputStream bos = new ByteArrayOutputStream();
		        ObjectOutputStream oos = new ObjectOutputStream(bos);
	        	
	        	oos.writeObject(area);
		        oos.flush();

		        byte[] data = bos.toByteArray();
		        
		        String sql="INSERT INTO Area (id, area_serialized) VALUES(?, ?)";
		        PreparedStatement query = connection.prepareStatement(sql);
		        query.setInt(1, area.getId());
		        query.setObject(2, data);
		        query.executeUpdate();
		        
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
            	
	            bais = new ByteArrayInputStream(rs.getBytes("area_serialized"));
	            ins = new ObjectInputStream(bais);
	
	            Area area = (Area)ins.readObject();
	            areas.add(area);
	
	            System.out.println("Object in value :" + area.getName());
	            ins.close();
            }
            catch (ClassCastException cce) {
            	System.out.println(cce.getMessage());
            } 
            catch (ClassNotFoundException e) {
				e.printStackTrace();
			}
            finally {
            	bais.close();
            	ins.close();
            }
        }
		
		return areas;
	}
}
