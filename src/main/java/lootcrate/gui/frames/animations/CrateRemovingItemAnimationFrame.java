package lootcrate.gui.frames.animations;

import lootcrate.LootCrate;
import lootcrate.enums.CustomizationOption;
import lootcrate.gui.events.custom.GUIItemClickEvent;
import lootcrate.gui.frames.types.AnimatedFrame;
import lootcrate.gui.items.GUIItem;
import lootcrate.managers.CrateManager;
import lootcrate.managers.CustomizationManager;
import lootcrate.objects.Crate;
import lootcrate.objects.CrateItem;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class CrateRemovingItemAnimationFrame extends AnimatedFrame implements Listener {

    private Material fillMaterial = Material.RED_STAINED_GLASS_PANE;
    private final LootCrate plugin;
    private final Crate crate;
    private long rewardSpeed = 3;
    private int taskID;
    private List<Integer> numList;
    private int rewardID;
    private CustomizationManager customizationManager;

    public CrateRemovingItemAnimationFrame(LootCrate plugin, Player p, Crate crate) {
        super(plugin, p, crate.getName());

        this.plugin = plugin;
        this.crate = crate;
        this.customizationManager = plugin.getManager(CustomizationManager.class);
        this.rewardSpeed = customizationManager.parseLong(CustomizationOption.REMOVING_ANIMATION_DURATION);
        this.fillMaterial = customizationManager.parseMaterial(CustomizationOption.REMOVING_ANIMATION_FILLER_MATERIAL);

        generateFrame();
        registerItems();
        registerFrame();
        initList();
    }

    @Override
    public void generateFrame() {
        initLineup();
    }

    @Override
    public void unregisterFrame() {
        GUIItemClickEvent.getHandlerList().unregister(this);
    }

    /**
     * Displays animations for open sequence
     */
    public void showAnimation() {
        rewardID = animateReward();
    }

    private void countdown() {
        taskID = Bukkit.getScheduler().scheduleSyncRepeatingTask(plugin, new Runnable() {
            int finalCount = 3;

            @Override
            public void run() {
                if (finalCount == 0) {
                    closeFrame(player, getAnimatedFrame());
                    Bukkit.getScheduler().cancelTask(taskID);
                }
                finalCount--;
            }
        }, 0L, 20L);
    }

    // animates rewards
    private int animateReward() {
        return Bukkit.getScheduler().scheduleSyncRepeatingTask(plugin, new Runnable() {
            @Override
            public void run() {
                int randomNumber = getRandomNumber();
                setItem(randomNumber, new GUIItem(randomNumber, fillMaterial, customizationManager.parseString(CustomizationOption.REMOVING_ANIMATION_FILLER_NAME)));
                if (numList.size() == 1) giveReward();
            }
        }, 0L, this.rewardSpeed);
    }

    private void initLineup() {
        for (int i = 0; i < this.getSize(); i++)
            setItem(i, new GUIItem(i, plugin.getManager(CrateManager.class).getRandomItem(crate)));
    }

    private void giveRewards(CrateItem crateItem) {
        plugin.getManager(CrateManager.class).giveReward(crateItem, getViewer(), crate.getName(), crate);
    }

    private void initList() {
        numList = new ArrayList<Integer>();
        for (int i = 0; i < this.getSize(); i++)
            numList.add(i);
    }

    private int getRandomNumber() {
        if (numList.size() == 0)
            return 0;

        int index = new Random().nextInt(numList.size());
        int num = numList.get(index);
        numList.remove(index);
        return num;
    }

    private int getRewardsLeftCount() {
        int count = 0;
        for (GUIItem i : getContents())
            if (i.getItemStack().getType() != fillMaterial)
                count++;
        return count;
    }

    private int getRemainingIndex() {
        if (getRewardsLeftCount() != 1)
            return 0;
        for (GUIItem i : getContents())
            if (i.getItemStack().getType() != fillMaterial)
                return i.getSlot();
        return 0;
    }

    private void giveReward() {
        Bukkit.getScheduler().cancelTask(rewardID);
        int index = getRemainingIndex();
        GUIItem item = getContents()[index];
        fillBackground(customizationManager.parseMaterial(CustomizationOption.REMOVING_ANIMATION_FILLER_MATERIAL), customizationManager.parseString(CustomizationOption.REMOVING_ANIMATION_FILLER_NAME));
        this.setItem(22, item);
        giveRewards(item.getCrateItem());
        countdown();
    }

    private void fillBackground(Material m, String name)
    {
        int index = 0;
        while (index < getInventory().getSize()) {
            if (index != 22)
                this.setItem(index, new GUIItem(index, m, name));
            index++;
        }
    }

    @EventHandler
    public void onGUIItemClick(GUIItemClickEvent e) {
        if (!e.sameFrame(this))
            return;
        e.setCancelled(true);
    }

    @Override
    public void nextPage() {

    }

    @Override
    public void previousPage() {

    }
}
