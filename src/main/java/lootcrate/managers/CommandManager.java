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

public class CommandManager extends BasicManager implements Manager, CommandExecutor, TabCompleter {
    private final MessageManager messageManager;
    private final CrateManager crateManager;
    private final LocationManager locationManager;

    public CommandManager(LootCrate plugin) {
        super(plugin);
        this.messageManager = plugin.getMessageManager();
        this.crateManager = plugin.getCrateManager();
        this.locationManager = plugin.getLocationManager();
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {

        if (cmd.getName().equalsIgnoreCase("message"))
            new MessageCommand(this.getPlugin(), args, sender).executeCommand();
        if (cmd.getName().equalsIgnoreCase("lootcrate"))
            new LootCrateCommand(this.getPlugin(), args, sender).executeCommand();
        if (cmd.getName().equalsIgnoreCase("meta"))
            new MetaCommand(this.getPlugin(), args, sender).executeCommand();
        return false;
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, Command cmd, String alias, String[] args) {
        if (cmd.getName().equalsIgnoreCase("message"))
            return new MessageCommand(this.getPlugin(), args, sender).runTabComplete();
        if (cmd.getName().equalsIgnoreCase("lootcrate"))
            return new LootCrateCommand(this.getPlugin(), args, sender).runTabComplete();
        if (cmd.getName().equalsIgnoreCase("meta"))
            return new MetaCommand(this.getPlugin(), args, sender).runTabComplete();
        return null;
    }

    @Override
    public void enable() {
        this.getPlugin().getCommand("message").setExecutor(this);
        this.getPlugin().getCommand("message").setTabCompleter(this);
        this.getPlugin().getCommand("lootcrate").setExecutor(this);
        this.getPlugin().getCommand("lootcrate").setTabCompleter(this);
        this.getPlugin().getCommand("meta").setExecutor(this);
        this.getPlugin().getCommand("meta").setTabCompleter(this);
    }

    @Override
    public void disable() {

    }
}