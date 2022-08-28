package lootcrate.managers;

import com.google.common.collect.ImmutableMap;
import lootcrate.LootCrate;
import lootcrate.enums.CrateOptionType;
import lootcrate.enums.Message;
import lootcrate.enums.Placeholder;
import lootcrate.gui.frames.types.Frame;
import lootcrate.objects.Crate;
import lootcrate.objects.CrateItem;
import lootcrate.objects.PlayerFrameMatch;
import lootcrate.utils.ItemUtils;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Item;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.*;

public class InventoryManager extends BasicManager {

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
            ItemStack itemStack = item.getItem().clone();
            ItemUtils.addRandomizer(this.getPlugin(), itemStack);
            if ((boolean) crate.getOption(CrateOptionType.DISPLAY_CHANCES).getValue()) {
                if (itemStack.getType() == Material.AIR) continue;
                ItemMeta meta = itemStack.getItemMeta();
                List<String> lore = meta.getLore() == null ? new ArrayList<String>() : meta.getLore();
                lore.add(" ");
                lore.add(this.getPlugin().getMessageManager().parseMessage(Message.CHANCE,
                        ImmutableMap.of(Placeholder.ITEM_CHANCE, item.getChance() + "")));
                meta.setLore(lore);
                itemStack.setItemMeta(meta);

            }
            newList.add(itemStack);
        }
        return newList;
    }

    public ItemStack addCrateEffect(Crate crate, CrateItem item)
    {
        ItemStack itemStack = item.getItem();
        if (itemStack.getType() == Material.AIR) return itemStack;
        ItemMeta meta = itemStack.getItemMeta();
        List<String> lore = meta.getLore() == null ? new ArrayList<String>() : meta.getLore();
        lore.add(" ");
        lore.add(this.getPlugin().getMessageManager().parseMessage(Message.CHANCE,
                ImmutableMap.of(Placeholder.ITEM_CHANCE, item.getChance() + "")));
        meta.setLore(lore);
        itemStack.setItemMeta(meta);
        return itemStack;
    }

    public void openFrame(Player p, Frame frame) {
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
