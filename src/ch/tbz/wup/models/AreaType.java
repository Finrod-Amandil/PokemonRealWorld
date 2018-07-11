package ch.tbz.wup.models;

import java.io.Serializable;

/**
 * Enumeration for all valid area types. Special values:
 * NONE: No area type mentioned --> Area is a unique / special one
 * REGIONBOUNDS: AreaType for area that specifies the shape of the region.
 */
public enum AreaType implements Serializable {
	NONE,
	REGIONBOUNDS,
	CITY,
	LAKE,
	RIVER,
	SHORE,
	PARK,
	FOREST,
	MEADOW,
	INDUSTRY,
	SPORTSFIELD,
	BATHS,
	HOSPITAL,
	CEMETERY,
	VINEYARD
}
