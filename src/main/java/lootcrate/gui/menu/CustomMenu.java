package lootcrate.gui.menu;

import lootcrate.LootCrate;
import lootcrate.gui.item.MenuItem;
import org.bukkit.entity.HumanEntity;
import org.bukkit.event.inventory.ClickType;

import java.util.Arrays;
import java.util.List;

public class CustomMenu extends SimpleMenu implements IPageable {

    private final SlotType[] slotTypes;
    private int crateId;
    private MenuType menuType;
    private AnimationType animationType;

    public CustomMenu(LootCrate plugin, MenuType menuType, AnimationType animationType, int crateId, Rows row, String title) {
        super(plugin, row, title);
        this.crateId = crateId;
        this.menuType = menuType;
        this.animationType = animationType;
        slotTypes = new SlotType[row.getSize()];
        Arrays.fill(slotTypes, SlotType.EMPTY);
    }

    @Override
    public void onSetItems() {

    }

    @Override
    public void onClose() {

    }

    @Override
    public void setItem(int slot, MenuItem item) {

        SlotType slotType = slotTypes[slot];

        switch (slotType) {
            case NEXT_PAGE -> item.addClickAction(ClickType.LEFT, player -> nextPage());
            case PREVIOUS_PAGE -> item.addClickAction(ClickType.LEFT, player -> previousPage());
            case CLOSE_MENU -> item.addClickAction(ClickType.LEFT, HumanEntity::closeInventory);
        }

        super.setItem(slot, item);
    }

    public void setSlotType(int slot, SlotType type) {
        slotTypes[slot] = type;
    }

    public SlotType getSlotType(int slot) {
        return slotTypes[slot];
    }

    public int getCrateId() {
        return crateId;
    }

    public SlotType[] getSlotTypes() {
        return slotTypes;
    }

    public MenuType getMenuType() {
        return menuType;
    }

    public AnimationType getAnimationType() {
        return animationType;
    }

    @Override
    public int getPage() {
        return 0;
    }

    @Override
    public void setPage(int page) {

    }

    @Override
    public List<MenuItem> getPageItems() {
        return List.of();
    }

    @Override
    public void nextPage() {

    }

    @Override
    public void previousPage() {

    }
}
