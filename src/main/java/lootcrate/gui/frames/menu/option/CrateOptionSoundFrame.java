package lootcrate.gui.frames.menu.option;

import lootcrate.LootCrate;
import lootcrate.enums.CrateOptionType;
import lootcrate.gui.events.custom.GUIItemClickEvent;
import lootcrate.gui.frames.types.ShiftClickAllowed;
import lootcrate.gui.frames.types.BaseFrame;
import lootcrate.gui.items.GUIItem;
import lootcrate.objects.Crate;
import lootcrate.objects.CrateOption;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryAction;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class CrateOptionSoundFrame extends BaseFrame implements Listener, ShiftClickAllowed {

    private final LootCrate plugin;
    private final Crate crate;
    private List<Sound> soundList;

    public CrateOptionSoundFrame(LootCrate plugin, Player p, Crate crate) {
        super(plugin, p, crate.getName());

        this.plugin = plugin;
        this.crate = crate;
        this.soundList = Arrays.stream(Sound.values()).toList();

        registerFrame();
        generateFrame();
        generateNavigation();
        registerItems();
    }

    @Override
    public void generateFrame() {
        fillBackground(Material.WHITE_STAINED_GLASS_PANE);
        fillOptions();
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

    public void fillOptions() {
        for (int i = 0; i < soundList.size(); i++) {
            this.setItem(i, createGUIItem(i, i));
        }
    }

    // events

    @EventHandler
    public void onGUIItemClick(GUIItemClickEvent e) {
        if (!e.sameFrame(this))
            return;


        Player p = e.getPlayer();
        ItemStack item = e.getItem().getItemStack();

        if(item.getType() != Material.MUSIC_DISC_FAR) return;

        InventoryAction action = e.getClickEvent().getAction();
       // if(!action.equals(InventoryAction.PICKUP_ONE) && !action.equals(InventoryAction.MOVE_TO_OTHER_INVENTORY)) return;

        Sound sound = Sound.valueOf(ChatColor.stripColor(e.getItem().getItemStack().getItemMeta().getDisplayName()));

        if(action.equals(InventoryAction.PICKUP_ALL))
        {
            p.stopAllSounds();
            p.playSound(p.getLocation(), sound, 10, 10);
        }

        if(action.equals(InventoryAction.MOVE_TO_OTHER_INVENTORY))
        {
            crate.setOption(new CrateOption(CrateOptionType.OPEN_SOUND, sound.name()));
            e.setCancelled(true);
            plugin.getCacheManager().update(crate);
            this.close();
        }
        e.setCancelled(true);
    }

    @Override
    public void nextPage() {
        if (usableSize - getUsableItems().size() > 0) return;
        clearUsableItems();
        page++;

        int itemIndex = (page * usableSize) - usableSize;
        int index = 0;
        List<Sound> items = new ArrayList<>(soundList);
        for (int i = 0; i < getUsableSize(); i++) {
            if (index < getUsableSize() && items.size() > itemIndex)
                this.setItem(index, createGUIItem(index, itemIndex));
            index++;
            itemIndex++;
        }
    }

    @Override
    public void previousPage() {

        if(page-1 == 0)
        {
            this.close();
            new CrateOptionMainMenuFrame(plugin, player, crate).open();
            return;
        }
        clearUsableItems();
        page--;

        int itemIndex = (page * usableSize) - usableSize;
        int index = 0;
        List<Sound> items = new ArrayList<>(soundList);
        for (int i = 0; i < getUsableSize(); i++) {
            if (index < getUsableSize() && items.size() > itemIndex)
                this.setItem(index, createGUIItem(index, itemIndex));
            index++;
            itemIndex++;
        }

    }

    private Sound getCurrentSound()
    {
        String sound = "";
        if(crate.getOption(CrateOptionType.OPEN_SOUND).getValue() == null) return Sound.UI_TOAST_CHALLENGE_COMPLETE;
        return Sound.valueOf((String) crate.getOption(CrateOptionType.OPEN_SOUND).getValue());
    }

    private GUIItem createGUIItem(int index, int itemIndex)
    {
        GUIItem item = new GUIItem(index, Material.MUSIC_DISC_FAR, ChatColor.GOLD + soundList.get(itemIndex).name(), " ",
                ChatColor.AQUA + "Left Click" + ChatColor.GRAY + " to play sound",
                ChatColor.AQUA + "Shift Left Click" + ChatColor.GRAY + " to select sound");
        if(getCurrentSound().equals(soundList.get(itemIndex)))
            item.setGlowing(true);
        return item;
    }
}