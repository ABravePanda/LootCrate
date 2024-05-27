package lootcrate.managers;

import lootcrate.LootCrate;
import lootcrate.gui.AbstractFrame;
import lootcrate.gui.Frame;
import lootcrate.gui.events.FrameCloseEvent;
import lootcrate.gui.events.FrameOpenEvent;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.checkerframework.checker.units.qual.A;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.UUID;

public class FrameManager extends BasicManager {

    private HashMap<UUID, Frame> frames;

    public FrameManager(LootCrate plugin) {
        super(plugin);
        this.frames = new HashMap<>();
    }

    @Override
    public void enable() {
        // Initialization logic for enabling the manager
    }

    @Override
    public void disable() {
        // Cleanup logic for disabling the manager
    }

    public void openFrame(UUID playerUUID, Frame frame) {
        FrameOpenEvent frameOpenEvent = new FrameOpenEvent(frame, playerUUID);
        getPlugin().getServer().getPluginManager().callEvent(frameOpenEvent);
    }

    public void closeFrame(UUID playerUUID, boolean manual) {
        FrameCloseEvent frameCloseEvent = new FrameCloseEvent(getOpenFrame(playerUUID), playerUUID, manual);
        getPlugin().getServer().getPluginManager().callEvent(frameCloseEvent);
    }

    public boolean isFrameOpen(UUID playerUUID) {
        return frames.containsKey(playerUUID);
    }

    public void addOpenFrame(UUID playerUUID, Frame openFrame) {
        frames.put(playerUUID, openFrame);
    }

    public Frame getOpenFrame(UUID playerUUID) {
        return frames.get(playerUUID);
    }


    public void removeOpenFrame(UUID playerUUID) {
        frames.remove(playerUUID);
    }
}
