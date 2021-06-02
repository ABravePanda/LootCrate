package com.tompkins_development.spigot.lootcrate.gui.frames.types;

import org.bukkit.entity.Player;
import org.bukkit.event.Listener;

import com.tompkins_development.spigot.lootcrate.LootCrate;
import com.tompkins_development.spigot.lootcrate.gui.items.GUIItem;

public abstract class ExtendedFrame extends BasicFrame implements Listener
{

    public ExtendedFrame(LootCrate plugin, Player p, String title, GUIItem[] contents)
    {
	super(plugin, p, title, contents, 54);
    }

    public ExtendedFrame(LootCrate plugin, Player p, String title)
    {
	super(plugin, p, title, 54);
    }



}
