package lootcrate.utils;

import org.bukkit.entity.Player;

import lootcrate.enums.CrateOptionType;
import lootcrate.objects.Crate;

public class PlayerUtils
{
    public static void knockBackPlayer(Crate crate, Player p)
    {
	if (crate.getOption(CrateOptionType.KNOCK_BACK) != null)
	    p.setVelocity(p.getLocation().getDirection()
		    .multiply(-(double) crate.getOption(CrateOptionType.KNOCK_BACK).getValue()));
    }
}
