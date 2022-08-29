package lootcrate.events.listeners.custom;

import com.google.common.collect.ImmutableMap;
import lootcrate.LootCrate;
import lootcrate.enums.CrateAction;
import lootcrate.events.custom.CrateViewEvent;
import lootcrate.gui.frames.CrateViewFrame;
import lootcrate.gui.frames.CustomViewFrame;
import lootcrate.gui.frames.types.Frame;
import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.scheduler.BukkitRunnable;

public class CrateViewListener implements Listener {
    private final LootCrate plugin;

    public CrateViewListener(LootCrate plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onView(CrateViewEvent e) {

        Frame frame = null;
        if(e.getCrate().getDisplay() == null || e.getCrate().getDisplay().getPages() == null || e.getCrate().getDisplay().getPages().size() == 0) {
            frame = new CrateViewFrame(plugin, e.getPlayer(), e.getCrate());
        } else
            frame = new CustomViewFrame(plugin, e.getPlayer(), e.getCrate());

        frame.open();

    }

}
