package org.Dungeons.PointsOfInterest;

import java.util.HashMap;
import java.util.Map;

import org.Dungeons.Main;
import org.Dungeons.PointOfInterest;
import org.Dungeons.Selector;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.util.Vector;

import com.PluginBase.LocationHelper;

public class Pillars extends PointOfInterest {

	private final int range = 15, amount = 30;

	private Selector selector = new Selector();

	@SuppressWarnings({ "boxing", "serial" })
	private final Map<Material, Double> collapsedMaterials = new HashMap<Material, Double>() {
		{
			put(Material.STONE_BRICKS, 1.0d);
			put(Material.CRACKED_STONE_BRICKS, 1.0d);
			put(Material.CHISELED_STONE_BRICKS, 1.0d);
			put(Material.STONE_BRICK_WALL, 2.0d);
			put(Material.COBBLESTONE_WALL, 2.0d);
		}
	};

	@Override
	public void generatePointOfInterest(Main main, Location pointOfInterestLocation) {
		for (int i = 0; i < this.amount; i++) {
			Location pillarLocation = LocationHelper.getInstance().getRandomNearbyPosition(pointOfInterestLocation,
					this.range);
			for (int y = 0; y < 4; y++) {
				LocationHelper.getInstance().offsetLocation(pillarLocation, new Vector(0, y, 0)).getBlock()
						.setType((Material) this.selector.selectRandomObjectFromWeightedList(this.collapsedMaterials));
			}
		}
	}
}