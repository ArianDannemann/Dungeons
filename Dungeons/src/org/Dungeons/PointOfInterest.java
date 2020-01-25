package org.Dungeons;

import org.bukkit.Location;

public abstract class PointOfInterest {
	
	protected Location location;
	
	public abstract void generatePointOfInterest(Main main, Location pointOfInterestLocation);
}
