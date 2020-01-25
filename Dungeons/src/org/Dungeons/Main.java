package org.Dungeons;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.plugin.java.JavaPlugin;

import com.PluginBase.Chat;
import com.PluginBase.VersionChecker;

/**
 * The main class of the project
 * @author Arian Dannemann
 */
public class Main extends JavaPlugin {
	
	@Override
	public void onEnable() {
		VersionChecker.getInstance().checkVersion(this, "1.2");
		@SuppressWarnings("unused")
		DungeonGenerator dungeonGenerator = new DungeonGenerator(this);
		Chat.getInstance().sendMessageToConsole(this, "Dungeons enabled");
	}
	
	/**
	 * Recieves a notification when a player sends a command and sends that information to the CommandHandler
	 */
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		// Send command information to command handler
		CommandHandler.handleCommand(this, sender, cmd, label, args);
		return true;
	}
}
