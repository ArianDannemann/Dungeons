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
					//ParticleEmitter.getInstance().emitParticles(spawnLocation.getWorld(),
					//		LocationHelper.getInstance().offsetLocation(spawnLocation, new Vector(0, 0.25, 0)),
					//		Particle.ENCHANTMENT_TABLE, 30, 0, new Vector(0.15, 0, 0.15));
					if (onlinePlayer.getLocation().distance(spawnLocation) <= spawnRange) {
						spawnLocation.getWorld().spawnEntity(spawnLocation, entityType);
						ParticleEmitter.getInstance().emitParticles(spawnLocation.getWorld(),
								LocationHelper.getInstance().offsetLocation(spawnLocation, new Vector(0, 0.5, 0)),
								Particle.CLOUD, 30, 0, new Vector(0.5, 1, 0.5));
						SoundEmitter.getInstance().emitSound(spawnLocation.getWorld(), spawnLocation,
								Sound.ENTITY_PHANTOM_FLAP, SoundCategory.PLAYERS, 1, 1);
						this.cancel();
					}
				}
			}
		};
		bukkitRunnable.runTaskTimer(main, 60, 20);
	}
}