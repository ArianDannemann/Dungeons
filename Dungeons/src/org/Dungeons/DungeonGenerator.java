package org.Dungeons;

import org.Dungeons.Dungeons.StoneDungeon;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.world.ChunkPopulateEvent;

import com.PluginBase.Chat;
import com.PluginBase.MathHelper;

public class DungeonGenerator implements Listener {
	
	private Main main;
	private final double dungeonChance = 0.05;
	
	public DungeonGenerator(Main main) {
		Bukkit.getPluginManager().registerEvents(this, main);
		this.main = main;
	}

	@EventHandler
	public void onChunkGenerator(ChunkPopulateEvent event) {
		if (MathHelper.getInstance().hasChanceHit(this.dungeonChance)) {
			Location dungeonLocation = new Location(event.getWorld(), event.getChunk().getX() * 16, 30,
					event.getChunk().getZ() * 16);
			event.getWorld().strikeLightning(dungeonLocation);
			Chat.getInstance().broadcastMessage("Generated dungeon at " + dungeonLocation.getX() + " "
					+ dungeonLocation.getY() + " " + dungeonLocation.getZ());
			StoneDungeon dungeon = new StoneDungeon(this.main);
			dungeon.generate(dungeonLocation);
		}
	}
}
