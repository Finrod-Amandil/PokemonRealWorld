package ch.tbz.wup.models;

import java.io.Serializable;

public class PokemonSpecies implements Serializable {
	private static final long serialVersionUID = 1L;

	public static PokemonBuilder builder() {
		return new PokemonBuilder();
	}
	
	private int _id;
	private String _name;
	private ElementalType _type1;
	private ElementalType _type2;
	
	private PokemonSpecies() {}
	
	public int getId() {
		return _id;
	}
	
	public String getName() {
		return _name;
	}
	
	public ElementalType getType1() {
		return _type1;
	}
	
	public ElementalType getType2() {
		return _type2;
	}
	
	public static class PokemonBuilder {
		private int _id;
		private String _name;
		private ElementalType _type1;
		private ElementalType _type2;
		
		public PokemonBuilder id(int id) {
			_id = id;
			return this;
		}
		
		public PokemonBuilder name(String name) {
			_name = name;
			return this;
		}
		
		public PokemonBuilder type1(ElementalType type) {
			_type1 = type;
			return this;
		}
		
		public PokemonBuilder type2(ElementalType type) {
			_type2 = type;
			return this;
		}
		
		public PokemonSpecies build() {
			PokemonSpecies species = new PokemonSpecies();
			species._id = this._id;
			species._name = this._name;
			species._type1 = this._type1;
			species._type2 = this._type2;
			
			return species;
		}
	}
	
	
	
}
