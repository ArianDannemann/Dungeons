package org.Dungeons.Dungeons;

import java.util.HashMap;
import java.util.Random;

import org.Dungeons.BossMob;
import org.Dungeons.Dungeon;
import org.Dungeons.Loot;
import org.Dungeons.Main;
import org.Dungeons.PointOfInterest;
import org.Dungeons.BossMobs.Godylis;
import org.Dungeons.PointsOfInterest.*;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.EntityType;
import org.bukkit.inventory.ItemStack;
import org.bukkit.util.Vector;

import com.PluginBase.LocationHelper;
import com.PluginBase.MathHelper;

public class DesertDungeon extends Dungeon {

	public DesertDungeon(Main main) {
		super(main);
	}

	@SuppressWarnings({ "serial", "boxing" })
	@Override
	public void setValues() {
		this.dungeonSize = 100;
		this.corridorLength = 10;
		this.bossMobPossibility = 0;
		this.entityPossibility = 80;
		this.pointOfInterestPossibility = 50;

		setWhitelist(new Material[] { Material.CHEST, Material.SPAWNER, Material.BEDROCK, Material.END_PORTAL });
		setGroundMaterials(new HashMap<Material, Double>() {
			{
				put(Material.SANDSTONE, 1.0d);
				put(Material.SAND, 1.0d);
			}
		});
		setWallMaterials(new HashMap<Material, Double>() {
			{
				put(Material.SANDSTONE, 1.0d);
				put(Material.SMOOTH_SANDSTONE, 1.0d);
				put(Material.CUT_SANDSTONE, 1.0d);
				put(Material.CHISELED_SANDSTONE, 1.0d);
			}
		});
		setStructureMaterial(new HashMap<Material, Double>() {
			{
				put(Material.COBWEB, 1.0d);
				put(Material.SMOOTH_SANDSTONE_SLAB, 1.0d);
				put(Material.PLAYER_HEAD, 0.1d);
			}
		});
		setPointsOfInterest(new HashMap<PointOfInterest, Double>() {
			{
				put(new LootChest(), 1.0d);
				put(new TrappedLootChest(), 0.25d);
				put(new Shelf(), 5.0d);
				put(new Collapse(), 1.0d);
			}
		});
		setBossMobs(new HashMap<BossMob, Double>() {
			{
				put(new Godylis(), 1.0d);
			}
		});
		setEntityTypes(new HashMap<EntityType, Double>() {
			{
				put(EntityType.HUSK, 2.0d);
				put(EntityType.SKELETON, 1.0d);
			}
		});
		setLootTable(new HashMap<Loot, Double>() {
			{
				put(new Loot(new ItemStack(Material.AIR)), 3.0d);
				put(new Loot(new ItemStack(Material.SANDSTONE), 15), 0.75d);
				put(new Loot(new ItemStack(Material.SAND), 15), 0.75d);
				put(new Loot(new ItemStack(Material.CHISELED_SANDSTONE), 15), 0.75d);
				put(new Loot(new ItemStack(Material.CUT_SANDSTONE_SLAB), 15), 0.75d);
				put(new Loot(new ItemStack(Material.BONE), 10), 0.5d);
				put(new Loot(new ItemStack(Material.STRING), 10), 0.5d);
				put(new Loot(new ItemStack(Material.BONE_BLOCK), 5), 0.2d);
				put(new Loot(new ItemStack(Material.IRON_INGOT), 5), 0.35d);
				put(new Loot(new ItemStack(Material.IRON_NUGGET), 5), 0.35d);
				put(new Loot(new ItemStack(Material.GOLD_INGOT), 5), 0.35d);
				put(new Loot(new ItemStack(Material.GOLD_NUGGET), 5), 0.35d);
				put(new Loot(new ItemStack(Material.GOLDEN_APPLE)), 0.05d);
				put(new Loot(new ItemStack(Material.DIAMOND)), 0.1d);
				put(new Loot(new ItemStack(Material.EMERALD)), 0.1d);
				put(new Loot(new ItemStack(Material.BREAD), 5), 0.75d);
				put(new Loot(new ItemStack(Material.APPLE), 5), 0.75d);
				put(new Loot(new ItemStack(Material.SUGAR), 5), 0.75d);
				put(new Loot(new ItemStack(Material.BLACK_DYE), 64), 0.05d);
				put(new Loot(new ItemStack(Material.BLUE_DYE), 64), 0.05d);
				put(new Loot(new ItemStack(Material.BROWN_DYE), 64), 0.05d);
				put(new Loot(new ItemStack(Material.CYAN_DYE), 64), 0.05d);
				put(new Loot(new ItemStack(Material.GRAY_DYE), 64), 0.05d);
				put(new Loot(new ItemStack(Material.GREEN_DYE), 64), 0.05d);
				put(new Loot(new ItemStack(Material.LIME_DYE), 64), 0.05d);
				put(new Loot(new ItemStack(Material.MAGENTA_DYE), 64), 0.05d);
				put(new Loot(new ItemStack(Material.ORANGE_DYE), 64), 0.05d);
				put(new Loot(new ItemStack(Material.PINK_DYE), 64), 0.05d);
				put(new Loot(new ItemStack(Material.PURPLE_DYE), 64), 0.05d);
				put(new Loot(new ItemStack(Material.RED_DYE), 64), 0.05d);
				put(new Loot(new ItemStack(Material.WHITE_DYE), 64), 0.05d);
				put(new Loot(new ItemStack(Material.YELLOW_DYE), 64), 0.05d);
				put(new Loot(new ItemStack(Material.LIGHT_BLUE_DYE), 64), 0.05d);
				put(new Loot(new ItemStack(Material.LIGHT_GRAY_DYE), 64), 0.05d);
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
