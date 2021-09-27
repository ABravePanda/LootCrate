package lootcrate.gui.frames.menu;

import lootcrate.LootCrate;
import lootcrate.gui.events.custom.GUIItemClickEvent;
import lootcrate.gui.frames.types.ExtendedFrame;
import lootcrate.gui.items.GUIItem;
import lootcrate.gui.items.NavItems;
import lootcrate.objects.Crate;
import lootcrate.objects.CrateItem;
import net.md_5.bungee.api.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryAction;
import org.bukkit.inventory.ItemStack;

public class CrateItemFrame extends ExtendedFrame implements Listener {

    private static final Material BACKGROUND = Material.WHITE_STAINED_GLASS_PANE;
    private final LootCrate plugin;
    private final Crate crate;

    public CrateItemFrame(LootCrate plugin, Player p, Crate crate) {
        super(plugin, p, crate.getName());

        this.plugin = plugin;
        this.crate = plugin.getCacheManager().getCrateById(crate.getId());

        generateFrame();
        registerItems();
        registerFrame();
    }

    @Override
    public void generateFrame() {
        fillBackground(BACKGROUND);
        fillItems();
        generateNav();
    }

    @Override
    public void unregisterFrame() {
        GUIItemClickEvent.getHandlerList().unregister(this);
    }

    // methods

    public void fillItems() {
        int index = 0;
        for (CrateItem item : crate.getItems()) {
            if(item == null) continue;
            if(item.getItem().getType() == Material.AIR) continue;
            this.setItem(index, createGUIItem(index, item));
            index++;
        }
    }

    public GUIItem createGUIItem(int index, CrateItem item) {
        GUIItem guiItem = new GUIItem(index, item.getItem().clone());
        guiItem.addLoreLine(" ");
        guiItem.addLoreLine(ChatColor.DARK_GRAY + "---[Info]---");
        guiItem.addLoreLine(ChatColor.GRAY + "ID: " + ChatColor.GOLD + item.getId());
        guiItem.addLoreLine(ChatColor.GRAY + "Chance: " + ChatColor.GOLD + item.getChance() + "%");
        guiItem.addLoreLine(ChatColor.GRAY + "Min: " + ChatColor.GOLD + item.getMinAmount());
        guiItem.addLoreLine(ChatColor.GRAY + "Max: " + ChatColor.GOLD + item.getMaxAmount());
        guiItem.addLoreLine(ChatColor.GRAY + "Display: " + ChatColor.GOLD + item.isDisplay());
        guiItem.addLoreLine(ChatColor.GRAY + "Commands: ");
        if(item.getCommands() != null)
            for (String command : item.getCommands())
                guiItem.addLoreLine(ChatColor.GRAY + "  - /" + ChatColor.GOLD + command);
        guiItem.addLoreLine(" ");
        guiItem.addLoreLine(ChatColor.RED + "Shift-Click to Remove");
        guiItem.setCrateItem(item);
        return guiItem;
    }

    @Override
    public void nextPage(Crate crate) {
        if((page*usableSize) >= crate.getItems().size()) return;
        clearUsableItems();
        for(int i = 0; i < usableSize; i++) {
            if(usableSize+i >= crate.getItems().size()) break;
            this.setItem(i, createGUIItem(i, crate.getItems().get(usableSize+i)));
        }
        page++;
    }

    @Override
    public void previousPage(Crate crate) {
        if(page == 1) return;
        clearUsableItems();
        for(int i = 0; i < usableSize; i++) {
            if(usableSize-i >= crate.getItems().size()) break;
            if(crate.getItems().get(usableSize-i) == null) continue;
            this.setItem(i, createGUIItem(i, crate.getItems().get(usableSize-i)));
        }

        page--;

    }

    // events

    @EventHandler
    public void onGUIItemClick(GUIItemClickEvent e) {
        Player p = e.getPlayer();

        if (!e.sameFrame(this))
            return;

        GUIItem itemClicked = e.getItem();

        if (itemClicked.getItemStack().getType() == BACKGROUND)
            return;

        if(navCheck(e, crate)) return;

        CrateItem item = crate.getItem(this.getContents()[e.getItem().getSlot()].getCrateItem().getId());

        if (e.getClickEvent().getAction() != InventoryAction.MOVE_TO_OTHER_INVENTORY)
            return;


        crate.removeItem(item);
        plugin.getCacheManager().update(crate);

        this.closeFrame(p, this);
        this.openFrame(p, new CrateItemFrame(plugin, p, crate));
    }

}
