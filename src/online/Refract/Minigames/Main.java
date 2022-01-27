package online.Refract.Minigames;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.java.JavaPlugin;


public class Main extends JavaPlugin {
	
	FileConfiguration config = this.getConfig();
	
	// Tag variables
	String tagPlayer;
	String prevTaggedPlayer;

	

	@Override
	public void onEnable() {

		
		// Creates config file if non-existant
		saveDefaultConfig();
		
		// Tag config handling
		tagPlayer = (String) config.get("tag.player");
		prevTaggedPlayer = (String) config.get("tag.prevplayer");
		
		System.out.println("tagPlayer: " + tagPlayer);
		System.out.println("prevTaggedPlayer: " + prevTaggedPlayer);
		
		// Register events and commands
		this.getServer().getPluginManager().registerEvents(new TagEventHandler(this),this);
		this.getCommand("tag").setExecutor(new TagCommandExecutor(this));
	}
	
	
	
	@Override
	public void onDisable() {
		
		// Save current tagers
		if (tagPlayer != null) {
			config.set("tag.player", tagPlayer);
			saveConfig();
		}
		if (prevTaggedPlayer != null) {
			config.set("tag.prevplayer", prevTaggedPlayer);
			saveConfig();
		}
	}
}
