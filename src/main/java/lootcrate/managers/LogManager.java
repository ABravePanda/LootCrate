package lootcrate.managers;

import lootcrate.LootCrate;
import lootcrate.enums.FileType;

import java.io.File;

public abstract class LogManager extends BasicManager {

    private final FileManager fileManager;
    private File logFile;

    public LogManager(LootCrate plugin) {
        super(plugin);
        this.fileManager = getPlugin().getFileManager();
        this.logFile = createLog();
    }

    private File createLog()
    {
        return fileManager.createFile(FileType.LOG);
    }

    public void log() {
    }

}
