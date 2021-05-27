package lootcrate;

import org.bstats.bukkit.Metrics;
import org.bukkit.Bukkit;
import org.bukkit.configuration.serialization.ConfigurationSerialization;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;

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
    private MessageManager messageManager;
    private FileManager fileManager;
    private CacheManager cacheManager;
    private CrateManager crateManager;
    private LocationManager locationManager;
    private InventoryManager invManager;
    private CommandManager commandManager;
    private OptionManager optionManager;
    private UpdateManager updateManager;
    private HologramManager holoManager;
    private ChatManager chatManager;

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

	initEvents();
    }

    @Override
    public void onDisable()
    {
    }

    private void initEvents()
    {
	Listener[] array =
	{
		new LootCrateInteractListener(this),
		new CrateAccessListener(this),
		new CrateOpenListener(this),
		new CrateViewListener(this),
		new GUICloseListener(),
		new PlayerJoinListener(this),
		new PlayerChatListener(this)
	};

	registerEvents(array);
    }

    private void registerEvents(Listener[] array)
    {
	for (Listener l : array)
	    this.getServer().getPluginManager().registerEvents(l, this);
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
	Bukkit.getConsoleSender().sendMessage("");
	Bukkit.getConsoleSender().sendMessage("");
	Bukkit.getConsoleSender().sendMessage(ChatColor.DARK_GRAY + "[  " + ChatColor.YELLOW + "LootCrate"
		+ ChatColor.GREEN + " v" + this.getDescription().getVersion() + ChatColor.DARK_GRAY + "  ]");
	Bukkit.getConsoleSender().sendMessage("");
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
	Bukkit.getConsoleSender().sendMessage("");
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

    /**
     * @return the invManager
     */
    public InventoryManager getInvManager()
    {
	return invManager;
    }

    /**
     * @return the messageManager
     */
    public MessageManager getMessageManager()
    {
	return messageManager;
    }

    /**
     * @return the fileManager
     */
    public FileManager getFileManager()
    {
	return fileManager;
    }

    /**
     * @return the cacheManager
     */
    public CacheManager getCacheManager()
    {
	return cacheManager;
    }

    /**
     * @return the crateManager
     */
    public CrateManager getCrateManager()
    {
	return crateManager;
    }

    /**
     * @return the locationManager
     */
    public LocationManager getLocationManager()
    {
	return locationManager;
    }

    /**
     * @return the commandManager
     */
    public CommandManager getCommandManager()
    {
	return commandManager;
    }

    /**
     * @return the optionManager
     */
    public OptionManager getOptionManager()
    {
	return optionManager;
    }

    /**
     * @return the updateManager
     */
    public UpdateManager getUpdateManager()
    {
	return updateManager;
    }

    /**
     * @return the holoManager
     */
    public HologramManager getHoloManager()
    {
	return holoManager;
    }

    /**
     * @return the chatManager
     */
    public ChatManager getChatManager()
    {
	return chatManager;
    }

}
