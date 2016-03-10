package de.flashbeatzz.servercore.utils.scoreboard;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;

public class ServerScoreboard {
    private ScoreboardManager sm;
    private Scoreboard board;
    private Objective o;
    private HashMap<Integer, String> scores = new HashMap<>();
    private List<Player> players = new ArrayList<>();

    public ServerScoreboard(String name, DisplaySlot ds) {
        sm = Bukkit.getScoreboardManager();
        board = sm.getNewScoreboard();
        o = board.registerNewObjective("obj" + board.getObjectives().size() + 1, "dummy");
        o.setDisplayName(name);
        o.setDisplaySlot(ds);
    }

    public Team addTeam(String name) {
        return board.registerNewTeam(name);
    }

    public void addLine(String line, Integer index) {
        Score score = o.getScore(line);
        score.setScore(index);
        scores.put(index, line);
    }

    public Score modifyLine(String name) {
        return o.getScore(name);
    }

    public Score modifyLine(Integer index) {
        for(Integer i : scores.keySet()) {
            if(Objects.equals(i, index)) {
                return o.getScore(scores.get(i));
            }
        }
        return null;
    }

    public void set(Player... players) {
        for(Player p : players) {
            p.setScoreboard(board);
            this.players.add(p);
        }
    }

    public void remove() {
        for(Player p : players) {
            p.setScoreboard(sm.getNewScoreboard());
        }
    }

}
