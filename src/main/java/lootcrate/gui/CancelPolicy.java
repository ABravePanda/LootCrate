package lootcrate.gui;

public enum CancelPolicy {

    /**
     * Always cancel the event regardless of context.
     */
    ALWAYS {
        @Override
        public boolean shouldCancel(ClickContext ctx) {
            return true;
        }
    },

    /**
     * Never cancel the event — allows raw Bukkit behavior.
     */
    NEVER {
        @Override
        public boolean shouldCancel(ClickContext ctx) {
            return false;
        }
    },

    /**
     * Cancel if the click is in the top (GUI) inventory.
     * Allow player inventory interaction.
     */
    TOP_ONLY {
        @Override
        public boolean shouldCancel(ClickContext ctx) {
            return ctx.isTopInventoryClick();
        }
    },

    /**
     * Cancel if the click is shift-click (i.e., item transfers).
     * Useful for restricting bulk moves.
     */
    SHIFT_ONLY {
        @Override
        public boolean shouldCancel(ClickContext ctx) {
            return ctx.isShiftClick();
        }
    },

    /**
     * Cancel if the click attempts to move an item from the hotbar into the GUI.
     */
    HOTBAR_SWAP_ONLY {
        @Override
        public boolean shouldCancel(ClickContext ctx) {
            return ctx.isNumberKeySwap();
        }
    },

    /**
     * Default behavior: cancel all top-inventory (GUI) clicks.
     */
    DEFAULT {
        @Override
        public boolean shouldCancel(ClickContext ctx) {
            return ctx.isTopInventoryClick();
        }
    };

    /**
     * Determines if the click should be cancelled given the click context.
     */
    public abstract boolean shouldCancel(ClickContext ctx);
}
