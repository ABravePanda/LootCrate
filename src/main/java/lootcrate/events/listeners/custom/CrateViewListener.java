package lootcrate.events.listeners.custom;

import lootcrate.LootCrate;
import lootcrate.events.custom.CrateViewEvent;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

public class CrateViewListener implements Listener {
    private final LootCrate plugin;

    public CrateViewListener(LootCrate plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onView(CrateViewEvent e) {
    }

}
