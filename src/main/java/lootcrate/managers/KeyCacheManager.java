package lootcrate.managers;

import lootcrate.LootCrate;
import lootcrate.enums.AnimationStyle;
import lootcrate.enums.CrateOptionType;
import lootcrate.enums.FileType;
import lootcrate.objects.Crate;
import lootcrate.objects.CrateItem;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;
import java.util.*;

public class KeyCacheManager extends BasicManager {
    private Map<UUID, List<Integer>> cache;

    public KeyCacheManager(LootCrate plugin) {
        super(plugin);
        cache = new HashMap<>();
    }

    /**
     * Updates a player key into the cache
     *
     * @param uuid the UUID of the player
     * @param crate the crate the player got the key to
     *
     */
    public void update(UUID uuid, Crate crate) {
        List<Integer> integerList = new ArrayList<>();
        integerList.addAll(getCrateIDSByUUID(uuid));
        integerList.add(crate.getId());
        cache.put(uuid, integerList);
    }

    public void remove(UUID uuid, Crate crate) {
        if(!cache.containsKey(uuid)) return;

        List<Integer> integerList = new ArrayList<>(getCrateIDSByUUID(uuid));
        integerList.remove((Integer) crate.getId());

        cache.put(uuid, integerList);

    }

    public boolean contains(UUID uuid, Crate crate) {
        if (!cache.containsKey(uuid))
            return false;

        return cache.get(uuid).contains(crate.getId());
    }

    private List<Integer> getCrateIDSByUUID(UUID uuid)
    {
        return cache.get(uuid) == null ? new ArrayList<>() : cache.get(uuid);
    }

    public List<Crate> getCratesByUUID(UUID uuid)
    {
        List<Crate> crateList = new ArrayList<>();
        for(int i : getCrateIDSByUUID(uuid))
        {
            if(this.getPlugin().getManager(CacheManager.class).getCrateById(i) != null)
                crateList.add(this.getPlugin().getManager(CacheManager.class).getCrateById(i));
        }
        return crateList;
    }


    /**
     * Gets the cache list
     *
     * @return cache list
     */
    public Map<UUID, List<Integer>> getCache() {
        return cache;
    }

    public boolean hasKeys(UUID uuid)
    {
        return cache.containsKey(uuid);
    }

    /**
     * Loads the cache
     */
    public void load() {
        cache = this.getPlugin().getManager(KeyFileManager.class).loadCache();
    }

    /**
     * Wipes the Crates file, then saves the full cache into Crates file
     */
    public void save() {


        File f = this.getPlugin().getManager(FileManager.class).getFile(FileType.KEYS);
        YamlConfiguration config = this.getPlugin().getManager(FileManager.class).getConfiguration(f);

        for (String key : config.getKeys(false)) {
            config.set(key, null);
        }

        try {
            config.save(f);
        } catch (IOException e) {
            e.printStackTrace();
        }

        this.getPlugin().getManager(KeyFileManager.class).saveCache(cache);
    }

    public List<Crate> convertIntToCrate(UUID uuid)
    {
        List<Integer> integerList = new ArrayList<>(getCrateIDSByUUID(uuid));
        List<Crate> crateList = new ArrayList<>();
        for(int i : integerList)
        {
            crateList.add(this.getPlugin().getManager(CacheManager.class).getCrateById(i));
        }
        return crateList;
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
        save();
    }

    public void displayCache()
    {
        for(UUID uuid : cache.keySet())
        {
            for(int i : cache.get(uuid))
            {
                System.out.println(i);
            }
        }
    }
}
