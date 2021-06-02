package com.tompkins_development.spigot.lootcrate.utils;

import org.bukkit.entity.Player;

import com.tompkins_development.spigot.lootcrate.enums.CrateOptionType;
import com.tompkins_development.spigot.lootcrate.objects.Crate;

public class PlayerUtils
{
    public static void knockBackPlayer(Crate crate, Player p)
    {
	if (crate.getOption(CrateOptionType.KNOCK_BACK) != null)
	    p.setVelocity(p.getLocation().getDirection()
		    .multiply(-(double) crate.getOption(CrateOptionType.KNOCK_BACK).getValue()));
    }
}
