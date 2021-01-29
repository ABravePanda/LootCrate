package lootcrate;

import java.util.ArrayList;
import java.util.List;

import org.bstats.bukkit.Metrics;
import org.bukkit.Bukkit;
import org.bukkit.configuration.serialization.ConfigurationSerialization;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import lootcrate.events.listeners.LootCrateInteractEvent;
import lootcrate.events.listeners.custom.CrateAccessListener;
import lootcrate.events.listeners.custom.CrateOpenListener;
import lootcrate.events.listeners.custom.CrateViewListener;
import lootcrate.managers.CacheManager;
import lootcrate.managers.CommandManager;
import lootcrate.managers.CrateManager;
import lootcrate.managers.FileManager;
import lootcrate.managers.HologramManager;
import lootcrate.managers.InventoryManager;
import lootcrate.managers.LocationManager;
import lootcrate.managers.MessageManager;
import lootcrate.managers.OptionManager;
import lootcrate.managers.UpdateManager;
import lootcrate.objects.Crate;
import lootcrate.objects.CrateItem;
import lootcrate.objects.CrateKey;
import lootcrate.objects.CrateOption;
import net.md_5.bungee.api.ChatColor;

public class LootCrate extends JavaPlugin
{ 
    public MessageManager messageManager;
    public FileManager fileManager;
    public CacheManager cacheManager;
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
	initSerial();
	
	registerConfig();
	optionManager = new OptionManager(this);
	updateManager = new UpdateManager(this);
	messageManager = new MessageManager(this);
	fileManager = new FileManager(this);
	cacheManager = new CacheManager(this);
	cacheManager.load();
	displayIntro();
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
	this.getServer().getPluginManager().registerEvents(new CrateAccessListener(this), this);
	this.getServer().getPluginManager().registerEvents(new CrateOpenListener(this), this);
	this.getServer().getPluginManager().registerEvents(new CrateViewListener(this), this);
    }
    
    public void registerConfig()
    {
	this.saveDefaultConfig();
    }
    
    public void reload()
    {
	cacheManager.reload();
	locationManager.reload();
	
	if(holoHook())
	    holoManager.reload();
	
    }
    
    public void displayIntro()
    {
	Bukkit.getLogger().info("");
	Bukkit.getConsoleSender().sendMessage(ChatColor.YELLOW + "LootCrate" + ChatColor.GREEN + " v" + this.getDescription().getVersion());
	Bukkit.getConsoleSender().sendMessage(ChatColor.DARK_GRAY + "Running " + this.getServer().getName() + " v" + this.getServer().getBukkitVersion());
	if(updateManager.checkForUpdates())
	    Bukkit.getConsoleSender().sendMessage(ChatColor.RED + "Update Available (v" + updateManager.getNewVersion() + "). Download here: " + updateManager.getResourceURL());
	Bukkit.getConsoleSender().sendMessage(ChatColor.DARK_GRAY + "Cached " + ChatColor.YELLOW
		+ cacheManager.getCache().size() + ChatColor.DARK_GRAY + " crate(s).");
	if(holoHook())
	    Bukkit.getConsoleSender().sendMessage(ChatColor.DARK_GRAY + "Detected " + ChatColor.YELLOW + "Holographic Displays" + ChatColor.DARK_GRAY + ".");
	if(initMetrics())
	    Bukkit.getConsoleSender().sendMessage(ChatColor.DARK_GRAY + "Detected " + ChatColor.YELLOW + "bStats Metrics" + ChatColor.DARK_GRAY + ".");
	Bukkit.getLogger().info("");
    }
    
    public boolean holoHook()
    {
	return Bukkit.getPluginManager().isPluginEnabled("HolographicDisplays");
    }
    
    public boolean initMetrics()
    {
	int pluginId = 9767;
        Metrics metrics = new Metrics(this, pluginId);
        return metrics.isEnabled();
    }
    
    public void initSerial()
    {
	ConfigurationSerialization.registerClass(Crate.class);
	ConfigurationSerialization.registerClass(CrateKey.class);
	ConfigurationSerialization.registerClass(CrateOption.class);
	ConfigurationSerialization.registerClass(CrateItem.class);
    }

}
