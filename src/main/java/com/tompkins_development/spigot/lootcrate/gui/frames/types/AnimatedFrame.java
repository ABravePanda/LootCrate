package com.tompkins_development.spigot.lootcrate.gui.frames.types;

import org.bukkit.entity.Player;
import org.bukkit.event.Listener;

import com.tompkins_development.spigot.lootcrate.LootCrate;
import com.tompkins_development.spigot.lootcrate.gui.items.GUIItem;

public abstract class AnimatedFrame extends BasicFrame implements Listener
{
    public AnimatedFrame(LootCrate plugin, Player p, String title, GUIItem[] contents)
    {
	super(plugin, p, title, contents, 45);
    }

    public AnimatedFrame(LootCrate plugin, Player p, String title)
    {
	super(plugin, p, title, 45);
    }

    public abstract void showAnimation();

}
