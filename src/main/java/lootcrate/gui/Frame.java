package lootcrate.gui;

import lootcrate.LootCrate;
import org.bukkit.inventory.Inventory;

import java.util.UUID;

public interface Frame {

    /**
     * Called before the frame is opened.
     * Responsible for creating the inventory and adding all required items.
     */
    void init();

    /**
     * Initialize the navigation for the frame.
     */
    void initNavigation();

    /**
     * Get the unique identifier for the frame.
     *
     * @return the UUID of the frame
     */
    UUID getId();

    /**
     * Get the items in the frame.
     *
     * @return an array of GUIItems in the frame
     */
    GUIItem[] getItems();

    /**
     * Get the size of the frame.
     *
     * @return the size of the frame
     */
    int getSize();

    /**
     * Get the title of the frame.
     *
     * @return the title of the frame
     */
    String getTitle();

    /**
     * Get the owner of the frame.
     *
     * @return the UUID of the owner
     */
    UUID getOwner();

    /**
     * Get the inventory associated with the frame.
     *
     * @return the inventory of the frame
     */
    Inventory getInventory();

    /**
     * Set an item in the frame at a specific slot.
     *
     * @param slot the slot to set the item
     * @param guiItem the item to set
     */
    void setItem(int slot, GUIItem guiItem);

    /**
     * Get an item from a specific slot in the frame.
     *
     * @param slot the slot to get the item from
     * @return the item at the specified slot
     */
    GUIItem getItem(int slot);

    /**
     * Get the plugin associated with the frame.
     *
     * @return the plugin instance
     */
    LootCrate getPlugin();

    /**
     * Check if the frame has navigation.
     *
     * @return true if the frame has navigation, false otherwise
     */
    boolean hasNavigation();

    /**
     * Move to the next page in the frame.
     */
    void nextPage();

    /**
     * Move to the previous page in the frame.
     */
    void previousPage();

    /**
     * Called right before the frame is closed.
     * Responsible for cleaning up the frame.
     * Is not responsible for closing the inventory itself.
     */
    void onClose();

    boolean canBeManuallyClosed();

    boolean readyToClose();
}
