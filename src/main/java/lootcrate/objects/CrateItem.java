package lootcrate.objects;

import lootcrate.utils.ObjUtils;
import org.bukkit.configuration.MemorySection;
import org.bukkit.configuration.serialization.ConfigurationSerializable;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class CrateItem implements Comparable<CrateItem>, ConfigurationSerializable {
    private int id;
    private ItemStack item;
    private double chance;
    private int minAmount;
    private int maxAmount;
    private List<String> commands;
    private boolean isDisplay;

    public CrateItem(ItemStack item, int minAmount, int maxAmount, double chance, boolean isDisplay,
                     List<String> commands) {
        this.setId(ObjUtils.randomID(5));
        this.setItem(item);
        this.setChance(chance);
        this.setMinAmount(minAmount);
        this.setMaxAmount(maxAmount);
        this.setDisplay(isDisplay);
        if (commands != null)
            this.setCommands(commands);
        else
            this.setCommands(new ArrayList<String>());
    }

    public CrateItem()
    {
        this.setId(ObjUtils.randomID(5));
        this.setItem(null);
        this.setChance(0);
        this.setMinAmount(0);
        this.setMaxAmount(0);
        this.setDisplay(false);
        this.setCommands(new ArrayList<String>());
    }

    public CrateItem(Map<String, Object> data) {
        if (data == null)
            return;

        this.id = (int) data.get("ID");

        // TODO remove
        // Old Version Support - will be removed in update after
        if (data.get("Item") instanceof MemorySection) {
            Map<String, Object> map = ObjUtils.MemoryToMap((MemorySection) data.get("Item"));
            this.item = ItemStack.deserialize(map);
        } else
            this.item = ItemStack.deserialize((Map<String, Object>) data.get("Item"));

        this.chance = (double) data.get("Chance");
        this.minAmount = (int) data.get("MinAmount");
        this.maxAmount = (int) data.get("MaxAmount");
        this.commands = (List<String>) data.get("Commands");
        this.isDisplay = (boolean) data.get("isDisplay");

    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public ItemStack getItem() {

        return item;
    }

    public void setItem(ItemStack item) {
        this.item = item;
    }

    public double getChance() {
        return chance;
    }

    public void setChance(double chance) {
        this.chance = chance;
    }

    public int getMinAmount() {
        return minAmount;
    }

    public void setMinAmount(int minAmount) {
        this.minAmount = minAmount;
    }

    public int getMaxAmount() {
        return maxAmount;
    }

    public void setMaxAmount(int maxAmount) {
        this.maxAmount = maxAmount;
    }

    public List<String> getCommands() {
        return commands;
    }

    public void setCommands(List<String> commands) {
        this.commands = commands;
    }

    public Map<String, Object> serialize() {
        Map<String, Object> map = new LinkedHashMap<String, Object>();

        if (getItem() == null)
            return map;

        map.put("ID", getId());
        map.put("Item", getItem().serialize());
        map.put("Chance", getChance());
        map.put("MinAmount", getMinAmount());
        map.put("MaxAmount", getMaxAmount());
        map.put("isDisplay", isDisplay());
        map.put("Commands", getCommands());

        return map;
    }

    public boolean isDisplay() {
        return isDisplay;
    }

    public void setDisplay(boolean isDisplay) {
        this.isDisplay = isDisplay;
    }

    @Override
    public int compareTo(CrateItem o) {
        return Double.valueOf(this.getChance()).compareTo(Double.valueOf(o.getChance()));
    }

    /**
     * Required static method for ConfigurationSerializable
     */
    public static CrateItem deserialize(Map<String, Object> data) {
        return new CrateItem(data);
    }

}
