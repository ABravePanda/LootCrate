package lootcrate.holograms;

import lootcrate.LootCrate;
import org.bukkit.Location;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.EntityType;

public class Hologram {

    public void makeHologram(LootCrate plugin, Location location, String text)
    {
        ArmorStand armorStand = (ArmorStand) location.getWorld().spawnEntity(location, EntityType.ARMOR_STAND);
        armorStand.setVisible(true);
    }

}
