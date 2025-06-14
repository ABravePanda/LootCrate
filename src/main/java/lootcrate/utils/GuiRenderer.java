package lootcrate.utils;

import lootcrate.gui.GUIItem;
import lootcrate.gui.frame.AbstractGuiFrame;
import lootcrate.gui.frame.GuiFrame;
import org.bukkit.Bukkit;
import org.bukkit.inventory.Inventory;

public class GuiRenderer {

    public static Inventory renderInventory(GuiFrame frame) {
        Inventory inv = Bukkit.createInventory(null, frame.getSize(), frame.getTitle());
        frame.render();

        for (GUIItem item : ((AbstractGuiFrame) frame).getItems()) {
            inv.setItem(item.getSlot(), item.getItemStack());
        }
        return inv;
    }

    public static void updateInventory(Inventory inv, GuiFrame frame) {
        inv.clear();
        frame.render();
        for (GUIItem item : ((AbstractGuiFrame) frame).getItems()) {
            inv.setItem(item.getSlot(), item.getItemStack());
        }
    }
}
