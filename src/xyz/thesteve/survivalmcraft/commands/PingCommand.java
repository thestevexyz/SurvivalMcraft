package xyz.thesteve.survivalmcraft.commands;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.craftbukkit.v1_16_R3.entity.CraftPlayer;
import org.bukkit.entity.Player;
import xyz.thesteve.survivalmcraft.Main;
import xyz.thesteve.survivalmcraft.Var;

public class PingCommand implements CommandExecutor {

    private final Main plugin;

    public PingCommand(Main plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String label, String[] args) {
        if (args.length == 0) {
            if (commandSender instanceof ConsoleCommandSender) {
                commandSender.sendMessage(Var.CANNOT_EXECUTE_AS_PLAYER);
                return true;
            }

            final Player player = (Player) commandSender;

            player.sendMessage(Var.PREFIX + "Your §5Ping §7is §b" + getPing(player) + "§7.");

            return true;
        }

        if (args.length == 1) {
            final Player target = Bukkit.getPlayer(args[0]);

            if (target == null) {
                commandSender.sendMessage(Var.playerNotFound(args[0]));
                return true;
            }

            commandSender.sendMessage(Var.PREFIX + "The §5Ping §7of §e" + target.getName() + " §7is §b" + getPing(target) + "§7.");

            return true;
        }

        commandSender.sendMessage(Var.PREFIX + "§cPlease use §e/ping [<Player>]§c.");

        return true;
    }

    private int getPing(final Player player) {
        return (((CraftPlayer)player).getHandle()).playerConnection.player.ping;
    }
}
