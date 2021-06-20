package lootcrate.utils;

import lootcrate.enums.CrateOptionType;
import lootcrate.objects.Crate;
import org.bukkit.entity.Player;

public class PlayerUtils {
    public static void knockBackPlayer(Crate crate, Player p) {
        if (crate.getOption(CrateOptionType.KNOCK_BACK) != null)
            p.setVelocity(p.getLocation().getDirection()
                    .multiply(-(double) crate.getOption(CrateOptionType.KNOCK_BACK).getValue()));
    }
}
