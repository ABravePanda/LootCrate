package lootcrate.gui.frames.creation.items;

import lootcrate.LootCrate;
import lootcrate.enums.ChatState;
import lootcrate.gui.events.custom.GUIItemClickEvent;
import lootcrate.gui.frames.menu.CrateFrame;
import lootcrate.gui.frames.menu.CrateKeyFrame;
import lootcrate.gui.frames.types.ExtendedFrame;
import lootcrate.gui.items.GUIItem;
import lootcrate.managers.CacheManager;
import lootcrate.managers.ChatManager;
import lootcrate.objects.Crate;
import lootcrate.objects.CrateItem;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.inventory.ItemStack;

import java.util.List;

public class CrateItemCreationCommandsFrame extends ExtendedFrame implements Listener {

    private final LootCrate plugin;
    private final Crate crate;
    private CrateItem crateItem;

    public CrateItemCreationCommandsFrame(LootCrate plugin, Player p, Crate crate, CrateItem item) {
        super(plugin, p, "");

        this.plugin = plugin;
        this.crate = crate;
        this.crateItem = item;
        this.title = ChatColor.GREEN + "" + crateItem.getId();
        this.usableSize = getUsableSize()-1;

        generateFrame();
        generateNavigation();
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
        int index = 0;
        for (String command : crateItem.getCommands()) {
            this.setItem(index, createGUIItem(index, command));
            index++;
        }

        this.setItem(44, new GUIItem(44, Material.WRITABLE_BOOK, ChatColor.GREEN + "New Command"));
    }

    public GUIItem createGUIItem(int index, String command) {
        GUIItem guiItem = new GUIItem(index, Material.PAPER, ChatColor.GRAY + "/" + command);
        guiItem.addLoreLine("");
        guiItem.addLoreLine(ChatColor.RED + "Left-Click to Remove");
        return guiItem;
    }

    // events

    @EventHandler
    public void onGUIItemClick(GUIItemClickEvent e) {
        if (!e.sameFrame(this))
            return;

        Player p = e.getPlayer();
        ItemStack item = e.getItem().getItemStack();


        if(item.getType() == Material.PAPER)
        {
            e.setCancelled(true);

            crateItem.getCommands().remove(e.getItem().getSlot());
            crate.replaceItem(crateItem);
            plugin.getManager(CacheManager.class).update(crate);

            this.closeFrame(p, this);
            this.openFrame(p, new CrateItemCreationCommandsFrame(plugin, p, crate, crateItem));
        }

        if(item.getType() == Material.WRITABLE_BOOK)
        {
            e.setCancelled(true);
            ChatState state = ChatState.ADD_ITEM_COMMAND;
            state.setCrate(crate);
            state.setCrateItem(crateItem);
            plugin.getManager(ChatManager.class).addPlayer(p, state);
            plugin.getManager(ChatManager.class).sendNotification(p);
            e.setCancelled(true);
            this.close();
        }

    }

    @Override
    public void nextPage() {
        clearUsableItems();
        page++;

        int itemIndex = (page*usableSize)-usableSize;
        int index = 0;
        List<String> commands = crateItem.getCommands();
        for (int i = 0; i < getUsableSize(); i++) {
            if (index < getUsableSize() && commands.size() > itemIndex)
                this.setItem(index, createGUIItem(index, commands.get(itemIndex)));
            index++;
            itemIndex++;
        }
    }

    @Override
    public void previousPage() {
        if(page-1 == 0) {
            this.closeFrame(player, this);
            this.openFrame(player, new CrateItemCreationFrame(plugin, player, crate, crateItem));
            return;
        }
        clearUsableItems();
        page--;

        int itemIndex = (page*usableSize)-usableSize;
        int index = 0;
        List<String> commands = crateItem.getCommands();
        for (int i = 0; i < getUsableSize(); i++) {
            if (index < getUsableSize() && commands.size() > itemIndex)
                this.setItem(index, createGUIItem(index, commands.get(itemIndex)));
            index++;
            itemIndex++;
        }
    }
}
