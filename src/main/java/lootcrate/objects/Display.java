package lootcrate.objects;

import lootcrate.utils.CommandUtils;
import org.bukkit.configuration.serialization.ConfigurationSerializable;
import org.bukkit.inventory.ItemStack;

import java.util.*;

public class Display implements ConfigurationSerializable {

    // Lines | Page | Pages
    private List<List<List<Integer>>> pages;
    private Map<Integer, ItemStack> items;
    private int nextPage;
    private int previousPage;
    private int close;

    public Display()
    {
        this.pages = new LinkedList<>();
        this.items = new HashMap<>();
        this.nextPage = -1;
        this.previousPage = -2;
        this.close = -3;
    }

    public Display(Map<String, Object> data)
    {
        this.nextPage = (int) data.get("NextPage");
        this.previousPage = (int) data.get("PreviousPage");
        this.close = (int) data.get("Close");
        this.pages = (List<List<List<Integer>>>) data.get("Pages");
        this.items = (Map<Integer, ItemStack>) data.get("Items");
    }

    public List<List<List<Integer>>> getPages() {
        return pages;
    }

    public Map<Integer, ItemStack> getItems() {
        return items;
    }

    public int addPage(List<List<Integer>> items)
    {
        int index = pages.size();
        pages.add(items);
        return index;
    }

    public List<List<Integer>> getPage(int index)
    {
        if(!items.containsKey(index)) return null;
        return pages.get(index);
    }

    public void addLine(int pageIndex, List<Integer> line)
    {
        if(line.size() != 9) return;
        List<List<Integer>> page = pages.get(pageIndex);
        page.add(line);
        pages.set(pageIndex, page);
    }

    public void setLine(int pageIndex, int lineIndex, List<Integer> line)
    {
        List<List<Integer>> page = pages.get(pageIndex);
        if(page.get(lineIndex) == null) return;
        page.remove(lineIndex);
        page.add(pageIndex, line);
        pages.set(pageIndex, page);
    }

    public void addItem(int number, ItemStack item)
    {
        items.put(number, item);
    }

    public ItemStack getItem(int index)
    {
        if(!items.containsKey(index)) return null;
        return items.get(index);
    }

    @Override
    public Map<String, Object> serialize() {
        Map<String, Object> map = new LinkedHashMap<String, Object>();
        map.put("Pages", pages);
        map.put("Items", items);
        map.put("NextPage", nextPage);
        map.put("PreviousPage", previousPage);
        map.put("Close", close);
        return map;
    }
}
