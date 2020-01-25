package org.Dungeons.PointsOfInterest;

import java.util.HashMap;
import java.util.Map;

import org.Dungeons.EntitySpawn;
import org.Dungeons.Loot;
import org.Dungeons.LootGenerator;
import org.Dungeons.Main;
import org.Dungeons.PointOfInterest;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Chest;
import org.bukkit.entity.EntityType;
import org.bukkit.inventory.ItemStack;

import com.PluginBase.MathHelper;

public class LootChest extends PointOfInterest {
	
	private LootGenerator lootGenerator = new LootGenerator();
	
	@SuppressWarnings({ "boxing", "serial" })
	private final Map<Loot, Double> lootTable = new HashMap<Loot, Double>() {{
		put(new Loot(new ItemStack(Material.AIR)), 2.0d);
		put(new Loot(new ItemStack(Material.COBBLESTONE), 15), 0.75d);
		put(new Loot(new ItemStack(Material.MOSSY_COBBLESTONE), 15), 0.75d);
		put(new Loot(new ItemStack(Material.COBBLESTONE_SLAB), 15), 0.75d);
		put(new Loot(new ItemStack(Material.MOSSY_COBBLESTONE_SLAB), 15), 0.75d);
		put(new Loot(new ItemStack(Material.COAL), 10), 0.5d);
		put(new Loot(new ItemStack(Material.IRON_INGOT), 5), 0.35d);
		put(new Loot(new ItemStack(Material.IRON_NUGGET), 5), 0.35d);
		put(new Loot(new ItemStack(Material.GOLD_INGOT), 5), 0.35d);
		put(new Loot(new ItemStack(Material.GOLD_NUGGET), 5), 0.35d);
		put(new Loot(new ItemStack(Material.GOLDEN_APPLE)), 0.05d);
		put(new Loot(new ItemStack(Material.DIAMOND)), 0.1d);
		put(new Loot(new ItemStack(Material.EMERALD)), 0.1d);
		put(new Loot(new ItemStack(Material.STRING), 5), 0.75d);
		put(new Loot(new ItemStack(Material.BREAD), 5), 0.75d);
		put(new Loot(new ItemStack(Material.ENCHANTED_GOLDEN_APPLE)), 0.0001d);
	}};
	
	@SuppressWarnings("unused")
	@Override
	public void generatePointOfInterest(Main main, Location pointOfInterestLocation) {
		pointOfInterestLocation.getBlock().setType(Material.CHEST);
		Chest chest = (Chest) pointOfInterestLocation.getBlock().getState();
		chest.getInventory().setContents(this.lootGenerator.generateChestContent(27, this.lootTable));
		
		if (MathHelper.getInstance().hasChanceHit(30)) {
			new EntitySpawn(main, EntityType.CREEPER, pointOfInterestLocation, 3);
		}
	}

}