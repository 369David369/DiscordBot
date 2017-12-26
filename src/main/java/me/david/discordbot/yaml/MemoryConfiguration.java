package me.david.discordbot.yaml;

public class MemoryConfiguration extends MemorySection implements Configuration {
    protected Configuration defaults;

    public MemoryConfiguration() {}

    public MemoryConfiguration(Configuration defaults) {
        this.defaults = defaults;
    }

    @Override
    public ConfigurationSection getParent() {
        return null;
    }
}
