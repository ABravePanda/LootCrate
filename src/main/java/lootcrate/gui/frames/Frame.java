package lootcrate.gui.frames;

import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;

import lootcrate.gui.items.GUIItem;

public interface Frame
{
    Player getViewer();
    String getTitle();
    GUIItem[] getContents();
    Inventory getInventory();
}
