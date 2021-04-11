package xyz.thesteve.survivalmcraft;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;
import xyz.thesteve.survivalmcraft.commands.HomeCommand;
import xyz.thesteve.survivalmcraft.commands.SethomeCommand;
import xyz.thesteve.survivalmcraft.commands.SpawnCommand;
import xyz.thesteve.survivalmcraft.events.PlayerDeathEvent;
import xyz.thesteve.survivalmcraft.events.PlayerMoveEvent;
import xyz.thesteve.survivalmcraft.utils.Home;
import xyz.thesteve.survivalmcraft.utils.HomesConfig;

import java.util.HashMap;

public class Main extends JavaPlugin {

    private FileConfiguration homesConfig;

    private final HomesConfig homes = new HomesConfig(this);

    @Override
    public void onEnable() {
        registerEvents();
        registerCommands();

        loadConfigFiles();

        Bukkit.getConsoleSender().sendMessage(Var.PREFIX + "§aPlugin enabled successfully.");
    }

    @Override
    public void onDisable() {
        Bukkit.getConsoleSender().sendMessage(Var.PREFIX + "§cPlugin disabled successfully.");
    }

    private void registerEvents() {
        PluginManager pluginManager = Bukkit.getPluginManager();
        pluginManager.registerEvents(new PlayerDeathEvent(), this);
        pluginManager.registerEvents(new PlayerMoveEvent(), this);
    }

    private void registerCommands() {
        getCommand("spawn").setExecutor(new SpawnCommand(this));
        getCommand("home").setExecutor(new HomeCommand(this));
        getCommand("sethome").setExecutor(new SethomeCommand(this));
    }

    private void loadConfigFiles() {
        homesConfig = getHomes().getConfig();
        if(!homesConfig.isSet("allHomes")) {
            this.homesConfig.addDefault("allHomes", new HashMap<>());
        }
        homesConfig.options().copyDefaults(true);
        getHomes().save();

    }

    public boolean hasHome(String uuid) {
        homesConfig = getHomes().getConfig();
        return homesConfig.contains("allHomes." + uuid);
    }

    public Location getHomeLocationFromConfig(String uuid) {
        String path = "allHomes." + uuid;
        World world = getServer().getWorld(homesConfig.getString(path + ".world"));
        double x = homesConfig.getDouble(path + ".x");
        double y = homesConfig.getDouble(path + ".y");
        double z = homesConfig.getDouble(path + ".z");
        float yaw = (float) homesConfig.getDouble(path + ".yaw");
        float pitch = (float) homesConfig.getDouble(path + ".pitch");
        return new Location(world, x, y, z, yaw, pitch);
    }

    public void saveHomeToConfig(String uuid, Home home) {
        String path = "allHomes." + uuid;
        homesConfig = getHomes().getConfig();
        homesConfig.set(path + ".world", home.getWorld());
        homesConfig.set(path + ".x", Double.valueOf(home.getX()));
        homesConfig.set(path + ".y", Double.valueOf(home.getY()));
        homesConfig.set(path + ".z", Double.valueOf(home.getZ()));
        homesConfig.set(path + ".yaw", Float.valueOf(home.getYaw()));
        homesConfig.set(path + ".pitch", Float.valueOf(home.getPitch()));
        getHomes().save();
    }

    public HomesConfig getHomes() {
        return homes;
    }
}
