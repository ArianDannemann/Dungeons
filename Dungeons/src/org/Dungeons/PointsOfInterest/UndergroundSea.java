package org.Dungeons.PointsOfInterest;

import java.util.HashMap;
import java.util.Map;

import org.Dungeons.Main;
import org.Dungeons.PointOfInterest;
import org.Dungeons.Selector;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.EntityType;
import org.bukkit.util.Vector;

import com.PluginBase.LocationHelper;
import com.PluginBase.MathHelper;

public class UndergroundSea extends PointOfInterest {
	
	private final int range = 5, amountOfFish = 3;

	private Selector selector = new Selector();
	
	@SuppressWarnings({ "serial", "boxing" })
	private final Map<Material, Double> wallMaterials = new HashMap<Material, Double>() {{
		put(Material.STONE, 1.0d);
		put(Material.DEAD_BRAIN_CORAL_BLOCK, 0.1d);
		put(Material.DEAD_BUBBLE_CORAL_BLOCK, 0.1d);
		put(Material.DEAD_HORN_CORAL_BLOCK, 0.1d);
		put(Material.COBBLESTONE, 1.0d);
		put(Material.COAL_ORE, 0.05d);
		put(Material.IRON_ORE, 0.005d);
		put(Material.DIAMOND_ORE, 0.0008d);
	}};
	
	@SuppressWarnings({ "serial", "boxing" })
	private final Map<Material, Double> groundMaterials = new HashMap<Material, Double>() {{
		put(Material.DIRT, 1.0d);
		put(Material.COARSE_DIRT, 0.2d);
		put(Material.GRAVEL, 0.2d);
	}};
	
	@SuppressWarnings({ "boxing", "serial" })
	private final Map<EntityType, Double> fishTypes = new HashMap<EntityType, Double>() {{
		put(EntityType.COD, 1.0d);
	}};
	
	
	@Override
	public void generatePointOfInterest(Main main, Location pointOfInterestLocation) {
		for (int x = -this.range - 2; x < this.range + 2; x++) {
			for (int y = -this.range - 2; y < this.range + 2; y++) {
				for (int z = -this.range - 2; z < this.range + 2; z++) {
					Location blockLocation = LocationHelper.getInstance().offsetLocation(pointOfInterestLocation,
							new Vector(x, y, z));
					if (blockLocation.distance(pointOfInterestLocation) <= this.range) {
						Material blockMaterial = blockLocation.getY() < pointOfInterestLocation.getY() ? Material.WATER
								: Material.AIR;
						blockLocation.getBlock().setType(blockMaterial);
						
						if (blockLocation.getBlockY() == pointOfInterestLocation.getBlockY()
								&& MathHelper.getInstance().hasChanceHit(10)) {
							blockLocation.getBlock().setType(Material.LILY_PAD);
						}
					} else if (blockLocation.distance(pointOfInterestLocation) > this.range && blockLocation.distance(pointOfInterestLocation) < this.range + 2) {
						Block block = blockLocation.getBlock();
						if (block.getType() != Material.CAVE_AIR && block.getType() != Material.AIR) {
							Map<Material, Double> materialList = blockLocation.getY() < pointOfInterestLocation.getY() - 1
									? this.groundMaterials
									: this.wallMaterials;
							block.setType((Material) this.selector.selectRandomObjectFromWeightedList(materialList));
						}
					}
				}
			}
		}
		for (int i = 0; i < this.amountOfFish; i++) {
			pointOfInterestLocation.getWorld().spawnEntity(pointOfInterestLocation, (EntityType) this.selector.selectRandomObjectFromWeightedList(this.fishTypes));
		}
	}
}
