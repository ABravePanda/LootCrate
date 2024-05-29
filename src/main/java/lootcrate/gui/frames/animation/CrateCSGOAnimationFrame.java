package lootcrate.gui.frames.animation;

import lootcrate.LootCrate;
import lootcrate.gui.AbstractAnimationFrame;
import lootcrate.gui.GUIItem;
import lootcrate.objects.Crate;
import lootcrate.objects.CrateItem;
import lootcrate.utils.ItemBuilder;
import lootcrate.utils.ItemUtils;
import org.bukkit.Material;
import org.bukkit.Sound;

import java.util.UUID;

public class CrateCSGOAnimationFrame extends AbstractAnimationFrame {

    private Crate crate;
    private boolean readyToClose = false;
    private boolean rewardGiven = false;
    private boolean manualClose;

    public CrateCSGOAnimationFrame(LootCrate plugin, Crate crate, UUID owner) {
        super(plugin, 27, crate.getName(), owner);
        this.crate = crate;
    }

    @Override
    public void init() {
        super.init();
        this.fillBackground(Material.BLUE_STAINED_GLASS_PANE);
        this.initLineup();
        setItem(4, new ItemBuilder(getPlugin(), Material.YELLOW_STAINED_GLASS_PANE).setName(" ").getClickableItem(null));
        setItem(22, new ItemBuilder(getPlugin(), Material.YELLOW_STAINED_GLASS_PANE).setName(" ").getClickableItem(null));
    }

    @Override
    public void tick() {
        super.tick();


        if(getTicksRemaining() < 50 && getTicksRemaining() > 20) {
            if(getTicksRemaining() % 2 == 0) return;
        } else if(getTicksRemaining() < 20) return;

        setItem(11, getItem(12));
        setItem(12, getItem(13));
        setItem(13, getItem(14));
        setItem(14, getItem(15));
        CrateItem randomItem = getPlugin().getCrateManager().getRandomItem(crate);
        setItem(15, new ItemBuilder(getPlugin(), randomItem.getItem()).setCrateItem(randomItem).getClickableItem(null));

        getPlayer().playSound(getPlayer().getLocation(), Sound.UI_BUTTON_CLICK, 1f, 1f);
    }

    @Override
    public void stop() {
        super.stop();

        if(rewardGiven) return;

        if(manualClose) {
            getPlugin().getCrateManager().giveReward(getPlugin().getCrateManager().getRandomItem(crate), getPlayer());
            getPlayer().playSound(getPlayer().getLocation(), Sound.ENTITY_EXPERIENCE_ORB_PICKUP, 1f, 1f);
            rewardGiven = true;
            readyToClose = true;
        } else {
            GUIItem rewardItem = getItem(13);
            int crateItemID = ItemUtils.getCrateItemID(rewardItem.getItemStack());
            CrateItem crateItem = getPlugin().getCrateManager().getCrateItemById(crate, crateItemID);
            getPlugin().getCrateManager().giveReward(crateItem, getPlayer());
            getPlayer().playSound(getPlayer().getLocation(), Sound.ENTITY_EXPERIENCE_ORB_PICKUP, 1f, 1f);
            rewardGiven = true;
            readyToClose = true;
            getPlugin().getFrameManager().closeFrame(getOwner(), false);
        }

    }

    @Override
    public boolean readyToClose() {
        return readyToClose;
    }

    @Override
    public boolean canBeManuallyClosed() {
        return true;
    }

    @Override
    public int getDurationTicks() {
        return 100;
    }

    @Override
    public int getTickRate() {
        return 1;
    }

    @Override
    public boolean hasNavigation() {
        return false;
    }

    @Override
    public void onClose(boolean manual) {
        this.manualClose = manual;
        stop();
    }

    private void initLineup() {
        CrateItem randomItem1 = getPlugin().getCrateManager().getRandomItem(crate);
        CrateItem randomItem2 = getPlugin().getCrateManager().getRandomItem(crate);
        CrateItem randomItem3 = getPlugin().getCrateManager().getRandomItem(crate);
        CrateItem randomItem4 = getPlugin().getCrateManager().getRandomItem(crate);
        CrateItem randomItem5 = getPlugin().getCrateManager().getRandomItem(crate);
        setItem(11, new ItemBuilder(getPlugin(), randomItem1.getItem()).setCrateItem(randomItem1).getClickableItem(null));
        setItem(12, new ItemBuilder(getPlugin(), randomItem2.getItem()).setCrateItem(randomItem2).getClickableItem(null));
        setItem(13, new ItemBuilder(getPlugin(), randomItem3.getItem()).setCrateItem(randomItem3).getClickableItem(null));
        setItem(14, new ItemBuilder(getPlugin(), randomItem4.getItem()).setCrateItem(randomItem4).getClickableItem(null));
        setItem(15, new ItemBuilder(getPlugin(), randomItem5.getItem()).setCrateItem(randomItem5).getClickableItem(null));
    }
}
