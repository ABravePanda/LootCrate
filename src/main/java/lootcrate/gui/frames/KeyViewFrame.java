package lootcrate.gui.frames;

import com.google.common.collect.ImmutableMap;
import lootcrate.LootCrate;
import lootcrate.enums.CustomizationOption;
import lootcrate.enums.Message;
import lootcrate.enums.Placeholder;
import lootcrate.gui.events.custom.GUIItemClickEvent;
import lootcrate.gui.frames.types.ExtendedFrame;
import lootcrate.gui.items.GUIItem;
import lootcrate.managers.*;
import lootcrate.objects.Crate;
import lootcrate.utils.ItemUtils;
import lootcrate.utils.ObjUtils;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

import java.util.List;

public class KeyViewFrame extends ExtendedFrame implements Listener {

    private final LootCrate plugin;
    private final GUIItem claimAll;

    public KeyViewFrame(LootCrate plugin, Player p) {
        super(plugin, p, "Keys to Claim");

        this.plugin = plugin;
        this.usableSize = getUsableSize()-1;
        this.claimAll = new GUIItem(usableSize, plugin.getManager(CustomizationManager.class).parseMaterial(CustomizationOption.CLAIM_MENU_CLAIMALL_MATERIAL), plugin.getManager(CustomizationManager.class).parseString(CustomizationOption.CLAIM_MENU_CLAIMALL_NAME));

        generateFrame();
        generateNavigation();
        registerItems();
        registerFrame();
    }

    @Override
    public void generateFrame() {
        int index = 0;
        for (Crate crate : plugin.getManager(KeyCacheManager.class).convertIntToCrate(player.getUniqueId())) {
            if (index < getUsableSize())
                if(crate != null)
                    this.setItem(index, new GUIItem(index, ItemUtils.addCrateID(plugin, crate.getKey().getItem(), crate.getId())));
            index++;
        }
        this.setItem(usableSize, claimAll);
    }


    @Override
    public void unregisterFrame() {
        GUIItemClickEvent.getHandlerList().unregister(this);
    }

    @Override
    public void nextPage() {
        if (usableSize - getUsableItems().size() > 0) return;
        clearUsableItems();
        page++;

        int itemIndex = (page * usableSize) - usableSize;
        int index = 0;
        List<Crate> crates = plugin.getManager(KeyCacheManager.class).convertIntToCrate(player.getUniqueId());
        for (int i = 0; i < getUsableSize(); i++) {
            if (index < getUsableSize() && crates.size() > itemIndex)
                this.setItem(index, new GUIItem(index, ItemUtils.addCrateID(plugin, crates.get(itemIndex).getKey().getItem(), crates.get(itemIndex).getId())));
            index++;
            itemIndex++;
        }
    }

    @Override
    public void previousPage() {
        if (page - 1 == 0) return;
        clearUsableItems();
        page--;

        int itemIndex = (page * usableSize) - usableSize;
        int index = 0;
        List<Crate> crates = plugin.getManager(KeyCacheManager.class).convertIntToCrate(player.getUniqueId());
        for (int i = 0; i < getUsableSize(); i++) {
            if (index < getUsableSize() && crates.size() > itemIndex)
                this.setItem(index, new GUIItem(index, ItemUtils.addCrateID(plugin, crates.get(itemIndex).getKey().getItem(), crates.get(itemIndex).getId())));
            index++;
            itemIndex++;
        }
    }


    @EventHandler
    public void onGUIItemClick(GUIItemClickEvent e) {
        Player p = e.getPlayer();
        if (!e.sameFrame(this))
            return;

        e.setCancelled(true);

        if(e.getItem() == claimAll)
        {
            for(Crate c : plugin.getManager(KeyCacheManager.class).getCratesByUUID(player.getUniqueId()))
            {
                claimKey(c);
            }
            return;
        }

        if(e.getItem() == null) return;
        int id = ItemUtils.getIDFromItem(plugin, e.getItem().getItemStack());
        Crate crate = plugin.getManager(CacheManager.class).getCrateById(id);
        if(crate == null) return;
        claimKey(crate);

    }

    private void claimKey(Crate crate)
    {
        if(player.getInventory().firstEmpty() == -1)
        {
            plugin.getManager(MessageManager.class).sendMessage(player, Message.LOOTCRATE_COMMAND_CLAIM_FULL_INVENTORY,
                    ImmutableMap.of(Placeholder.CRATE_ID, crate.getId() + "", Placeholder.CRATE_NAME, crate.getName(), Placeholder.PLAYER_NAME, player.getName()));
            return;
        }
        player.getInventory().addItem(ObjUtils.assignCrateToKey(plugin, crate));
        plugin.getManager(MessageManager.class).sendMessage(player, Message.LOOTCRATE_COMMAND_CLAIM_SUCCESS,
                ImmutableMap.of(Placeholder.CRATE_ID, crate.getId() + "", Placeholder.CRATE_NAME, crate.getName()));
        plugin.getManager(KeyCacheManager.class).remove(player.getUniqueId(), crate);
        plugin.getManager(InventoryManager.class).closeFrame(player, this);
        plugin.getManager(InventoryManager.class).openFrame(player, new KeyViewFrame(plugin, player));
    }






}
