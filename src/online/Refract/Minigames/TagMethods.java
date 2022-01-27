package online.Refract.Minigames;

import java.util.ArrayList;
import java.util.Map;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.TextComponent;


// Standalone methods used in tag minigame
public class TagMethods {

	// Constructor
	Main plugin;
	public TagMethods(Main instance) {
		plugin = instance;
	}
		
		
	// Create tag ItemStack
	public ItemStack createTag() {	
		ItemStack tag = new ItemStack(Material.NAME_TAG, 1);
		ItemMeta meta = tag.getItemMeta();
		meta.setDisplayName(ChatColor.RED+ "TAG");
		ArrayList<String> lore = new ArrayList<String>();
		lore.add(ChatColor.LIGHT_PURPLE + "You are IT");
		meta.setLore(lore);
		tag.setItemMeta(meta);
		
		return tag;
	}
	
	
	
	// Give player tag and attributes
	public void givePlayerTag(Player targetPlayer, ItemStack tag) {
		
		// Set player in variable
		plugin.tagPlayer = targetPlayer.getUniqueId().toString();
		System.out.println(plugin.tagPlayer);
		
		// Sets tablist color to red and give tag
		targetPlayer.setPlayerListName(ChatColor.RED + targetPlayer.getName());
		Map<Integer, ItemStack> map = targetPlayer.getInventory().addItem(tag);
		if (map.size() > 0) {
			targetPlayer.getWorld().dropItemNaturally(targetPlayer.getLocation(), tag);
		}
	}
	
	
	// Send message to new tagger
	public void sendTagMessage(Player targetPlayer) {
		String msg = "&6You have been Tagged! \n &rClick &nhere&r to know more";
		TextComponent formattedMsg = new TextComponent(TextComponent.fromLegacyText(ChatColor.translateAlternateColorCodes('&', msg)));
		formattedMsg.setClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, "/tag help"));
		targetPlayer.spigot().sendMessage(formattedMsg);
	}
		
	
	
}
