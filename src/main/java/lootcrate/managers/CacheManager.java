package lootcrate.managers;

import lootcrate.LootCrate;
import lootcrate.enums.AnimationStyle;
import lootcrate.enums.CrateOptionType;
import lootcrate.enums.SortType;
import lootcrate.objects.Crate;
import lootcrate.objects.CrateItem;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class CacheManager extends BasicManager {
    private List<Crate> cache;

    public CacheManager(LootCrate plugin) {
        super(plugin);
        cache = new ArrayList<Crate>();
    }

    /**
     * Updates Crate into cache
     *
     * @param crate Crate to update
     */
    //TODO run async
    public void update(Crate crate) {
        crate = verify(crate);
        getPlugin().getManager(CrateFileManager.class).saveCrate(crate);

        cache.remove(crate);
        cache.add(crate);
    }

    public void rename(String oldCrate, Crate Crate) {
        getPlugin().getManager(CrateFileManager.class).overrideSave(oldCrate, Crate);
        cache.remove(oldCrate);
        cache.add(Crate);
    }

    /**
     * Removes Crate from the file and cache
     *
     * @param Crate Crate to remove
     */
    public void remove(Crate Crate) {
        getPlugin().getManager(CrateFileManager.class).removeCrate(Crate);
        List<Crate> copiedCache = new ArrayList<Crate>(cache);

        for (Crate cacheCrate : copiedCache) {
            if (cacheCrate.getId() == Crate.getId())
                cache.remove(cacheCrate);
        }
    }

    /**
     * Gets Crate by given id in cache
     *
     * @param id id to look for
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
        final LootCrate plugin = this.getPlugin();
        Bukkit.getScheduler().runTaskAsynchronously(this.getPlugin(), new Runnable() {
            @Override
            public void run() {
                cache = plugin.getManager(CrateFileManager.class).loadAllCrates();
                cache = verify(cache);
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
        cache = getPlugin().getManager(CrateFileManager.class).loadAllCrates();
        cache = verify(cache);
    }

    public List<Crate> verify(List<Crate> crates) {
        for (Crate crate : new ArrayList<Crate>(crates)) {
            crate = verify(crate);
        }

        return crates;
    }

    public Crate verify(Crate crate) {
        for (CrateItem item : new ArrayList<CrateItem>(crate.getItems())) {
            if (item.getItem() == null || item.getItem().getType() == null || item.getItem().getType() == Material.AIR) {
                crate.removeItem(item);
            }
            if(crate.getOption(CrateOptionType.HOLOGRAM_ENABLED) == null) {
                crate.addOption(CrateOptionType.HOLOGRAM_ENABLED, true);
                getPlugin().getManager(CrateFileManager.class).saveCrate(crate);
            }
            if (crate.getOption(CrateOptionType.ANIMATION_STYLE) == null) {
                crate.addOption(CrateOptionType.ANIMATION_STYLE, AnimationStyle.RANDOM_GLASS.toString());
                this.update(crate);
            }
            if (crate.getOption(CrateOptionType.SOUND_VOLUME) == null) {
                crate.addOption(CrateOptionType.SOUND_VOLUME, 1);
                this.update(crate);
            }
            if (crate.getOption(CrateOptionType.SORT_TYPE) == null) {
                crate.addOption(CrateOptionType.SORT_TYPE, SortType.CHANCE.toString());
                this.update(crate);
            }
            if (crate.getOption(CrateOptionType.COOLDOWN) == null) {
                crate.addOption(CrateOptionType.COOLDOWN, 0);
                this.update(crate);
            }
        }
        return crate;
    }

    /**
     * Wipes the Crates file, then saves the full cache into Crates file
     */
    public void save() {
        File f = new File(this.getPlugin().getDataFolder(), File.separator + "crates.yml");
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
            getPlugin().getManager(CrateFileManager.class).saveCrate(Crate);
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
