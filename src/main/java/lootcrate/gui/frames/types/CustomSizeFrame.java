package lootcrate.gui.frames.types;

import lootcrate.LootCrate;
import lootcrate.gui.items.GUIItem;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;

public abstract class CustomSizeFrame extends BaseFrame implements Listener {

    public CustomSizeFrame(LootCrate plugin, Player p, String title, GUIItem[] contents, int size) {
        super(plugin, p, title, contents, size);
    }

    public CustomSizeFrame(LootCrate plugin, Player p, String title, int size) {
        super(plugin, p, title, size);
    }

}