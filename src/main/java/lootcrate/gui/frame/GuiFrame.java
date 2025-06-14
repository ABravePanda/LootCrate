package lootcrate.gui.frame;

import lootcrate.gui.GUIItem;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.UUID;

public interface GuiFrame {
    UUID getId();
    Player getViewer();

    String getTitle();
    int getSize();

    void render(); // Builds virtual items
    void tick();   // Called periodically if animated

    GUIItem getItemAtSlot(int slot);

    boolean preventsClose();
    void setReadyToClose();
    void unsetReadyToClose();

    default boolean onItemPickup(int slot, ItemStack item) {
        return false; // Allow by default
    }

    default boolean onItemPlace(int slot, ItemStack item) {
        return false; // Allow by default
    }

    default void onOpen() {}
    default void onClose() {}
}
