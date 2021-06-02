package com.tompkins_development.spigot.lootcrate.events.listeners.custom;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.inventory.ItemStack;

import com.google.common.collect.ImmutableMap;
import com.tompkins_development.spigot.lootcrate.LootCrate;
import com.tompkins_development.spigot.lootcrate.enums.AnimationStyle;
import com.tompkins_development.spigot.lootcrate.enums.CrateOptionType;
import com.tompkins_development.spigot.lootcrate.enums.Message;
import com.tompkins_development.spigot.lootcrate.enums.Placeholder;
import com.tompkins_development.spigot.lootcrate.events.custom.CrateOpenEvent;
import com.tompkins_development.spigot.lootcrate.gui.frames.animations.CrateCSGOAnimationFrame;
import com.tompkins_development.spigot.lootcrate.gui.frames.animations.CrateRandomGlassAnimationFrame;
import com.tompkins_development.spigot.lootcrate.gui.frames.animations.CrateRemovingItemAnimationFrame;
import com.tompkins_development.spigot.lootcrate.gui.frames.types.AnimatedFrame;
import com.tompkins_development.spigot.lootcrate.objects.Crate;
import com.tompkins_development.spigot.lootcrate.objects.CrateOption;
import com.tompkins_development.spigot.lootcrate.utils.InventoryUtils;
import com.tompkins_development.spigot.lootcrate.utils.ObjUtils;
import com.tompkins_development.spigot.lootcrate.utils.PlayerUtils;

public class CrateOpenListener implements Listener
{
    private LootCrate plugin;

    public CrateOpenListener(LootCrate plugin)
    {
	this.plugin = plugin;
    }

    @EventHandler
    public void onOpen(CrateOpenEvent e)
    {
	Player p = e.getPlayer();
	Crate crate = e.getCrate();
	ItemStack item = p.getInventory().getItemInMainHand();

	// if they clicked w/same item as key && they match

	if (crate.getKey() == null || crate.getKey().getItem() == null || item == null)
	{
	    plugin.getMessageManager().sendMessage(p, Message.LOOTCRATE_INCORRECT_KEY,
		    ImmutableMap.of(Placeholder.CRATE_NAME, crate.getName(), Placeholder.CRATE_ID, crate.getId() + ""));
	    PlayerUtils.knockBackPlayer(crate, p);
	    return;
	}

	// if no items
	if (crate.getItems().size() == 0)
	    return;

	// if the keys match
	if (!item.getType().equals(crate.getKey().getItem().getType()) || !ObjUtils.doKeysMatch(plugin, item, crate))
	{
	    plugin.getMessageManager().sendMessage(p, Message.LOOTCRATE_INCORRECT_KEY,
		    ImmutableMap.of(Placeholder.CRATE_NAME, crate.getName()));
	    PlayerUtils.knockBackPlayer(crate, p);
	    return;
	}

	// if inv is full
	if (InventoryUtils.isFull(p.getInventory()))
	{
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

    private void openAnimation(Crate crate, Player p)
    {
	AnimatedFrame frame = null;
	CrateOption opt = crate.getOption(CrateOptionType.ANIMATION_STYLE);
	AnimationStyle type = AnimationStyle.valueOf((String) opt.getValue());
	System.out.println(type);
	switch (type)
	{
	case CSGO:
	    frame = new CrateCSGOAnimationFrame(plugin, p, crate);
	    break;
	case RANDOM_GLASS:
	    frame = new CrateRandomGlassAnimationFrame(plugin, p, crate);
	    break;
	case REMOVING_ITEM:
	    frame = new CrateRemovingItemAnimationFrame(plugin, p, crate);
	    break;
	default:
	    frame = new CrateRandomGlassAnimationFrame(plugin, p, crate);
	    break;

	}

	frame.open();

	frame.showAnimation();
    }

}
