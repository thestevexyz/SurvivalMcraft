package xyz.thesteve.survivalmcraft.events;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import xyz.thesteve.survivalmcraft.Var;

public class PlayerDeathEvent implements Listener {

    @EventHandler
    public void onDeath(final org.bukkit.event.entity.PlayerDeathEvent event) {
        Player player = event.getEntity();
        Location location = player.getLocation();
        Bukkit.broadcastMessage(Var.PREFIX + "§6" + player.getName() + " §cdied §7at §b§lx§b:" + (Math.round(location.getX() * 100.0) / 100.0) + " §b§ly§b:" + (Math.round(location.getY() * 100.0) / 100.0) + " §b§lz§b:" + (Math.round(location.getZ() * 100.0) / 100.0) + "§7.");
    }
}
