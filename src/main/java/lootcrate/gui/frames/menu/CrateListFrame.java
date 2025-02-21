package lootcrate.gui.frames.menu;

import lootcrate.LootCrate;
import lootcrate.enums.CustomizationOption;
import lootcrate.gui.events.custom.GUIItemClickEvent;
import lootcrate.gui.frames.types.ExtendedFrame;
import lootcrate.gui.items.GUIItem;
import lootcrate.managers.CacheManager;
import lootcrate.managers.CrateManager;
import lootcrate.managers.CustomizationManager;
import lootcrate.objects.Crate;
import lootcrate.utils.ItemUtils;
import org.bukkit.ChatColor;
import org.bukkit.*;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.inventory.ItemStack;

import java.util.*;

public class CrateListFrame extends ExtendedFrame implements Listener {

    private final LootCrate plugin;
    private final List<Crate> crates;

    public CrateListFrame(LootCrate plugin, Player p) {
        super(plugin, p, plugin.getManager(CustomizationManager.class).parseString(CustomizationOption.ALL_CRATES_MENU_TITLE));

        this.plugin = plugin;
        crates = plugin.getManager(CacheManager.class).getCache();

        generateFrame();
        generateNavigation();
        registerItems();
        registerFrame();
    }

    @Override
    public void generateFrame() {
        //fillBackground(Material.WHITE_STAINED_GLASS_PANE);
        fillCrates();
    }

    @Override
    public void unregisterFrame() {
        GUIItemClickEvent.getHandlerList().unregister(this);
    }

    // methods

    private void fillCrates() {
        for (int i = 0; i < crates.size(); i++) {
            this.setItem(i, new GUIItem(i, Material.CHEST, "§f" + crates.get(i).getName(),
                    ChatColor.GRAY + "" + crates.get(i).getId()));
        }
    }


    // events

    @EventHandler
    public void onGUIItemClick(GUIItemClickEvent e) {
        if (!e.sameFrame(this))
            return;

        Player p = e.getPlayer();
        ItemStack item = e.getItem().getItemStack();

        if (item.getType() != Material.CHEST)
            return;

        String idFromLore = ChatColor.stripColor(ItemUtils.getOrCreateItemMeta(item).getLore().get(0));
        Crate crate = plugin.getManager(CacheManager.class).getCrateById(Integer.parseInt(idFromLore));

        CrateFrame crateFrame = new CrateFrame(plugin, p, crate);

        this.closeFrame(p, this);
        this.openFrame(p, crateFrame);
    }

    @Override
    public void nextPage() {
        if (usableSize - getUsableItems().size() >= 0) return;
        clearUsableItems();
        page++;

        int itemIndex = (page * usableSize) - usableSize;
        int index = 0;
        List<Crate> items = new ArrayList<>(crates);
        for (int i = 0; i < getUsableSize(); i++) {
            if (index < getUsableSize() && items.size() > itemIndex)
                this.setItem(index, new GUIItem(index, Material.CHEST, crates.get(itemIndex).getName(),
                        ChatColor.GRAY + "" + crates.get(itemIndex).getId()));
            index++;
            itemIndex++;
        }
    }

    @Override
    public void previousPage() {

        if(page == 1) {
            this.closeFrame(player, this);
            this.openFrame(player, new CrateMainMenuFrame(plugin, player));
            return;
        }
        clearUsableItems();
        page--;

        int itemIndex = (page * usableSize) - usableSize;
        int index = 0;
        List<Crate> items = new ArrayList<>(crates);
        for (int i = 0; i < getUsableSize(); i++) {
            if (index < getUsableSize() && items.size() > itemIndex)
                this.setItem(index, new GUIItem(index, Material.CHEST, crates.get(itemIndex).getName(),
                        ChatColor.GRAY + "" + crates.get(itemIndex).getId()));
            index++;
            itemIndex++;
        }

    }
}
