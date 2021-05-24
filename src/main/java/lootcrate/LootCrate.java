package lootcrate;

import org.bstats.bukkit.Metrics;
import org.bukkit.Bukkit;
import org.bukkit.configuration.serialization.ConfigurationSerialization;
import org.bukkit.plugin.java.JavaPlugin;

import lootcrate.events.listeners.LootCrateInteractListener;
import lootcrate.events.listeners.PlayerJoinListener;
import lootcrate.events.listeners.custom.CrateAccessListener;
import lootcrate.events.listeners.custom.CrateOpenListener;
import lootcrate.events.listeners.custom.CrateViewListener;
import lootcrate.gui.events.listeners.GUICloseListener;
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
	cacheManager.loadAsync(this);

    }

    @Override
    public void onDisable()
    {
    }

    public void registerEvents()
    {
	this.getServer().getPluginManager().registerEvents(new LootCrateInteractListener(this), this);
	this.getServer().getPluginManager().registerEvents(new CrateAccessListener(this), this);
	this.getServer().getPluginManager().registerEvents(new CrateOpenListener(this), this);
	this.getServer().getPluginManager().registerEvents(new CrateViewListener(this), this);
	this.getServer().getPluginManager().registerEvents(new GUICloseListener(), this);
	this.getServer().getPluginManager().registerEvents(new PlayerJoinListener(this), this);
    }

    public void registerConfig()
    {
	this.saveDefaultConfig();
    }

    public void reload()
    {
	cacheManager.reload();
	locationManager.reload();

	if (holoHook())
	    holoManager.reload();

    }

    public void displayIntro(long cacheTime)
    {
	Bukkit.getLogger().info("");
	Bukkit.getLogger().info("");
	Bukkit.getConsoleSender().sendMessage(ChatColor.DARK_GRAY + "[  " + ChatColor.YELLOW + "LootCrate"
		+ ChatColor.GREEN + " v" + this.getDescription().getVersion() + ChatColor.DARK_GRAY + "  ]");
	Bukkit.getLogger().info("");
	Bukkit.getConsoleSender().sendMessage(ChatColor.DARK_GRAY + "Running " + ChatColor.YELLOW
		+ this.getServer().getName() + " v" + this.getServer().getBukkitVersion() + ChatColor.DARK_GRAY + ".");
	if (updateManager.checkForUpdates())
	    Bukkit.getConsoleSender().sendMessage(ChatColor.RED + "Update Available (v" + updateManager.getNewVersion()
		    + "). Download here: " + updateManager.getResourceURL() + ChatColor.DARK_GRAY + ".");
	Bukkit.getConsoleSender().sendMessage(ChatColor.DARK_GRAY + "Cached " + ChatColor.YELLOW
		+ cacheManager.getCache().size() + ChatColor.DARK_GRAY + " crate(s).");
	Bukkit.getConsoleSender()
		.sendMessage(ChatColor.DARK_GRAY + "Caching took " + ChatColor.YELLOW + cacheTime + ChatColor.DARK_GRAY
			+ " nanoseconds or " + ChatColor.YELLOW + cacheTime / 1000000 + ChatColor.DARK_GRAY
			+ " miliseconds.");
	if (holoHook())
	    Bukkit.getConsoleSender().sendMessage(ChatColor.DARK_GRAY + "Detected " + ChatColor.YELLOW
		    + "Holographic Displays" + ChatColor.DARK_GRAY + ".");
	if (initMetrics())
	    Bukkit.getConsoleSender().sendMessage(ChatColor.DARK_GRAY + "Detected " + ChatColor.YELLOW
		    + "bStats Metrics" + ChatColor.DARK_GRAY + ".");
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

    public void onAsyncDone(long starttime)
    {
	long endTime = System.nanoTime();
	long timeElapsed = endTime - starttime;

	crateManager = new CrateManager(this);
	locationManager = new LocationManager(this);
	locationManager.populateLocations();
	invManager = new InventoryManager(this);
	commandManager = new CommandManager(this);

	displayIntro(timeElapsed);

	if (holoHook())
	{
	    holoManager = new HologramManager(this);
	    holoManager.reload();
	}

	registerEvents();
    }

}
