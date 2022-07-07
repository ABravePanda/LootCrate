package lootcrate.gui.frames.menu;

import lootcrate.LootCrate;
import lootcrate.gui.events.custom.GUIItemClickEvent;
import lootcrate.gui.frames.menu.option.*;
import lootcrate.gui.frames.types.BasicFrame;
import lootcrate.gui.items.GUIItem;
import lootcrate.objects.Crate;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;

import java.util.concurrent.Callable;

public class CrateLocationFrame extends BasicFrame {
    private final Player p;
    private final LootCrate plugin;
    private final Crate crate;

    public CrateLocationFrame(LootCrate plugin, Player p, Crate crate) {
        super(plugin, p, crate.getName());

        this.plugin = plugin;
        this.crate = crate;
        this.p = p;

        registerFrame();
        generateFrame();
        generateNavigation();
        registerItems();
    }

    @Override
    public void generateFrame() {
        fillBackground(Material.WHITE_STAINED_GLASS_PANE);
        fillLocations();
    }

    @Override
    public void nextPage() {
        this.closeFrame(player, this);
        this.openFrame(player, new CrateOptionMainMenuFrame(plugin, player, crate));
        return;
    }

    @Override
    public void previousPage() {
        this.closeFrame(player, this);
        this.openFrame(player, new CrateFrame(plugin, player, crate));
        return;
    }

    @Override
    public void unregisterFrame() {
        GUIItemClickEvent.getHandlerList().unregister(this);
    }

    // methods


    public void fillLocations() {
        int index = 0;
        for (final Location l : plugin.getLocationManager().getCrateLocations(crate)) {
            final GUIItem item = new GUIItem(index, l.getBlock().getType(),
                    ChatColor.GOLD + "" + l.getBlockX() + ", " + l.getBlockY() + ", " + l.getBlockZ(),
                    ChatColor.AQUA + "" + l.getWorld().getName());

            item.setClickHandler(new Callable<Integer>() {
                public Integer call() {
                    item.setClickHandler(null);
                    p.teleport(l);
                    return 1;
                }
            });

            this.setItem(index, item);
            index++;
        }
    }

}
