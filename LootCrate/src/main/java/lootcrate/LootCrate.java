package lootcrate;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import lootcrate.commands.LootCrateCommand;
import lootcrate.commands.MetaCommand;
import lootcrate.events.LootCrateInteractEvent;
import lootcrate.managers.CrateManager;
import lootcrate.managers.InventoryManager;
import lootcrate.managers.LocationManager;
import lootcrate.managers.MessageManager;

public class LootCrate extends JavaPlugin
{
    
    public MessageManager messageManager;
    public CrateManager crateManager;
    public LocationManager locationManager;
    public InventoryManager invManager;
    public List<Player> playersInInventory = new ArrayList<Player>();

    @Override
    public void onEnable()
    {
	registerConfig();
	messageManager = new MessageManager(this);
	crateManager = new CrateManager(this);
	locationManager = new LocationManager(this);
	locationManager.populateLocations();
	invManager = new InventoryManager(this);
	registerCommands();
	registerEvents();
	
    }

    @Override
    public void onDisable()
    {
    }

    public void registerEvents()
    {
	this.getServer().getPluginManager().registerEvents(new LootCrateInteractEvent(this), this);
    }
    
    public void registerConfig()
    {
	this.saveDefaultConfig();
    }
    
    public void registerCommands()
    {
	new LootCrateCommand(this);
	new MetaCommand(this);
    }
    
    public void reload()
    {
	crateManager.reload();
	locationManager.reload();
    }

}
