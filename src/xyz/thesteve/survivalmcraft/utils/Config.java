package xyz.thesteve.survivalmcraft.utils;

import org.bukkit.configuration.file.FileConfiguration;
import xyz.thesteve.survivalmcraft.Main;

public abstract class Config {

    public abstract void reload();

    public abstract FileConfiguration getConfig();

    public abstract void save();
}
