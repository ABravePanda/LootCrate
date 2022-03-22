package lootcrate.holograms.events;

import lootcrate.LootCrate;
import org.bukkit.entity.ArmorStand;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.persistence.PersistentDataType;

public class HologramDeathEvent implements Listener {

    private LootCrate plugin;

    public HologramDeathEvent(LootCrate plugin)
    {
        this.plugin = plugin;
    }

    @EventHandler
    public void onEntityKill(EntityDeathEvent e)
    {
        if(e.getEntity().getPersistentDataContainer().has(plugin.getHoloManager().getKey(), PersistentDataType.STRING))
            plugin.getHoloManager().reload(plugin.getHoloManager().getHologramByEntity((ArmorStand) e.getEntity()));
    }
}
