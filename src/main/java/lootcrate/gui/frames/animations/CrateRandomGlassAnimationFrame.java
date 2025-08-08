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

public class CrateRandomGlassAnimationFrame extends AnimatedFrame implements Listener {

    private final LootCrate plugin;
    private final Crate crate;
    private long backgroundSpeed = 2;
    private long rewardSpeed = 3;
    private int duration = 6;
    private int taskID;
    private CustomizationManager customizationManager;

    public CrateRandomGlassAnimationFrame(LootCrate plugin, Player p, Crate crate) {
        super(plugin, p, crate.getName());

        this.plugin = plugin;
        this.crate = crate;
        this.customizationManager = plugin.getManager(CustomizationManager.class);
        this.duration = (int) customizationManager.parseLong(CustomizationOption.RND_ANIMATION_DURATION);
        this.rewardSpeed = customizationManager.parseLong(CustomizationOption.RND_ANIMATION_SCROLL_SPEED);
        this.backgroundSpeed = customizationManager.parseLong(CustomizationOption.RND_ANIMATION_GLASS_SPEED);

        generateFrame();
        registerItems();
        registerFrame();
    }

    @Override
    public void generateFrame() {
        fillBackground(customizationManager.parseMaterial(CustomizationOption.RND_ANIMATION_WINNER_BACKGROUND_MATERIAL), "", true);
    }

    @Override
    public void unregisterFrame() {
        GUIItemClickEvent.getHandlerList().unregister(this);
    }

    /**
     * Displays animations for open sequence
     */
    public void showAnimation() {
        final int backgroundID = animateBackground();
        final int rewardID = animateReward();

        taskID = Bukkit.getScheduler().scheduleSyncRepeatingTask(plugin, new Runnable() {
            int timeLeft = duration;

            @Override
            public void run() {
                if (timeLeft == 0) {
                    Bukkit.getScheduler().cancelTask(backgroundID);
                    Bukkit.getScheduler().cancelTask(rewardID);
                    fillBackground(customizationManager.parseMaterial(CustomizationOption.RND_ANIMATION_WINNER_BACKGROUND_MATERIAL), customizationManager.parseString(CustomizationOption.RND_ANIMATION_WINNER_BACKGROUND_NAME), false);
                    giveRewards(getContents()[22].getCrateItem());
                }
                if (timeLeft == -3) {
                    closeFrame(player, getAnimatedFrame());
                    Bukkit.getScheduler().cancelTask(taskID);
                }
                timeLeft--;
            }
        }, 0L, 20L);
    }

    // animates background
    private int animateBackground() {
        return Bukkit.getScheduler().scheduleSyncRepeatingTask(plugin, new Runnable() {
            @Override
            public void run() {
                for (int i = 0; i < getInventory().getSize(); i++) {
                    if (i == 22 || i == 13 || i == 31)
                        continue;
                    setItem(i, new GUIItem(i, randomGlass(), customizationManager.parseString(CustomizationOption.RND_ANIMATION_GLASS_NAME)));
                }
            }
        }, 0L, this.backgroundSpeed);
    }

    // animates rewards
    private int animateReward() {
        return Bukkit.getScheduler().scheduleSyncRepeatingTask(plugin, new Runnable() {
            @Override
            public void run() {
                setItem(22, new GUIItem(22, plugin.getManager(CrateManager.class).getRandomItem(crate)));
            }
        }, 0L, this.rewardSpeed);
    }

    private void giveRewards(CrateItem crateItem) {
        plugin.getManager(CrateManager.class).giveReward(crateItem, getViewer(), crate.getName(), crate);
    }

    private Material randomGlass() {
        List<Material> glass = new ArrayList<Material>();
        glass.add(Material.BLACK_STAINED_GLASS_PANE);
        glass.add(Material.BLUE_STAINED_GLASS_PANE);
        glass.add(Material.BROWN_STAINED_GLASS_PANE);
        glass.add(Material.CYAN_STAINED_GLASS_PANE);
        glass.add(Material.GRAY_STAINED_GLASS_PANE);
        glass.add(Material.GREEN_STAINED_GLASS_PANE);
        glass.add(Material.LIGHT_BLUE_STAINED_GLASS_PANE);
        glass.add(Material.LIME_STAINED_GLASS_PANE);
        glass.add(Material.MAGENTA_STAINED_GLASS_PANE);
        glass.add(Material.ORANGE_STAINED_GLASS_PANE);
        glass.add(Material.PINK_STAINED_GLASS_PANE);
        glass.add(Material.PURPLE_STAINED_GLASS_PANE);
        glass.add(Material.YELLOW_STAINED_GLASS_PANE);
        glass.add(Material.WHITE_STAINED_GLASS_PANE);
        glass.add(Material.RED_STAINED_GLASS_PANE);
        return glass.get(new Random().nextInt(glass.size() - 1));
    }

    public void fillBackground(Material m, String name, boolean showRewardsPointer) {
        int index = 0;
        while (index < getInventory().getSize()) {
            if (index != 22)
                this.setItem(index, new GUIItem(index, m, name));
            index++;
        }
        if (showRewardsPointer) {
            this.setItem(13, new GUIItem(13, customizationManager.parseMaterial(CustomizationOption.RND_ANIMATION_POINTER_MATERIAL), customizationManager.parseString(CustomizationOption.RND_ANIMATION_POINTER_NAME)));
            this.setItem(31, new GUIItem(31, customizationManager.parseMaterial(CustomizationOption.RND_ANIMATION_POINTER_MATERIAL), customizationManager.parseString(CustomizationOption.RND_ANIMATION_POINTER_NAME)));
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
