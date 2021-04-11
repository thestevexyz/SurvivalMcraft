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

public class SpawnCommand implements CommandExecutor {

    private final Main plugin;

    public static List<String> playersTeleporting = new ArrayList<>();

    public static HashMap<String, Integer> playersTaskIds = new HashMap<>();
    public static HashMap<String, Location> executedPlayerLocations = new HashMap<>();

    public SpawnCommand(Main plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String label, String[] args) {
        if (commandSender instanceof ConsoleCommandSender) {
            commandSender.sendMessage(Var.NO_PLAYER);
            return true;
        }

        Player player = (Player) commandSender;

        if (args.length != 0) {
            player.sendMessage(Var.PREFIX + "§cPlease use §e/spawn§c.");
            return true;
        }

        if (playersTeleporting.contains(player.getUniqueId().toString())) {
            player.sendMessage(Var.PREFIX + "§cYou are already teleporting!");
            return true;
        }

        if (HomeCommand.playersTeleporting.contains(player.getUniqueId().toString())) {
            player.sendMessage(Var.PREFIX + "§cYou are already teleporting to your §bhome§c!");
            return true;
        }

        Location spawn = Bukkit.getWorld("world").getSpawnLocation();
        if (spawn == null) {
            player.sendMessage(Var.PREFIX + "§cCouldn't find world spawn!");
            return true;
        }

        playersTeleporting.add(player.getUniqueId().toString());
        executedPlayerLocations.put(player.getUniqueId().toString(), player.getLocation());
        AtomicInteger delay = new AtomicInteger(5);
        playersTaskIds.put(player.getUniqueId().toString(), Bukkit.getScheduler().scheduleSyncRepeatingTask(plugin, () -> {
            if (delay.get() == 0) {
                Bukkit.getScheduler().cancelTask(playersTaskIds.get(player.getUniqueId().toString()));
                playersTeleporting.remove(player.getUniqueId().toString());
                executedPlayerLocations.remove(player.getUniqueId().toString());
                player.teleport(spawn);
                player.spawnParticle(Particle.PORTAL, player.getLocation(), 100);
                player.playNote(player.getLocation(), Instrument.BELL, Note.sharp(2, Note.Tone.F
                ));
                player.sendMessage(Var.PREFIX + "§aSuccessfully §5teleported §7to the §bspawn§7.");
                playersTaskIds.remove(player.getUniqueId().toString());
            } else {
                player.sendTitle("§5Teleporting §7you in §b" + delay + " seconds...", "§5Move around §7to §ccancel§7.",  0, 20,0);
                player.playNote(player.getLocation(), Instrument.DIDGERIDOO, Note.sharp(2, Note.Tone.F));
                delay.getAndDecrement();
            }
        }, 0, 20));

        return true;
    }
}
