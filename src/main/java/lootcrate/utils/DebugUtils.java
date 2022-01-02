package lootcrate.utils;

import lootcrate.objects.Crate;
import lootcrate.objects.CrateItem;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

import java.util.List;

public class DebugUtils
{
    public static CrateItem createRandomItem(Crate crate) {
        return new CrateItem(getRandomItem(), 1, 1, 1, false, null);
    }

    private static ItemStack getRandomItem() {
        Material[] mats = Material.values();
        int randomIndex = (int) ((Math.random() * ((mats.length-1) - 0)) + 0);
        if(!mats[randomIndex].isItem()) return getRandomItem();
        if(mats[randomIndex] == Material.AIR) return getRandomItem();
        return new ItemStack(mats[randomIndex]);
    }

    public static Crate addItems(Crate crate, int amount) {
        for(int i = 0; i < amount; i++) {
            crate.addItem(createRandomItem(crate));
        }
        return crate;
    }
}
