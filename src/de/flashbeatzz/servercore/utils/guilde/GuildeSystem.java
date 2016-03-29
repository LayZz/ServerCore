package de.flashbeatzz.servercore.utils.guilde;

import de.flashbeatzz.servercore.ServerCore;
import de.flashbeatzz.servercore.utils.MySQL;
import de.flashbeatzz.servercore.utils.SocketTarget;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.UUID;

//vorteile für miningames erkaufen
//sachen anbauen um geld zu bekommen
//felder erst kaufen
public class GuildeSystem implements Listener {

    public static Boolean newGuilde(String name, UUID founder) {
        if(!exist(name)) {
            MySQL.update("INSERT INTO `guildes` (`name`, `founder_uuid`, `tag`, `money`) VALUES ('" + name + "', '" + founder.toString() + "', '', '0');");
            new Guilde(name).addMember(founder);
            return true;
        }
        return false;
    }

    public static Guilde getGuilde(Integer id) {
        if(exist(id)) {
            return new Guilde(id);
        }
        return null;
    }

    public static Guilde getGuilde(String name) {
        if(exist(name)) {
            return new Guilde(name);
        }
        return null;
    }

    public static Boolean exist(String name) {
        ResultSet rs = MySQL.query("SELECT * FROM `guildes` WHERE `name`='" + name + "';");
        try {
            return rs != null && rs.next();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public static Boolean exist(Integer id) {
        ResultSet rs = MySQL.query("SELECT * FROM `guildes` WHERE `id`='" + id + "';");
        try {
            return rs != null && rs.next();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public static Guilde getGuilde(UUID uuid) {
        ResultSet rs = MySQL.query("SELECT `guilde_id` FROM `userdata` WHERE `uuid`='" + uuid.toString() + "';");
        try {
            if(rs != null && rs.next()) {
                return new Guilde(rs.getInt("guilde_id"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @EventHandler
    public void onChat(AsyncPlayerChatEvent e) {
        if(e.getMessage().startsWith("#")) {
            if(getGuilde(e.getPlayer().getUniqueId()) != null) {
                ServerCore.sendMessage(SocketTarget.SPIGOT, "GUILDECHAT-" + getGuilde(e.getPlayer().getUniqueId()).getId(), e.getPlayer().getUniqueId().toString() + "§-§" + e.getMessage().replace("#", ""), true);
                return;
            }
        }
        if(getGuilde(e.getPlayer().getUniqueId()) != null) {
            Guilde g = getGuilde(e.getPlayer().getUniqueId());
            // first %s player, 2nd msg
            e.setFormat("");
        }
    }

}
