package ch.tbz.wup;

import java.awt.Polygon;
import java.util.ArrayList;
import java.util.List;

public class Area {
	private static List<Area> _allAreas = new ArrayList<Area>();
	
	public static Area add(Polygon bounds, String name, AreaType type) {
		//Check if area with same name and type already exists.
		for (Area area : _allAreas) {
			if (area.getName().equals(name) && area.getType().equals(type)) {
				area.addSection(bounds);
				return area;
			}
		}
		
		//Area does not exist yet
		Area newArea = new Area(bounds, name, type);
		return newArea;
	}
	
	public static List<Area> getAllAreas() {
		return new ArrayList<Area>(_allAreas);
	}
	
	private String _name;
	private List<Polygon> _sectionBounds;
	private AreaType _type;
	
	private Area(Polygon bounds, String name, AreaType type) {
		_sectionBounds = new ArrayList<Polygon>();
		_sectionBounds.add(bounds);
		_type = type;
		_name = name;
	} 
	
	public String getName() {
		return _name;
	}
	
	public AreaType getType() {
		return _type;
	}
	
	private void addSection(Polygon section) {
		_sectionBounds.add(section);
	}
}
