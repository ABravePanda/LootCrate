package lootcrate.managers;

import lootcrate.LootCrate;
import lootcrate.enums.FileType;
import lootcrate.objects.Crate;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.configuration.MemorySection;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;
import java.util.*;

public class LocationManager extends BasicManager implements Manager {

    private final Map<Location, Crate> locationList = new LinkedHashMap<Location, Crate>();

    private final CrateManager crateManager;
    private final String locationPrefix = "locations.";
    File f;
    FileConfiguration config;

    /**
     * Constructor for LocationManager
     *
     * @param plugin Instance of plugin
     */
    public LocationManager(LootCrate plugin) {
        super(plugin);
        this.crateManager = plugin.getCrateManager();

    }

    /**
     * Reloads the config and repopulates location list
     */
    public void reload() {
        config = this.getPlugin().getFileManager().getConfiguration(f);
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
            config.save(f);
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
        config = this.getPlugin().getFileManager().getConfiguration(f);
        String uuid = findUUIDByLocation(l);
        if (uuid == null)
            return;
        config.set(uuid, null);
        locationList.remove(l);
        try {
            config.save(f);
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    /**
     * Removes crate from list/file
     *
     * @param crate Crate to be removed
     */
    public void removeCrateLocation(Crate crate) {
        reload();
        config = this.getPlugin().getFileManager().getConfiguration(f);
        String uuid = findUUIDByCrate(crate);
        if (uuid == null)
            return;
        config.set(uuid, null);
        try {
            config.save(f);
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        populateLocations();
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
            MemorySection section = (MemorySection) config.get(s);
            if (section.get("Location") == null)
                continue;
            if (Bukkit.getWorld((String) section.get("Location.world")) == null)
                continue;
            Location loc = new Location(Bukkit.getWorld((String) section.get("Location.world")),
                    (double) section.get("Location.x"), (double) section.get("Location.y"),
                    (double) section.get("Location.z"));
            if (l.equals(loc))
                return s;
        }
        return null;
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
            Crate crate2 = this.getPlugin().getCacheManager().getCrateById(section.getInt("Crate"));
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
            MemorySection section = (MemorySection) config.get(s);
            Location loc = new Location(Bukkit.getWorld((String) section.get("Location.world")),
                    (double) section.get("Location.x"), (double) section.get("Location.y"),
                    (double) section.get("Location.z"));
            Crate crate = this.getPlugin().getCacheManager().getCrateById(section.getInt("Crate"));
            if (crate == null || loc == null)
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
        f = this.getPlugin().getFileManager().getFile(FileType.LOCATIONS);
        config = this.getPlugin().getFileManager().getConfiguration(f);
        populateLocations();
    }

    @Override
    public void disable() {

    }
}
