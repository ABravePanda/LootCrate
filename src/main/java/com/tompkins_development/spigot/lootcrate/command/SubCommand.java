package com.tompkins_development.spigot.lootcrate.command;

import java.util.List;

public interface SubCommand
{
    void executeSubCommand();
    List<String> getTabComplete();
}
