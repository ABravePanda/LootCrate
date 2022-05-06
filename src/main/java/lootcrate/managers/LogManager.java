package lootcrate.managers;

import lootcrate.LootCrate;
import lootcrate.enums.CrateAction;
import lootcrate.enums.FileType;
import lootcrate.objects.Crate;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;

import java.io.File;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class LogManager extends BasicManager {

    private File file;
    private YamlConfiguration config;

    public LogManager(LootCrate plugin)
    {
        super(plugin);
    }

    @Override
    public void enable() {
        file = this.getPlugin().getFileManager().createFile(FileType.LOG);
        config = this.getPlugin().getFileManager().getConfiguration(file);
    }

    @Override
    public void disable() {

    }

    public void log(CrateAction action, Crate crate, Map<String, Object> map)
    {
        String date = getDate() + " | " + UUID.randomUUID().toString().substring(0, 3);

        config.set(date + ".Action", action.toString());
        config.set(date + ".Crate", crate.getId());

        for(String key : map.keySet())
        {
            config.set(date + "." + key, map.get(key));
        }
        saveLog();
    }

    public void log(CrateAction action, Crate crate, Player p, String... other)
    {
        String date = getDate() + " | " + UUID.randomUUID().toString().substring(0, 3);
        Map<String, Object> map = new HashMap<String, Object>();

        map.put("Name", p.getName());
        map.put("UUID", p.getUniqueId().toString());

        if(other != null) {
            int index = 0;
            for (String s : other) {
                map.put("Other" + index, s);
                index++;
            }
        }
        log(action, crate, map);
    }

    private void saveLog()
    {
        this.getPlugin().getFileManager().saveFile(file, config);
    }

    private String getDate()
    {
        Date date = new Date();
        SimpleDateFormat format = new SimpleDateFormat("E, MMMM dd, YYYY @ HH:mm:ss z");
        return format.format(date);
    }

}
