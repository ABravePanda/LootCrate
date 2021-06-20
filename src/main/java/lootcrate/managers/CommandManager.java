package lootcrate.managers;

import lootcrate.LootCrate;
import lootcrate.commands.LootCrateCommand;
import lootcrate.commands.MessageCommand;
import lootcrate.commands.MetaCommand;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;

import java.util.List;

public class CommandManager implements Manager, CommandExecutor, TabCompleter {
    private final LootCrate plugin;
    private final MessageManager messageManager;
    private final CrateManager crateManager;
    private final LocationManager locationManager;

    public CommandManager(LootCrate plugin) {
        this.plugin = plugin;
        this.messageManager = plugin.getMessageManager();
        this.crateManager = plugin.getCrateManager();
        this.locationManager = plugin.getLocationManager();
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {

        if (cmd.getName().equalsIgnoreCase("message"))
            new MessageCommand(plugin, args, sender).executeCommand();
        if (cmd.getName().equalsIgnoreCase("lootcrate"))
            new LootCrateCommand(plugin, args, sender).executeCommand();
        if (cmd.getName().equalsIgnoreCase("meta"))
            new MetaCommand(plugin, args, sender).executeCommand();
        return false;
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, Command cmd, String alias, String[] args) {
        if (cmd.getName().equalsIgnoreCase("message"))
            return new MessageCommand(plugin, args, sender).runTabComplete();
        if (cmd.getName().equalsIgnoreCase("lootcrate"))
            return new LootCrateCommand(plugin, args, sender).runTabComplete();
        if (cmd.getName().equalsIgnoreCase("meta"))
            return new MetaCommand(plugin, args, sender).runTabComplete();
        return null;
    }

    @Override
    public void enable() {
        plugin.getCommand("message").setExecutor(this);
        plugin.getCommand("message").setTabCompleter(this);
        plugin.getCommand("lootcrate").setExecutor(this);
        plugin.getCommand("lootcrate").setTabCompleter(this);
        plugin.getCommand("meta").setExecutor(this);
        plugin.getCommand("meta").setTabCompleter(this);
    }

    @Override
    public void disable() {

    }
}