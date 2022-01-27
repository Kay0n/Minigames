package online.Refract.Minigames;

import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import net.md_5.bungee.api.ChatColor;

public class TagCommandExecutor implements CommandExecutor{ 
	
	
	TagMethods tagMethods;

	
	// Constructor with Main class References
	Main plugin;
	public TagCommandExecutor(Main instance) {
		plugin = instance;
		tagMethods = new TagMethods(instance);
	}
	

	@Override 
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if (label.equalsIgnoreCase("tag")) {
			if (args.length > 0) {

				
				// Command: /tag set	
				if (args[0].equalsIgnoreCase("set")) {
					if (!sender.isOp()) {
						sender.sendMessage(ChatColor.RED + "You do not have permission to use this command");
						return false;
					}
					if (args.length > 1 ) {
						Player targetPlayer = Bukkit.getPlayerExact(args[1]);
						if (targetPlayer != null) {
								
							ItemStack tag = tagMethods.createTag();
							System.out.println("PrevTagger:" + plugin.tagPlayer);
							// Remove previous player's color, tag item and tagback player 
							if(plugin.tagPlayer != null) {
								Player prevPlayer = Bukkit.getPlayer(UUID.fromString(plugin.tagPlayer));
								if (prevPlayer != null) {
									prevPlayer.setPlayerListName(prevPlayer.getName());
									prevPlayer.getInventory().remove(tag);
								}
								plugin.prevTaggedPlayer = null;
							}
								
							// Add tag attributes to new player
							tagMethods.givePlayerTag(targetPlayer, tag);
							tagMethods.sendTagMessage(targetPlayer);
							return true;
						}
					}
				}
				
				
				// Command: /tag stop
				if (args[0].equalsIgnoreCase("stop")) {
					if (!sender.isOp()) {
						sender.sendMessage(ChatColor.RED + "You do not have permission to use this command");
						return false;
					}
					
					// Set config values
					plugin.config.set("tag.player", null);
					plugin.config.set("tag.prevplayer", null);
					plugin.saveConfig();
					
					if (plugin.tagPlayer != null) {
						System.out.println(plugin.tagPlayer);
						Player targetPlayer = Bukkit.getPlayer(UUID.fromString(plugin.tagPlayer));
						if(targetPlayer != null){
							targetPlayer.setPlayerListName(targetPlayer.getName());
							targetPlayer.getInventory().remove(tagMethods.createTag());
						}
						plugin.prevTaggedPlayer = null;
						plugin.tagPlayer = null;		
					}
					sender.sendMessage(ChatColor.GOLD + "Tag game stoped.");
					return true;
				}
				
				
				
				//Command: /tag help
				if (args[0].equalsIgnoreCase("help")) {
					sender.sendMessage(ChatColor.GOLD + "" + ChatColor.BOLD + "Welcome to the game of TAG! Try not to get tagged!");
					sender.sendMessage(ChatColor.UNDERLINE + "" + ChatColor.GOLD + "The Rules are simple:");
					sender.sendMessage(ChatColor.GOLD + "1. You must keep the tag with you.");
					sender.sendMessage(ChatColor.GOLD + "2. Hit another player with the tag to pass it on");
					sender.sendMessage(ChatColor.GOLD + "3. You cannot tag the player who tagged you.");
					sender.sendMessage(ChatColor.LIGHT_PURPLE + "If you lose the tag, rename a nametag \"tag\" in an anvil to retrieve it.");
					return true;
				}
				
				
			}
			sender.sendMessage(ChatColor.RED + "Usage: /tag <help|set <player>|stop>");
			return false;
		}
		return false;	
	}

}
