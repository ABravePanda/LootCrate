package lootcrate.utils.interfaces;

import java.util.List;

public interface SubCommand {


    void runSubCommand();
    List<String> runTabComplete();

}