package lootcrate;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import lootcrate.events.LootCrateInteractEvent;
import lootcrate.managers.CommandManager;
import lootcrate.managers.CrateManager;
import lootcrate.managers.HologramManager;
import lootcrate.managers.InventoryManager;
import lootcrate.managers.LocationManager;
import lootcrate.managers.MessageManager;
import lootcrate.managers.OptionManager;
import lootcrate.managers.UpdateManager;
import net.md_5.bungee.api.ChatColor;

public class LootCrate extends JavaPlugin
{
    
    public MessageManager messageManager;
    public CrateManager crateManager;
    public LocationManager locationManager;
    public InventoryManager invManager;
    public CommandManager commandManager;
    public OptionManager optionManager;
    public UpdateManager updateManager;
    public HologramManager holoManager;
    public List<Player> playersInInventory = new ArrayList<Player>();

    @Override
    public void onEnable()
    {
	registerConfig();
	optionManager = new OptionManager(this);
	updateManager = new UpdateManager(this);
	displayIntro();
	messageManager = new MessageManager(this);
	crateManager = new CrateManager(this);
	locationManager = new LocationManager(this);
	locationManager.populateLocations();
	invManager = new InventoryManager(this);
	commandManager = new CommandManager(this);
	if(holoHook())
	    holoManager = new HologramManager(this);
	registerEvents();
	reload();
    }

    @Override
    public void onDisable()
    {
    }

    public void registerEvents()
    {
	this.getServer().getPluginManager().registerEvents(new LootCrateInteractEvent(this), this);
    }
    
    public void registerConfig()
    {
	this.saveDefaultConfig();
    }
    
    public void reload()
    {
	
	crateManager.reload();
	locationManager.reload();
	System.out.println(locationManager.getLocationList().size());
	holoManager.reload();
	
    }
    
    public void displayIntro()
    {
	Bukkit.getLogger().info("");
	Bukkit.getConsoleSender().sendMessage(ChatColor.YELLOW + "LootCrate" + ChatColor.GREEN + " v" + this.getDescription().getVersion());
	Bukkit.getConsoleSender().sendMessage(ChatColor.DARK_GRAY + "Running " + this.getServer().getName() + " v" + this.getServer().getBukkitVersion());
	if(updateManager.checkForUpdates())
	    Bukkit.getConsoleSender().sendMessage(ChatColor.RED + "Update Available (v" + updateManager.getNewVersion() + "). Download here: " + updateManager.getResourceURL());
	if(holoHook())
	    Bukkit.getConsoleSender().sendMessage(ChatColor.DARK_GRAY + "Detected " + ChatColor.YELLOW + "Holographic Displays" + ChatColor.DARK_GRAY + ".");
	Bukkit.getLogger().info("");
    }
    
    public boolean holoHook()
    {
	return Bukkit.getPluginManager().isPluginEnabled("HolographicDisplays");
    }

}
