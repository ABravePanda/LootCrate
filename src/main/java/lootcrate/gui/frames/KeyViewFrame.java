package lootcrate.gui.frames;

import com.google.common.collect.ImmutableMap;
import lootcrate.LootCrate;
import lootcrate.enums.Message;
import lootcrate.enums.Placeholder;
import lootcrate.gui.events.custom.GUIItemClickEvent;
import lootcrate.gui.frames.types.ExtendedFrame;
import lootcrate.gui.items.GUIItem;
import lootcrate.objects.Crate;
import lootcrate.utils.ItemUtils;
import lootcrate.utils.ObjUtils;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.inventory.ItemStack;

import java.util.List;

public class KeyViewFrame extends ExtendedFrame implements Listener {

    private final LootCrate plugin;
    private final GUIItem claimAll;

    public KeyViewFrame(LootCrate plugin, Player p) {
        super(plugin, p, "Keys to Claim");

        this.plugin = plugin;
        this.usableSize = getUsableSize()-1;
        this.claimAll = new GUIItem(usableSize, Material.CHEST, ChatColor.GOLD + "Claim All", ChatColor.GRAY + "Click to claim all keys");

        generateFrame();
        generateNavigation();
        registerItems();
        registerFrame();
    }

    @Override
    public void generateFrame() {
        int index = 0;
        for (Crate crate : plugin.getKeyCacheManager().convertIntToCrate(player.getUniqueId())) {
            if (index < getUsableSize())
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
        List<Crate> crates = plugin.getKeyCacheManager().convertIntToCrate(player.getUniqueId());
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
        List<Crate> crates = plugin.getKeyCacheManager().convertIntToCrate(player.getUniqueId());
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
        e.setCancelled(true);
        if (!e.sameFrame(this))
            return;

        if(e.getItem() == claimAll)
        {
            for(Crate c : plugin.getKeyCacheManager().getCratesByUUID(player.getUniqueId()))
            {
                claimKey(c);
            }
            return;
        }

        int id = ItemUtils.getIDFromItem(plugin, e.getItem().getItemStack());
        Crate crate = plugin.getCacheManager().getCrateById(id);
        if(crate == null) return;
        claimKey(crate);

    }

    private void claimKey(Crate crate)
    {
        if(player.getInventory().firstEmpty() == -1)
        {
            plugin.getMessageManager().sendMessage(player, Message.LOOTCRATE_COMMAND_CLAIM_FULL_INVENTORY,
                    ImmutableMap.of(Placeholder.CRATE_ID, crate.getId() + "", Placeholder.CRATE_NAME, crate.getName(), Placeholder.PLAYER_NAME, player.getName()));
            return;
        }
        player.getInventory().addItem(ObjUtils.assignCrateToItem(plugin, crate));
        plugin.getMessageManager().sendMessage(player, Message.LOOTCRATE_COMMAND_CLAIM_SUCCESS,
                ImmutableMap.of(Placeholder.CRATE_ID, crate.getId() + "", Placeholder.CRATE_NAME, crate.getName()));
        plugin.getKeyCacheManager().remove(player.getUniqueId(), crate);
        plugin.getInvManager().closeFrame(player, this);
        plugin.getInvManager().openFrame(player, new KeyViewFrame(plugin, player));
    }






}
