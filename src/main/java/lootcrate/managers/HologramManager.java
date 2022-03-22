package lootcrate.managers;

import com.gmail.filoghost.holographicdisplays.api.HologramsAPI;
import lootcrate.LootCrate;
import lootcrate.enums.CrateOptionType;
import lootcrate.holograms.Hologram;
import lootcrate.objects.Crate;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.NamespacedKey;
import org.bukkit.block.Block;
import org.bukkit.entity.ArmorStand;

import java.util.LinkedList;
import java.util.List;

public class HologramManager extends BasicManager implements Manager {
    private final OptionManager optionManager;
    private final LocationManager locationManager;
    private List<Hologram> holoList;

    /**
     * Constructor for HologramManager
     *
     * @param plugin An instance of the plugin
     */
    public HologramManager(LootCrate plugin) {
        super(plugin);
        this.optionManager = plugin.getOptionManager();
        this.locationManager = plugin.getLocationManager();
        this.holoList = new LinkedList<>();
    }

    public void createHologram(Block block, Crate crate, boolean offset) {
        double xOffset = (double) crate.getOption(CrateOptionType.HOLOGRAM_OFFSET_X).getValue();
        double yOffset = (double) crate.getOption(CrateOptionType.HOLOGRAM_OFFSET_Y).getValue();
        double zOffset = (double) crate.getOption(CrateOptionType.HOLOGRAM_OFFSET_Z).getValue();

        Hologram hologram = null;
        if(offset)
            hologram = new Hologram(this.getPlugin(), crate, block.getLocation().clone().add(xOffset, yOffset, zOffset), block.getLocation().clone());
        else
            hologram = new Hologram(this.getPlugin(), crate, block.getLocation().clone(), block.getLocation().clone());

        List<String> list = (List<String>) crate.getOption(CrateOptionType.HOLOGRAM_LINES).getValue();
        for (String line : list) {
            hologram.addLine(ChatColor.translateAlternateColorCodes('&', line).replace("{crate_name}",
                    crate.getName().replace("{crate_id}", "" + crate.getId())));
        }
        createHologram(hologram);
    }

    public int createHologram(Hologram holo)
    {
        holoList.add(holo);
        return holoList.indexOf(holo);
    }

    public void reload() {
        emptyList();
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

            createHologram(l.getBlock(), crate, true);
        }

    }

    public void reload(Hologram hologram)
    {
        if(!holoList.contains(hologram)) return;
        holoList.remove(hologram);
        hologram.delete();

        createHologram(hologram.getInitalLocation().getBlock(), hologram.getCrate(), true);
    }

    private void emptyList()
    {
        for (Hologram holo : holoList) {
            holo.delete();
        }
    }

    public Hologram getHologramByEntity(ArmorStand armorStand)
    {
        for (Hologram holo : holoList) {
            for(ArmorStand armorStand1 : holo.getArmorStandList())
            {
                if(armorStand1.equals(armorStand)) return holo;
            }
        }
        return null;
    }

    public NamespacedKey getKey()
    {
        return new NamespacedKey(this.getPlugin(), "lootcrate.holograms");
    }

    @Override
    public void enable() {
        reload();
    }

    @Override
    public void disable() {
        emptyList();
    }
}
