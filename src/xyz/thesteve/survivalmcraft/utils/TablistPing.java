package xyz.thesteve.survivalmcraft.utils;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.DisplaySlot;
import org.bukkit.scoreboard.Objective;
import org.bukkit.scoreboard.Scoreboard;
import xyz.thesteve.survivalmcraft.Main;
import xyz.thesteve.survivalmcraft.commands.PingCommand;

import java.util.HashMap;

public class TablistPing {

    private final Main plugin;

    public static int taskID;

    public TablistPing(Main plugin) {
        this.plugin = plugin;
    }

    public void start() {
        taskID = Bukkit.getScheduler().scheduleSyncRepeatingTask(plugin, () -> {
            HashMap<String, Integer> pings = new HashMap<>();

            for (Player all : Bukkit.getOnlinePlayers()) {
                pings.put(all.getName(), Integer.valueOf(PingCommand.getPing(all)));
            }

            for (Player all : Bukkit.getOnlinePlayers()) {
                Scoreboard scoreboard = all.getScoreboard();
                if (scoreboard == null) {
                    scoreboard = Bukkit.getScoreboardManager().getNewScoreboard();
                    all.setScoreboard(scoreboard);
                }

                Objective objective = scoreboard.getObjective("pingtab");
                if (objective == null) {
                    scoreboard.registerNewObjective("pingtab", "dummy").setDisplaySlot(DisplaySlot.PLAYER_LIST);
                    objective = scoreboard.getObjective("pingtab");
                }

                for (HashMap.Entry<String, Integer> e : pings.entrySet()) {
                    objective.getScore(e.getKey()).setScore((e.getValue()).intValue());
                }

                all.setScoreboard(scoreboard);
            }
        }, 0L, 20);
    }
}
