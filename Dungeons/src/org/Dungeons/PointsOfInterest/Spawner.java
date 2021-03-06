package org.Dungeons.PointsOfInterest;

import org.Dungeons.Main;
import org.Dungeons.PointOfInterest;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.util.Vector;

import com.PluginBase.LocationHelper;
import com.PluginBase.MathHelper;

public class Spawner extends PointOfInterest {
	@Override
	public void generatePointOfInterest(Main main, Location pointOfInterestLocation) {
		pointOfInterestLocation.getBlock().setType(Material.SPAWNER);
		for (int x = -3; x < 3; x++) {
			for (int y = -3; y < 3; y++) {
				for (int z = -3; z < 3; z++) {
					Location cobwebLocation = LocationHelper.getInstance().offsetLocation(pointOfInterestLocation,
							new Vector(x, y, z));
					if (MathHelper.getInstance().hasChanceHit(60) && (cobwebLocation.getBlock().getType() == Material.AIR || cobwebLocation.getBlock().getType() == Material.CAVE_AIR)) {
						cobwebLocation.getBlock().setType(Material.COBWEB);
					}
				}
			}
		}
	}
}
