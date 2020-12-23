package lootcrate.utils.interfaces;

import java.util.List;

public interface Command
{
    void executeCommand();
    List<String> runTabComplete();
}
