package lootcrate.managers;

import java.util.List;

import org.bukkit.Location;
import org.bukkit.block.Block;

import com.gmail.filoghost.holographicdisplays.api.Hologram;
import com.gmail.filoghost.holographicdisplays.api.HologramsAPI;

import lootcrate.LootCrate;
import lootcrate.objects.Crate;
import lootcrate.other.Option;
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
		block.getLocation().clone().add((double) optionManager.valueOf(Option.HOLOGRAM_OFFSET_X),
			(double) optionManager.valueOf(Option.HOLOGRAM_OFFSET_Y),
			(double) optionManager.valueOf(Option.HOLOGRAM_OFFSET_Z)));

	List<String> list = optionManager.valueOf(Option.HOLOGRAM_LIST);
	for (String line : list)
	{
	    hologram.appendTextLine(ChatColor.translateAlternateColorCodes('&', line).replace("{crate_name}",
		    crate.getName().replace("{crate_id}", "" + crate.getId())));
	}
    }
    
    public void reload()
    {
	for(Hologram holo : HologramsAPI.getHolograms(plugin))
	    holo.delete();
	for(Location l : locationManager.getLocationList().keySet())
	{
	    Crate crate = locationManager.getLocationList().get(l);
	    createHologram(l.getBlock(), crate);
	}
	    
    }
}
