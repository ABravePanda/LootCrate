package lootcrate;

import java.util.ArrayList;

import org.bstats.bukkit.Metrics;
import org.bukkit.Bukkit;
import org.bukkit.configuration.serialization.ConfigurationSerialization;
import org.bukkit.plugin.java.JavaPlugin;

import lootcrate.enums.AnimationStyle;
import lootcrate.enums.CrateOptionType;
import lootcrate.events.listeners.LootCrateInteractListener;
import lootcrate.events.listeners.PlayerChatListener;
import lootcrate.events.listeners.PlayerJoinListener;
import lootcrate.events.listeners.custom.CrateAccessListener;
import lootcrate.events.listeners.custom.CrateOpenListener;
import lootcrate.events.listeners.custom.CrateViewListener;
import lootcrate.gui.events.listeners.GUICloseListener;
import lootcrate.managers.CacheManager;
import lootcrate.managers.ChatManager;
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
    private Metrics metrics;
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
    public ChatManager chatManager;

    @Override
    public void onEnable()
    {
	initSerial();
	initMetrics();

	registerConfig();
	optionManager = new OptionManager(this);
	updateManager = new UpdateManager(this);
	messageManager = new MessageManager(this);
	fileManager = new FileManager(this);
	cacheManager = new CacheManager(this);
	cacheManager.load();
	crateManager = new CrateManager(this);
	locationManager = new LocationManager(this);
	locationManager.populateLocations();
	invManager = new InventoryManager(this);
	commandManager = new CommandManager(this);
	chatManager = new ChatManager(this);

	displayIntro();

	if (holoHook())
	{
	    holoManager = new HologramManager(this);
	    holoManager.reload();
	}

	registerEvents();
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
	this.getServer().getPluginManager().registerEvents(new PlayerChatListener(this), this);
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

    public void displayIntro()
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
	Bukkit.getConsoleSender().sendMessage(ChatColor.DARK_GRAY + "Loaded " + ChatColor.YELLOW
		+ cacheManager.getCache().size() + ChatColor.DARK_GRAY + " crate(s).");
	if (holoHook())
	    Bukkit.getConsoleSender().sendMessage(ChatColor.DARK_GRAY + "Detected " + ChatColor.YELLOW
		    + "Holographic Displays" + ChatColor.DARK_GRAY + ".");
	if (metricsHook())
	    Bukkit.getConsoleSender().sendMessage(ChatColor.DARK_GRAY + "Detected " + ChatColor.YELLOW
		    + "bStats Metrics" + ChatColor.DARK_GRAY + ".");
	Bukkit.getLogger().info("");
    }

    public boolean holoHook()
    {
	return Bukkit.getPluginManager().isPluginEnabled("HolographicDisplays");
    }

    public void initMetrics()
    {
	int pluginId = 9767;
	metrics = new Metrics(this, pluginId);
    }

    public boolean metricsHook()
    {
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
