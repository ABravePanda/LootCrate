package lootcrate.gui.menu;

import lootcrate.LootCrate;
import lootcrate.gui.item.MenuItem;
import org.bukkit.Material;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

public class TestMenu extends SimpleMenu {

    public TestMenu(LootCrate plugin) {
        super(plugin, Rows.FOUR, "TEST");
    }

    @Override
    public void onSetItems() {

        MenuItem menuItem = new MenuItem(new ItemStack(Material.STONE));
        menuItem.addClickAction(ClickType.LEFT, player -> {
            System.out.println("Left Click");
        });
        menuItem.addClickAction(ClickType.RIGHT, player -> {
            System.out.println("Right Click");
        });

        setItem(13, menuItem);
    }

}
