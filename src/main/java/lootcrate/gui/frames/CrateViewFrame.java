package lootcrate.gui.frames;

import java.util.concurrent.Callable;

import org.bukkit.Material;
import org.bukkit.entity.Player;

import lootcrate.gui.items.GUIItem;

public class CrateViewFrame extends BasicFrame
{

    public CrateViewFrame(Player p, String title)
    {
	super(p, title);
	generateFrame();
    }

    @Override
    public void generateFrame()
    {
	GUIItem item1 = new GUIItem(Material.STONE, "&cTest", "&eTest");
	item1.setClickHandler(new Callable<Integer>() {
	    public Integer call() {
		System.out.println("Hello");
	        return 1;
	   }
	});
	this.setItem(0, item1);
    }
}
