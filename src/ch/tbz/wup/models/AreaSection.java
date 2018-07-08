package ch.tbz.wup.models;

import java.awt.Polygon;
import java.awt.Rectangle;
import java.io.Serializable;

public class AreaSection implements Serializable {
	
	private static final long serialVersionUID = 1L;
	public Polygon bounds;
	public Rectangle boundingBox;
	
	public AreaSection(Polygon bounds) {
		this.bounds = bounds;
		boundingBox = bounds != null ? bounds.getBounds() : new Rectangle(0,0,0,0);
	}
}
