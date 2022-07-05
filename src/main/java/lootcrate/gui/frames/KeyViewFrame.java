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
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.inventory.ItemStack;

import java.util.List;

public class KeyViewFrame extends ExtendedFrame implements Listener {

    private final LootCrate plugin;

    public KeyViewFrame(LootCrate plugin, Player p) {
        super(plugin, p, "Keys to Claim");

        this.plugin = plugin;

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
    }


    @Override
    public void unregisterFrame() {
        GUIItemClickEvent.getHandlerList().unregister(this);
    }

    @Override
    public void nextPage() {

    }

    @Override
    public void previousPage() {

    }


    @EventHandler
    public void onGUIItemClick(GUIItemClickEvent e) {
        Player p = e.getPlayer();
        e.setCancelled(true);
        if (!e.sameFrame(this))
            return;

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
