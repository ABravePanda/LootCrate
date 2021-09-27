package lootcrate.gui.items;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

public class NavItems {
    public static ItemStack NAV_CLOSE = new GUIItem(0, Material.BARRIER, ChatColor.RED + "Close").getItemStack();
    public static ItemStack NAV_NEXT = new GUIItem(0, Material.SPECTRAL_ARROW, ChatColor.GOLD + "Next").getItemStack();
    public static ItemStack NAV_PREV = new GUIItem(0, Material.ARROW, ChatColor.GOLD + "Back").getItemStack();
}
