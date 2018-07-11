package ch.tbz.wup.models;

import java.awt.Point;
import java.awt.Polygon;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * An Area represents a part of a region where specific Pokémon can spawn. An Area is composed
 * out of multiple polygonal sections and is associated with Spawn objects that define which
 * Pokémon can occur.
 */
public class Area implements Serializable {

	private static final long serialVersionUID = 1L;
	
	//Instance tracker for pseudo-Singleton pattern
	private static List<Area> _allAreas = new ArrayList<Area>();
	
	/**
	 * Pseudo-Singleton pattern entry point. Takes a section with additional area data as
	 * arguments. Checks if an area of that ID already exists, if yes, adds the specified
	 * section to that Area and returns it, otherwise creates and returns a new Area.
	 * 
	 * @param id  The ID of the area.
	 * @param bounds  The polygonal bounds of one section of an area.
	 * @param name  The name of the area
	 * @param type  The type of the area
	 * @return  If existing, the corresponding area, else a new area.
	 */
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
	
	/**
	 * @return  All areas.
	 */
	public static List<Area> getAllAreas() {
		return new ArrayList<Area>(_allAreas);
	}
	
	private int _id;
	private String _name;
	private AreaType _type;
	
	private List<AreaSection> _sectionBounds; //Areas consist of multiple polygonal sections.
	private List<Spawn> _spawns = new ArrayList<Spawn>(); //Pokémon which spawn here, as Spawn objects.
	
	//Private constructor for pseudo-Singleton pattern
	private Area(int id, Polygon bounds, String name, AreaType type) {
		_id = id;
		_sectionBounds = new ArrayList<AreaSection>();
		_sectionBounds.add(new AreaSection(bounds));
		_type = type;
		_name = name;
	} 
	
	/**
	 * @return  The name of the area (may be empty)
	 */
	public String getName() {
		return _name;
	}
	
	/**
	 * @return  The type of the area.
	 */
	public AreaType getType() {
		return _type;
	}
	
	/**
	 * @return  The id of the area.
	 */
	public int getId() {
		return _id;
	}
	
	/**
	 * Adds a new Spawn to this area.
	 * 
	 * @param spawn  The Spawn object to associate with this area. Should
	 * not be associated with any other area.
	 */
	public void addSpawn(Spawn spawn) {
		_spawns.add(spawn);
	}
	
	/**
	 * @return  All Spawns that are associated with this Area.
	 */
	public List<Spawn> getSpawns() {
		return new ArrayList<Spawn>(_spawns);
	}
	
	/**
	 * Checks if a point is contained within this area. Iterates through all
	 * sections and first checks, if the point is contained in its bounding
	 * box, and only if yes, checks if the point is contained in the actual
	 * polygon.
	 * 
	 * @param point  The point to check.
	 * @return  Whether the point is inside the area or not.
	 */
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
	
	//Adds a new section to the Area
	private void addSection(Polygon section) {
		_sectionBounds.add(new AreaSection(section));
	}
}
