package xyz.thesteve.survivalmcraft.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;
import xyz.thesteve.survivalmcraft.Main;
import xyz.thesteve.survivalmcraft.Var;
import xyz.thesteve.survivalmcraft.utils.Home;

public class SethomeCommand implements CommandExecutor {

    private final Main plugin;

    public SethomeCommand(Main plugin) {
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
            player.sendMessage(Var.PREFIX + "§cPlease use §e/sethome§c.");
            return true;
        }

        plugin.saveHomeToConfig(uuid, new Home(player.getLocation()));
        player.sendMessage(Var.PREFIX + "§aSuccessfully §5saved §7your §bHome§7.");

        return true;
    }
}
