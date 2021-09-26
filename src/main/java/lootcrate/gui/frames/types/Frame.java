package lootcrate.gui.frames.types;

import lootcrate.gui.items.GUIItem;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;

public interface Frame {
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

    void generateFrame();

    void generateNav();

}
