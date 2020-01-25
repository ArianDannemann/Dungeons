package org.Dungeons;

import org.Dungeons.Dungeons.TestDungeon;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.world.ChunkPopulateEvent;

import com.PluginBase.Chat;
import com.PluginBase.MathHelper;

public class DungeonGenerator implements Listener {
	
	private Main main;
	
	public DungeonGenerator(Main main) {
		Bukkit.getPluginManager().registerEvents(this, main);
		this.main = main;
	}

	@EventHandler
	public void onChunkGenerator(ChunkPopulateEvent event) {
		if (MathHelper.getInstance().hasChanceHit(0.1)) {
			Location dungeonLocation = new Location(event.getWorld(), event.getChunk().getX() * 16, 30,
					event.getChunk().getZ() * 16);
			event.getWorld().strikeLightning(dungeonLocation);
			Chat.getInstance().broadcastMessage("Generated dungeon at " + dungeonLocation.getX() + " "
					+ dungeonLocation.getY() + " " + dungeonLocation.getZ());
			TestDungeon dungeon = new TestDungeon(this.main);
			dungeon.generate(dungeonLocation);
		}
	}
}