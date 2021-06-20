package lootcrate.managers;

import lootcrate.LootCrate;
import lootcrate.enums.AnimationStyle;
import lootcrate.enums.CrateOptionType;
import lootcrate.objects.Crate;
import org.bukkit.Bukkit;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class CacheManager implements Manager {
    private final LootCrate plugin;
    private List<Crate> cache;

    public CacheManager(LootCrate plugin) {
        cache = new ArrayList<Crate>();
        this.plugin = plugin;
    }

    /**
     * Updates Crate into cache
     *
     * @param Crate Crate to update
     */
    public void update(Crate Crate) {
        plugin.getFileManager().saveCrate(Crate);

        cache.remove(Crate);
        cache.add(Crate);
    }

    public void rename(String oldCrate, Crate Crate) {
        plugin.getFileManager().overrideSave(oldCrate, Crate);
        cache.remove(oldCrate);
        cache.add(Crate);
    }

    /**
     * Removes Crate from the file and cache
     *
     * @param Crate Crate to remove
     */
    public void remove(Crate Crate) {
        plugin.getFileManager().removeCrate(Crate);
        List<Crate> copiedCache = new ArrayList<Crate>(cache);

        for (Crate cacheCrate : copiedCache) {
            if (cacheCrate.getId() == Crate.getId())
                cache.remove(cacheCrate);
        }
    }

    /**
     * Gets Crate by given name in cache
     *
     * @param name Name to look for
     * @return Crate or null
     */
    public Crate getCrateById(int id) {
        for (Crate crate : cache) {
            if (crate.getId() == id)
                return crate;
        }
        return null;
    }

    /**
     * Gets the cache list
     *
     * @return cache list
     */
    public List<Crate> getCache() {
        return cache;
    }

    /**
     * @deprecated Loads the cache asynchronously
     */
    public void loadAsync(final LootCrate callback) {
        final long startTime = System.nanoTime();
        Bukkit.getScheduler().runTaskAsynchronously(plugin, new Runnable() {
            @Override
            public void run() {
                cache = plugin.getFileManager().loadAllCrates();

                Bukkit.getScheduler().runTask(plugin, new Runnable() {
                    @Override
                    public void run() {
                        //callback.onAsyncDone(startTime);
                    }
                });
            }
        });
    }

    /**
     * Loads the cache
     */
    public void load() {
        cache = plugin.getFileManager().loadAllCrates();

        for (Crate crate : new ArrayList<Crate>(this.getCache()))
            if (crate.getOption(CrateOptionType.ANIMATION_STYLE) == null) {
                crate.addOption(CrateOptionType.ANIMATION_STYLE, AnimationStyle.RANDOM_GLASS.toString());
                this.update(crate);
            }
    }

    /**
     * Wipes the Crates file, then saves the full cache into Crates file
     */
    public void save() {
        File f = new File(plugin.getDataFolder(), File.separator + "crates.yml");
        YamlConfiguration config = YamlConfiguration.loadConfiguration(f);

        for (String key : config.getKeys(false)) {
            config.set(key, null);
        }

        try {
            config.save(f);
        } catch (IOException e) {
            e.printStackTrace();
        }

        for (Crate Crate : cache) {
            plugin.getFileManager().saveCrate(Crate);
        }
    }

    /**
     * Reloads the cache
     */
    public void reload() {
        load();
    }

    @Override
    public void enable() {
        load();
    }

    @Override
    public void disable() {

    }
}
