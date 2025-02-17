package lootcrate.gui.items;

import lootcrate.LootCrate;
import lootcrate.enums.CustomizationOption;
import lootcrate.managers.CustomizationManager;
import org.bukkit.inventory.ItemStack;

public class NavItems
{

    private LootCrate plugin;
    private CustomizationManager customizationManager;

    public NavItems(LootCrate plugin)
    {
        this.plugin = plugin;
        this.customizationManager = plugin.getManager(CustomizationManager.class);
    }

    public ItemStack getNavClose()
    {
        return new GUIItem(0, customizationManager.parseMaterial(CustomizationOption.NAVIGATION_CLOSE_MATERIAL), customizationManager.parseString(CustomizationOption.NAVIGATION_CLOSE_NAME)).getItemStack();
    }
    public ItemStack getNavNext()
    {
        return new GUIItem(0, customizationManager.parseMaterial(CustomizationOption.NAVIGATION_NEXT_MATERIAL), customizationManager.parseString(CustomizationOption.NAVIGATION_NEXT_NAME)).getItemStack();
    }
    public ItemStack getNavPrev()
    {
        return new GUIItem(0, customizationManager.parseMaterial(CustomizationOption.NAVIGATION_PREVIOUS_MATERIAL), customizationManager.parseString(CustomizationOption.NAVIGATION_PREVIOUS_NAME)).getItemStack();
    }
    public ItemStack getNavBlocker()
    {
        return new GUIItem(0, customizationManager.parseMaterial(CustomizationOption.NAVIGATION_BLOCKER_MATERIAL), customizationManager.parseString(CustomizationOption.NAVIGATION_BLOCKER_NAME)).getItemStack();
    }
}
