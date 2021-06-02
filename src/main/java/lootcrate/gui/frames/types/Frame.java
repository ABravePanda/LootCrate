package lootcrate.gui.frames.types;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;

import lootcrate.gui.items.GUIItem;

public interface Frame
{
    int getId();
    
    int getSize();

    Player getViewer();

    String getTitle();

    GUIItem[] getContents();

    Inventory getInventory();
    
    Inventory createInventory();

    void setItem(int slot, GUIItem item);

    void open();

    void close();

    void registerItems();

    void registerFrame();

    void unregisterFrame();

    void fillBackground(Material m);
    
    abstract void generateFrame();
    
}
