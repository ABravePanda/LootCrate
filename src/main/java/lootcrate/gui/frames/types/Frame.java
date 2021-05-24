package lootcrate.gui.frames.types;

import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;

import lootcrate.gui.items.GUIItem;

public interface Frame
{
    int getId();
    Player getViewer();
    String getTitle();
    GUIItem[] getContents();
    Inventory getInventory();
    void setItem(int slot, GUIItem item);
    void open();
    void close();
    void registerItems();
    void registerFrame();
    void unregisterFrame();
    abstract void generateFrame();
}
