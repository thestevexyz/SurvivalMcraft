package xyz.thesteve.survivalmcraft.utils;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import xyz.thesteve.survivalmcraft.Main;

import java.io.*;
import java.util.logging.Level;

public class HomesConfig extends Config {

    private final Main plugin;

    private FileConfiguration homesConfig = null;

    private File homesFile = null;

    public HomesConfig(Main plugin) {
        this.plugin = plugin;
    }

    @Override
    public void reload() {
        if (homesFile == null) {
            this.homesFile = new File(plugin.getDataFolder().getPath(), "homes.yml");
        }
        homesConfig = YamlConfiguration.loadConfiguration(homesFile);
        save();
        try {
            Reader defaultConfigStream = new FileReader(homesFile);
            YamlConfiguration defaultConfig = YamlConfiguration.loadConfiguration(defaultConfigStream);
            homesConfig.setDefaults(defaultConfig);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public FileConfiguration getConfig() {
        if (homesConfig == null) {
            reload();
        }
        return homesConfig;
    }

    @Override
    public void save() {
        if (homesConfig == null || homesFile == null) {
            return;
        }

        try {
            getConfig().save(homesFile);
        } catch (IOException e) {
            plugin.getLogger().log(Level.SEVERE, Var.PREFIX + "§cCould not save config!", e);
        }
    }
}
