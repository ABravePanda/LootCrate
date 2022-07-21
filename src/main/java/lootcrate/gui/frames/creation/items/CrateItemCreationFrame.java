package lootcrate.gui.frames.creation.items;

import lootcrate.LootCrate;
import lootcrate.gui.events.custom.GUIItemClickEvent;
import lootcrate.gui.frames.types.ExtendedFrame;
import lootcrate.gui.items.GUIItem;
import lootcrate.objects.Crate;
import lootcrate.objects.CrateItem;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.inventory.ItemStack;

public class CrateItemCreationFrame extends ExtendedFrame implements Listener {

    private final LootCrate plugin;
    private final Crate crate;
    private CrateItem crateItem;

    public CrateItemCreationFrame(LootCrate plugin, Player p, Crate crate, CrateItem item) {
        super(plugin, p, "");

        this.plugin = plugin;
        this.crate = crate;
        this.crateItem = item;
        this.title = ChatColor.GREEN + "" + crateItem.getId();

        generateFrame();
        registerItems();
        registerFrame();
    }

    @Override
    public void generateFrame() {
        fillBackground(Material.WHITE_STAINED_GLASS_PANE);
        fillItems();
    }

    @Override
    public void unregisterFrame() {
        GUIItemClickEvent.getHandlerList().unregister(this);
    }



    private void fillItems()
    {
        this.setItem(10, new GUIItem(10, Material.ITEM_FRAME, ChatColor.GREEN + "Icon", ChatColor.GRAY + "Set the icon of the reward"));
        this.setItem(13, new GUIItem(13, Material.BUCKET, ChatColor.GREEN + "Minimum Amount", ChatColor.GRAY + "Set the minimum amount of the reward players will receive"));
        this.setItem(16, new GUIItem(16, Material.WATER_BUCKET, ChatColor.GREEN + "Maximum Amount", ChatColor.GRAY + "Set the maximum amount of the reward players will receive"));
        this.setItem(28, new GUIItem(28, Material.GLASS, ChatColor.GREEN + "Display", ChatColor.GRAY + "Set if item is an icon only or if players will receive the reward"));
        this.setItem(31, new GUIItem(31, Material.COMMAND_BLOCK, ChatColor.GREEN + "Commands", ChatColor.GRAY + "Assign commands to run when reward is received"));
        this.setItem(34, new GUIItem(34, Material.BEACON, ChatColor.GREEN + "Chance", ChatColor.GRAY + "Set the chance the player will receive this reward"));
        this.setItem(48, new GUIItem(48, Material.FIRE_CHARGE, ChatColor.RED + "Remove", ChatColor.GRAY + "Remove this reward"));
        this.setItem(49, new GUIItem(49, Material.PAPER, ChatColor.GOLD + "Reward ID", ChatColor.GRAY + "" + crateItem.getId()));
        this.setItem(50, new GUIItem(50, Material.SLIME_BALL, ChatColor.GREEN + "Finish", ChatColor.GRAY + "Save this reward"));
    }

    // events

    @EventHandler
    public void onGUIItemClick(GUIItemClickEvent e) {
        if (!e.sameFrame(this))
            return;

        Player p = e.getPlayer();
        ItemStack item = e.getItem().getItemStack();


        if(item.getType() == Material.ITEM_FRAME)
        {
            e.setCancelled(true);
            this.closeFrame(p, this);
            this.openFrame(p, new CrateItemCreationMaterialFrame(plugin, p, crate, crateItem));
        }

        if(item.getType() == Material.BUCKET || item.getType() == Material.WATER_BUCKET)
        {
            e.setCancelled(true);
            this.closeFrame(p, this);
            this.openFrame(p, new CrateItemCreationAmountFrame(plugin, p, crate, crateItem));
        }

        if(item.getType() == Material.GLASS)
        {
            e.setCancelled(true);
            this.closeFrame(p, this);
            this.openFrame(p, new CrateItemCreationDisplayFrame(plugin, p, crate, crateItem));
        }

        if(item.getType() == Material.COMMAND_BLOCK)
        {
            e.setCancelled(true);
            this.closeFrame(p, this);
            this.openFrame(p, new CrateItemCreationCommandsFrame(plugin, p, crate, crateItem));
        }

        if(item.getType() == Material.BEACON)
        {
            e.setCancelled(true);
            this.closeFrame(p, this);
            this.openFrame(p, new CrateItemCreationChanceFrame(plugin, p, crate, crateItem));
        }

        if(item.getType() == Material.SLIME_BALL)
        {
            e.setCancelled(true);
            if(crateItem.getItem() != null) {
                if (!crate.replaceItem(crateItem)) crate.addItem(crateItem);
                plugin.getCacheManager().update(crate);
            }
            this.closeFrame(p, this);
            this.openFrame(p, new CrateItemFrame(plugin, p, crate));
        }

        if(item.getType() == Material.FIRE_CHARGE) {
            e.setCancelled(true);
            if(crate.getItem(crateItem.getId()) != null) {
                crate.removeItem(crateItem);
                plugin.getCacheManager().update(crate);
            }
            this.closeFrame(p, this);
            this.openFrame(p, new CrateItemFrame(plugin, p, crate));
        }

    }

    @Override
    public void nextPage() {

    }

    @Override
    public void previousPage() {

    }
}
