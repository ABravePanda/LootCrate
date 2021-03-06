package lootcrate.managers;

import java.util.List;

import org.bukkit.Location;
import org.bukkit.block.Block;

import com.gmail.filoghost.holographicdisplays.api.Hologram;
import com.gmail.filoghost.holographicdisplays.api.HologramsAPI;

import lootcrate.LootCrate;
import lootcrate.objects.Crate;
import lootcrate.other.CrateOptionType;
import net.md_5.bungee.api.ChatColor;

public class HologramManager
{
    private LootCrate plugin;
    private OptionManager optionManager;
    private LocationManager locationManager;

    /**
     * Constructor for HologramManager
     * 
     * @param plugin
     *            An instance of the plugin
     */
    public HologramManager(LootCrate plugin)
    {
	this.plugin = plugin;
	this.optionManager = plugin.optionManager;
	this.locationManager = plugin.locationManager;
    }

    public void createHologram(Block block, Crate crate)
    {
	Hologram hologram = HologramsAPI.createHologram(plugin,
		block.getLocation().clone().add((double) crate.getOption(CrateOptionType.HOLOGRAM_OFFSET_X).getValue(),
			(double) crate.getOption(CrateOptionType.HOLOGRAM_OFFSET_Y).getValue(),
			(double) crate.getOption(CrateOptionType.HOLOGRAM_OFFSET_Z).getValue()));

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
	    if(l == null) return;
	    if(l.getBlock() == null) return;
	    
	    Crate crate = locationManager.getLocationList().get(l);
	    createHologram(l.getBlock(), crate);
	}

    }
}
