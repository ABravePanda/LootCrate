package lootcrate.utils;

import org.bukkit.entity.Player;

import lootcrate.managers.OptionManager;
import lootcrate.other.Option;

public class PlayerUtils
{
    public static void knockBackPlayer(OptionManager manager, Player p)
    {
	if(manager.valueOf(Option.CRATE_KNOCKBACK))
	    p.setVelocity(p.getLocation().getDirection().multiply(-(double) manager.valueOf(Option.CRATE_KNOCKBACK_POWER)));
    }
}
