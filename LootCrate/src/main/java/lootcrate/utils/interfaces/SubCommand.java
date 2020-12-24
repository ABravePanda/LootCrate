package lootcrate.utils.interfaces;

import java.util.List;

import org.bukkit.permissions.Permission;

public interface SubCommand {


    void runSubCommand();
    List<String> runTabComplete();

}