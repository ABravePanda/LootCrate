package lootcrate.gui.frames;

import lootcrate.LootCrate;
import lootcrate.gui.events.custom.GUIItemClickEvent;
import lootcrate.gui.frames.types.ExtendedFrame;
import lootcrate.gui.items.GUIItem;
import lootcrate.objects.Crate;
import lootcrate.objects.Display;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class CustomViewFrame extends ExtendedFrame implements Listener {

    private final LootCrate plugin;
    private final Crate crate;

    public CustomViewFrame(LootCrate plugin, Player p, Crate crate) {
        super(plugin, p, crate.getName());

        this.plugin = plugin;
        this.crate = plugin.getCacheManager().getCrateById(crate.getId());

        generateFrame();
       // generateNavigation();
        registerItems();
        registerFrame();
    }

    @Override
    public void generateFrame() {
        Display display = crate.getDisplay();
        Map<Integer, ItemStack> items = display.getItems();
        List<List<List<Integer>>> pages = display.getPages();
        int index = 0;

        List<List<Integer>> page = pages.get(0);
        for(List<Integer> line : page)
        {
            for(Integer i : line) {
                if(crate.getItem(i) == null)
                    this.setItem(index, new GUIItem(index, items.get(i)));
                else
                    this.setItem(index, new GUIItem(index, plugin.getInvManager().addCrateEffect(crate, crate.getItem(i))));
                index++;
            }
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
        List<ItemStack> items = plugin.getInvManager().addCrateEffects(crate);
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
        List<ItemStack> items = plugin.getInvManager().addCrateEffects(crate);
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
