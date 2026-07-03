package lootcrate.core.ports;

import org.bukkit.World;

import java.util.List;

public interface WorldLookup {
    List<World> getWorlds();
    World getWorld(String name);
}
