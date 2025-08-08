package lootcrate.events.listeners.custom;

import com.google.common.collect.ImmutableMap;
import lootcrate.LootCrate;
import lootcrate.enums.*;
import lootcrate.events.custom.CrateOpenEvent;
import lootcrate.gui.frames.animations.CrateCSGOAnimationFrame;
import lootcrate.gui.frames.animations.CrateRandomGlassAnimationFrame;
import lootcrate.gui.frames.animations.CrateRemovingItemAnimationFrame;
import lootcrate.gui.frames.types.AnimatedFrame;
import lootcrate.managers.*;
import lootcrate.objects.Crate;
import lootcrate.objects.CrateOption;
import lootcrate.utils.CommandUtils;
import lootcrate.utils.InventoryUtils;
import lootcrate.utils.ObjUtils;
import lootcrate.utils.PlayerUtils;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.inventory.ItemStack;

public class CrateOpenListener implements Listener {
    private final LootCrate plugin;
    private final CooldownManager cooldownManager;

    public CrateOpenListener(LootCrate plugin) {
        this.plugin = plugin;
        this.cooldownManager = plugin.getManager(CooldownManager.class);
    }

    @EventHandler
    public void onOpen(CrateOpenEvent e) {
        Player p = e.getPlayer();
        Crate crate = e.getCrate();
        ItemStack item = p.getInventory().getItemInMainHand();

        // If config allows virtual keys, check if they have the key in the cache
        if ((boolean) plugin.getManager(OptionManager.class).valueOf(Option.ALLOW_VIRTUAL_KEYS) && plugin.getManager(KeyCacheManager.class).contains(p.getUniqueId(), crate)) {
            if(isCooldownInEffect(crate, p)) return;
            // They have the key in cache, remove then run the code as if they have the physical key
            plugin.getManager(KeyCacheManager.class).remove(p.getUniqueId(), crate);
            openCrate(crate, p);
            return;
        }

        // if they clicked w/same item as key && they match

        if (crate.getKey() == null || crate.getKey().getItem() == null || item == null) {
            plugin.getManager(MessageManager.class).sendMessage(p, Message.LOOTCRATE_INCORRECT_KEY,
                    ImmutableMap.of(Placeholder.CRATE_NAME, crate.getName(), Placeholder.CRATE_ID, crate.getId() + ""));
            PlayerUtils.knockBackPlayer(crate, p);
            return;
        }

        // if no items
        if (crate.getItems().isEmpty())
            return;

        // if the keys match
        if (!item.getType().equals(crate.getKey().getItem().getType()) || !ObjUtils.doKeysMatch(plugin, item, crate)) {
            plugin.getManager(MessageManager.class).sendMessage(p, Message.LOOTCRATE_INCORRECT_KEY,
                    ImmutableMap.of(Placeholder.CRATE_NAME, crate.getName()));
            PlayerUtils.knockBackPlayer(crate, p);
            return;
        }

        //if cooldown is in effect
        if(isCooldownInEffect(crate, p)) return;

        // if inv is full
        if (InventoryUtils.isFull(p.getInventory())) {
            plugin.getManager(MessageManager.class).sendMessage(p, Message.INVENTORY_FULL, null);
            return;
        }

        // remove item
        p.getInventory().getItemInMainHand().setAmount(item.getAmount() - 1);
        p.updateInventory();
        openCrate(crate, p);
    }

    private void openCrate(Crate crate, Player p) {
        plugin.getManager(CrateManager.class).crateOpenEffects(crate, p);
        cooldownManager.addCooldown(p.getUniqueId(), crate);
        openAnimation(crate, p);
    }

    private boolean isCooldownInEffect(Crate crate, Player p) {
        if(CommandUtils.hasCooldownOverride(crate, p)) return false;
        if(!cooldownManager.canOpen(p.getUniqueId(), crate)) {
            plugin.getManager(MessageManager.class).sendMessage(p, Message.LOOTCRATE_COOLDOWN_IN_EFFECT,
                    ImmutableMap.of(Placeholder.CRATE_NAME, crate.getName(), Placeholder.TIME, cooldownManager.timeLeft(p.getUniqueId(), crate) + ""));
            return true;
        }
        return false;
    }

    private void openAnimation(Crate crate, Player p) {
        AnimatedFrame frame = null;
        CrateOption opt = crate.getOption(CrateOptionType.ANIMATION_STYLE);
        AnimationStyle type = AnimationStyle.valueOf((String) opt.getValue());
        switch (type) {
            case CSGO:
                frame = new CrateCSGOAnimationFrame(plugin, p, crate);
                break;
            case REMOVING_ITEM:
                frame = new CrateRemovingItemAnimationFrame(plugin, p, crate);
                break;
            case NONE:
                plugin.getManager(CrateManager.class).giveReward(plugin.getManager(CrateManager.class).getRandomItem(crate), p, crate.getName(), crate);
                return;
            default:
                frame = new CrateRandomGlassAnimationFrame(plugin, p, crate);
                break;

        }

        plugin.getManager(InventoryManager.class).openFrame(p, frame);

        frame.showAnimation();
    }

}
