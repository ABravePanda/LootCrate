package lootcrate.gui.menu;

import lootcrate.LootCrate;

public class CustomMenu extends SimpleMenu {

    private final SlotType[] slotTypes;

    public CustomMenu(LootCrate plugin, Rows row, String title) {
        super(plugin, row, title);
        slotTypes = new SlotType[row.getSize()];
    }

    @Override
    public void onSetItems() {

    }

    public void setSlotType(int slot, SlotType type) {
        slotTypes[slot] = type;
    }

    public SlotType getSlotType(int slot) {
        return slotTypes[slot];
    }
}
