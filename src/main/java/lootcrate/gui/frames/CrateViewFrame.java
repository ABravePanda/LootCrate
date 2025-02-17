package lootcrate.gui.frames;

import lootcrate.LootCrate;
import lootcrate.enums.CrateOptionType;
import lootcrate.enums.SortType;
import lootcrate.gui.events.custom.GUIItemClickEvent;
import lootcrate.gui.frames.types.ExtendedFrame;
import lootcrate.gui.frames.types.Pageable;
import lootcrate.gui.items.GUIItem;
import lootcrate.gui.items.NavItems;
import lootcrate.managers.CacheManager;
import lootcrate.managers.InventoryManager;
import lootcrate.objects.Crate;
import lootcrate.objects.CrateItem;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.inventory.ItemStack;

import java.awt.*;
import java.util.List;

public class CrateViewFrame extends ExtendedFrame implements Listener {

    private final LootCrate plugin;
    private final Crate crate;
    private SortType sortType;

    public CrateViewFrame(LootCrate plugin, Player p, Crate crate) {
        super(plugin, p, crate.getName());

        this.plugin = plugin;
        this.crate = plugin.getManager(CacheManager.class).getCrateById(crate.getId());
        this.sortType = SortType.CHANCE;
        if(crate.getOption(CrateOptionType.SORT_TYPE) != null) {
            this.sortType = SortType.valueOf(crate.getOption(CrateOptionType.SORT_TYPE).getValue().toString());
        }

        generateFrame();
        generateNavigation();
        registerItems();
        registerFrame();
    }

    @Override
    public void generateFrame() {
        int index = 0;
        for (ItemStack item : plugin.getManager(InventoryManager.class).addCrateEffects(crate, sortType)) {
            if (index < getUsableSize())
                this.setItem(index, new GUIItem(index, item));
            index++;
        }
    }

    @Override
    public void unregisterFrame() {
        GUIItemClickEvent.getHandlerList().unregister(this);
    }

    @Override
    public void nextPage() {
        if(usableSize - getUsableItems().size() > 0) return;
        clearUsableItems();
        page++;

        int itemIndex = (page*usableSize)-usableSize;
        int index = 0;
        List<ItemStack> items = plugin.getManager(InventoryManager.class).addCrateEffects(crate, sortType);
        for (int i = 0; i < getUsableSize(); i++) {
            if (index < getUsableSize() && items.size() > itemIndex)
                this.setItem(index, new GUIItem(index, items.get(itemIndex)));
            index++;
            itemIndex++;
        }
    }

    @Override
    public void previousPage() {
        if(page-1 == 0) return;
        clearUsableItems();
        page--;

        int itemIndex = (page*usableSize)-usableSize;
        int index = 0;
        List<ItemStack> items = plugin.getManager(InventoryManager.class).addCrateEffects(crate, sortType);
        for (int i = 0; i < getUsableSize(); i++) {
            if (index < getUsableSize() && items.size() > itemIndex)
                this.setItem(index, new GUIItem(index, items.get(itemIndex)));
            index++;
            itemIndex++;
        }

    }


    @EventHandler
    public void onGUIItemClick(GUIItemClickEvent e) {
        Player p = e.getPlayer();
        e.setCancelled(true);
        if (!e.sameFrame(this))
            return;
    }






}
