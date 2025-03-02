package lootcrate.gui.item;


import org.bukkit.entity.Player;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.inventory.ItemStack;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Consumer;

public class MenuItem {

    private final Map<ClickType, Consumer<Player>> actions = new HashMap<>();
    private ItemStack item;

    public MenuItem(ItemStack item) {
        this.item = item;
    }

    public void setStack(ItemStack item) {
        this.item = item;
    }

    public ItemStack getStack() {
        return item;
    }

    public void addClickAction(ClickType type, Consumer<Player> action) {
        actions.put(type, action);
    }

    public Consumer<Player> getClickAction(ClickType type) {
        if(actions.containsKey(type)) {
            return actions.get(type);
        }
        return player -> {};
    }
}
