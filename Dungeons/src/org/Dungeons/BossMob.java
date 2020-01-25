package org.Dungeons;

import org.bukkit.Location;
import org.bukkit.event.Listener;

public abstract class BossMob implements Listener {
	public abstract void spawn(Main main, Location spawnLocation);
}
