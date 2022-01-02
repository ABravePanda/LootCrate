package lootcrate.gui.frames.types;

import lootcrate.gui.items.GUIItem;
import lootcrate.objects.Crate;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;

import java.util.List;

public interface Frame {
    int getId();

    int getSize();

    int getUsableSize();

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

    void generateNavigation();

    void clearUsableItems();

    List<GUIItem> getUsableItems();

}
