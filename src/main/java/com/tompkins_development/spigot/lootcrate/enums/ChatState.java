package com.tompkins_development.spigot.lootcrate.enums;

import com.tompkins_development.spigot.lootcrate.objects.Crate;

public enum ChatState
{
    CHANGE_CRATE_NAME(null), CHANGE_CRATE_MESSAGE(null), CHANGE_CRATE_SOUND(null);

    private Crate crate;

    ChatState(Crate crate)
    {
	this.crate = crate;
    }

    public Crate getCrate()
    {
	return this.crate;
    }

    public void setCrate(Crate crate)
    {
	this.crate = crate;
    }
}
