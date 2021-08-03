package lootcrate.managers;

import lootcrate.LootCrate;
import lootcrate.enums.CrateOptionType;
import lootcrate.gui.frames.types.Frame;
import lootcrate.objects.Crate;
import lootcrate.objects.CrateItem;
import lootcrate.objects.PlayerFrameMatch;
import net.md_5.bungee.api.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.*;

public class InventoryManager extends BasicManager implements Manager {

    private final List<PlayerFrameMatch> matches;
    /**
     * Constructor for InventoryManager
     *
     * @param plugin An instance of the plugin
     */
    public InventoryManager(LootCrate plugin) {
        super(plugin);
        this.matches = new ArrayList<PlayerFrameMatch>();
    }

    /**
     * @param crate
     * @return
     */
    public List<ItemStack> addCrateEffects(Crate crate) {
        List<CrateItem> items = new ArrayList<CrateItem>(crate.getItems());
        List<ItemStack> newList = new ArrayList<ItemStack>();
        Collections.sort(items);
        for (CrateItem item : new ArrayList<CrateItem>(items)) {
            // item = ObjUtils.assignRandomIDToItem(plugin, item);
            ItemStack itemStack = item.getItem().clone();
            if ((boolean) crate.getOption(CrateOptionType.DISPLAY_CHANCES).getValue()) {
                if (itemStack.getType() == Material.AIR) continue;
                ItemMeta meta = itemStack.getItemMeta();
                List<String> lore = meta.getLore() == null ? new ArrayList<String>() : meta.getLore();
                lore.add(ChatColor.GOLD + "" + ChatColor.BOLD + "Chance: " + ChatColor.RED + item.getChance() + "%");
                meta.setLore(lore);
                itemStack.setItemMeta(meta);
                newList.add(itemStack);
            }
        }
        return newList;
    }

    public void openFrame(Player p, Frame frame) {
        System.out.println("Open frame -> " + p.getName() + " ----> " + frame.getId());
        PlayerFrameMatch match = new PlayerFrameMatch(p.getUniqueId(), frame.getId());
        removeSimilar(match);
        p.openInventory(frame.getInventory());
        matches.add(match);
    }

    public boolean contains(PlayerFrameMatch match) {
        for(PlayerFrameMatch m : matches)
            if(m.getUuid().equals(match.getUuid()) && m.getFrameid() == match.getFrameid()) return true;

        return false;
    }

    public boolean exists(PlayerFrameMatch match)
    {
        for(PlayerFrameMatch m : matches)
            if(m.getUuid().equals(match.getUuid())) return true;

        return false;
    }

    public void removeSimilar(PlayerFrameMatch match)
    {
        if(!exists(match)) return;
        for(PlayerFrameMatch m : new ArrayList<PlayerFrameMatch>(matches))
            if(m.getUuid().equals(match.getUuid())) matches.remove(m);
    }

    public void closeFrame(Player p, Frame frame) {
        System.out.println("Close frame -> " + p.getName() + " ----> " + frame.getId());
        PlayerFrameMatch match = new PlayerFrameMatch(p.getUniqueId(), frame.getId());
        if(contains(match)) p.closeInventory();
        removeSimilar(match);
    }

    @Override
    public void enable() {

    }

    @Override
    public void disable() {

    }
}
