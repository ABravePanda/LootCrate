package lootcrate.managers;

import com.google.common.collect.ImmutableMap;
import lootcrate.LootCrate;
import lootcrate.enums.CrateOptionType;
import lootcrate.enums.Message;
import lootcrate.enums.Placeholder;
import lootcrate.enums.SortType;
import lootcrate.objects.Crate;
import lootcrate.objects.CrateItem;
import lootcrate.objects.PlayerFrameMatch;
import lootcrate.utils.InventoryUtils;
import lootcrate.utils.ItemUtils;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.List;

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
    public List<ItemStack> addCrateEffects(Crate crate, SortType sortType) {
        List<CrateItem> items = new ArrayList<CrateItem>(crate.getItems());
        List<ItemStack> newList = new ArrayList<ItemStack>();
        InventoryUtils.sort(items, sortType);
        for (CrateItem item : new ArrayList<>(items)) {
            ItemStack itemStack = item.getItem().clone();
            ItemUtils.addRandomizer(this.getPlugin(), itemStack);
            if ((boolean) crate.getOption(CrateOptionType.DISPLAY_CHANCES).getValue()) {
                if (itemStack.getType() == Material.AIR) continue;
                ItemMeta meta = itemStack.getItemMeta();
                List<String> lore = meta.getLore() == null ? new ArrayList<>() : meta.getLore();
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


    @Override
    public void enable() {

    }

    @Override
    public void disable() {

    }
}
