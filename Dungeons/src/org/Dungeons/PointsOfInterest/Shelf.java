package org.Dungeons.PointsOfInterest;

import java.util.List;

import org.Dungeons.Main;
import org.Dungeons.PointOfInterest;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.util.Vector;

import com.PluginBase.LocationHelper;
import com.PluginBase.MathHelper;

public class Shelf extends PointOfInterest {
	@Override
	public void generatePointOfInterest(Main main, Location pointOfInterestLocation) {
		Vector shelfSize = new Vector(0, 4, 0);
		int shelfLength = MathHelper.getInstance().getRandom().nextInt(10);
		// Choose between X and Z axis for the corridor
		if (MathHelper.getInstance().hasChanceHit(50)) {
			// Choose which direction (on the X axis) the corridor should face
			shelfSize.setX(MathHelper.getInstance().hasChanceHit(50) ? -shelfLength : shelfLength);
			shelfSize.setZ(2);
		} else {
			// Choose which direction (on the Z axis) the corridor should face
			shelfSize.setZ(MathHelper.getInstance().hasChanceHit(50) ? -shelfLength : shelfLength);
			shelfSize.setX(2);
		}
		List<Block> blocks = LocationHelper.getInstance().getBlocksBetweenLocations(
				LocationHelper.getInstance().getSmallerLocation(
						LocationHelper.getInstance().offsetLocation(pointOfInterestLocation, shelfSize),
						pointOfInterestLocation),
				LocationHelper.getInstance().getBiggerLocation(
						LocationHelper.getInstance().offsetLocation(pointOfInterestLocation, shelfSize),
						pointOfInterestLocation));
		for (Block block : blocks) {
			block.setType(Material.BOOKSHELF);
		}
	}
}
