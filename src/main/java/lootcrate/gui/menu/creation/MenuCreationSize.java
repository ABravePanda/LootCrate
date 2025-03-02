package lootcrate.gui.menu.creation;

import lootcrate.LootCrate;
import lootcrate.gui.item.MenuItem;
import lootcrate.gui.menu.SimpleMenu;
import lootcrate.managers.MenuManager;
import lootcrate.utils.ItemBuilder;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.List;

public class MenuCreationSize extends SimpleMenu {

    private final MenuManager menuManager;

    public MenuCreationSize(LootCrate plugin) {
        super(plugin, Rows.TWO, "Menu Configuration (Rows)");
        this.menuManager = plugin.getManager(MenuManager.class);
    }

    @Override
    public void onSetItems() {

        ItemStack infoPaper = new ItemBuilder(Material.PAPER)
                .withName(ChatColor.GOLD + "Rows")
                .withLoreLine(ChatColor.GRAY + "How many rows do you want the menu to have?")
                .build();

        ItemStack rowOne = new ItemBuilder(Material.PLAYER_HEAD)
                .withName(ChatColor.GREEN + "One")
                .withSkullTexture("http://textures.minecraft.net/texture/7e69b5cfebe05dd43050277e5be83c807b45ade30e08291f6867c723e854b482")
                .withLoreLine(ChatColor.GRAY + "" + Rows.ONE.getSize() + " total rows")
                .build();


        ItemStack rowTwo = new ItemBuilder(Material.PLAYER_HEAD)
                .withName(ChatColor.GREEN + "Two")
                .withSkullTexture("http://textures.minecraft.net/texture/43dbcd32b55e8bbc363a7497f0757638a7bb078be07f362d11128cf4288a6bcc")
                .withLoreLine(ChatColor.GRAY + "" + Rows.TWO.getSize() + " total rows")
                .build();

        ItemStack rowThree = new ItemBuilder(Material.PLAYER_HEAD)
                .withName(ChatColor.GREEN + "Three")
                .withSkullTexture("http://textures.minecraft.net/texture/3c29e477401889c9e263cd49ec4c98805401499b27c954614201018b9170076d")
                .withLoreLine(ChatColor.GRAY + "" + Rows.THREE.getSize() + " total rows")
                .build();

        ItemStack rowFour = new ItemBuilder(Material.PLAYER_HEAD)
                .withName(ChatColor.GREEN + "Four")
                .withSkullTexture("http://textures.minecraft.net/texture/4d86f0a05dc6fa76bc46c4e3c121b4e0b95178228084747cad67e51d9c5e6bab")
                .withLoreLine(ChatColor.GRAY + "" + Rows.FOUR.getSize() + " total rows")
                .build();

        ItemStack rowFive = new ItemBuilder(Material.PLAYER_HEAD)
                .withName(ChatColor.GREEN + "Five")
                .withSkullTexture("http://textures.minecraft.net/texture/93f1c35240d4df8a2e5f8159347a1d42fb7bdd50c90de95af306841825d72bbd")
                .withLoreLine(ChatColor.GRAY + "" + Rows.FIVE.getSize() + " total rows")
                .build();


        setItem(4, new MenuItem(infoPaper));

        setItem(11, createRowMenuItem(rowOne, Rows.ONE));
        setItem(12, createRowMenuItem(rowTwo, Rows.TWO));
        setItem(13, createRowMenuItem(rowThree, Rows.THREE));
        setItem(14, createRowMenuItem(rowFour, Rows.FOUR));
        setItem(15, createRowMenuItem(rowFive, Rows.FIVE));
    }

    private MenuItem createRowMenuItem(ItemStack itemStack, Rows row) {
        MenuItem rowOneMenuItem = new MenuItem(itemStack);
        rowOneMenuItem.addClickAction(ClickType.LEFT, player -> {
            menuManager.getMenuCreationStage(player).setSize(row);
            menuManager.openMenu(player, new MenuCreationMenuType(getPlugin()));
        });
        return rowOneMenuItem;
    }
}
