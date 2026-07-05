package lootcrate.managers.service;

import lootcrate.objects.Crate;
import org.bukkit.block.Block;

public interface HologramService {

    void createHologram(Block block, Crate crate);
    void reload();
    void enable();
    void disable();
    String getName();
}
