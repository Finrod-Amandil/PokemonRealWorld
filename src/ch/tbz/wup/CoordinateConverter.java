package ch.tbz.wup;

import java.awt.Point;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;

public class CoordinateConverter {

	public CoordinateConverter() {}
	
	public static Point convertPointFromWGS84toLV03(double latitude, double longitude) {
		Point point = null;
		
		String urlStr = "http://geodesy.geo.admin.ch/reframe/wgs84tolv03?easting=" + latitude + "&northing=" + longitude;
		
		try {
		    URL url = new URL(urlStr);
		    BufferedReader in = new BufferedReader(new InputStreamReader(url.openStream()));

		    String text = "";
		    String line = "";
		    while ((line = in.readLine()) != null) {
		    	text += line;
		    }
		    
		    point = parsePoint(text);
            in.close();
		} 
		catch (MalformedURLException e) { 
			System.out.println("The url for conversion was malformed: " + urlStr);
			e.printStackTrace();
		} 
		catch (IOException e) {   
		    // openConnection() failed
		    // ...
		}
		
		return point;
	}
	
	private static Point parsePoint(String text) {
		if (text.contains("\"coordinates\"")) {
			String attribute = text.substring(text.indexOf("\"coordinates\""), text.indexOf("]", text.indexOf("\"coordinates\"")) + 1);
			String xCoordinateString = attribute.substring(attribute.indexOf("[") + 1, attribute.indexOf(",")).trim();
			String yCoordinateString = attribute.substring(attribute.indexOf(",") + 1, attribute.indexOf("]")).trim();
			int x = (int)Double.parseDouble(xCoordinateString);
			int y = (int)Double.parseDouble(yCoordinateString);
			return new Point(x, y);
		}
		return null;
	}

}
