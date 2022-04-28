package lootcrate.gui.items;

import lootcrate.LootCrate;
import lootcrate.enums.Message;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

public class NavItems
{

    private LootCrate plugin;

    public NavItems(LootCrate plugin)
    {
        this.plugin = plugin;
    }

    public ItemStack getNavClose()
    {
        return new GUIItem(0, Material.BARRIER, plugin.getMessageManager().parseMessage(Message.LOOTCRATE_VIEW_CLOSE_ITEM, null)).getItemStack();
    }
    public ItemStack getNavNext()
    {
        return new GUIItem(0, Material.SPECTRAL_ARROW, plugin.getMessageManager().parseMessage(Message.LOOTCRATE_VIEW_NEXT_ITEM, null)).getItemStack();
    }
    public ItemStack getNavPrev()
    {
        return new GUIItem(0, Material.ARROW, plugin.getMessageManager().parseMessage(Message.LOOTCRATE_VIEW_PREV_ITEM, null)).getItemStack();
    }
    public ItemStack getNavBlocker()
    {
        return new GUIItem(0, Material.RED_STAINED_GLASS_PANE, plugin.getMessageManager().parseMessage(Message.LOOTCRATE_VIEW_BLOCKER_ITEM, null)).getItemStack();
    }
}
