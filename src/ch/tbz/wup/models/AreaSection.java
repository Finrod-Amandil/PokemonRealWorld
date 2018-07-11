package ch.tbz.wup.models;

import java.awt.Polygon;
import java.awt.Rectangle;
import java.io.Serializable;

/**
 *An AreaSection is a container for a part of the map area covered by an Area instance. It contains the
 *polygonal shape of the section as well as the corresponding, precalculated bounding box.
 */
public class AreaSection implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	/**
	 * Shape of the section.
	 */
	public Polygon bounds;
	
	/**
	 * The pre-calculated bounding box of the section's shape.
	 */
	public Rectangle boundingBox;
	
	/**
	 * Creates a new Area section and calculates the bounding box.
	 * 
	 * @param bounds  The polygonal bounds of the section.
	 */
	public AreaSection(Polygon bounds) {
		this.bounds = bounds;
		boundingBox = bounds != null ? bounds.getBounds() : new Rectangle(0,0,0,0);
	}
}
