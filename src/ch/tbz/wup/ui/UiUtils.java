package ch.tbz.wup.ui;

import java.awt.Point;
import java.awt.Rectangle;

public class UiUtils {
	public static Point transform(Rectangle windowBounds, Point rc_point, Point rc_center) {
		//real coordinates of center and window coordinates of center coalesce
		int halfWidth = windowBounds.width / 2;
		int halfHeight = windowBounds.height / 2;
		
		//Get upper left corner in real coordinates
		Point rc_upperLeftCorner = new Point(
			rc_center.x - halfWidth,
			rc_center.y + halfHeight);
		
		//Window coordinates are now equal to distances between point and real coordinates of point in upper left corner.
		int dX = rc_point.x - rc_upperLeftCorner.x;
		int dY = -(rc_point.y - rc_upperLeftCorner.y);
		
		Point transformed = new Point(dX, dY);
		
		return transformed;
	}
}
