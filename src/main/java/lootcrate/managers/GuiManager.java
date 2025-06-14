package lootcrate.managers;

import lootcrate.LootCrate;
import lootcrate.gui.frame.GuiFrame;
import lootcrate.utils.GuiRenderer;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.inventory.Inventory;

import java.util.*;

public class GuiManager extends BasicManager implements Listener {

    private final Map<UUID, GuiFrame> openFrames = new HashMap<>();
    private final Map<UUID, Inventory> openInventories = new HashMap<>();

    public GuiManager(LootCrate plugin) {
        super(plugin);
        Bukkit.getPluginManager().registerEvents(this, plugin);
        Bukkit.getScheduler().runTaskTimer(plugin, this::tickAll, 1L, 1L);
    }

    public void open(Player player, GuiFrame frame) {
        UUID uuid = player.getUniqueId();
        Inventory currentInv = openInventories.get(uuid);
        GuiFrame currentFrame = openFrames.get(uuid);

        boolean canReuse = currentInv != null && currentInv.getSize() == frame.getSize();

        openFrames.put(uuid, frame);
        frame.onOpen();

        if (canReuse) {
            openInventories.put(uuid, currentInv);
            GuiRenderer.updateInventory(currentInv, frame);
            player.updateInventory(); // avoids cursor snap
        } else {
            Inventory newInv = GuiRenderer.renderInventory(frame);
            openInventories.put(uuid, newInv);
            player.openInventory(newInv);
        }
    }

    public void close(Player player) {
        UUID uuid = player.getUniqueId();
        GuiFrame frame = openFrames.remove(uuid);
        Inventory inv = openInventories.remove(uuid);

        if (frame != null) {
            frame.onClose();
        }

        if (player.getOpenInventory() != null) {
            player.closeInventory();
        }
    }

    public void reopen(Player player) {
        UUID uuid = player.getUniqueId();
        GuiFrame frame = openFrames.get(uuid);

        if (frame == null) return;

        Inventory inv = openInventories.get(uuid);
        if (inv != null && inv.getSize() == frame.getSize()) {
            GuiRenderer.updateInventory(inv, frame);
            player.openInventory(inv);
        } else {
            open(player, frame);
        }
    }

    public void refresh(Player player) {
        UUID uuid = player.getUniqueId();
        GuiFrame frame = openFrames.get(uuid);
        Inventory inv = openInventories.get(uuid);

        if (frame != null && inv != null) {
            GuiRenderer.updateInventory(inv, frame);
        }
    }

    public boolean isOpen(Player player) {
        return openFrames.containsKey(player.getUniqueId());
    }

    public GuiFrame getCurrentFrame(Player player) {
        return openFrames.get(player.getUniqueId());
    }

    public Inventory getOpenInventory(Player player) {
        return openInventories.get(player.getUniqueId());
    }

    public void closeAll() {
        for (UUID uuid : new HashSet<>(openFrames.keySet())) {
            Player player = Bukkit.getPlayer(uuid);
            if (player != null) {
                close(player);
            }
        }
    }

    public void tickAll() {
        for (GuiFrame frame : openFrames.values()) {
            frame.tick();
        }
    }

    @EventHandler
    public void onInventoryClose(InventoryCloseEvent event) {
        Player player = (Player) event.getPlayer();
        UUID uuid = player.getUniqueId();

        GuiFrame frame = openFrames.get(uuid);
        Inventory inv = openInventories.get(uuid);

        if (frame == null || inv != event.getInventory()) return;

        if (frame.preventsClose()) {
            Bukkit.getScheduler().runTaskLater(getPlugin(), () -> {
                if (openFrames.get(uuid) == frame) {
                    player.openInventory(inv);
                }
            }, 2L);
        } else {
            // Schedule cleanup to avoid Bukkit internal issues
            Bukkit.getScheduler().runTaskLater(getPlugin(), () -> {
                if (openFrames.get(uuid) == frame) {
                    openFrames.remove(uuid);
                    openInventories.remove(uuid);
                    frame.onClose();
                }
            }, 1L);
        }
    }

    @Override
    public void enable() {
        // already active
    }

    @Override
    public void disable() {
        closeAll();
    }
}
