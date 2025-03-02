package lootcrate.gui.menu.creation;

import lootcrate.LootCrate;
import lootcrate.gui.item.MenuItem;
import lootcrate.gui.menu.AnimationType;
import lootcrate.gui.menu.MenuType;
import lootcrate.gui.menu.SimpleMenu;
import lootcrate.managers.MenuManager;
import lootcrate.utils.ItemBuilder;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.inventory.ItemStack;

public class MenuCreationAnimationType extends SimpleMenu {

    private final MenuManager menuManager;

    public MenuCreationAnimationType(LootCrate plugin) {
        super(plugin, Rows.TWO, "Menu Configuration (Animation)");
        this.menuManager = plugin.getManager(MenuManager.class);
    }

    @Override
    public void onSetItems() {
        ItemStack infoPaper = new ItemBuilder(Material.PAPER)
                .withName(ChatColor.GOLD + "Animation Type")
                .withLoreLine(ChatColor.GRAY + "What type of animation do you want?")
                .build();

        ItemStack staticAnimation = new ItemBuilder(Material.GUNPOWDER)
                .withName(ChatColor.GREEN + "Static")
                .withLoreLine(ChatColor.GRAY + "Non-animated menu")
                .build();

        ItemStack animation = new ItemBuilder(Material.REDSTONE)
                .withName(ChatColor.GREEN + "Animated")
                .withLoreLine(ChatColor.GRAY + "Animated menu")
                .build();

        setItem(4, new MenuItem(infoPaper));

        setItem(12, createAnimationTypeMenuItem(staticAnimation, AnimationType.STATIC));
        setItem(14, createAnimationTypeMenuItem(animation, AnimationType.ANIMATION));
    }

    private MenuItem createAnimationTypeMenuItem(ItemStack itemStack, AnimationType animationType) {
        MenuItem menuTypeMenuItem = new MenuItem(itemStack);
        menuTypeMenuItem.addClickAction(ClickType.LEFT, player -> {
            menuManager.getMenuCreationStage(player).setAnimationType(animationType);
           // menuManager.openMenu(player, new MenuCreationMenuType(getPlugin()));
        });
        return menuTypeMenuItem;
    }
}
