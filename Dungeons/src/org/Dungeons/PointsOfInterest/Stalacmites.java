package org.Dungeons.PointsOfInterest;

import org.Dungeons.Main;
import org.Dungeons.PointOfInterest;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.util.Vector;

import com.PluginBase.LocationHelper;
import com.PluginBase.MathHelper;

public class Stalacmites extends PointOfInterest {
	
	private final int range = 8, amount = 10;

	@Override
	public void generatePointOfInterest(Main main, Location pointOfInterestLocation) {
		for (int i = 0; i < this.amount; i++) {
			Location stalactiteLocation = LocationHelper.getInstance().getRandomNearbyPosition(pointOfInterestLocation,
					this.range);
			if (MathHelper.getInstance().hasChanceHit(30)) {
				stalactiteLocation.getBlock()
						.setType(MathHelper.getInstance().hasChanceHit(50) ? Material.COBBLESTONE
								: Material.MOSSY_COBBLESTONE);
				LocationHelper.getInstance().offsetLocation(stalactiteLocation, new Vector(0, 1, 0)).getBlock()
						.setType(MathHelper.getInstance().hasChanceHit(50) ? Material.COBBLESTONE_WALL
								: Material.MOSSY_COBBLESTONE_WALL);
				LocationHelper.getInstance().offsetLocation(stalactiteLocation, new Vector(0, 2, 0)).getBlock()
						.setType(Material.END_ROD);
			} else {
				stalactiteLocation.getBlock()
						.setType(MathHelper.getInstance().hasChanceHit(50) ? Material.COBBLESTONE_WALL
								: Material.MOSSY_COBBLESTONE_WALL);
				LocationHelper.getInstance().offsetLocation(stalactiteLocation, new Vector(0, 1, 0)).getBlock()
						.setType(Material.END_ROD);
			}
		}
	}
}
