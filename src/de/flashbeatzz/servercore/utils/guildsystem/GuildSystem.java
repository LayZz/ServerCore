package de.flashbeatzz.servercore.utils.guildsystem;

import de.flashbeatzz.servercore.ServerCore;
import de.flashbeatzz.servercore.utils.Data;
import de.flashbeatzz.servercore.utils.MySQL;
import de.flashbeatzz.servercore.utils.SocketTarget;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Objects;
import java.util.UUID;

//vorteile für miningames erkaufen
//sachen anbauen um geld zu bekommen
//felder erst kaufen
public class GuildSystem implements Listener {
    private static HashMap<String, Guild> guilds = new HashMap<>();
    public static HashMap<String, Guild> getguildMap() { return guilds; }

    public static Boolean newGuild(String name, UUID founder) {
        if(!exist(name)) {
            MySQL.update("INSERT INTO `guilds` (`name`, `founder_uuid`, `tag`, `money`) VALUES ('" + name + "', '" + founder.toString() + "', '', '0');");
            Data.console.info("Created new guildsystem '" + name + "'.");
            new Guild(name).addMember(founder);
            return true;
        }
        return false;
    }

    public static Guild getGuild(Integer id) {
        for (Guild g : guilds.values()) {
            if (g.getId().equals(id)) {
                return g;
            }
        }
        if (exist(id)) {
            return new Guild(id);
        }
        return null;
    }

    public static Guild getGuild(String name) {
        if (guilds.containsKey(name)) {
            return guilds.get(name);
        }
        if (exist(name)) {
            return new Guild(name);
        }
        return null;
    }

    public static Boolean exist(String name) {
        ResultSet rs = MySQL.query("SELECT * FROM `guilds` WHERE `name`='" + name + "';");
        try {
            return rs != null && rs.next();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public static Boolean exist(Integer id) {
        ResultSet rs = MySQL.query("SELECT * FROM `guilds` WHERE `id`='" + id + "';");
        try {
            return rs != null && rs.next();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public static Guild getGuild(UUID uuid) {
        ResultSet rs = MySQL.query("SELECT `guild_id` FROM `userdata` WHERE `uuid`='" + uuid.toString() + "';");
        try {
            if(rs != null && rs.next()) {
                if(!Objects.equals(rs.getInt("guild_id"), -1)) return new Guild(rs.getInt("guild_id"));
            }
            return null;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @EventHandler
    public void onChat(AsyncPlayerChatEvent e) {
        if(e.getMessage().startsWith("#")) {
            if(getGuild(e.getPlayer().getUniqueId()) != null) {
                ServerCore.sendMessage(SocketTarget.SPIGOT, "guildCHAT-" + getGuild(e.getPlayer().getUniqueId()).getId(), e.getPlayer().getUniqueId().toString() + "§-§" + e.getMessage().replace("#", ""), true);
                return;
            }
        }
        if(getGuild(e.getPlayer().getUniqueId()) != null) {
            Guild g = getGuild(e.getPlayer().getUniqueId());
            // first %s player, 2nd msg
            e.setFormat("");
        }
    }

}
