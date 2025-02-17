package lootcrate;

import lootcrate.enums.CustomizationOption;
import lootcrate.enums.HologramPlugin;
import lootcrate.enums.Message;
import lootcrate.enums.Option;
import lootcrate.events.listeners.LootCrateInteractListener;
import lootcrate.events.listeners.PlayerChatListener;
import lootcrate.events.listeners.PlayerJoinListener;
import lootcrate.events.listeners.custom.CrateAccessListener;
import lootcrate.events.listeners.custom.CrateOpenListener;
import lootcrate.events.listeners.custom.CrateViewListener;
import lootcrate.gui.events.listeners.GUICloseListener;
import lootcrate.managers.*;
import lootcrate.objects.*;
import org.bukkit.ChatColor;
import org.bstats.bukkit.Metrics;
import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.configuration.serialization.ConfigurationSerialization;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;

import java.io.File;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

public class LootCrate extends JavaPlugin {
    private Metrics metrics;
    private HologramManager holoManager;

    private Map<Integer, Manager> managersMap;

    @Override
    public void onEnable() {
        initSerial();
        initMetrics();

        registerConfig();
        createManagersMap();

        managersMap = new HashMap<>();



        registerEvents(new LootCrateInteractListener(this), new CrateAccessListener(this), new CrateOpenListener(this),
                new CrateViewListener(this), new GUICloseListener(this), new PlayerJoinListener(this),
                new PlayerChatListener(this));


        toggleManagers(true);

        if(isHologramPluginDetected(HologramPlugin.DECENT_HOLOGRAMS))
        {
            holoManager = new HologramManager(this);
            toggleManager(true, holoManager);
        }

        displayIntro();
        startReload();


        //Temporary fix to stop loot crates not working on startup.

        Bukkit.getScheduler().scheduleAsyncDelayedTask(this, new BukkitRunnable(){

            @Override
            public void run() {
                reloadConfig();
                reload();

                if(getHoloManager() != null)
                {
                    getHoloManager().reload();
                }
            }

        }, 80L);

    }

    private void createManagersMap() {

        managersMap.put(1, new OptionManager(this));
        managersMap.put(2, new UpdateManager(this));
        managersMap.put(3, new MessageManager(this));
        managersMap.put(4, new FileManager(this));
        managersMap.put(5, new CustomizationManager(this));
        managersMap.put(6, new CrateFileManager(this));
        managersMap.put(7, new CacheManager(this));
        managersMap.put(8, new CrateManager(this));
        managersMap.put(9, new KeyFileManager(this));
        managersMap.put(10, new KeyCacheManager(this));
        managersMap.put(11, new LocationManager(this));
        managersMap.put(12, new InventoryManager(this));
        managersMap.put(13, new CommandManager(this));
        managersMap.put(14, new ChatManager(this));
        managersMap.put(15, new CooldownManager(this));

    }

    @Override
    public void onDisable() {
        toggleManagers(false);
        if(isHologramPluginDetected(HologramPlugin.DECENT_HOLOGRAMS))
        {
            toggleManager(false, holoManager);
        }
    }

    private void registerEvents(Listener... array) {
        for (Listener l : array)
            this.getServer().getPluginManager().registerEvents(l, this);
    }

    private void toggleManagers(boolean enabled) {
        for (Manager m : managersMap.values()) {
            toggleManager(enabled, m);
        }
    }

    private void toggleManager(boolean enabled, Manager manager) {
        if (enabled)
            manager.enable();
        else
            manager.disable();
    }

    private void registerConfig() {
        this.saveDefaultConfig();
        this.getOrCreateConfigDefaults();
    }

    private void getOrCreateConfigDefaults() {
        File configFile = new File(getDataFolder(), "config.yml");
        FileConfiguration config = YamlConfiguration.loadConfiguration(configFile);

        InputStream defaultConfigStream = getResource("config.yml");
        if (defaultConfigStream != null) {
            FileConfiguration defaultConfig = YamlConfiguration.loadConfiguration(new InputStreamReader(defaultConfigStream, StandardCharsets.UTF_8));

            for (Message message : Message.values()) {
                String key = "messages." + message.getKey();
                if (!config.contains(key)) {
                    config.set(key, defaultConfig.get(key));
                }
            }

            for (CustomizationOption option : CustomizationOption.values()) {
                String key = "custom-gui." + option.getKey();
                if (!config.contains(key)) {
                    config.set(key, defaultConfig.get(key));
                }
            }

            for (Option option : Option.values()) {
                String key = "options." + option.getKey();
                if (!config.contains(key)) {
                    config.set(key, defaultConfig.get(key));
                }
            }

            try {
                config.save(configFile);
            } catch (Exception e) {
                getLogger().severe("Error : Cannot save config file !");
                e.printStackTrace();
            }
        }
    }

    public void reload() {

        CacheManager cacheManager = getManager(CacheManager.class);
        LocationManager locationManager = getManager(LocationManager.class);

        cacheManager.reload();
        locationManager.reload();

        if (isHologramPluginDetected(HologramPlugin.DECENT_HOLOGRAMS))
            holoManager.reload();

    }

    private void displayIntro() {

        UpdateManager updateManager = getManager(UpdateManager.class);
        CacheManager cacheManager = getManager(CacheManager.class);

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
        if (isHologramPluginDetected(HologramPlugin.DECENT_HOLOGRAMS))
            Bukkit.getConsoleSender().sendMessage(ChatColor.DARK_GRAY + "Detected " + ChatColor.YELLOW
                    + "DecentHolograms" + ChatColor.DARK_GRAY + ".");
        else
            Bukkit.getConsoleSender().sendMessage(ChatColor.RED + "No Hologram Plugin Found. Disabling Hologram Feature.");
        if (metrics != null)
            Bukkit.getConsoleSender().sendMessage(ChatColor.DARK_GRAY + "Detected " + ChatColor.YELLOW
                    + "bStats Metrics" + ChatColor.DARK_GRAY + ".");
        Bukkit.getConsoleSender().sendMessage("");
    }

    public boolean isHologramPluginDetected(HologramPlugin hologramPlugin) {
        return Bukkit.getPluginManager().isPluginEnabled(hologramPlugin.getPluginName());
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
        ConfigurationSerialization.registerClass(Cooldown.class);
    }

    private void startReload()
    {
        this.reloadConfig();
        this.reload();

        if(this.getHoloManager() != null)
        {
            this.getHoloManager().reload();
        }
    }

    @SuppressWarnings("unchecked")
    public <T extends Manager> T getManager(Class<T> clazz) {
        for (Manager manager : managersMap.values()) {
            if (clazz.isInstance(manager)) {
                return (T) manager;
            }
        }
        return null;
    }

    public HologramManager getHoloManager() {
        return holoManager;
    }
}
