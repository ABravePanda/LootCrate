package lootcrate.managers;

import eu.decentsoftware.holograms.api.DHAPI;
import eu.decentsoftware.holograms.api.holograms.Hologram;
import eu.decentsoftware.holograms.api.holograms.HologramLine;
import eu.decentsoftware.holograms.api.holograms.HologramPage;
import lootcrate.LootCrate;
import lootcrate.enums.CrateOptionType;
import lootcrate.enums.HologramPlugin;
import lootcrate.objects.Crate;
import lootcrate.utils.ObjUtils;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.block.Block;

import java.util.ArrayList;
import java.util.List;

public class HologramManager extends BasicManager implements Manager {
    private final OptionManager optionManager;
    private final LocationManager locationManager;
    private List<Hologram> holograms;

    /**
     * Constructor for HolographicDisplaysManager
     *
     * @param plugin An instance of the plugin
     */
    public HologramManager(LootCrate plugin) {
        super(plugin);
        this.optionManager = plugin.getManager(OptionManager.class);
        this.locationManager = plugin.getManager(LocationManager.class);
        this.holograms = new ArrayList<>();
    }

    public void createHologram(Block block, Crate crate) {
        double xOffset = (double) crate.getOption(CrateOptionType.HOLOGRAM_OFFSET_X).getValue();
        double yOffset = (double) crate.getOption(CrateOptionType.HOLOGRAM_OFFSET_Y).getValue();
        double zOffset = (double) crate.getOption(CrateOptionType.HOLOGRAM_OFFSET_Z).getValue();

        List<String> lines = new ArrayList<>();
        List<String> list = (List<String>) crate.getOption(CrateOptionType.HOLOGRAM_LINES).getValue();
        for (String line : list) {
            lines.add(ChatColor.translateAlternateColorCodes('&', line).replace("{crate_name}",
                    crate.getName().replace("{crate_id}", "" + crate.getId())));
        }
        Hologram hologram = DHAPI.createHologram(ObjUtils.getRandomString(5), block.getLocation().clone().add(xOffset, yOffset, zOffset), false, lines);
        holograms.add(hologram);
    }

    public void reload() {

        for (Hologram holo : holograms)
            holo.delete();
        for (Location l : locationManager.getLocationList().keySet()) {
            if (l == null)
                continue;
            if (l.getWorld() == null)
                continue;
            if (l.getBlock() == null)
                continue;
            Crate crate = locationManager.getLocationList().get(l);
            if (crate == null)
                continue;
            if ((boolean) crate.getOption(CrateOptionType.HOLOGRAM_ENABLED).getValue())
                createHologram(l.getBlock(), crate);
        }

    }

    @Override
    public void enable() {
        reload();
    }

    @Override
    public void disable() {
        for (Hologram holo : holograms)
            holo.delete();
    }
}
