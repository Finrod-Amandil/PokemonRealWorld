package ch.tbz.wup.models;

import java.awt.Polygon;
import java.util.List;

public class Region {
	private List<Area> _areas;
	private Polygon _bounds;
	private String _name;
	
	public Region(String name, List<Area> areas) {
		_areas = areas;
		_name = name;
	}
	
	public Polygon getBounds() {
		//TODO temp only
		return new Polygon(new int[] {681000, 681000, 686000, 686000}, new int[] {244000, 249000, 244000, 249000}, 4);
	}
	
	public String getName() {
		return _name;
	}
}
