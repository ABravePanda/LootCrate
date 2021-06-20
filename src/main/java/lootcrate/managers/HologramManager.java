package lootcrate.managers;

import java.util.List;

import org.bukkit.Location;
import org.bukkit.block.Block;

import com.gmail.filoghost.holographicdisplays.api.Hologram;
import com.gmail.filoghost.holographicdisplays.api.HologramsAPI;

import lootcrate.LootCrate;
import lootcrate.enums.CrateOptionType;
import lootcrate.objects.Crate;
import net.md_5.bungee.api.ChatColor;

public class HologramManager implements Manager
{
    private final LootCrate plugin;
    private final OptionManager optionManager;
    private final LocationManager locationManager;

    /**
     * Constructor for HologramManager
     * 
     * @param plugin
     *            An instance of the plugin
     */
    public HologramManager(LootCrate plugin)
    {
	this.plugin = plugin;
	this.optionManager = plugin.getOptionManager();
	this.locationManager = plugin.getLocationManager();
    }

    public void createHologram(Block block, Crate crate)
    {
	double xOffset = (double) crate.getOption(CrateOptionType.HOLOGRAM_OFFSET_X).getValue();
	double yOffset = (double) crate.getOption(CrateOptionType.HOLOGRAM_OFFSET_Y).getValue();
	double zOffset = (double) crate.getOption(CrateOptionType.HOLOGRAM_OFFSET_Z).getValue();
	Hologram hologram = HologramsAPI.createHologram(plugin,
		block.getLocation().clone().add(xOffset, yOffset, zOffset));

	List<String> list = (List<String>) crate.getOption(CrateOptionType.HOLOGRAM_LINES).getValue();
	for (String line : list)
	{
	    hologram.appendTextLine(ChatColor.translateAlternateColorCodes('&', line).replace("{crate_name}",
		    crate.getName().replace("{crate_id}", "" + crate.getId())));
	}
    }

    public void reload()
    {
	for (Hologram holo : HologramsAPI.getHolograms(plugin))
	    holo.delete();
	for (Location l : locationManager.getLocationList().keySet())
	{
	    if (l == null)
		continue;
	    if (l.getWorld() == null)
		continue;
	    if (l.getBlock() == null)
		continue;

	    Crate crate = locationManager.getLocationList().get(l);

	    if (crate == null)
		continue;

	    createHologram(l.getBlock(), crate);
	}

    }

	@Override
	public void enable() {
		reload();
	}

	@Override
	public void disable() {

	}
}
