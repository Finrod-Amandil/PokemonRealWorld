package ch.tbz.wup.models;

public class PokemonSpecies {
	public static PokemonBuilder builder() {
		return new PokemonBuilder();
	}
	
	private String _name;
	private ElementalType _type1;
	private ElementalType _type2;
	
	private PokemonSpecies() {}
	
	public Pokemon getInstance() {
		return new Pokemon(this);
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
		private String _name;
		private ElementalType _type1;
		private ElementalType _type2;
		
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
			species._name = this._name;
			species._type1 = this._type1;
			species._type2 = this._type2;
			
			return species;
		}
	}
	
	
	
}
