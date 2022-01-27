package online.Refract.Minigames;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.inventory.ItemStack;

import net.md_5.bungee.api.ChatColor;

public class TagEventHandler implements Listener {
	
	// Var Init
	TagMethods tagMethods;
	ItemStack tag;
	
	
	// Constructor with Main class References
	Main plugin;
	public TagEventHandler(Main instance) {
		plugin = instance;
		tagMethods = new TagMethods(plugin);
		tag = tagMethods.createTag();
	}
	
	

	
	// Sets tagged player's name to red upon join, removes tag from other players
	@EventHandler
	public void onPlayerJoin(PlayerJoinEvent event) {
		if (event.getPlayer().getUniqueId().toString().equals(plugin.tagPlayer)) {
			event.getPlayer().setPlayerListName(ChatColor.RED + event.getPlayer().getName());
		} else {
			event.getPlayer().getInventory().remove(tag);
		}
	}
	
	
	
	// On tag event
	@EventHandler
	public void onPlayerTag(EntityDamageByEntityEvent event) {
		if (event.getEntity() instanceof Player) {
			if (event.getDamager() instanceof Player) {
				if (event.getDamager().getUniqueId().toString().equals(plugin.tagPlayer)) {
					if (((Player) event.getDamager()).getInventory().getItemInMainHand().equals(tag)) {
						// Variables for convenience 
						Player prevPlayer = (Player) event.getDamager();
						Player targetPlayer = (Player) event.getEntity();

						
						// Ensure no tag back
						if (plugin.prevTaggedPlayer != null && plugin.prevTaggedPlayer.equals(targetPlayer.getUniqueId().toString()) ) {
							prevPlayer.sendMessage(ChatColor.RED + "You cannot tag the person who tagged you!");
							return;
						}
						
						// Remove tag attributes from previous player
						prevPlayer.getInventory().remove(tag);
						prevPlayer.setPlayerListName(prevPlayer.getName());
						plugin.prevTaggedPlayer = prevPlayer.getUniqueId().toString();
						
						// Add tag attributes to new player
						tagMethods.givePlayerTag(targetPlayer, tag);
						tagMethods.sendTagMessage(targetPlayer);
					}
				}
			}
		}
	}

	
	
	
	// Sets tag item in anvil 
	@EventHandler
	public void onAnvilRenameTag(InventoryClickEvent event) {
		if (event.getClickedInventory() == null) {
			return;
		}
		if (event.getClickedInventory().getType().equals(InventoryType.ANVIL)) {
			if(!(event.getCurrentItem().getItemMeta() == null)) {
				if(event.getCurrentItem().getItemMeta().getDisplayName().equalsIgnoreCase("tag")) {
						event.getView().setCursor(tag);
					event.getInventory().clear();
				}
			}		
		}
	}
}
