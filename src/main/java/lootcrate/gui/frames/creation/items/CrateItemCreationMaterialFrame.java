package lootcrate.gui.frames.creation.items;

import lootcrate.LootCrate;
import lootcrate.gui.events.custom.GUIItemClickEvent;
import lootcrate.gui.frames.types.BasicFrame;
import lootcrate.gui.frames.types.InputAllowed;
import lootcrate.gui.items.GUIItem;
import lootcrate.objects.Crate;
import lootcrate.objects.CrateItem;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.inventory.ItemStack;

public class CrateItemCreationMaterialFrame extends BasicFrame implements Listener, InputAllowed {

    private final LootCrate plugin;
    private final Crate crate;
    private CrateItem crateItem;
    private final GUIItem saveItem;
    private final GUIItem resetItem;

    public CrateItemCreationMaterialFrame(LootCrate plugin, Player p, Crate crate, CrateItem item) {
        super(plugin, p, "");

        this.plugin = plugin;
        this.crate = crate;
        this.crateItem = item;
        this.title = ChatColor.GREEN + "" + crateItem.getId();
        this.resetItem = new GUIItem(21, Material.FIRE_CHARGE, ChatColor.RED + "Reset Item");
        this.saveItem = new GUIItem(25, Material.SLIME_BALL, ChatColor.GREEN + "Save Item");

        generateFrame();
        generateNavigation();
        registerItems();
        registerFrame();
    }

    @Override
    public void generateFrame() {
        fillBackground(Material.WHITE_STAINED_GLASS_PANE);
        fillItems();
        setRewardItem();
    }

    @Override
    public void unregisterFrame() {
        GUIItemClickEvent.getHandlerList().unregister(this);
    }



    private void fillItems()
    {
        this.setItem(12, new GUIItem(12, Material.GRAY_STAINED_GLASS_PANE, ""));
        this.setItem(13, new GUIItem(13, Material.GRAY_STAINED_GLASS_PANE, ""));
        this.setItem(14, new GUIItem(14, Material.GRAY_STAINED_GLASS_PANE, ""));
        this.setItem(19, resetItem);
        this.setItem(21, new GUIItem(21, Material.GRAY_STAINED_GLASS_PANE, ""));
        this.setItem(22, new GUIItem(22, Material.AIR));
        this.setItem(23, new GUIItem(23, Material.GRAY_STAINED_GLASS_PANE, ""));
        this.setItem(25, saveItem);
        this.setItem(30, new GUIItem(30, Material.GRAY_STAINED_GLASS_PANE, ""));
        this.setItem(31, new GUIItem(31, Material.GRAY_STAINED_GLASS_PANE, ""));
        this.setItem(32, new GUIItem(32, Material.GRAY_STAINED_GLASS_PANE, ""));
    }

    private void setRewardItem()
    {
        if(crateItem.getItem() != null)
            this.setItem(22, new GUIItem(22, crateItem.getItem()));
    }

    // events

    @EventHandler
    public void onGUIItemClick(GUIItemClickEvent e) {
        if (!e.sameFrame(this))
            return;

        Player p = e.getPlayer();
        ItemStack item = e.getItem().getItemStack();

        if(e.getItem() == resetItem)
        {
            crateItem.setItem(null);
            this.setItem(22, new GUIItem(22, Material.AIR));
        }

        if(e.getItem() == saveItem)
        {
            crateItem.setItem(inventory.getContents()[22]);
        }

    }

    @Override
    public void nextPage() {

    }

    @Override
    public void previousPage() {
        this.closeFrame(player, this);
        this.openFrame(player, new CrateItemCreationFrame(plugin, player, crate, crateItem));
    }
}
