package lootcrate.enums;

import lootcrate.objects.Crate;
import lootcrate.objects.CrateItem;

public enum ChatState {
    CHANGE_CRATE_NAME(null),
    CHANGE_CRATE_MESSAGE(null),
    CHANGE_CRATE_SOUND(null),
    CREATE_CRATE_NAME(null),
    ADD_ITEM_COMMAND(null),
    KNOCKBACK(null),
    COOLDOWN(null),
    NONE(null);

    private Crate crate;
    private CrateItem crateItem;

    ChatState(Crate crate) {
        this.crate = crate;
    }

    public Crate getCrate() {
        return this.crate;
    }

    public void setCrate(Crate crate) {
        this.crate = crate;
    }

    public CrateItem getCrateItem() {
        return crateItem;
    }

    public void setCrateItem(CrateItem crateItem) {
        this.crateItem = crateItem;
    }
}
