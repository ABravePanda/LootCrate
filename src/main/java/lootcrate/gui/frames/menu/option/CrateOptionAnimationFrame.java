package lootcrate.gui.frames.menu.option;

import lootcrate.LootCrate;
import lootcrate.enums.AnimationStyle;
import lootcrate.enums.CrateOptionType;
import lootcrate.gui.events.custom.GUIItemClickEvent;
import lootcrate.gui.frames.types.BasicFrame;
import lootcrate.gui.items.GUIItem;
import lootcrate.objects.Crate;
import lootcrate.objects.CrateOption;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CrateOptionAnimationFrame extends BasicFrame implements Listener {

    private final LootCrate plugin;
    private final Crate crate;
    private final Map<AnimationStyle, GUIItem> styles;

    public CrateOptionAnimationFrame(LootCrate plugin, Player p, Crate crate) {
        super(plugin, p, crate.getName());

        this.plugin = plugin;
        this.crate = crate;
        this.styles = new HashMap<AnimationStyle, GUIItem>();

        registerFrame();
        generateFrame();
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
        for(AnimationStyle style : AnimationStyle.values())
        {
            GUIItem item = new GUIItem(0, style.getItemStack(), style.getName(), style.getDescription());
            item.setNameColor(ChatColor.RED);
            item.setLoreColor(ChatColor.GRAY);
            styles.put(style, item);
        }
    }

    public void fillOptions() {
        int index = 0;
        for(int i = 0; i < this.size; i++)
        {
            if(index >= styles.size()) return;

            if(i%2==0)
            {
                GUIItem item = (GUIItem) styles.values().toArray()[index];
                item.setSlot(i);
                this.setItem(i, item);
                index++;
            }
        }
    }

    private void setInactive()
    {
        for(GUIItem item : styles.values()) {
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
        AnimationStyle style = AnimationStyle.valueOf((String) crate.getOption(CrateOptionType.ANIMATION_STYLE).getValue());
        setActive(styles.get(style));
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

        for(AnimationStyle style : AnimationStyle.values())
        {
            if(style.getItemStack().equals(guiItem.getItemStack().getType()))
                crate.setOption(new CrateOption(CrateOptionType.ANIMATION_STYLE, style.toString()));
            plugin.getCacheManager().update(crate);
        }


    }

    @Override
    public void nextPage() {

    }

    @Override
    public void previousPage() {

    }
}
