package lootcrate.gui;

import lootcrate.LootCrate;
import org.bukkit.Bukkit;

import java.util.UUID;

public abstract class AbstractAnimationFrame extends AbstractFrame implements Animation{

    private int ticksRemaining;
    private int taskId;

    public AbstractAnimationFrame(LootCrate plugin, int size, String title, UUID owner) {
        super(plugin, size, title, owner);
    }

    @Override
    public void play() {
        ticksRemaining = getDurationTicks();
        taskId = Bukkit.getServer().getScheduler().scheduleSyncRepeatingTask(getPlugin(), this::tick, 0, getTickRate());
    }

    @Override
    public void stop() {
        cancelTask();
    }

    @Override
    public void tick() {
        ticksRemaining-=getTickRate();
        if(ticksRemaining == 0) stop();
    }

    @Override
    public int getTicksRemaining() {
        return ticksRemaining;
    }

    public void cancelTask() {
        Bukkit.getServer().getScheduler().cancelTask(taskId);
    }
}
