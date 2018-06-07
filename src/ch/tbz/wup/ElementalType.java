package ch.tbz.wup;

import java.awt.Color;

public class ElementalType {
	private String _name;
	private int _id;
	
	public ElementalType(String name, int id) {
		_name = name;
		_id = id;
	}
	
	public String getName() {
		return _name;
	}
	
	public int getId() {
		return _id;
	}
}
