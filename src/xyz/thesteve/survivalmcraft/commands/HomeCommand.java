package xyz.thesteve.survivalmcraft.commands;

import org.bukkit.*;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;
import xyz.thesteve.survivalmcraft.Main;
import xyz.thesteve.survivalmcraft.Var;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

public class HomeCommand implements CommandExecutor {

    private final Main plugin;

    public static List<String> playersTeleporting = new ArrayList<>();

    public static HashMap<String, Integer> playersTaskIds = new HashMap<>();
    public static HashMap<String, Location> executedPlayerLocations = new HashMap<>();

    public HomeCommand(Main plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String label, String[] args) {
        if (commandSender instanceof ConsoleCommandSender) {
            commandSender.sendMessage(Var.NO_PLAYER);
            return true;
        }

        Player player = (Player) commandSender;
        String uuid = player.getUniqueId().toString();

        if (args.length != 0) {
            player.sendMessage(Var.PREFIX + "§cPlease use §e/home§c.");
            return true;
        }

        if (playersTeleporting.contains(uuid)) {
            player.sendMessage(Var.PREFIX + "§cYou are already teleporting!");
            return true;
        }

        if (SpawnCommand.playersTeleporting.contains(uuid)) {
            player.sendMessage(Var.PREFIX + "§cYou are already teleporting to the §bspawn§c!");
            return true;
        }

        if (!plugin.hasHome(uuid)) {
            player.sendMessage(Var.PREFIX + "§cYou do not have a home! Please set one by using §e/sethome§c.");
            return true;
        }

        Location homeLocation = plugin.getHomeLocationFromConfig(uuid);

        playersTeleporting.add(uuid);
        executedPlayerLocations.put(uuid, player.getLocation());
        AtomicInteger delay = new AtomicInteger(5);
        playersTaskIds.put(uuid, Bukkit.getScheduler().scheduleSyncRepeatingTask(plugin, () -> {
            if (delay.get() == 0) {
                Bukkit.getScheduler().cancelTask(playersTaskIds.get(uuid));
                playersTeleporting.remove(uuid);
                executedPlayerLocations.remove(uuid);
                player.teleport(homeLocation);
                player.spawnParticle(Particle.PORTAL, player.getLocation(), 100);
                player.playNote(player.getLocation(), Instrument.BELL, Note.sharp(2, Note.Tone.F
                ));
                player.sendMessage(Var.PREFIX + "§aSuccessfully §5teleported §7to your §bhome§7.");
                playersTaskIds.remove(uuid);
            } else {
                player.sendTitle("§5Teleporting §7you in §b" + delay + " seconds...", "§5Move around §7to §ccancel§7.", 0, 20, 0);
                player.playNote(player.getLocation(), Instrument.DIDGERIDOO, Note.sharp(2, Note.Tone.F));
                delay.getAndDecrement();
            }
        }, 0, 20));

        return true;
    }
}
