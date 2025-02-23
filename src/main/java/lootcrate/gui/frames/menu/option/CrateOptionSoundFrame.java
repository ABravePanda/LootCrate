package lootcrate.gui.frames.menu.option;

import com.google.common.collect.ImmutableMap;
import lootcrate.LootCrate;
import lootcrate.enums.*;
import lootcrate.gui.events.custom.GUIItemClickEvent;
import lootcrate.gui.frames.types.ShiftClickAllowed;
import lootcrate.gui.frames.types.BaseFrame;
import lootcrate.gui.items.GUIItem;
import lootcrate.managers.CacheManager;
import lootcrate.managers.CustomizationManager;
import lootcrate.managers.MessageManager;
import lootcrate.objects.Crate;
import lootcrate.objects.CrateOption;
import lootcrate.utils.ItemUtils;
import lootcrate.utils.SoundUtils;
import org.bukkit.*;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryAction;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class CrateOptionSoundFrame extends BaseFrame implements Listener, ShiftClickAllowed {

    private final LootCrate plugin;
    private final Crate crate;
    private final List<Sounds> soundList;

    public CrateOptionSoundFrame(LootCrate plugin, Player p, Crate crate) {
        super(plugin, p, crate.getName());

        this.plugin = plugin;
        this.crate = crate;
        this.soundList = List.of(Sounds.values())
                .stream()
                .sorted(Comparator.comparing(Sounds::name))
                .collect(Collectors.toList());

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

        Sounds sound = SoundUtils.valueOf(ChatColor.stripColor(ItemUtils.getOrCreateItemMeta(e.getItem().getItemStack()).getDisplayName().toLowerCase()));

        if(action.equals(InventoryAction.PICKUP_ALL))
        {
            p.stopAllSounds();
            SoundUtils.playSound(p, sound, 10, 10);
        }

        if(action.equals(InventoryAction.MOVE_TO_OTHER_INVENTORY))
        {
            crate.setOption(new CrateOption(CrateOptionType.OPEN_SOUND, sound.getKey()));
            e.setCancelled(true);
            plugin.getManager(CacheManager.class).update(crate);
            new CrateOptionSoundFrame(plugin, p, crate).open();
            plugin.getManager(MessageManager.class).sendMessage(p, Message.LOOTCRATE_SOUND_CHANGED, ImmutableMap.of(Placeholder.SOUND_NAME, sound.name()));
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
        List<Sounds> items = new ArrayList<>(soundList);
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
        List<Sounds> items = new ArrayList<>(soundList);
        for (int i = 0; i < getUsableSize(); i++) {
            if (index < getUsableSize() && items.size() > itemIndex)
                this.setItem(index, createGUIItem(index, itemIndex));
            index++;
            itemIndex++;
        }

    }

    private Sounds getCurrentSound()
    {
        String sound = "";
        if(crate.getOption(CrateOptionType.OPEN_SOUND).getValue() == null) return Sounds.UI__TOAST__CHALLENGE_COMPLETE;
        return SoundUtils.valueOf((String) crate.getOption(CrateOptionType.OPEN_SOUND).getValue());
    }

    private GUIItem createGUIItem(int index, int itemIndex) {
        Sounds sound = soundList.get(itemIndex);

        // Get the actual sound key instead of the enum name
        String soundKey = sound.getKey(); // Assuming `getKey()` returns a NamespacedKey or String

        GUIItem item = new GUIItem(index, Material.MUSIC_DISC_FAR, ChatColor.GOLD + soundKey, " ",
                plugin.getManager(CustomizationManager.class).parseString(CustomizationOption.CRATE_SOUND_LEFT_CLICK_ACTION),
                plugin.getManager(CustomizationManager.class).parseString(CustomizationOption.CRATE_SOUND_SHIFT_LEFT_CLICK_ACTION));

        if (getCurrentSound().equals(sound))
            item.setGlowing(true);

        return item;
    }


}