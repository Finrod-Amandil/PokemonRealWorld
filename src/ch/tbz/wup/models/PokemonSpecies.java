package ch.tbz.wup.models;

import java.io.Serializable;

/**
 * Instances of this class represent a SPECIES of Pokémon, not a concretely spawned or owned Pokémon.
 * In prospect of many possible future attributes, uses the Builder Pattern for instantiation.
 */
public class PokemonSpecies implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * Returns a fresh Builder object to start constructing a new instance of this class.
	 * 
	 * @return  A PokemonBuilder instance.
	 */
	public static PokemonBuilder builder() {
		return new PokemonBuilder();
	}
	
	private int _id;
	private String _name;
	private ElementalType _type1;
	private ElementalType _type2; //Optional, may be null
	
	//private Constructor, instantiation through Builder Pattern.
	private PokemonSpecies() {}
	
	/**
	 * @return  The ID of this species, matching the "National Pokédex Number"
	 */
	public int getId() {
		return _id;
	}
	
	/**
	 * @return  The german name of the Pokémon.
	 */
	public String getName() {
		return _name;
	}
	
	/**
	 * @return  The primary (mandatory) type of this Pokémon.
	 */
	public ElementalType getType1() {
		return _type1;
	}
	
	/**
	 * @return  The secondary (optional) type of this Pokémon.
	 */
	public ElementalType getType2() {
		return _type2;
	}
	
	/**
	 * Nested class for Builder Pattern. Continuously assembles data for attributes
	 * and then bulk copies them to a new instance of PokemonSpecies.
	 */
	public static class PokemonBuilder {
		private int _id;
		private String _name;
		private ElementalType _type1;
		private ElementalType _type2;
		
		/**
		 * Sets the ID of the work-in-progress PokemonSpecies.
		 * 
		 * @param id  The value for the ID.
		 * @return  The calling PokemonBuilder instance for method chaining.
		 */
		public PokemonBuilder id(int id) {
			_id = id;
			return this;
		}
		
		/**
		 * Sets the name of the work-in-progress PokemonSpecies.
		 * 
		 * @param id  The value for the name.
		 * @return  The calling PokemonBuilder instance for method chaining.
		 */
		public PokemonBuilder name(String name) {
			_name = name;
			return this;
		}
		
		/**
		 * Sets the primary type of the work-in-progress PokemonSpecies.
		 * 
		 * @param id  The value for the primary type.
		 * @return  The calling PokemonBuilder instance for method chaining.
		 */
		public PokemonBuilder type1(ElementalType type) {
			_type1 = type;
			return this;
		}
		
		/**
		 * Sets the optional secondary type of the work-in-progress PokemonSpecies.
		 * 
		 * @param id  The value for the secondary type.
		 * @return  The calling PokemonBuilder instance for method chaining.
		 */
		public PokemonBuilder type2(ElementalType type) {
			_type2 = type;
			return this;
		}
		
		/**
		 * Copies the values from the PokemonBuilder to a new PokemonSpecies instance.
		 * 
		 * @return  A new instance of PokemonSpecies with the values defined in the Builder.
		 */
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
