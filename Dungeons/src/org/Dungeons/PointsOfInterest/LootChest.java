package org.Dungeons.PointsOfInterest;

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

import com.PluginBase.Chat;
import com.PluginBase.MathHelper;

public class LootChest extends PointOfInterest {
	
	private LootGenerator lootGenerator = new LootGenerator();
	
	@SuppressWarnings("unused")
	public void generatePointOfInterest(Main main, Location pointOfInterestLocation, Map<Loot, Double> lootTable) {
		pointOfInterestLocation.getBlock().setType(Material.CHEST);
		Chest chest = (Chest) pointOfInterestLocation.getBlock().getState();
		chest.getInventory().setContents(this.lootGenerator.generateChestContent(27, lootTable));
		
		if (MathHelper.getInstance().hasChanceHit(30)) {
			new EntitySpawn(main, EntityType.CREEPER, pointOfInterestLocation, 3);
		}
	}

	@Override
	public void generatePointOfInterest(Main main, Location pointOfInterestLocation) {
		Chat.getInstance().sendWarningMessageToConsole(main, "Tried to generate loot chest without loot table");
	}
}
