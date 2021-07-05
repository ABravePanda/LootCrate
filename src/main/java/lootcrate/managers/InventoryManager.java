package lootcrate.managers;

import lootcrate.LootCrate;
import lootcrate.enums.CrateOptionType;
import lootcrate.objects.Crate;
import lootcrate.objects.CrateItem;
import net.md_5.bungee.api.ChatColor;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class InventoryManager extends BasicManager implements Manager {

    /**
     * Constructor for InventoryManager
     *
     * @param plugin An instance of the plugin
     */
    public InventoryManager(LootCrate plugin) {
        super(plugin);
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

    @Override
    public void enable() {

    }

    @Override
    public void disable() {

    }
}
