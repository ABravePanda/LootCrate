package lootcrate;

import lootcrate.events.listeners.LootCrateInteractListener;
import lootcrate.events.listeners.PlayerChatListener;
import lootcrate.events.listeners.PlayerJoinListener;
import lootcrate.events.listeners.custom.CrateAccessListener;
import lootcrate.events.listeners.custom.CrateOpenListener;
import lootcrate.events.listeners.custom.CrateViewListener;
import lootcrate.gui.events.listeners.GUICloseListener;
import lootcrate.managers.*;
import lootcrate.objects.Crate;
import lootcrate.objects.CrateItem;
import lootcrate.objects.CrateKey;
import lootcrate.objects.CrateOption;
import org.bukkit.ChatColor;
import org.bstats.bukkit.Metrics;
import org.bukkit.Bukkit;
import org.bukkit.configuration.serialization.ConfigurationSerialization;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;

public class LootCrate extends JavaPlugin {
    private Metrics metrics;
    private MessageManager messageManager;
    private FileManager fileManager;
    private CrateFileManager crateFileManager;
    private CacheManager cacheManager;
    private CrateManager crateManager;
    private KeyFileManager keyFileManager;
    private KeyCacheManager keyCacheManager;
    private LocationManager locationManager;
    private InventoryManager invManager;
    private CommandManager commandManager;
    private OptionManager optionManager;
    private UpdateManager updateManager;
    private HologramManager holoManager;
    private ChatManager chatManager;
    private CustomizationManager customizationManager;

    @Override
    public void onEnable() {
        initSerial();
        initMetrics();

        registerConfig();
        optionManager = new OptionManager(this);
        updateManager = new UpdateManager(this);
        messageManager = new MessageManager(this);
        fileManager = new FileManager(this);
        customizationManager = new CustomizationManager(this);
        crateFileManager = new CrateFileManager(this);
        cacheManager = new CacheManager(this);
        crateManager = new CrateManager(this);
        keyFileManager = new KeyFileManager(this);
        keyCacheManager = new KeyCacheManager(this);
        locationManager = new LocationManager(this);
        invManager = new InventoryManager(this);
        commandManager = new CommandManager(this);
        chatManager = new ChatManager(this);

        registerEvents(new LootCrateInteractListener(this), new CrateAccessListener(this), new CrateOpenListener(this),
                new CrateViewListener(this), new GUICloseListener(this), new PlayerJoinListener(this),
                new PlayerChatListener(this));

        toggleManagers(true, optionManager, updateManager, messageManager, fileManager, customizationManager, crateFileManager, cacheManager, crateManager, keyFileManager, keyCacheManager, locationManager, invManager, commandManager, chatManager);

        if(holoHook())
        {
            holoManager = new HologramManager(this);
            toggleManagers(true, holoManager);
        }

        displayIntro();
    }

    @Override
    public void onDisable() {
        toggleManagers(false, optionManager, updateManager, messageManager, fileManager, customizationManager, cacheManager, crateManager, keyFileManager, keyCacheManager, locationManager, invManager, commandManager, chatManager);
        if(holoHook())
        {
            toggleManagers(false, holoManager);
        }
    }

    private void registerEvents(Listener... array) {
        for (Listener l : array)
            this.getServer().getPluginManager().registerEvents(l, this);
    }

    private void toggleManagers(boolean enable, Manager... array) {
        for (Manager m : array)
            if (enable)
                m.enable();
            else
                m.disable();
    }

    private void registerConfig() {
        this.saveDefaultConfig();
    }

    public void reload() {
        cacheManager.reload();
        locationManager.reload();

        if (holoHook())
            holoManager.reload();

    }

    private void displayIntro() {
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
                    + "DecentHolograms" + ChatColor.DARK_GRAY + ".");
        else
            Bukkit.getConsoleSender().sendMessage(ChatColor.RED + "No Hologram Plugin Found. Disabling Hologram Feature.");
        if (metrics != null)
            Bukkit.getConsoleSender().sendMessage(ChatColor.DARK_GRAY + "Detected " + ChatColor.YELLOW
                    + "bStats Metrics" + ChatColor.DARK_GRAY + ".");
        Bukkit.getConsoleSender().sendMessage("");
    }

    public boolean holoHook() {
        return Bukkit.getPluginManager().isPluginEnabled("DecentHolograms");
    }

    private void initMetrics() {
        int pluginId = 9767;
        metrics = new Metrics(this, pluginId);
    }

    private void initSerial() {
        ConfigurationSerialization.registerClass(Crate.class);
        ConfigurationSerialization.registerClass(CrateKey.class);
        ConfigurationSerialization.registerClass(CrateOption.class);
        ConfigurationSerialization.registerClass(CrateItem.class);
    }

    /**
     * @return the invManager
     */
    public InventoryManager getInvManager() {
        return invManager;
    }

    /**
     * @return the messageManager
     */
    public MessageManager getMessageManager() {
        return messageManager;
    }

    /**
     * @return the fileManager
     */
    public CrateFileManager getCrateFileManager() {
        return crateFileManager;
    }

    /**
     * @return the cacheManager
     */
    public CacheManager getCacheManager() {
        return cacheManager;
    }

    /**
     * @return the crateManager
     */
    public CrateManager getCrateManager() {
        return crateManager;
    }

    /**
     * @return the keyCacheManager
     */
    public KeyCacheManager getKeyCacheManager() {
        return keyCacheManager;
    }

    /**
     * @return the keyFileManager
     */
    public KeyFileManager getKeyFileManager() {
        return keyFileManager;
    }

    /**
     * @return the locationManager
     */
    public LocationManager getLocationManager() {
        return locationManager;
    }

    /**
     * @return the commandManager
     */
    public CommandManager getCommandManager() {
        return commandManager;
    }

    /**
     * @return the optionManager
     */
    public OptionManager getOptionManager() {
        return optionManager;
    }

    /**
     * @return the updateManager
     */
    public UpdateManager getUpdateManager() {
        return updateManager;
    }

    /**
     * @return the holoManager
     */
    public HologramManager getHoloManager() {
        return holoManager;
    }

    /**
     * @return the chatManager
     */
    public ChatManager getChatManager() {
        return chatManager;
    }

    public FileManager getFileManager() {
        return fileManager;
    }

    public CustomizationManager getCustomizationManager() { return customizationManager; }
}
