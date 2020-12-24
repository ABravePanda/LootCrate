package lootcrate;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import lootcrate.events.LootCrateInteractEvent;
import lootcrate.managers.CommandManager;
import lootcrate.managers.CrateManager;
import lootcrate.managers.InventoryManager;
import lootcrate.managers.LocationManager;
import lootcrate.managers.MessageManager;
import lootcrate.managers.OptionManager;

public class LootCrate extends JavaPlugin
{
    
    public MessageManager messageManager;
    public CrateManager crateManager;
    public LocationManager locationManager;
    public InventoryManager invManager;
    public CommandManager commandManager;
    public OptionManager optionManager;
    public List<Player> playersInInventory = new ArrayList<Player>();

    @Override
    public void onEnable()
    {
	registerConfig();
	optionManager = new OptionManager(this);
	messageManager = new MessageManager(this);
	crateManager = new CrateManager(this);
	locationManager = new LocationManager(this);
	locationManager.populateLocations();
	invManager = new InventoryManager(this);
	commandManager = new CommandManager(this);
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
    
    public void reload()
    {
	crateManager.reload();
	locationManager.reload();
    }

}
