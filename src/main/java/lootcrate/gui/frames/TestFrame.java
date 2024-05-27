package lootcrate.gui.frames;

import lootcrate.LootCrate;
import lootcrate.gui.AbstractFrame;
import lootcrate.gui.GUIItem;
import lootcrate.utils.ItemBuilder;
import org.bukkit.Material;

import java.util.UUID;

public class TestFrame extends AbstractFrame {

    private boolean readyToClose;

    public TestFrame(LootCrate plugin, UUID owner) {
        super(plugin, 9, "Test Frame", owner);
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

    public void setReadyToClose(boolean readyToClose) {
        this.readyToClose = readyToClose;
    }

    @Override
    public boolean readyToClose() {
        return readyToClose;
    }

    @Override
    public boolean hasNavigation() {
        return false;
    }

    @Override
    public void nextPage() {
        return;
    }

    @Override
    public void previousPage() {
        return;
    }
}
