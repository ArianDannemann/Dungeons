package org.Dungeons.PointsOfInterest;

import java.util.HashMap;
import java.util.Map;

import org.Dungeons.Loot;
import org.Dungeons.LootGenerator;
import org.Dungeons.Main;
import org.Dungeons.PointOfInterest;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Chest;
import org.bukkit.inventory.ItemStack;
import org.bukkit.util.Vector;

import com.PluginBase.LocationHelper;

public class TrappedLootChest extends PointOfInterest {
	
	private LootGenerator lootGenerator = new LootGenerator();
	
	@SuppressWarnings({ "boxing", "serial" })
	private final Map<Loot, Double> lootTable = new HashMap<Loot, Double>() {{
		put(new Loot(new ItemStack(Material.AIR)), 2.0d);
		put(new Loot(new ItemStack(Material.DIRT), 15), 1.0d);
		put(new Loot(new ItemStack(Material.COARSE_DIRT), 15), 1.0d);
		put(new Loot(new ItemStack(Material.DEAD_BUSH)), 1.0d);
	}};
	
	@Override
	public void generatePointOfInterest(Main main, Location pointOfInterestLocation) {
		pointOfInterestLocation.getBlock().setType(Material.TRAPPED_CHEST);
		Chest chest = (Chest) pointOfInterestLocation.getBlock().getState();
		chest.getInventory().setContents(this.lootGenerator.generateChestContent(27, this.lootTable));
		LocationHelper.getInstance().offsetLocation(pointOfInterestLocation, new Vector(0, -2, 0)).getBlock()
				.setType(Material.TNT);
	}
}
