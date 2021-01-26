package com.tompkins_development.spigot.lootcrate.command;

import java.util.List;

public interface Command
{
    void executeCommand();
    List<String> getTabComplete();
}
