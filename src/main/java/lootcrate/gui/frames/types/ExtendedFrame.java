package lootcrate.gui.frames.types;

import lootcrate.LootCrate;
import lootcrate.gui.items.GUIItem;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;

public abstract class ExtendedFrame extends BasicFrame implements Listener {

    public ExtendedFrame(LootCrate plugin, Player p, String title, GUIItem[] contents) {
        super(plugin, p, title, contents, 54);
    }

    public ExtendedFrame(LootCrate plugin, Player p, String title) {
        super(plugin, p, title, 54);
    }


    @Override
    public void generateNav() {
        for(int i = size-9; i < getInventory().getSize(); i++) {
            this.setItem(i, new GUIItem(i, Material.RED_STAINED_GLASS_PANE, ""));
        }
        this.setItem(size-5, new GUIItem(size-5, Material.BARRIER, ChatColor.RED + "Close"));
        this.setItem(size-7, new GUIItem(size-7, Material.ARROW, ChatColor.GOLD + "Back"));
        this.setItem(size-3, new GUIItem(size-3, Material.SPECTRAL_ARROW, ChatColor.GOLD + "Next"));
    }
}
