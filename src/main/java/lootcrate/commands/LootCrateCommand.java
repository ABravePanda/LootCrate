package lootcrate.commands;

import lootcrate.LootCrate;
import lootcrate.commands.subs.*;
import lootcrate.enums.Message;
import lootcrate.enums.Permission;
import org.bukkit.command.CommandSender;

import java.util.LinkedList;
import java.util.List;

public class LootCrateCommand extends Command {

    private final LootCrate plugin;
    private final String[] args;
    private final CommandSender sender;

    public LootCrateCommand(LootCrate plugin, String[] args, CommandSender sender) {
        this.plugin = plugin;
        this.args = args;
        this.sender = sender;
    }

    @Override
    public void executeCommand() {

        if (args.length == 0) {
            plugin.getMessageManager().sendMessage(sender, Message.LOOTCRATE_BASIC_USAGE, null);
            return;
        }

        switch (args[0].toLowerCase()) {
            case "create":
                new SubCommandLootCrateCreate(plugin, sender, args).runSubCommand(false);
                break;
            case "key":
                new SubCommandLootCrateKey(plugin, sender, args).runSubCommand(true);
                break;
            case "add":
                new SubCommandLootCrateAdd(plugin, sender, args).runSubCommand(true);
                break;
            case "remove":
                new SubCommandLootCrateRemove(plugin, sender, args).runSubCommand(false);
                break;
            case "items":
                new SubCommandLootCrateItems(plugin, sender, args).runSubCommand(false);
                break;
            case "give":
                new SubCommandLootCrateGive(plugin, sender, args).runSubCommand(false);
                break;
            case "set":
                new SubCommandLootCrateSet(plugin, sender, args).runSubCommand(true);
                break;
            case "command":
                new SubCommandLootCrateCommand(plugin, sender, args).runSubCommand(false);
                break;
            case "reload":
                new SubCommandLootCrateReload(plugin, sender, args).runSubCommand(false);
                break;
            case "displaychances":
                new SubCommandLootCrateDisplayChances(plugin, sender, args).runSubCommand(false);
                break;
            case "version":
                new SubCommandLootCrateVersion(plugin, sender, args).runSubCommand(false);
                break;
            case "delete":
                new SubCommandLootCrateDelete(plugin, sender, args).runSubCommand(false);
                break;
            case "list":
                new SubCommandLootCrateList(plugin, sender, args).runSubCommand(false);
                break;
            case "gui":
                new SubCommandLootCrateGui(plugin, sender, args).runSubCommand(true);
                break;
            case "preview":
                new SubCommandLootCratePreview(plugin, sender, args).runSubCommand(true);
                break;
            case "claim":
                new SubCommandLootCrateClaim(plugin, sender, args).runSubCommand(true);
                break;
            default:
                plugin.getMessageManager().sendMessage(sender, Message.LOOTCRATE_BASIC_USAGE, null);
                break;
        }
    }

    @Override
    public List<String> runTabComplete() {
        List<String> list = new LinkedList<String>();
        if (args.length == 1) {
            if (hasPermission(sender, Permission.COMMAND_LOOTCRATE_CREATE))
                list.add("create");
            if (hasPermission(sender, Permission.COMMAND_LOOTCRATE_DELETE))
                list.add("delete");
            if (hasPermission(sender, Permission.COMMAND_LOOTCRATE_ADD))
                list.add("add");
            if (hasPermission(sender, Permission.COMMAND_LOOTCRATE_REMOVE))
                list.add("remove");
            if (hasPermission(sender, Permission.COMMAND_LOOTCRATE_SET))
                list.add("set");
            if (hasPermission(sender, Permission.COMMAND_LOOTCRATE_GIVE))
                list.add("give");
            if (hasPermission(sender, Permission.COMMAND_LOOTCRATE_KEY))
                list.add("key");
            if (hasPermission(sender, Permission.COMMAND_LOOTCRATE_ITEMS))
                list.add("items");
            if (hasPermission(sender, Permission.COMMAND_LOOTCRATE_LIST))
                list.add("list");
            if (hasPermission(sender, Permission.COMMAND_LOOTCRATE_COMMAND))
                list.add("command");
            if (hasPermission(sender, Permission.COMMAND_LOOTCRATE_RELOAD))
                list.add("reload");
            if (hasPermission(sender, Permission.COMMAND_LOOTCRATE_DISPLAYCHANCES))
                list.add("displaychances");
            if (hasPermission(sender, Permission.COMMAND_LOOTCRATE_VERSION))
                list.add("version");
            if (hasPermission(sender, Permission.COMMAND_LOOTCRATE_GUI))
                list.add("gui");
            if (hasPermission(sender, Permission.COMMAND_LOOTCRATE_PREVIEW))
                list.add("preview");
            if (hasPermission(sender, Permission.COMMAND_LOOTCRATE_CLAIM))
                list.add("claim");
            return list;
        }

        list.clear();

        switch (args[0].toLowerCase()) {
            case "create":
                return new SubCommandLootCrateCreate(plugin, sender, args).runTabComplete();
            case "key":
                return new SubCommandLootCrateKey(plugin, sender, args).runTabComplete();
            case "add":
                return new SubCommandLootCrateAdd(plugin, sender, args).runTabComplete();
            case "remove":
                return new SubCommandLootCrateRemove(plugin, sender, args).runTabComplete();
            case "items":
                return new SubCommandLootCrateItems(plugin, sender, args).runTabComplete();
            case "give":
                return new SubCommandLootCrateGive(plugin, sender, args).runTabComplete();
            case "set":
                return new SubCommandLootCrateSet(plugin, sender, args).runTabComplete();
            case "command":
                return new SubCommandLootCrateCommand(plugin, sender, args).runTabComplete();
            case "reload":
                return new SubCommandLootCrateReload(plugin, sender, args).runTabComplete();
            case "displaychances":
                return new SubCommandLootCrateDisplayChances(plugin, sender, args).runTabComplete();
            case "version":
                return new SubCommandLootCrateVersion(plugin, sender, args).runTabComplete();
            case "delete":
                return new SubCommandLootCrateDelete(plugin, sender, args).runTabComplete();
            case "list":
                return new SubCommandLootCrateList(plugin, sender, args).runTabComplete();
            case "gui":
                return new SubCommandLootCrateGui(plugin, sender, args).runTabComplete();
            case "preview":
                return new SubCommandLootCratePreview(plugin, sender, args).runTabComplete();
            case "claim":
                return new SubCommandLootCrateClaim(plugin, sender, args).runTabComplete();
            default:
                return list;
        }
    }

    public boolean hasPermission(CommandSender sender, Permission permission) {
        return this.hasPermission(sender, Permission.COMMAND_LOOTCRATE_ADMIN, permission);
    }

}
