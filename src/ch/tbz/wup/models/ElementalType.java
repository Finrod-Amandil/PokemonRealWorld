package ch.tbz.wup.models;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Every Pokémon species has either one or two elemental types, represented by instances of this class.
 * Uses a pseudo-Singleton pattern to ensure all type instances are unique.
 */
public class ElementalType implements Serializable {
	private static final long serialVersionUID = 1L;
	
	//Instance variable for pseudo-singleton pattern
	private static List<ElementalType> _allTypes = new ArrayList<ElementalType>();
	
	/**
	 * Returns the elemental type corresponding to the given name.
	 * 
	 * @param name  The (non-case-sensitive) name of the type.
	 * @return  The single instance representing this type.
	 */
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
	
	private String _name; //Name of the type.
	
	/**
	 * @return  The name of the type.
	 */
	public String getName() {
		return _name;
	}
	
	//Private constructor for pseudo-Singleton pattern.
	private ElementalType(String name) {
		_name = name;
	}
}
