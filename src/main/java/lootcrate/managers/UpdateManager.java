package lootcrate.managers;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

import org.bukkit.plugin.java.JavaPlugin;

import lootcrate.LootCrate;
import lootcrate.other.Option;

public class UpdateManager
{
    private OptionManager optionManager;
    private int project = 0;
    private URL checkURL;
    private String newVersion = "";
    private LootCrate plugin;

    /**
     * Constructor for CrateManager
     * 
     * @param plugin
     *            An instance of the plugin
     */
    public UpdateManager(LootCrate plugin)
    {
	this.plugin = plugin;
	this.optionManager = plugin.optionManager;
	this.newVersion = plugin.getDescription().getVersion();
	this.project = optionManager.valueOf(Option.RESOURCE_ID);
	try
	{
	    this.checkURL = new URL("https://api.spigotmc.org/legacy/update.php?resource=" + getProjectID());
	} catch (MalformedURLException e)
	{
	}
    }

    public int getProjectID()
    {
	return project;
    }

    public JavaPlugin getPlugin()
    {
	return plugin;
    }

    public String getResourceURL()
    {
	return "https://www.spigotmc.org/resources/" + project;	
    }

    public String getNewVersion()
    {
	URLConnection con;
	try
	{
	    con = checkURL.openConnection();
	    return new BufferedReader(new InputStreamReader(con.getInputStream())).readLine();
	} catch (IOException e)
	{
	    // TODO Auto-generated catch block
	    e.printStackTrace();
	}
	return "None";
    }

    public boolean checkForUpdates()
    {
	URLConnection con = null;
	try
	{
	    con = checkURL.openConnection();
	} catch (IOException e)
	{
	    // TODO Auto-generated catch block
	    e.printStackTrace();
	}
	try
	{
	    this.newVersion = new BufferedReader(new InputStreamReader(con.getInputStream())).readLine();
	} catch (IOException e)
	{
	    // TODO Auto-generated catch block
	    e.printStackTrace();
	}
	return !plugin.getDescription().getVersion().equals(newVersion);
    }
}
