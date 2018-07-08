package ch.tbz.wup.models;

import java.awt.Polygon;
import java.awt.Rectangle;

public class AreaSection {
	
	public Polygon bounds;
	public Rectangle boundingBox;
	
	public AreaSection(Polygon bounds) {
		this.bounds = bounds;
		boundingBox = bounds.getBounds();
	}
}
