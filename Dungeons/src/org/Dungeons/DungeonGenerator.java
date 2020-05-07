package org.Dungeons;

import org.Dungeons.Dungeons.*;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.block.Biome;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.world.ChunkPopulateEvent;

import com.PluginBase.Chat;
import com.PluginBase.MathHelper;

public class DungeonGenerator implements Listener {

	private Main main;
	// Chance to spawn a new dungeon when a chunk generates
	private final double dungeonChance = 0.05;

	public DungeonGenerator(Main main) {
		Bukkit.getPluginManager().registerEvents(this, main);
		this.main = main;
	}

	@EventHandler
	public void onChunkGenerator(ChunkPopulateEvent event) {
		if (MathHelper.getInstance().hasChanceHit(this.dungeonChance)) {

			// Get the block position of the new dungeon
			Location dungeonLocation = new Location(event.getWorld(), event.getChunk().getX() * 16, 30,
					event.getChunk().getZ() * 16);

			// Play a lightning effect at the dungeon location
			event.getWorld().strikeLightning(dungeonLocation);
			// Broadcast the location of the dungeon to players
			Chat.getInstance().broadcastMessage("Generated dungeon at " + dungeonLocation.getX() + " "
					+ dungeonLocation.getY() + " " + dungeonLocation.getZ());

			// Get the biome that the dungeon will spawn in
			Biome dungeonBiome = dungeonLocation.getBlock().getBiome();

			// Create and generate a new dungeon
			Dungeon dungeon = getDungeonType(dungeonBiome);
			dungeon.generate(dungeonLocation);
		}
	}

	public Dungeon getDungeonType(Biome biome) {
		if (biome == Biome.DESERT || biome == Biome.DESERT_HILLS || biome == Biome.DESERT_LAKES) {
			return new DesertDungeon(this.main);
		}
		return new StoneDungeon(this.main);
	}
}
