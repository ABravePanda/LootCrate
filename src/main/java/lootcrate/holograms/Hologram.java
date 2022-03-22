package lootcrate.holograms;

import lootcrate.LootCrate;
import lootcrate.objects.Crate;
import lootcrate.utils.ObjUtils;
import org.apache.commons.lang.math.RandomUtils;
import org.bukkit.Location;
import org.bukkit.NamespacedKey;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.EntityType;
import org.bukkit.persistence.PersistentDataType;

import java.util.LinkedList;
import java.util.List;

public class Hologram {

    private Crate crate;
    private List<ArmorStand> armorStandList;
    private List<String> lines;
    private final Location initalLocation;
    private final Location baseLocation;
    private LootCrate plugin;

    public Hologram(LootCrate plugin, Crate crate, Location location, Location initalLocation)
    {
        this.plugin = plugin;
        this.crate = crate;
        armorStandList = new LinkedList<>();
        lines = new LinkedList<>();
        baseLocation = location;
        this.initalLocation = initalLocation;
    }

    private void addArmorStand(String line)
    {
        ArmorStand armorStand = null;
        if(armorStandList.size() == 0)
            armorStand = (ArmorStand) baseLocation.getWorld().spawnEntity(baseLocation.clone().subtract(0, 2.0, 0), EntityType.ARMOR_STAND);
        else
            armorStand = (ArmorStand) baseLocation.getWorld().spawnEntity(baseLocation.clone().subtract(0, 2.0+(armorStandList.size()*.25),0), EntityType.ARMOR_STAND);
        armorStand.setVisible(false);
        armorStand.setCustomName(line);
        armorStand.setCustomNameVisible(true);
        armorStand.setGravity(false);
        armorStand.setBasePlate(false);
        armorStand.setPersistent(true);
        armorStand.getPersistentDataContainer().set(plugin.getHoloManager().getKey(), PersistentDataType.STRING, "hologram");
        armorStandList.add(armorStand);
    }

    public void addLine(String text)
    {
        addArmorStand(text);
    }

    public void removeLine(int line)
    {
        if(armorStandList.size() <= line) return;
        armorStandList.get(line).remove();
        armorStandList.remove(line);
    }

    public void updateLine(int line, String text)
    {
        if(armorStandList.size() <= line) return;

        ArmorStand stand = armorStandList.get(line);
        stand.setCustomName(text);
    }

    public void delete()
    {
        for (ArmorStand stand : armorStandList)
        {
            stand.setVisible(true);
            stand.remove();
        }

    }

    public List<ArmorStand> getArmorStandList() {
        return armorStandList;
    }

    public Crate getCrate() {
        return crate;
    }

    public Location getBaseLocation() {
        return baseLocation;
    }

    public Location getInitalLocation() {
        return initalLocation;
    }
}
