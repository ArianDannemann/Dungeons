package org.Dungeons;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Random;

import org.Dungeons.PointsOfInterest.*;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.EntityType;
import org.bukkit.util.Vector;

import com.PluginBase.LocationHelper;
import com.PluginBase.MathHelper;

public abstract class Dungeon {

	private Main main;
	private List<Location> pointOfInterestLocations = new ArrayList<>();
	public Selector selector = new Selector();

	private Material[] whitelist;
	private Map<Material, Double> groundMaterials, wallMaterials, structureMaterials;
	private Map<PointOfInterest, Double> pointsOfInterest;
	private Map<BossMob, Double> bossMobs;
	private Map<EntityType, Double> entityTypes;
	private Map<Loot, Double> lootTable;

	public int dungeonSize, corridorLength, bossMobPossibility, entityPossibility, pointOfInterestPossibility;

	public Dungeon(Main main) {
		this.setMain(main);
		setValues();
	}

	public abstract void setValues();

	public abstract void generate(Location dungeonLocation);

	/**
	 * Generate a room (with walls and stuff) based on a location and a size
	 * 
	 * @param roomLocation
	 *            One of the corners of the room
	 * @param size
	 *            The size of room (Vector3)
	 */
	public void generateRoom(Location roomLocation, Vector size) {

		// Essentially the other corner of the room
		Location targetLocation = LocationHelper.getInstance().offsetLocation(roomLocation, size);

		// Get a list of all blocks in the room that should be set to air
		List<Block> airBlocks = LocationHelper.getInstance().getBlocksBetweenLocations(roomLocation, targetLocation);
		// Set all blocks in the list to air
		for (Block airBlock : airBlocks) {
			if (!isMaterialOnWhitelist(airBlock.getType())) {
				airBlock.setType(Material.AIR);
			}
		}

		// Get a list of all blocks in the room that should be set to air
		List<Block> groundBlocks = LocationHelper.getInstance().getBlocksBetweenLocations(
				LocationHelper.getInstance().offsetLocation(roomLocation, new Vector(0, -1, 0)),
				LocationHelper.getInstance().offsetLocation(targetLocation,
						new Vector(0, -(targetLocation.getY() - roomLocation.getY()), 0)));
		// Set all blocks in the list to stone
		for (Block groundBlock : groundBlocks) {
			groundBlock.setType((Material) this.selector.selectRandomObjectFromWeightedList(getGroundMaterials()));
		}

		// Get a list of all blocks in the room that should be set to walls
		List<Block> wallBlocks = LocationHelper.getInstance().getBlocksBetweenLocations(
				LocationHelper.getInstance().offsetLocation(
						LocationHelper.getInstance().getSmallerLocation(roomLocation, targetLocation),
						new Vector(-1, 0, -1)),
				LocationHelper.getInstance().offsetLocation(
						LocationHelper.getInstance().getBiggerLocation(roomLocation, targetLocation),
						new Vector(1, 1, 1)));
		// Set all blocks in the list to cobblestone if they are not cave air (center of
		// the room)
		for (Block wallBlock : wallBlocks) {
			if (wallBlock.getType() != Material.CAVE_AIR && wallBlock.getType() != Material.AIR
					&& !isMaterialOnWhitelist(wallBlock.getType())) {
				wallBlock.setType((Material) this.selector.selectRandomObjectFromWeightedList(getWallMaterials()));
			}
		}
	}

	public void generatePointOfInterest(Location location) {
		if (MathHelper.getInstance().hasChanceHit(this.pointOfInterestPossibility)) {
			this.pointOfInterestLocations.add(location);
		}
	}

	/*
	 * Spawn point of interest structures at the previously defined locations
	 */
	public void generatePointsOfInterest() {
		
		// Loop through all locations at which a point of interest should be created
		for (Location location : this.pointOfInterestLocations) {
			
			// Select a random point of interest
			PointOfInterest pointOfInterest = (PointOfInterest) this.selector
					.selectRandomObjectFromWeightedList(getPointsOfInterest());

			if (pointOfInterest instanceof LootChest) {
				((LootChest) pointOfInterest).generatePointOfInterest(this.main, location, this.lootTable);
			} else if (pointOfInterest instanceof Collapse) {
				((Collapse) pointOfInterest).generatePointOfInterest(this.main, location, this.structureMaterials);
			} else if (pointOfInterest instanceof Pillars) {
				((Pillars) pointOfInterest).generatePointOfInterest(this.main, location, this.structureMaterials);
			} else {
				pointOfInterest.generatePointOfInterest(this.main, location);
			}
		}

		// Spawn a boss mob at a random location
		if (MathHelper.getInstance().hasChanceHit(this.bossMobPossibility)) {
			Random random = new Random();
			Location location = this.pointOfInterestLocations.get(random.nextInt(this.pointOfInterestLocations.size()));
			BossMob bossMob = (BossMob) this.selector.selectRandomObjectFromWeightedList(getBossMobs());
			bossMob.spawn(getMain(), location);
		}
	}

	@SuppressWarnings("unused")
	public void spawnEntity(Location location) {
		if (MathHelper.getInstance().hasChanceHit(this.entityPossibility)) {
			new EntitySpawn(getMain(), (EntityType) this.selector.selectRandomObjectFromWeightedList(getEntityTypes()),
					location, 10);
		}
	}

	/**
	 * Generate the size for the next corridor
	 * 
	 * @return Size for the next corridor
	 */
	public Vector getNextCorridorSize() {
		// Prepare a vector for the size of the corridor
		Vector corridorSize = new Vector(0, 3, 0);
		// Choose between X and Z axis for the corridor
		if (MathHelper.getInstance().hasChanceHit(50)) {
			// Choose which direction (on the X axis) the corridor should face
			corridorSize.setX(MathHelper.getInstance().hasChanceHit(50) ? -this.corridorLength : this.corridorLength);
			corridorSize.setZ(2);
		} else {
			// Choose which direction (on the Z axis) the corridor should face
			corridorSize.setZ(MathHelper.getInstance().hasChanceHit(50) ? -this.corridorLength : this.corridorLength);
			corridorSize.setX(2);
		}
		return corridorSize;
	}

	/**
	 * Generate the starting location for the next corridor
	 * 
	 * @param previousRoomLocation
	 *            The starting corner of the current room
	 * @param previousRoomSize
	 *            The size of the current room
	 * @return Starting location for the next corridor
	 */
	public Location getRoomCenter(Location roomLocation, Vector roomSize) {
		// Get the center of the room for the corridor start location
		return LocationHelper.getInstance().offsetLocation(roomLocation,
				new Vector(roomSize.getX() / 2 - 1, 0, roomSize.getZ() / 2 - 1));
	}

	/**
	 * Check if a given material is on the whitelist of materials
	 * 
	 * @param material
	 *            The material that should be checked
	 * @return If the material is on the whitelist
	 */
	public boolean isMaterialOnWhitelist(Material material) {
		for (Material whitelistedMaterial : this.whitelist) {
			if (whitelistedMaterial == material) {
				return true;
			}
		}
		return false;
	}

	/**
	 * @return the whitelist
	 */
	public Material[] getWhitelist() {
		return this.whitelist;
	}

	/**
	 * @param whitelist
	 *            the whitelist to set
	 */
	public void setWhitelist(Material[] whitelist) {
		this.whitelist = whitelist;
	}

	/**
	 * @return the groundMaterials
	 */
	public Map<Material, Double> getGroundMaterials() {
		return this.groundMaterials;
	}

	/**
	 * @param groundMaterials
	 *            the groundMaterials to set
	 */
	public void setGroundMaterials(Map<Material, Double> groundMaterials) {
		this.groundMaterials = groundMaterials;
	}

	/**
	 * @return the wallMaterials
	 */
	public Map<Material, Double> getWallMaterials() {
		return this.wallMaterials;
	}

	/**
	 * @param wallMaterials
	 *            the wallMaterials to set
	 */
	public void setWallMaterials(Map<Material, Double> wallMaterials) {
		this.wallMaterials = wallMaterials;
	}

	/**
	 * @return the pointsOfInterest
	 */
	public Map<PointOfInterest, Double> getPointsOfInterest() {
		return this.pointsOfInterest;
	}

	/**
	 * @param pointsOfInterest
	 *            the pointsOfInterest to set
	 */
	public void setPointsOfInterest(Map<PointOfInterest, Double> pointsOfInterest) {
		this.pointsOfInterest = pointsOfInterest;
	}

	/**
	 * @return the bossMobs
	 */
	public Map<BossMob, Double> getBossMobs() {
		return this.bossMobs;
	}

	/**
	 * @param bossMobs
	 *            the bossMobs to set
	 */
	public void setBossMobs(Map<BossMob, Double> bossMobs) {
		this.bossMobs = bossMobs;
	}

	/**
	 * @return the entityTypes
	 */
	public Map<EntityType, Double> getEntityTypes() {
		return this.entityTypes;
	}

	/**
	 * @param entityTypes
	 *            the entityTypes to set
	 */
	public void setEntityTypes(Map<EntityType, Double> entityTypes) {
		this.entityTypes = entityTypes;
	}

	/**
	 * @return the main
	 */
	public Main getMain() {
		return this.main;
	}

	/**
	 * @param main
	 *            the main to set
	 */
	public void setMain(Main main) {
		this.main = main;
	}

	/**
	 * @return the lootTable
	 */
	public Map<Loot, Double> getLootTable() {
		return this.lootTable;
	}

	/**
	 * @param lootTable
	 *            the lootTable to set
	 */
	public void setLootTable(Map<Loot, Double> lootTable) {
		this.lootTable = lootTable;
	}

	/**
	 * @return the structureMaterial
	 */
	public Map<Material, Double> getStructureMaterial() {
		return this.structureMaterials;
	}

	/**
	 * @param structureMaterial the structureMaterial to set
	 */
	public void setStructureMaterial(Map<Material, Double> structureMaterial) {
		this.structureMaterials = structureMaterial;
	}
}
