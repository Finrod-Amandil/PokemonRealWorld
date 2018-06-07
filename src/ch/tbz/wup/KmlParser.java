package ch.tbz.wup;

import java.awt.Point;
import java.awt.Polygon;
import java.io.File;
import java.io.IOException;
import java.util.List;

import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.JDOMException;
import org.jdom2.input.SAXBuilder;

public class KmlParser implements IAreaParser {
	
	private ICoordinateConverter _converter;
	
	public KmlParser(ICoordinateConverter converter) {
		_converter = converter;
	}
	
	public List<Area> readAreas(String filePath) {
		//JDOM document builder
	    SAXBuilder builder = new SAXBuilder();
	    
	    try {
	      //Create JDOM document
	      Document doc = builder.build(new File(filePath));

	      //Get first element
	      Element root = doc.getRootElement();
	      
	      //Read areas
	      for (Element placemarkElement : root.getChildren("Placemark")) {
	    	  buildArea(placemarkElement);
	      }
	    }
	    catch (JDOMException e) { 
	      System.out.println(e.getMessage());
	    }  
	    catch (IOException e) { 
	      System.out.println(e);
	    } 
	    
	    return Area.getAllAreas();
	}
	
	private Area buildArea(Element placemarkElement) {
		String name = placemarkElement.getChildText("description"); //TODO
		String polygonCoordinates = placemarkElement.getChild("Polygon")
				.getChild("outerBoundaryIs")
				.getChild("LinearRing")
				.getChildText("coordinates");
		
		Polygon bounds = new Polygon();
		
		for (String pointCoordinates : polygonCoordinates.split(" ")) {
			String[] coordinates = pointCoordinates.split(",");
			double latitude = Double.parseDouble(coordinates[0]);
			double longitude = Double.parseDouble(coordinates[1]);
			Point pointInLV03 = _converter.convertPoint(latitude, longitude);
			
			bounds.addPoint(pointInLV03.x, pointInLV03.y);
		}
		
		return Area.add(bounds, name, AreaType.CITY); //TODO
	}
}
