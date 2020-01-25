package org.Dungeons.PointsOfInterest;

import org.Dungeons.Main;
import org.Dungeons.PointOfInterest;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.block.data.BlockData;
import org.bukkit.block.data.Directional;
import org.bukkit.util.Vector;

import com.PluginBase.LocationHelper;
import com.PluginBase.MathHelper;

public class Stalactites extends PointOfInterest {
	
	private final int range = 8, amount = 10;

	@Override
	public void generatePointOfInterest(Main main, Location pointOfInterestLocation) {
		for (int i = 0; i < this.amount; i++) {
			Location stalacmiteLocation = LocationHelper.getInstance().getRandomNearbyPosition(pointOfInterestLocation,
					this.range);
			Location roofifiedLocation = roofify(stalacmiteLocation);
			if (roofifiedLocation == null) {
				return;
			}
			if (MathHelper.getInstance().hasChanceHit(30)) {
				roofifiedLocation.getBlock()
						.setType(MathHelper.getInstance().hasChanceHit(50) ? Material.COBBLESTONE
								: Material.MOSSY_COBBLESTONE);
				LocationHelper.getInstance().offsetLocation(roofifiedLocation, new Vector(0, -1, 0)).getBlock()
						.setType(MathHelper.getInstance().hasChanceHit(50) ? Material.COBBLESTONE_WALL
								: Material.MOSSY_COBBLESTONE_WALL);
				LocationHelper.getInstance().offsetLocation(roofifiedLocation, new Vector(0, -2, 0)).getBlock()
						.setType(Material.END_ROD);
				Block endRod = LocationHelper.getInstance().offsetLocation(roofifiedLocation, new Vector(0, -2, 0)).getBlock();
				if (endRod.getType() == Material.END_ROD) {
					BlockData blockData = endRod.getBlockData();
					Directional directional = (Directional) blockData;
					directional.setFacing(BlockFace.DOWN);
					endRod.setBlockData(directional);
				}
			} else {
				roofifiedLocation.getBlock()
						.setType(MathHelper.getInstance().hasChanceHit(50) ? Material.COBBLESTONE_WALL
								: Material.MOSSY_COBBLESTONE_WALL);
				LocationHelper.getInstance().offsetLocation(roofifiedLocation, new Vector(0, -1, 0)).getBlock()
						.setType(Material.END_ROD);
				Block endRod = LocationHelper.getInstance().offsetLocation(roofifiedLocation, new Vector(0, -1, 0)).getBlock();
				if (endRod.getType() == Material.END_ROD) {
					BlockData blockData = endRod.getBlockData();
					Directional directional = (Directional) blockData;
					directional.setFacing(BlockFace.DOWN);
					endRod.setBlockData(directional);
				}
			}
		}
	}
	
	public Location roofify(Location stalacmiteLocation) {
		for (int i = 0; i < 10; i++) {
			stalacmiteLocation.setY(stalacmiteLocation.getY() + 1);
			if (stalacmiteLocation.getBlock().getType().isSolid()) {
				return LocationHelper.getInstance().offsetLocation(stalacmiteLocation, new Vector(0, -1, 0));
			}
		}
		return null;
	}
}
