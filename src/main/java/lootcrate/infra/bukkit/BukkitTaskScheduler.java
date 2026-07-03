package lootcrate.infra.bukkit;

import lootcrate.core.ports.TaskScheduler;
import org.bukkit.Bukkit;
import org.bukkit.plugin.Plugin;

public class BukkitTaskScheduler implements TaskScheduler {

    private final Plugin plugin;

    public BukkitTaskScheduler(Plugin plugin) {
        this.plugin = plugin;
    }

    @Override
    public void runTaskAsynchronously(Runnable task) {
        Bukkit.getScheduler().runTaskAsynchronously(plugin, task);
    }
}
