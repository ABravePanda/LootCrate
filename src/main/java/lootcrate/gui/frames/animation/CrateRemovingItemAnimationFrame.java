package lootcrate.gui.frames.animation;

import lootcrate.LootCrate;
import lootcrate.gui.AbstractAnimationFrame;
import lootcrate.gui.GUIItem;
import lootcrate.objects.Crate;
import lootcrate.objects.CrateItem;
import lootcrate.utils.ArrayUtils;
import lootcrate.utils.ItemBuilder;
import org.bukkit.ChatColor;
import org.bukkit.Material;

import java.util.Arrays;
import java.util.Random;
import java.util.UUID;

public class CrateRemovingItemAnimationFrame extends AbstractAnimationFrame {

    private Crate crate;
    private static final int[] EXTERIOR_SLOTS = {
            0, 1, 2, 3, 4, 5, 6, 7, 8,
            9, 17, 18, 26, 27, 35, 36,
            37, 38, 39, 40, 41, 42, 43,
            44,
    };

    public CrateRemovingItemAnimationFrame(LootCrate plugin, Crate crate, UUID owner) {
        super(plugin, 45, crate.getName(), owner);
        this.crate = crate;
    }

    @Override
    public void init() {
        super.init();
        this.fillBackground(Material.BLUE_STAINED_GLASS_PANE);
        this.fillRewards();
    }

    @Override
    public void tick() {
        super.tick();
        removeRandomItem();
    }

    @Override
    public int getDurationTicks() {
        return 1000000;
    }

    @Override
    public int getTickRate() {
        return 2;
    }

    @Override
    public boolean hasNavigation() {
        return false;
    }

    @Override
    public void onClose(boolean manual) {

    }

    private void fillRewards() {
        for(int i = 0; i < getSize(); i++) {
            if(ArrayUtils.contains(EXTERIOR_SLOTS, i)) continue;
            CrateItem randomItem = getPlugin().getCrateManager().getRandomItem(crate);
            GUIItem guiItem = new ItemBuilder(getPlugin(), randomItem.getItem()).setCrateItem(randomItem).getClickableItem(null);
            setItem(i, guiItem);
        }
    }

    //NEED TO MAKE AN ARRAY INSTEAD

    private void removeRandomItem() {
        Random random = new Random();
        int randomIndex = random.nextInt(0, getSize());

        if(ArrayUtils.contains(EXTERIOR_SLOTS, randomIndex)) return;

        setItem(randomIndex, new ItemBuilder(getPlugin(), Material.BARRIER).setName(ChatColor.RED + "X").getClickableItem(null));
    }

}
