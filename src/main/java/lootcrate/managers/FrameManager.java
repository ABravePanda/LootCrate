package lootcrate.managers;

import lootcrate.LootCrate;
import lootcrate.gui.Frame;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.UUID;

public class FrameManager extends BasicManager {

    private HashMap<UUID, Frame> frames;
    private HashMap<UUID, UUID> playerFrames;

    public FrameManager(LootCrate plugin) {
        super(plugin);
        this.frames = new HashMap<>();
        this.playerFrames = new HashMap<>();
    }

    @Override
    public void enable() {

    }

    @Override
    public void disable() {

    }

    public void openFrame(Frame frame, UUID uuid) {
        frames.put(frame.getId(), frame);
        playerFrames.put(uuid, frame.getId());

        frame.init();

        Player player = Bukkit.getPlayer(uuid);
        if(player == null) {
            //TODO error catch
            return;
        }

        closeFrame(uuid);

        player.openInventory(frame.getInventory());

    }

    public void closeFrame(UUID uuid) {
        UUID frameUUID = playerFrames.get(uuid);
        if(frameUUID == null)
            return;

        Frame frame = frames.get(uuid);
        if(frame == null)
            return;

        frame.onClose();
        playerFrames.remove(uuid);

        Player player = Bukkit.getPlayer(uuid);
        if(player == null) {
            //TODO error catch
            return;
        }

        player.closeInventory();
    }
}
