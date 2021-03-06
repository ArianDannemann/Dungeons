package org.Dungeons.Dungeons;

import java.util.HashMap;
import java.util.Random;

import org.Dungeons.BossMob;
import org.Dungeons.Dungeon;
import org.Dungeons.Loot;
import org.Dungeons.Main;
import org.Dungeons.PointOfInterest;
import org.Dungeons.BossMobs.Godylis;
import org.Dungeons.PointsOfInterest.Collapse;
import org.Dungeons.PointsOfInterest.LootChest;
import org.Dungeons.PointsOfInterest.Pillars;
import org.Dungeons.PointsOfInterest.Stalacmites;
import org.Dungeons.PointsOfInterest.Stalactites;
import org.Dungeons.PointsOfInterest.TrappedLootChest;
import org.Dungeons.PointsOfInterest.UndergroundSea;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.EntityType;
import org.bukkit.inventory.ItemStack;
import org.bukkit.util.Vector;

import com.PluginBase.LocationHelper;
import com.PluginBase.MathHelper;

public class StoneDungeon extends Dungeon {

	public StoneDungeon(Main main) {
		super(main);
	}

	@SuppressWarnings({ "serial", "boxing" })
	@Override
	public void setValues() {
		this.dungeonSize = 100;
		this.corridorLength = 10;
		this.bossMobPossibility = 100;
		this.entityPossibility = 80;
		this.pointOfInterestPossibility = 30;

		setWhitelist(new Material[] { Material.CHEST, Material.SPAWNER, Material.BEDROCK, Material.END_PORTAL });
		setGroundMaterials(new HashMap<Material, Double>() {
			{
				put(Material.STONE, 1.0d);
				put(Material.STONE_SLAB, 0.5d);
				put(Material.COBBLESTONE, 1.0d);
				put(Material.COBBLESTONE_SLAB, 0.5d);
				put(Material.MOSSY_COBBLESTONE, 0.3d);
			}
		});
		setWallMaterials(new HashMap<Material, Double>() {
			{
				put(Material.STONE, 1.0d);
				put(Material.COBBLESTONE, 1.0d);
				put(Material.COBWEB, 0.05d);
				put(Material.COAL_ORE, 0.05d);
				put(Material.IRON_ORE, 0.005d);
				put(Material.LAVA, 0.0009d);
				put(Material.WATER, 0.0009d);
				put(Material.DIAMOND_ORE, 0.0008d);
			}
		});
		setStructureMaterial(new HashMap<Material, Double>() {
			{
				put(Material.STONE_BRICKS, 1.0d);
				put(Material.CRACKED_STONE_BRICKS, 1.0d);
				put(Material.CHISELED_STONE_BRICKS, 1.0d);
				put(Material.STONE_BRICK_WALL, 1.0d);
				put(Material.STONE_BRICK_SLAB, 1.0d);
			}
		});
		setPointsOfInterest(new HashMap<PointOfInterest, Double>() {
			{
				put(new LootChest(), 1.0d);
				put(new TrappedLootChest(), 0.25d);
				put(new Stalactites(), 2.0d);
				put(new Stalacmites(), 2.0d);
				put(new Pillars(), 1.5d);
				put(new Collapse(), 1.5d);
				put(new UndergroundSea(), 0.5d);
			}
		});
		setBossMobs(new HashMap<BossMob, Double>() {
			{
				put(new Godylis(), 1.0d);
			}
		});
		setEntityTypes(new HashMap<EntityType, Double>() {
			{
				put(EntityType.ZOMBIE, 6.0d);
				put(EntityType.SKELETON, 1.0d);
				put(EntityType.WITCH, 1.5d);
				put(EntityType.ILLUSIONER, 1.0d);
			}
		});
		setLootTable(new HashMap<Loot, Double>() {
			{
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
			}
		});
	}

	@Override
	public void generate(Location dungeonLocation) {
		Random random = new Random();
		Vector roomSize = new Vector(3 + random.nextInt(6), 3 + random.nextInt(2), 3 + random.nextInt(6));
		Location roomLocation = dungeonLocation;

		// Chat.getInstance().sendErrorToConsole("Dungeons", "#1", "Generating
		// dungeon...");

		for (int i = 0; i < this.dungeonSize; i++) {

			// Generate a room with the randomly set position and size
			generateRoom(roomLocation, roomSize);

			// Every second room should be a corridor
			boolean nextRoomIsCorridor = i % 2 == 0;

			if (nextRoomIsCorridor) {

				// Generate a point of interest in the middle of the room
				generatePointOfInterest(getRoomCenter(roomLocation, roomSize));

				// Spawn a random entity
				spawnEntity(getRoomCenter(roomLocation, roomSize));

				/*
				 * Size and location for next corridor
				 */

				// Every room has a 30% chance of generating two corridors
				if (MathHelper.getInstance().hasChanceHit(30)) {
					generateRoom(getRoomCenter(roomLocation, roomSize), getNextCorridorSize());
				}

				// Set values for the corridor
				roomLocation = getRoomCenter(roomLocation, roomSize);
				roomSize = getNextCorridorSize();
			} else {
				/*
				 * Size and location for next room
				 */

				// Get the distance between the start of the corridor to the end of the corridor
				// -1 because we need to get a location inside the walls
				Vector corridorEndOffset = new Vector(roomSize.getX() > 0 ? roomSize.getX() - 1 : roomSize.getX() + 1,
						0, roomSize.getZ() > 0 ? roomSize.getZ() - 1 : roomSize.getZ() + 1);
				// Get the actual location at the end of the corridor (no relative values any
				// more)
				Location corridorEndLocation = LocationHelper.getInstance().offsetLocation(roomLocation,
						corridorEndOffset);
				// Set a random size for the new room
				roomSize = new Vector(3 + random.nextInt(6), 3 + random.nextInt(2), 3 + random.nextInt(6));
				// Move the corner of the room so it's centered at the end of the corridor
				roomLocation = LocationHelper.getInstance().offsetLocation(corridorEndLocation,
						new Vector(-roomSize.getX() / 2, 0, -roomSize.getX() / 2));
			}
		}
		generatePointsOfInterest();
	}
}
