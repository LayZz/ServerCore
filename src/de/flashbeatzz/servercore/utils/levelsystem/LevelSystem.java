package de.flashbeatzz.servercore.utils.levelsystem;

import de.flashbeatzz.servercore.utils.MySQL;
import de.flashbeatzz.servercore.utils.events.LevelChangeEvent;
import org.bukkit.Bukkit;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.UUID;

public class LevelSystem {

    public static Integer getEXPLevel(UUID uuid) {
        ResultSet rs = MySQL.query("SELECT * FROM `levelsystem` WHERE `uuid`='" + uuid.toString() + "';");
        try {
            if(rs != null && rs.next()) {
                return rs.getInt("exp");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static Integer getTotalEXP(UUID uuid) {
        Integer lvl = 0;
        Integer exp = 0;
        ResultSet rs = MySQL.query("SELECT * FROM `levelsystem` WHERE `uuid`='" + uuid.toString() + "';");
        try {
            if(rs != null && rs.next()) {
                lvl = rs.getInt("level");
                exp = rs.getInt("exp");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        for(int i = 1; i <= lvl; i++) {
            exp += new Level(i).MAX_EXP;
        }
        return exp;
    }

    public static Boolean setTotalExp(UUID uuid, Integer exp) {
        if(!exist(uuid)) {
            return false;
        }
        Integer lvlBefore;
        Integer lvl = 1;
        for(int i = 1; exp - new Level(i).MAX_EXP >= 0; i++) {
            exp -= new Level(i).MAX_EXP;
            lvlBefore = lvl;
            lvl = i++;
            Bukkit.getPluginManager().callEvent(new LevelChangeEvent(uuid, lvlBefore, lvl));
        }
        MySQL.update("UPDATE `levelsystem` SET `level`='" + lvl + "', `exp`='" + exp + "' WHERE `uuid`='" + uuid.toString() + "';");
        return true;
    }

    public static Boolean addExp(UUID uuid, Integer Exp) {
        if (!exist(uuid)) {
            return false;
        }
        Level lvl = new Level(getLevel(uuid));
        Integer exp = getEXPLevel(uuid);

        for (int i = 1; exp + Exp >= lvl.MAX_EXP; i++) {
            lvl = new Level(i);
            Bukkit.getPluginManager().callEvent(new LevelChangeEvent(uuid, lvl.toInt() - 1, lvl.toInt()));
            Exp -= (exp + Exp) - lvl.MAX_EXP;
        }
        MySQL.update("UPDATE `levelsystem` SET `level`='" + lvl + "', `exp`='" + Exp + "' WHERE `uuid`='" + uuid.toString() + "';");
        return true;

    }

    public static Boolean remExp(UUID uuid, Integer Exp) {
        if (!exist(uuid)) {
            return false;
        }
        Integer lvlExp = getEXPLevel(uuid);
        Integer lvl = getLevel(uuid);
        for (int i = lvl; lvlExp - Exp < 0; i--) {
            lvl -= i;
            Exp -= lvlExp;
        }
        MySQL.update("UPDATE `levelsystem` SET `level`='" + lvl + "', `exp`='" + Exp + "' WHERE `uuid`='" + uuid.toString() + "';");
        return true;

    }

    public static Integer getLevel(UUID uuid) {
        ResultSet rs = MySQL.query("SELECT * FROM `levelsystem` WHERE `uuid`='" + uuid.toString() + "';");
        try {
            if(rs != null && rs.next()) {
                return rs.getInt("level");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static Boolean exist(UUID uuid) {
        ResultSet rs = MySQL.query("SELECT * FROM `levelsystem` WHERE `uuid`='" + uuid.toString() + "';");
        try {
            if(rs != null && rs.next()) {
                return true;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public enum RewardTypes {
        MONEY,
        ITEMS,
        PETS,
        EXP,
    }

    public enum ExpFrom {
        GAME,
        ACHIEVEMENT,
        QUEST
    }

}
