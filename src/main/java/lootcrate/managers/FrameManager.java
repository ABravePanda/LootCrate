package lootcrate.managers;

import lootcrate.LootCrate;
import lootcrate.gui.frames.types.Frame;
import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class FrameManager extends BasicManager {

    public Map<UUID, Frame> frameMap;

    public FrameManager(LootCrate plugin)
    {
        super(plugin);
        this.frameMap = new HashMap<UUID, Frame>();
    }

    public boolean inFrame(Player player)
    {
        return frameMap.containsKey(player.getUniqueId());
    }

    public Frame getFrame(Player player)
    {
        if(!inFrame(player)) return null;
        return frameMap.get(player.getUniqueId());
    }

    public void openFrame(Player player, Frame frame)
    {
        if(inFrame(player))
            closeFrame(player);
        addPlayerToFrame(player, frame);
        player.openInventory(frame.getInventory());
    }

    public void closeFrame(Player player)
    {
        if(!inFrame(player)) return;
        player.closeInventory();
    }

    private void addPlayerToFrame(Player player, Frame frame)
    {
        frameMap.put(player.getUniqueId(), frame);
    }

    @Override
    public void enable() {

    }

    @Override
    public void disable() {

    }
}
