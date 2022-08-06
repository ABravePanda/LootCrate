package lootcrate.events.listeners.custom;

import com.google.common.collect.ImmutableMap;
import lootcrate.LootCrate;
import lootcrate.enums.*;
import lootcrate.events.custom.CrateOpenEvent;
import lootcrate.gui.frames.animations.CrateCSGOAnimationFrame;
import lootcrate.gui.frames.animations.CrateRandomGlassAnimationFrame;
import lootcrate.gui.frames.animations.CrateRemovingItemAnimationFrame;
import lootcrate.gui.frames.types.AnimatedFrame;
import lootcrate.objects.Crate;
import lootcrate.objects.CrateOption;
import lootcrate.utils.InventoryUtils;
import lootcrate.utils.ObjUtils;
import lootcrate.utils.PlayerUtils;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.inventory.ItemStack;

public class CrateOpenListener implements Listener {
    private final LootCrate plugin;

    public CrateOpenListener(LootCrate plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onOpen(CrateOpenEvent e) {
        Player p = e.getPlayer();
        Crate crate = e.getCrate();
        ItemStack item = p.getInventory().getItemInMainHand();

        // If config allows virtual keys, check if they have the key in the cache
        if (plugin.getOptionManager().valueOf(Option.ALLOW_VIRTUAL_KEYS)) {
            if (plugin.getKeyCacheManager().contains(p.getUniqueId(), crate)) {
                // They have the key in cache, remove then run the code as if they have the physical key
                plugin.getKeyCacheManager().remove(p.getUniqueId(), crate);

                plugin.getCrateManager().crateOpenEffects(crate, p);
                openAnimation(crate, p);
                return;
            }
        }

        // if they clicked w/same item as key && they match

        if (crate.getKey() == null || crate.getKey().getItem() == null || item == null) {
            plugin.getMessageManager().sendMessage(p, Message.LOOTCRATE_INCORRECT_KEY,
                    ImmutableMap.of(Placeholder.CRATE_NAME, crate.getName(), Placeholder.CRATE_ID, crate.getId() + ""));
            PlayerUtils.knockBackPlayer(crate, p);
            return;
        }

        // if no items
        if (crate.getItems().size() == 0)
            return;

        // if the keys match
        if (!item.getType().equals(crate.getKey().getItem().getType()) || !ObjUtils.doKeysMatch(plugin, item, crate)) {
            plugin.getMessageManager().sendMessage(p, Message.LOOTCRATE_INCORRECT_KEY,
                    ImmutableMap.of(Placeholder.CRATE_NAME, crate.getName()));
            PlayerUtils.knockBackPlayer(crate, p);
            return;
        }

        // if inv is full
        if (InventoryUtils.isFull(p.getInventory())) {
            plugin.getMessageManager().sendMessage(p, Message.INVENTORY_FULL, null);
            return;
        }

        // remove item
        p.getInventory().getItemInMainHand().setAmount(item.getAmount() - 1);
        p.updateInventory();

        // play sound
        plugin.getCrateManager().crateOpenEffects(crate, p);
        openAnimation(crate, p);
    }

    private void openAnimation(Crate crate, Player p) {
        AnimatedFrame frame = null;
        CrateOption opt = crate.getOption(CrateOptionType.ANIMATION_STYLE);
        AnimationStyle type = AnimationStyle.valueOf((String) opt.getValue());
        switch (type) {
            case CSGO:
                frame = new CrateCSGOAnimationFrame(plugin, p, crate);
                break;
            case RANDOM_GLASS:
                frame = new CrateRandomGlassAnimationFrame(plugin, p, crate);
                break;
            case REMOVING_ITEM:
                frame = new CrateRemovingItemAnimationFrame(plugin, p, crate);
                break;
            case NONE:
                plugin.getCrateManager().giveReward(plugin.getCrateManager().getRandomItem(crate), p);
                return;
            default:
                frame = new CrateRandomGlassAnimationFrame(plugin, p, crate);
                break;

        }

        plugin.getInvManager().openFrame(p, frame);

        frame.showAnimation();
    }

}
