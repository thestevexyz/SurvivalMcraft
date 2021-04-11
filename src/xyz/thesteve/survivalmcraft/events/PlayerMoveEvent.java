package xyz.thesteve.survivalmcraft.events;

import org.bukkit.Bukkit;
import org.bukkit.Instrument;
import org.bukkit.Location;
import org.bukkit.Note;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import xyz.thesteve.survivalmcraft.commands.HomeCommand;
import xyz.thesteve.survivalmcraft.commands.SpawnCommand;

public class PlayerMoveEvent implements Listener {

    @EventHandler
    public void onMove(org.bukkit.event.player.PlayerMoveEvent event) {
        Player player = event.getPlayer();
        String uuid = player.getUniqueId().toString();

        if (SpawnCommand.playersTeleporting.contains(uuid)) {
            Location executedlocation = SpawnCommand.executedPlayerLocations.get(uuid);
            if (player.getLocation().getX() != executedlocation.getX() || player.getLocation().getY() != executedlocation.getY() || player.getLocation().getZ() != executedlocation.getZ()) {
                Bukkit.getScheduler().cancelTask(SpawnCommand.playersTaskIds.get(uuid));
                SpawnCommand.playersTeleporting.remove(uuid);
                SpawnCommand.executedPlayerLocations.remove(uuid);
                player.sendTitle("§cCancelled §5Teleporting§7.", "",  0, 20 * 2,0);
                player.playNote(player.getLocation(), Instrument.SNARE_DRUM, Note.natural(0, Note.Tone.F));
            }
        }

        if (HomeCommand.playersTeleporting.contains(uuid)) {
            Location executedlocation = HomeCommand.executedPlayerLocations.get(uuid);
            if (player.getLocation().getX() != executedlocation.getX() || player.getLocation().getY() != executedlocation.getY() || player.getLocation().getZ() != executedlocation.getZ()) {
                Bukkit.getScheduler().cancelTask(HomeCommand.playersTaskIds.get(uuid));
                HomeCommand.playersTeleporting.remove(uuid);
                HomeCommand.executedPlayerLocations.remove(uuid);
                player.sendTitle("§cCancelled §5Teleporting§7.", "",  0, 20 * 2,0);
                player.playNote(player.getLocation(), Instrument.SNARE_DRUM, Note.natural(0, Note.Tone.F));
            }
        }
    }
}
