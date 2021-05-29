package lootcrate.gui.frames.animations;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;

import lootcrate.LootCrate;
import lootcrate.gui.events.custom.GUIItemClickEvent;
import lootcrate.gui.frames.types.AnimatedFrame;
import lootcrate.gui.items.GUIItem;
import lootcrate.objects.Crate;
import lootcrate.objects.CrateItem;

public class CrateRemovingItemAnimationFrame extends AnimatedFrame
{

    private LootCrate plugin;
    private Crate crate;
    private long rewardSpeed = 3;
    private int taskID;
    private final Material fillMaterial = Material.RED_STAINED_GLASS_PANE;
    private List<Integer> numList;
    private int rewardID;

    public CrateRemovingItemAnimationFrame(LootCrate plugin, Player p, Crate crate)
    {
	super(plugin, p, crate.getName());

	this.plugin = plugin;
	this.crate = crate;

	generateFrame();
	registerItems();
	registerFrame();
	initList();
    }

    @Override
    public void generateFrame()
    {
	initLineup();
    }

    @Override
    public void unregisterFrame()
    {
	GUIItemClickEvent.getHandlerList().unregister(this);
    }

    /**
     * Displays animations for open sequence
     */
    public void showAnimation()
    {
	rewardID = animateReward();
    }
    
    private void countdown()
    {
	taskID = Bukkit.getScheduler().scheduleSyncRepeatingTask(plugin, new Runnable()
	{
	    int finalCount = 3;

	    @Override
	    public void run()
	    {
		if (finalCount == 0)
		{
		    close();
		    Bukkit.getScheduler().cancelTask(taskID);
		}
		finalCount--;
	    }
	}, 0L, 20L);
    }

    // animates rewards
    private int animateReward()
    {
	return Bukkit.getScheduler().scheduleSyncRepeatingTask(plugin, new Runnable()
	{
	    @Override
	    public void run()
	    {
		int randomNumber = getRandomNumber();
		setItem(randomNumber, new GUIItem(randomNumber, fillMaterial, " "));
		if(numList.size() == 1) giveReward();
	    }
	}, 0L, this.rewardSpeed);
    }

    private void initLineup()
    {
	for (int i = 0; i < this.getSize(); i++)
	    setItem(i, new GUIItem(i, plugin.getCrateManager().getRandomItem(crate)));
    }

    private void giveRewards(CrateItem crateItem)
    {
	plugin.getCrateManager().giveReward(crateItem, getViewer());
    }

    private void initList()
    {
	numList = new ArrayList<Integer>();
	for (int i = 0; i < this.getSize(); i++)
	    numList.add(i);
    }

    private int getRandomNumber()
    {
	if (numList.size() == 0)
	    return 0;

	int index = new Random().nextInt(numList.size());
	int num = numList.get(index);
	numList.remove(index);
	return num;
    }

    private int getRewardsLeftCount()
    {
	int count = 0;
	for (GUIItem i : getContents())
	    if (i.getItemStack().getType() != fillMaterial)
		count++;
	return count;
    }

    private int getRemainingIndex()
    {
	if (getRewardsLeftCount() != 1)
	    return 0;
	for (GUIItem i : getContents())
	    if (i.getItemStack().getType() != fillMaterial)
		return i.getSlot();
	return 0;
    }

    private void giveReward()
    {
	Bukkit.getScheduler().cancelTask(rewardID);
	int index = getRemainingIndex();
	GUIItem item = getContents()[index];
	fillBackground(Material.LIME_STAINED_GLASS_PANE);
	this.setItem(22, item);
	giveRewards(item.getCrateItem());
	countdown();
    }

}
