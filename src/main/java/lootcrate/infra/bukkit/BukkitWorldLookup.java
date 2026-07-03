package lootcrate.infra.bukkit;

import lootcrate.core.ports.WorldLookup;
import org.bukkit.Bukkit;
import org.bukkit.World;

import java.util.List;

public class BukkitWorldLookup implements WorldLookup {

    @Override
    public List<World> getWorlds() {
        return Bukkit.getWorlds();
    }

    @Override
    public World getWorld(String name) {
        return Bukkit.getWorld(name);
    }
}
