package lootcrate.gui.frames;

import lootcrate.LootCrate;
import lootcrate.gui.AbstractFrame;
import lootcrate.gui.GUIItem;
import lootcrate.gui.ItemBuilder;
import org.bukkit.Material;

import java.util.UUID;

public class TestFrame extends AbstractFrame {


    public TestFrame(LootCrate plugin, int size, String title, UUID owner) {
        super(plugin, size, title, owner);
    }

    @Override
    public void onClose() {
        System.out.println("Closing frame");
    }

    @Override
    public void init() {
        super.init();

        GUIItem guiItem = new ItemBuilder(getPlugin(), Material.BARRIER).setDurability(22).setName("Test").getClickableItem((itemStack, uuid) -> {
            System.out.println("Test click");
        });

        setItem(0, guiItem);

    }
}
