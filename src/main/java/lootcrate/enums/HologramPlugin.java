package lootcrate.enums;

public enum HologramPlugin {
    DECENT_HOLOGRAMS("DecentHolograms"),
    HOLOGRAPHIC_DISPLAYS("HolographicDisplays"),
    FANCY_HOLOGRAMS("FancyHolograms");

    final String pluginName;

    HologramPlugin(String pluginName) {
        this.pluginName = pluginName;
    }

    public String getPluginName() {
        return this.pluginName;
    }

}
