package lootcrate.managers;

import lootcrate.LootCrate;
import lootcrate.managers.service.HologramService;
import lootcrate.managers.service.impl.DecentHologramServiceImpl;
import lootcrate.objects.Crate;
import org.bukkit.Bukkit;
import org.bukkit.block.Block;

public class HologramManager extends BasicManager implements Manager {
    private HologramService hologramService;

    public HologramManager(LootCrate plugin) {
        super(plugin);
    }

    @Override
    public void enable() {
        if (Bukkit.getPluginManager().isPluginEnabled("DecentHolograms")) {
            hologramService = new DecentHologramServiceImpl(getPlugin());
            getPlugin().getLogger().info("Using DecentHolograms for holograms.");
        }  else {
            hologramService = null;
            getPlugin().getLogger().warning("No hologram plugin found. Install DecentHolograms.");
            return;
        }

        hologramService.enable();
    }

    public void createHologram(Block block, Crate crate) {
        if(!hasPlugin())  return;
        hologramService.createHologram(block, crate);
    }

    public void reload() {
        if(!hasPlugin())  return;
        hologramService.reload();
    }

    public boolean hasPlugin() {
        return hologramService != null;
    }

    public String getPluginName() {
        if(hasPlugin()) return getPlugin().getName();
        return "";
    }

    @Override
    public void disable() {
        if(!hasPlugin())  return;
        hologramService.disable();
    }
}