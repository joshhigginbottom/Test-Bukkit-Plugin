package me.joshhigginbottom;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class PluginCommandExecutor implements CommandExecutor{
    private final testbukkitplugin plugin;

    public PluginCommandExecutor(testbukkitplugin plugin) {
		this.plugin = plugin; // Store the plugin in situations where you need it.
	}

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args){
        if (cmd.getName().equalsIgnoreCase("whereami")) { // If the player typed /basic then do the following, note: If you only registered this executor for one command, you don't need this
            if (sender instanceof Player) {
                Player player = (Player) sender;
                player.sendMessage(player.getName() + " is at location: " + player.getLocation());
		    } else {
                sender.sendMessage("Command can only be run by players!");
            }
	    	return true;
	    } else if (cmd.getName().equalsIgnoreCase("testoutput")){
            if (sender instanceof Player) {
                Player player = (Player) sender;
                player.sendMessage(player.getName() + " is at location: " + player.getLocation());
		    } else {
                sender.sendMessage("Command can only be run by players!");
            }
        }
	    return false; 
    }
}
