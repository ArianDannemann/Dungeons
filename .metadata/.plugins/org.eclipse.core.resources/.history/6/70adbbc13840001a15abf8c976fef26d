package org.Dungeons.BossMobs;

import org.Dungeons.BossMob;
import org.Dungeons.Main;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Particle;
import org.bukkit.Sound;
import org.bukkit.SoundCategory;
import org.bukkit.World;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.FallingBlock;
import org.bukkit.entity.Player;
import org.bukkit.entity.Zombie;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.Vector;

import com.PluginBase.ItemCreator;
import com.PluginBase.LocationHelper;
import com.PluginBase.MathHelper;
import com.PluginBase.ParticleEmitter;
import com.PluginBase.SoundEmitter;

public class Godylis extends BossMob {

	private Main main;
	private Zombie godylis;
	private final float dropChance = 0.1f;

	@Override
	public void spawn(Main plugin, Location spawnLocation) {

		this.main = plugin;
		Bukkit.getPluginManager().registerEvents(this, this.main);

		// Get the world in which godylis should be spawned
		World world = spawnLocation.getWorld();

		// Spawn godylis
		this.godylis = (Zombie) world.spawnEntity(spawnLocation, EntityType.ZOMBIE);

		// Set some general information
		this.godylis.setCustomName(ChatColor.RED + "Godylis");
		this.godylis.setRemoveWhenFarAway(false);

		// Generate his equipment
		ItemStack axe = ItemCreator.getInstance().createItem(Material.IRON_AXE, 1, ChatColor.RED + "Godylis' Axe",
				"This mighty axe was infused\nwith the stone of Belynea");
		axe.addEnchantment(Enchantment.DAMAGE_ALL, 1);
		ItemStack helmet = ItemCreator.getInstance().createItem(Material.IRON_HELMET, 1,
				ChatColor.RED + "Godylis' Helmet", "This helmet was once worn\nby the undead warrior Godylis");
		helmet.addEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL, 2);
		ItemStack chestplate = ItemCreator.getInstance().createItem(Material.DIAMOND_CHESTPLATE, 1,
				ChatColor.RED + "Godylis' Chestplate", "This chestplate was once worn\nby the undead warrior Godylis");
		chestplate.addEnchantment(Enchantment.PROTECTION_FIRE, 1);
		ItemStack leggings = ItemCreator.getInstance().createItem(Material.LEATHER_LEGGINGS, 1,
				ChatColor.RED + "Godylis' Pants",
				"These pants were once worn\nby the undead warrior Godylis\nThe have a somewhat unpleasent smell to them");
		leggings.addEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL, 1);
		ItemStack boots = ItemCreator.getInstance().createItem(Material.IRON_BOOTS, 1, ChatColor.RED + "Godylis' Boots",
				"These boots was were worn\nby the undead warrior Godylis");
		boots.addEnchantment(Enchantment.PROTECTION_FALL, 1);

		// Set equipment
		this.godylis.getEquipment().setItemInMainHand(axe);
		this.godylis.getEquipment().setItemInMainHandDropChance(this.dropChance);
		this.godylis.getEquipment().setHelmet(helmet);
		this.godylis.getEquipment().setHelmetDropChance(this.dropChance);
		this.godylis.getEquipment().setChestplate(chestplate);
		this.godylis.getEquipment().setChestplateDropChance(this.dropChance);
		this.godylis.getEquipment().setLeggings(leggings);
		this.godylis.getEquipment().setLeggingsDropChance(this.dropChance);
		this.godylis.getEquipment().setBoots(boots);
		this.godylis.getEquipment().setBootsDropChance(this.dropChance);

		SoundEmitter.getInstance().emitSound(world, spawnLocation, Sound.ENTITY_ZOMBIE_AMBIENT, SoundCategory.HOSTILE,
				3, 1);
		// SoundEmitter.getInstance().emitSound(world, spawnLocation,
		// Sound.MUSIC_DISC_13, SoundCategory.HOSTILE, 1, 1);

		startEventTimer();
	}

	public void startEventTimer() {
		BukkitRunnable bukkitRunnable = new BukkitRunnable() {
			@SuppressWarnings("synthetic-access")
			@Override
			public void run() {
				if (Godylis.this.godylis == null || Godylis.this.godylis.isDead()) {
					this.cancel();
				}
				useAbilities();
			}
		};
		bukkitRunnable.runTaskTimer(this.main, 20, 20);
	}

	public void useAbilities() {
		for (Player onlinePlayer : Bukkit.getOnlinePlayers()) {
			if (onlinePlayer.getLocation().distance(this.godylis.getLocation()) <= 20
					&& onlinePlayer.hasLineOfSight(this.godylis)) {

				Location godylisLocation = this.godylis.getLocation();
				Location playerLocation = onlinePlayer.getLocation();

				/*
				 * 'Pull player' ability
				 */
				if (MathHelper.getInstance().hasChanceHit(10)) {
					Vector velocity = new Vector(godylisLocation.getX() - playerLocation.getX(), 2,
							godylisLocation.getZ() - playerLocation.getZ());
					onlinePlayer.setVelocity(velocity.multiply(0.2));

					ParticleEmitter.getInstance().emitParticles(playerLocation.getWorld(), playerLocation,
							Particle.CLOUD, 30, 0, new Vector(0.5, 1, 0.5));
					SoundEmitter.getInstance().emitSound(playerLocation.getWorld(), playerLocation,
							Sound.ENTITY_PHANTOM_FLAP, SoundCategory.PLAYERS, 1, 1);
				}

				/*
				 * 'Spawn zombie' ability
				 */
				if (MathHelper.getInstance().hasChanceHit(30)) {
					Zombie zombie = (Zombie) godylisLocation.getWorld().spawnEntity(godylisLocation, EntityType.ZOMBIE);
					zombie.addPotionEffect(new PotionEffect(PotionEffectType.WITHER, 1000000, 0, false, false));
					ParticleEmitter.getInstance().emitParticles(godylisLocation.getWorld(),
							LocationHelper.getInstance().offsetLocation(godylisLocation, new Vector(0, 0.5, 0)),
							Particle.CLOUD, 30, 0, new Vector(0.5, 1, 0.5));
					SoundEmitter.getInstance().emitSound(playerLocation.getWorld(), playerLocation,
							Sound.ENTITY_PHANTOM_FLAP, SoundCategory.PLAYERS, 1, 1);
					ParticleEmitter.getInstance().emitParticlesContinuously(zombie, new Vector(0, 0.25, 0),
							Particle.SQUID_INK, 10, 0, new Vector(0.25, 0.25, 0.25), this.main, 0, 5, 1000000);
				}

				/*
				 * 'Rage' ability
				 */
				if (MathHelper.getInstance().hasChanceHit(10)) {
					int duration = 200;
					ParticleEmitter.getInstance().emitParticlesContinuously(this.godylis, new Vector(0, 2, 0),
							Particle.VILLAGER_ANGRY, 5, 0, new Vector(0.5, 0.25, 0.5), this.main, 0, 5, duration);
					SoundEmitter.getInstance().emitSound(godylisLocation.getWorld(), godylisLocation,
							Sound.ENTITY_ZOMBIE_AMBIENT, SoundCategory.HOSTILE, 3, 1);
					PotionEffect speed = new PotionEffect(PotionEffectType.SPEED, duration, 1, false, true);
					PotionEffect strength = new PotionEffect(PotionEffectType.INCREASE_DAMAGE, duration, 1, false,
							true);
					this.godylis.addPotionEffect(speed);
					this.godylis.addPotionEffect(strength);
				}

				/*
				 * 'Throw blocks' ability
				 */
				if (MathHelper.getInstance().hasChanceHit(10)) {
					for (int x = -6; x < 6; x++) {
						for (int z = -6; z < 6; z++) {
							Vector offset = new Vector(x, -1, z);
							Location blockLocation = LocationHelper.getInstance().offsetLocation(godylisLocation,
									offset);
							if (blockLocation.distance(godylisLocation) <= 6
									&& MathHelper.getInstance().hasChanceHit(20)) {
								FallingBlock fallingBlock = godylisLocation.getWorld().spawnFallingBlock(blockLocation,
										blockLocation.getBlock().getBlockData());
								fallingBlock.setVelocity(
										new Vector(0, MathHelper.getInstance().getRandom().nextDouble() * 1, 0));
								blockLocation.getBlock().setType(Material.AIR);
								SoundEmitter.getInstance().emitSound(blockLocation.getWorld(), blockLocation,
										Sound.ENTITY_PHANTOM_FLAP, SoundCategory.PLAYERS, 1, 1);
							}
						}
					}
				}

				/*
				 * 'Heal' ability
				 */
				if (MathHelper.getInstance().hasChanceHit(15)) {
					if (this.godylis.getHealth() < 15) {
						this.godylis.setHealth(this.godylis.getHealth() + 2);
						ParticleEmitter.getInstance().emitParticles(godylisLocation.getWorld(), godylisLocation,
								Particle.HEART, 20, 0, new Vector(0.5, 1, 0.5));
					}
				}
			}
		}
	}
}
