package lootcrate.commands;

import lootcrate.enums.Permission;
import org.bukkit.command.CommandSender;

import java.util.List;

public abstract class Command {
    public abstract void executeCommand();

    public abstract List<String> runTabComplete();

    public boolean hasPermission(CommandSender sender, Permission adminPermission, Permission... perm) {
        if (adminPermission != null)
            return sender.hasPermission(adminPermission.getKey());
        for (Permission p : perm)
            if (!sender.hasPermission(p.getKey()))
                return false;
        return true;
    }
}
