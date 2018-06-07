package ch.tbz.wup;

import java.awt.Polygon;
import java.util.ArrayList;

public class Region {
	private ArrayList<Area> _areas;
	private Polygon _bounds;
	private String _name;
	
	public Region(String name, ArrayList<Area> areas) {
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
