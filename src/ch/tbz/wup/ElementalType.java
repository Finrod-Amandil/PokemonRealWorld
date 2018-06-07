package ch.tbz.wup;

import java.awt.Color;

public class ElementalType {
	private String _name;
	private Color _color;
	
	public ElementalType(String name, Color color) {
		_name = name;
		_color = color;
	}
	
	public String getName() {
		return _name;
	}
	
	public Color getColor() {
		return _color;
	}
}
