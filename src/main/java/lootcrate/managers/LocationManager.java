package lootcrate.managers;

import lootcrate.LootCrate;
import lootcrate.core.ports.WorldLookup;
import lootcrate.enums.FileType;
import lootcrate.objects.Crate;
import org.bukkit.Location;
import org.bukkit.NamespacedKey;
import org.bukkit.World;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.MemorySection;

import java.io.IOException;
import java.util.*;

public class LocationManager extends AbstractFileBackedStore {

    private final Map<Location, Crate> locationList = new LinkedHashMap<Location, Crate>();

    private final CacheManager cacheManager;
    private final WorldLookup worldLookup;
    private final String locationPrefix = "locations.";

    /**
     * Constructor for LocationManager
     *
     * @param plugin Instance of plugin
     */
    public LocationManager(LootCrate plugin, FileManager fileManager, CacheManager cacheManager, WorldLookup worldLookup) {
        super(plugin, fileManager);
        this.cacheManager = cacheManager;
        this.worldLookup = worldLookup;
    }

    /**
     * Reloads the config and repopulates location list
     */
    public void reload() {
        reloadConfig();
        populateLocations();
    }

    /**
     * Adds a crate to location list/file
     *
     * @param l     Location to be added
     * @param crate Crate to be added
     */
    public void addCrateLocation(Location l, Crate crate) {
        locationList.put(l, crate);
        UUID randomUUID = UUID.randomUUID();
        String uuid = findUUIDByLocation(l);
        if (uuid != null)
            randomUUID = UUID.fromString(uuid);
        config.set(randomUUID + ".Crate", crate.getId());
        config.set(randomUUID + ".Location", l.serialize());
        try {
            config.save(file);
        } catch (IOException e) {
            e.printStackTrace();
        }
        reload();
    }


    /**
     * Removes crate from list/file
     *
     * @param l Location to be removed
     */
    public void removeCrateLocation(Location l) {
        reload();
        reloadConfig();
        String uuid = findUUIDByLocation(l);
        if (uuid == null)
            return;
        config.set(uuid, null);
        locationList.remove(l);
        try {
            config.save(file);
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        reload();
    }

    /**
     * Removes crate from list/file
     *
     * @param crate Crate to be removed
     */
    public void removeCrateLocation(Crate crate) {
        reload();
        reloadConfig();
        String uuid = findUUIDByCrate(crate);
        if (uuid == null)
            return;
        config.set(uuid, null);
        try {
            config.save(file);
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        reload();
    }

    /**
     * Returns the locations uuid as specified by the file
     *
     * @param l Location to be searched
     * @return UUID of the location or null
     */
    public String findUUIDByLocation(Location l) {
        reload();
        for (String s : config.getKeys(false)) {
            ConfigurationSection section = config.getConfigurationSection(s + ".Location");
            if (section == null)
                continue;
            World world = resolveWorld(section);
            if (world == null)
                continue;
            Location loc = new Location(world, section.getDouble("x"), section.getDouble("y"), section.getDouble("z"));
            if (l.equals(loc))
                return s;
        }
        return null;
    }

    /**
     * Resolves the world stored in a serialized Location section, supporting
     * both the legacy "world" name key and the "world_key" namespaced key.
     */
    private World resolveWorld(ConfigurationSection section) {
        String worldKey = section.getString("world_key");
        if (worldKey != null) {
            NamespacedKey key = NamespacedKey.fromString(worldKey.replace("minecraft:", ""));
            if (key != null) {
                for (World world : worldLookup.getWorlds())
                    if (world.getKey().equals(key))
                        return world;
            }
        }
        return worldLookup.getWorld(section.getString("world", ""));
    }

    /**
     * Returns the crates uud as specified by file
     *
     * @param crate Crate to be searched
     * @return UUID of the crate or null
     */
    public String findUUIDByCrate(Crate crate) {
        reload();
        for (String s : config.getKeys(false)) {
            MemorySection section = (MemorySection) config.get(s);
            if (section.get("Crate") == null)
                continue;
            Crate crate2 = cacheManager.getCrateById(section.getInt("Crate"));
            if (crate2 == null)
                continue;
            if (crate.getId() == crate2.getId())
                return s;
        }
        return null;
    }

    /**
     * Populates the location file
     */
    public void populateLocations() {
        locationList.clear();
        for (String s : config.getKeys(false)) {
            ConfigurationSection section = config.getConfigurationSection(s + ".Location");
            if (section == null)
                continue;
            World world = resolveWorld(section);
            if (world == null)
                continue;
            Location loc = new Location(world, section.getDouble("x"), section.getDouble("y"), section.getDouble("z"));
            Crate crate = cacheManager.getCrateById(config.getInt(s + ".Crate"));
            if (crate == null)
                continue;
            locationList.put(loc, crate);
        }
    }

    /**
     * Returns the location list
     *
     * @return location list of all locations and crates
     */
    public Map<Location, Crate> getLocationList() {
        return locationList;
    }

    /**
     * Returns a list of all locations for a specified crate
     *
     * @param crate Crate to find locations for
     * @return List of all locations attached to crate
     */
    public List<Location> getCrateLocations(Crate crate) {
        List<Location> locations = new ArrayList<Location>();

        for (Location l : getLocationList().keySet())
            if (getLocationList().get(l) == crate)
                locations.add(l);

        return locations;
    }

    @Override
    public void enable() {
        loadConfig(FileType.LOCATIONS);
        populateLocations();
    }

    @Override
    public void disable() {

    }
}
