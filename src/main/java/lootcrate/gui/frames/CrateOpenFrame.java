package lootcrate.gui.frames;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;

import lootcrate.LootCrate;
import lootcrate.gui.frames.types.BasicFrame;
import lootcrate.gui.items.GUIItem;
import lootcrate.objects.Crate;
import lootcrate.objects.CrateItem;

public class CrateOpenFrame extends BasicFrame
{

    private LootCrate plugin;
    private Crate crate;
    private long backgroundSpeed;
    private long rewardSpeed;
    private int duration;
    private int taskID;

    public CrateOpenFrame(LootCrate plugin, Player p, Crate crate, long backgroundSpeed, long rewardSpeed, int duration)
    {
	super(plugin, p, crate.getName());

	this.plugin = plugin;
	this.crate = crate;

	this.backgroundSpeed = backgroundSpeed;
	this.rewardSpeed = rewardSpeed;
	this.duration = duration;

	generateFrame();
	registerItems();
	registerFrame();
    }

    @Override
    public void generateFrame()
    {
	int index = 0;
	while (index < getInventory().getSize())
	{
	    this.setItem(index, new GUIItem(Material.WHITE_STAINED_GLASS_PANE));
	    index++;
	}
	this.setItem(13, new GUIItem(Material.REDSTONE_TORCH, "&cReward"));
	this.setItem(31, new GUIItem(Material.REDSTONE_TORCH, "&cReward"));
    }

    public void showAnimation()
    {
	final int backgroundID = animateBackground();
	final int rewardID = animateReward();

	taskID = Bukkit.getScheduler().scheduleSyncRepeatingTask(plugin, new Runnable()
	{
	    int timeLeft = duration;

	    @Override
	    public void run()
	    {
		if (timeLeft == 0)
		{
		    Bukkit.getScheduler().cancelTask(backgroundID);
		    Bukkit.getScheduler().cancelTask(rewardID);
		    giveRewards(getContents()[22].getCrateItem());
		}
		if (timeLeft == -2)
		{
		    close();
		    Bukkit.getScheduler().cancelTask(taskID);
		}
		timeLeft--;
	    }
	}, 0L, 20L);
    }

    private int animateBackground()
    {
	return Bukkit.getScheduler().scheduleSyncRepeatingTask(plugin, new Runnable()
	{
	    @Override
	    public void run()
	    {
		for (int i = 0; i < getInventory().getSize(); i++)
		{
		    if (i == 22 || i == 13 || i == 31)
			continue;
		    setItem(i, new GUIItem(randomGlass()));
		}
	    }
	}, 0L, this.backgroundSpeed);
    }

    private int animateReward()
    {
	return Bukkit.getScheduler().scheduleSyncRepeatingTask(plugin, new Runnable()
	{
	    @Override
	    public void run()
	    {
		setItem(22, new GUIItem(plugin.crateManager.getRandomItem(crate)));
	    }
	}, 0L, this.rewardSpeed);
    }

    private void giveRewards(CrateItem crateItem)
    {
	plugin.crateManager.giveReward(crateItem, getViewer());
    }

    private Material randomGlass()
    {
	List<Material> glass = new ArrayList<Material>();
	glass.add(Material.BLACK_STAINED_GLASS_PANE);
	glass.add(Material.BLUE_STAINED_GLASS_PANE);
	glass.add(Material.BROWN_STAINED_GLASS_PANE);
	glass.add(Material.CYAN_STAINED_GLASS_PANE);
	glass.add(Material.GRAY_STAINED_GLASS_PANE);
	glass.add(Material.GREEN_STAINED_GLASS_PANE);
	glass.add(Material.LIGHT_BLUE_STAINED_GLASS_PANE);
	glass.add(Material.LIME_STAINED_GLASS_PANE);
	glass.add(Material.MAGENTA_STAINED_GLASS_PANE);
	glass.add(Material.ORANGE_STAINED_GLASS_PANE);
	glass.add(Material.PINK_STAINED_GLASS_PANE);
	glass.add(Material.PURPLE_STAINED_GLASS_PANE);
	glass.add(Material.YELLOW_STAINED_GLASS_PANE);
	glass.add(Material.WHITE_STAINED_GLASS_PANE);
	glass.add(Material.RED_STAINED_GLASS_PANE);
	return glass.get(new Random().nextInt(glass.size() - 1));
    }

}
