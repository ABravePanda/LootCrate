package lootcrate.events.listeners.custom;

import com.google.common.collect.ImmutableMap;
import lootcrate.LootCrate;
import lootcrate.enums.CrateAction;
import lootcrate.events.custom.CrateViewEvent;
import lootcrate.gui.frames.CrateViewFrame;
import lootcrate.gui.frames.CustomViewFrame;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

public class CrateViewListener implements Listener {
    private final LootCrate plugin;

    public CrateViewListener(LootCrate plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onView(CrateViewEvent e) {

        System.out.println(e.getCrate().getDisplay());
        if(e.getCrate().getDisplay() == null) {
            CrateViewFrame frame = new CrateViewFrame(plugin, e.getPlayer(), e.getCrate());
            frame.open();
            return;
        }

        CustomViewFrame frame = new CustomViewFrame(plugin, e.getPlayer(), e.getCrate());
        frame.open();


    }

}
