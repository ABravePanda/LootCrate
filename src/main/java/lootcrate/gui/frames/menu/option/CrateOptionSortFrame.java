package lootcrate.gui.frames.menu.option;

import lootcrate.LootCrate;
import lootcrate.enums.AnimationStyle;
import lootcrate.enums.CrateOptionType;
import lootcrate.enums.SortType;
import lootcrate.gui.events.custom.GUIItemClickEvent;
import lootcrate.gui.frames.types.BaseFrame;
import lootcrate.gui.items.GUIItem;
import lootcrate.objects.Crate;
import lootcrate.objects.CrateOption;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.inventory.ItemStack;

import java.util.HashMap;
import java.util.Map;

public class CrateOptionSortFrame extends BaseFrame implements Listener {

    private final LootCrate plugin;
    private final Crate crate;
    private final Map<SortType, GUIItem> sortTypes;

    public CrateOptionSortFrame(LootCrate plugin, Player p, Crate crate) {
        super(plugin, p, crate.getName());

        this.plugin = plugin;
        this.crate = crate;
        this.sortTypes = new HashMap<>();

        registerFrame();
        generateFrame();
        generateNavigation();
        registerItems();
    }

    @Override
    public void generateFrame() {
        fillBackground(Material.WHITE_STAINED_GLASS_PANE);
        createOptions();
        fillOptions();
        setActiveStyle();
    }

    @Override
    public void unregisterFrame() {
        GUIItemClickEvent.getHandlerList().unregister(this);
    }

    // methods

    public void fillBackground(Material m) {
        for (int i = 0; i < getInventory().getSize(); i++) {
            this.setItem(i, new GUIItem(i, m));
        }
    }

    private void createOptions()
    {
        for(SortType sort : SortType.values())
        {
            GUIItem item = new GUIItem(0, sort.getItemStack(), sort.getName(), sort.getDescription());
            item.setNameColor(ChatColor.RED);
            item.setLoreColor(ChatColor.GRAY);
            sortTypes.put(sort, item);
        }
    }

    public void fillOptions() {
        int index = 0;
        for(int i = 0; i < this.size; i++)
        {
            if(index >= sortTypes.size()) return;

            if(i%2==0)
            {
                GUIItem item = (GUIItem) sortTypes.values().toArray()[index];
                item.setSlot(i);
                this.setItem(i, item);
                index++;
            }
        }
    }

    private void setInactive()
    {
        for(GUIItem item : sortTypes.values()) {
            item.setGlowing(false);
            item.setNameColor(ChatColor.RED);
            resetItem(item);
        }
    }

    private void setActive(GUIItem item)
    {
        item.setNameColor(ChatColor.GREEN);
        item.setGlowing(true);
        resetItem(item);
    }

    private void resetItem(GUIItem item)
    {
        this.setItem(item.getSlot(), item);
    }

    private void setActiveStyle()
    {
        SortType sortType = SortType.CHANCE;
        CrateOption value = crate.getOption(CrateOptionType.SORT_TYPE);
        if(value != null) sortType = SortType.valueOf((String) crate.getOption(CrateOptionType.SORT_TYPE).getValue());
        setActive(sortTypes.get(sortType));
    }

    // events

    @EventHandler
    public void onGUIItemClick(GUIItemClickEvent e) {
        if (!e.sameFrame(this))
            return;

        GUIItem guiItem = e.getItem();
        ItemStack item = guiItem.getItemStack();
        setInactive();

        setActive(guiItem);

        for(SortType sortTypes : SortType.values())
        {
            if(sortTypes.getItemStack().equals(guiItem.getItemStack().getType()))
                crate.setOption(new CrateOption(CrateOptionType.SORT_TYPE, sortTypes.toString()));
            plugin.getCacheManager().update(crate);
        }
        e.setCancelled(true);


    }

    @Override
    public void nextPage() {
        this.closeFrame(player, this);
        this.openFrame(player, new CrateOptionHologramEnabledFrame(plugin, player, crate));
    }

    @Override
    public void previousPage() {
        this.closeFrame(player, this);
        this.openFrame(player, new CrateOptionMainMenuFrame(plugin, player, crate));
    }
}
