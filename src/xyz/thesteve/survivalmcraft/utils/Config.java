package xyz.thesteve.survivalmcraft.utils;

import org.bukkit.configuration.file.FileConfiguration;
import xyz.thesteve.survivalmcraft.Main;

public abstract class Config {
    private Main plugin;

    public Config(Main plugin) {
        this.plugin = plugin;
    }

    public abstract void reload();

    public abstract FileConfiguration getConfig();

    public abstract void save();
}
