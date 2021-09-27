package lootcrate.gui.frames;

import lootcrate.LootCrate;
import lootcrate.gui.events.custom.GUIItemClickEvent;
import lootcrate.gui.frames.types.ExtendedFrame;
import lootcrate.gui.items.GUIItem;
import lootcrate.gui.items.NavItems;
import lootcrate.objects.Crate;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.inventory.ItemStack;

public class CrateViewFrame extends ExtendedFrame implements Listener {

    private final LootCrate plugin;
    private final Crate crate;

    public CrateViewFrame(LootCrate plugin, Player p, Crate crate) {
        super(plugin, p, crate.getName());

        this.plugin = plugin;
        this.crate = plugin.getCacheManager().getCrateById(crate.getId());

        generateFrame();
        generateNav();
        registerItems();
        registerFrame();
    }

    @Override
    public void generateFrame() {
        int index = 0;
        for (ItemStack item : plugin.getInvManager().addCrateEffects(crate)) {
            if (index < getInventory().getSize())
                this.setItem(index, new GUIItem(index, item));
            index++;
        }
    }

    @Override
    public void unregisterFrame() {
        GUIItemClickEvent.getHandlerList().unregister(this);
    }

    @EventHandler
    public void onGUIItemClick(GUIItemClickEvent e) {
        Player p = e.getPlayer();

        if(navCheck(e, crate)) return;

        if (!e.sameFrame(this))
            return;
    }



}
