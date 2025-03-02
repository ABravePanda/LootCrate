package lootcrate.gui.menu.creation;

import lootcrate.LootCrate;
import lootcrate.gui.item.MenuItem;
import lootcrate.gui.menu.AnimationType;
import lootcrate.gui.menu.CustomMenu;
import lootcrate.gui.menu.MenuType;
import lootcrate.gui.menu.SimpleMenu;
import lootcrate.managers.MenuManager;
import lootcrate.utils.ItemBuilder;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.List;

public class MenuCreationMenuType extends SimpleMenu {

    private final MenuManager menuManager;

    public MenuCreationMenuType(LootCrate plugin) {
        super(plugin, Rows.TWO, "Menu Configuration (Type)");
        this.menuManager = plugin.getManager(MenuManager.class);
    }

    @Override
    public void onSetItems() {
        ItemStack infoPaper = new ItemBuilder(Material.PAPER)
                .withName(ChatColor.GOLD + "Menu Type")
                .withLoreLine(ChatColor.GRAY + "What type of menu do you want to create?")
                .build();

        ItemStack preview = new ItemBuilder(Material.ENDER_EYE)
                .withName(ChatColor.GREEN + "Preview")
                .withLoreLine(ChatColor.GRAY + "When the player previews the crates rewards")
                .build();

        ItemStack open = new ItemBuilder(Material.TRIPWIRE_HOOK)
                .withName(ChatColor.GREEN + "Open")
                .withLoreLine(ChatColor.GRAY + "When the player uses a key on the crate")
                .build();

        setItem(4, new MenuItem(infoPaper));

        setItem(12, createMenuTypeMenuItem(preview, MenuType.PREVIEW));
        setItem(14, createMenuTypeMenuItem(open, MenuType.OPEN));
    }

    private MenuItem createMenuTypeMenuItem(ItemStack itemStack, MenuType menuType) {
        MenuItem menuTypeMenuItem = new MenuItem(itemStack);
        menuTypeMenuItem.addClickAction(ClickType.LEFT, player -> {
            menuManager.getMenuCreationStage(player).setMenuType(menuType);
            menuManager.openMenu(player, new MenuCreationAnimationType(getPlugin()));
        });
        return menuTypeMenuItem;
    }
}
