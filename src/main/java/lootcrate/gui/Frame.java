package lootcrate.gui;

import lootcrate.LootCrate;
import org.bukkit.inventory.Inventory;

import java.util.UUID;

public interface Frame {



    /**
     * Called before the frame is opened.
     * Responsible for creating the inventory and added all required items.
     */
    public void init();



    public UUID getId();
    public GUIItem[] getItems();
    public int getSize();

    public String getTitle();

    public UUID getOwner();
    public Inventory getInventory();

    public void setItem(int slot, GUIItem guiItem);
    public GUIItem getItem(int slot);

    public LootCrate getPlugin();

    /**
     * Called right before the frame is closed.
     * Responsible for cleaning up the frame.
     * Is not responsible for closing the inventory itself.
     */
    public void onClose();

}
