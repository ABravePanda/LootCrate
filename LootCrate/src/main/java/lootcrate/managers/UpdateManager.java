package lootcrate.managers;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.Scanner;

import org.bukkit.Bukkit;
import org.bukkit.util.Consumer;

import lootcrate.LootCrate;
import lootcrate.other.Option;
import net.md_5.bungee.api.ChatColor;

public class UpdateManager
{
    private LootCrate plugin;
    private int resourceId;
    private OptionManager optionManager;

    /**
     * Constructor for CrateManager
     * 
     * @param plugin
     *            An instance of the plugin
     */
    public UpdateManager(LootCrate plugin)
    {
	this.plugin = plugin;
	optionManager = plugin.optionManager;
	resourceId = optionManager.valueOf(Option.RESOURCE_ID);
    }

    public void getVersion(final Consumer<String> consumer) {
        Bukkit.getScheduler().runTaskAsynchronously(this.plugin, new Runnable()
	{
	    @Override
	    public void run()
	    {
	        try (InputStream inputStream = new URL("https://api.spigotmc.org/legacy/update.php?resource=" + UpdateManager.this.resourceId).openStream(); Scanner scanner = new Scanner(inputStream)) {
	            if (scanner.hasNext()) {
	                consumer.accept(scanner.next());
	            }
	        } catch (IOException exception) {
	            UpdateManager.this.plugin.getLogger().info("Cannot look for updates: " + exception.getMessage());
	        }
	    }
	});
    }
    
    public void checkForUpdates()
    {
	getVersion(new Consumer<String>()
	{
	    @Override
	    public void accept(String version)
	    {
		Bukkit.getLogger().info("You are running version: v" + UpdateManager.this.plugin.getDescription().getVersion());
	        if (UpdateManager.this.plugin.getDescription().getVersion().equalsIgnoreCase(version)) {
	            Bukkit.getLogger().info("There is not a new update available.");
	        } else {
	            Bukkit.getLogger().info("There is a new update available (v" + version + "). Please goto: https://api.spigotmc.org/legacy/update.php?resource=" + UpdateManager.this.resourceId);
	        }
	    }
	});
    }
}
