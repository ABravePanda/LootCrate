package lootcrate.gui.frame;

import lootcrate.gui.GUIItem;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.UUID;

/**
 * Represents the structure and behavior of a custom GUI frame.
 * <p>
 * A {@code GuiFrame} defines:
 * <ul>
 *   <li>The unique identity of the frame</li>
 *   <li>The player viewing it</li>
 *   <li>Title and size information</li>
 *   <li>Logic for rendering and updating displayed items</li>
 *   <li>Rules for interaction, closing, and item placement/pickup</li>
 * </ul>
 * <p>
 * Implementations are responsible for providing the item layout in {@link #render()},
 * responding to periodic updates via {@link #tick()}, and handling inventory events.
 */
public interface GuiFrame {

    /**
     * @return a unique identifier for this frame instance
     */
    UUID getId();

    /**
     * @return the {@link Player} currently viewing this GUI
     */
    Player getViewer();

    /**
     * @return the title of the GUI, shown to the player
     */
    String getTitle();

    /**
     * @return the total inventory size in slots (must be a multiple of 9)
     */
    int getSize();

    /**
     * Builds the virtual items for the frame.
     * <p>
     * Called when the frame is first opened or needs a full redraw.
     * Implementations should populate slots with {@link GUIItem}s.
     */
    void render();

    /**
     * Called periodically (e.g., each server tick) if the GUI is animated
     * or requires live updates.
     */
    void tick();

    /**
     * Gets the {@link GUIItem} currently assigned to a given slot.
     *
     * @param slot the slot index (0-based)
     * @return the {@link GUIItem} at the slot, or {@code null} if empty
     */
    GUIItem getItemAtSlot(int slot);

    /**
     * @return true if the GUI should prevent the player from closing it normally
     */
    boolean preventsClose();

    /**
     * Marks the frame as ready to close (e.g., after a prevent-close condition has been lifted).
     */
    void setReadyToClose();

    /**
     * Marks the frame as not ready to close, preventing closure until explicitly allowed.
     */
    void unsetReadyToClose();

    /**
     * Called when the player attempts to pick up an item from a slot.
     *
     * @param slot the slot index
     * @param item the item being picked up
     * @return true to block the pickup, false to allow
     */
    default boolean onItemPickup(int slot, ItemStack item) {
        return false; // Allow by default
    }

    /**
     * Called when the player attempts to place an item into a slot.
     *
     * @param slot the slot index
     * @param item the item being placed
     * @return true to block the placement, false to allow
     */
    default boolean onItemPlace(int slot, ItemStack item) {
        return false; // Allow by default
    }

    /**
     * Called when the GUI is opened for the viewer.
     * <p>
     * Default implementation does nothing.
     */
    default void onOpen() {}

    /**
     * Called when the GUI is closed by the viewer.
     * <p>
     * Default implementation does nothing.
     */
    default void onClose() {}
}
