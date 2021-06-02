package com.tompkins_development.spigot.lootcrate.gui.frames;

import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import com.tompkins_development.spigot.lootcrate.LootCrate;
import com.tompkins_development.spigot.lootcrate.gui.events.custom.GUIItemClickEvent;
import com.tompkins_development.spigot.lootcrate.gui.frames.types.ExtendedFrame;
import com.tompkins_development.spigot.lootcrate.gui.items.GUIItem;
import com.tompkins_development.spigot.lootcrate.objects.Crate;

public class CrateViewFrame extends ExtendedFrame
{

    private LootCrate plugin;
    private Crate crate;

    public CrateViewFrame(LootCrate plugin, Player p, Crate crate)
    {
	super(plugin, p, crate.getName());

	this.plugin = plugin;
	this.crate = plugin.getCacheManager().getCrateById(crate.getId());

	generateFrame();
	registerItems();
	registerFrame();
    }

    @Override
    public void generateFrame()
    {
	int index = 0;
	for (ItemStack item : plugin.getInvManager().addCrateEffects(crate))
	{
	    if (index < getInventory().getSize())
		this.setItem(index, new GUIItem(index, item));
	    index++;
	}
    }

    @Override
    public void unregisterFrame()
    {
	GUIItemClickEvent.getHandlerList().unregister(this);
    }

}
