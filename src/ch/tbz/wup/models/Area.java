package ch.tbz.wup.models;

import java.awt.Point;
import java.awt.Polygon;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Area implements Serializable {

	private static final long serialVersionUID = 1L;
	private static List<Area> _allAreas = new ArrayList<Area>();
	
	public static Area add(int id, Polygon bounds, String name, AreaType type) {
		//Check if area with same name and type already exists.
		for (Area area : _allAreas) {
			if (area.getId() == id) {
				area.addSection(bounds);
				return area;
			}
		}
		
		//Area does not exist yet
		Area newArea = new Area(id, bounds, name, type);
		_allAreas.add(newArea);
		return newArea;
	}
	
	public static List<Area> getAllAreas() {
		return new ArrayList<Area>(_allAreas);
	}
	
	private int _id;
	private String _name;
	private List<AreaSection> _sectionBounds;
	private AreaType _type;
	private List<Spawn> _spawns = new ArrayList<Spawn>();
	
	private Area(int id, Polygon bounds, String name, AreaType type) {
		_id = id;
		_sectionBounds = new ArrayList<AreaSection>();
		_sectionBounds.add(new AreaSection(bounds));
		_type = type;
		_name = name;
	} 
	
	public String getName() {
		return _name;
	}
	
	public AreaType getType() {
		return _type;
	}
	
	public int getId() {
		return _id;
	}
	
	public void addSpawn(Spawn spawn) {
		_spawns.add(spawn);
	}
	
	public List<Spawn> getSpawns() {
		return new ArrayList<Spawn>(_spawns);
	}
	
	public boolean contains(Point point) {
		for (AreaSection section : _sectionBounds) {
			if (section.boundingBox.contains(point)) {
				if (section.bounds.contains(point)) {
					return true;
				}
			}
		}
		return false;
	}
	
	private void addSection(Polygon section) {
		_sectionBounds.add(new AreaSection(section));
	}
}
