package ch.tbz.wup.models;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class ElementalType implements Serializable {
	private static final long serialVersionUID = 1L;
	private static List<ElementalType> _allTypes = new ArrayList<ElementalType>();
	
	public static ElementalType getInstance(String name) {
		if (name == null) {
			return null;
		}
		
		for (ElementalType type : _allTypes) {
			if (type.getName().equalsIgnoreCase(name)) {
				return type;
			}
		}
		
		ElementalType newType = new ElementalType(name);
		_allTypes.add(newType);
		return newType;
	}
	
	private String _name;
	
	private ElementalType(String name) {
		_name = name;
	}
	
	public String getName() {
		return _name;
	}
}
