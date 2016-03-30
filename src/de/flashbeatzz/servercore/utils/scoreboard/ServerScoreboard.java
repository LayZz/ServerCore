package de.flashbeatzz.servercore.utils.scoreboard;

import org.bukkit.Bukkit;
import org.bukkit.scoreboard.*;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class ServerScoreboard {
    private static Scoreboard board = Bukkit.getScoreboardManager().getNewScoreboard();
    private static List<UUID> recipients = new ArrayList<>();

    public static void newObjective(String displayname, DisplaySlot ds) {
        Objective o = board.registerNewObjective(ds.name() + 1, "dummy");
        o.setDisplaySlot(ds);
        o.setDisplayName(displayname);
    }

    public static void addLine(DisplaySlot ds, String name, Integer index) {
        board.getObjective(ds).getScore(name).setScore(index);
    }

    public static void modifyLine(DisplaySlot ds, String oldName, String newName) {
        Integer i = board.getObjective(ds).getScore(oldName).getScore();
        board.resetScores(oldName);
        board.getObjective(ds).getScore(newName).setScore(i);
    }

    public static void setLineIndex(DisplaySlot ds, Integer oldIndex, Integer newIndex) {
        for(Score s : board.getScores(ds.name())) {
            if(s.getScore() == oldIndex) {
                s.setScore(newIndex);
            }
        }
    }

    public static Team getTeam(String name) {
        if(board.getTeam(name) == null) {
            return board.registerNewTeam(name);
        }
        return board.getTeam(name);
    }

    public static void setDisplayName(DisplaySlot ds, String newName) {
        board.getObjective(ds).setDisplayName(newName);
    }

    public static void showFor(UUID... players) {
        for(UUID u : players) {
            if(!recipients.contains(u)) {
                Bukkit.getPlayer(u).setScoreboard(board);
                recipients.add(u);
            }
        }
    }

    public static List<UUID> getReciepients() {
        return recipients;
    }

    public static void resetFor(UUID... players) {
        for(UUID u : players) {
            if(recipients.contains(u)) {
                Bukkit.getPlayer(u).setScoreboard(Bukkit.getScoreboardManager().getNewScoreboard());
                recipients.remove(u);
            }
        }
    }

    public static Scoreboard getBoard() {
        return board;
    }
}
