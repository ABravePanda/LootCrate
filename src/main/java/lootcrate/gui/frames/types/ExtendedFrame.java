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

}
