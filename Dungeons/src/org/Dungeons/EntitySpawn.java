package org.Dungeons;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Particle;
import org.bukkit.Sound;
import org.bukkit.SoundCategory;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.Vector;

import com.PluginBase.LocationHelper;
import com.PluginBase.ParticleEmitter;
import com.PluginBase.SoundEmitter;

public class EntitySpawn {
	public EntitySpawn(Main main, EntityType entityType, Location spawnLocation, int spawnRange) {
		BukkitRunnable bukkitRunnable = new BukkitRunnable() {
			@Override
			public void run() {
				for (Player onlinePlayer : Bukkit.getOnlinePlayers()) {
					if (onlinePlayer.getLocation().distance(spawnLocation) <= spawnRange) {
						Location entitySpawnLocation = spawnLocation.getBlock().getType().isSolid()
								? LocationHelper.getInstance().getRandomNearbyPosition(spawnLocation, 5)
								: spawnLocation;
						entitySpawnLocation.getWorld().spawnEntity(entitySpawnLocation, entityType);
						ParticleEmitter.getInstance().emitParticles(entitySpawnLocation.getWorld(),
								LocationHelper.getInstance().offsetLocation(entitySpawnLocation, new Vector(0, 0.5, 0)),
								Particle.CLOUD, 30, 0, new Vector(0.5, 1, 0.5));
						SoundEmitter.getInstance().emitSound(entitySpawnLocation.getWorld(), entitySpawnLocation,
								Sound.ENTITY_PHANTOM_FLAP, SoundCategory.PLAYERS, 1, 1);
						this.cancel();
					}
				}
			}
		};
		bukkitRunnable.runTaskTimer(main, 60, 20);
	}
}
