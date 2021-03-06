package org.Dungeons;

import org.Dungeons.BossMobs.Godylis;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.PluginBase.Chat;

/**
 * This class will handle all player commands
 * @author Arian Dannemann
 */
public class CommandHandler {
	
	/**
	 * Recieves player commands from main class and executes them
	 * @param sender	Sender of the command
	 * @param cmd		The command itself
	 * @param label		The label or name of the command
	 * @param args		Extra command arguments
	 */
	public static void handleCommand(Main main, CommandSender sender, Command cmd, String label, String[] args) {
		
		// Check if the command was sent by a player
		if (sender instanceof Player != true) {
			return;
		}
		
		// Get the player who sent the command
		Player player = (Player) sender;
		
		// Check for the command "/d"
		if (label.equals("d")) {
			Chat.getInstance().sendMessageToPlayer(player, "Generating dungeon...");
			Dungeon dungeon = main.dungeonGenerator.getDungeonType(player.getLocation().getBlock().getBiome());
			dungeon.generate(player.getLocation());
		}
		
		// Check for the command "/g"
		if (label.equals("g")) {
			Chat.getInstance().sendMessageToPlayer(player, "Spawning Godylis...");
			new Godylis().spawn(main, player.getLocation());
			//new EntitySpawn(main, EntityType.CREEPER, player.getLocation(), 3);
		}
	}
}
