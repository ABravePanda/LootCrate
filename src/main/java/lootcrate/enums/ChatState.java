package lootcrate.enums;

import lootcrate.objects.Crate;

public enum ChatState {
    CHANGE_CRATE_NAME(null), CHANGE_CRATE_MESSAGE(null), CHANGE_CRATE_SOUND(null), CREATE_CRATE_NAME(null);

    private Crate crate;

    ChatState(Crate crate) {
        this.crate = crate;
    }

    public Crate getCrate() {
        return this.crate;
    }

    public void setCrate(Crate crate) {
        this.crate = crate;
    }
}
