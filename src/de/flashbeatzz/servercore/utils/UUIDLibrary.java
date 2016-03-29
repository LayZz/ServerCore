package de.flashbeatzz.servercore.utils;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.UUID;

public class UUIDLibrary {

    public static UUID getUUIDtoName(String name) {
        if(isRegistred(name)) {
            try {
                ResultSet rs = MySQL.query("SELECT * FROM `userdata` WHERE `name`='" + name + "';");
                if(rs != null && rs.next()) {
                    return UUID.fromString(rs.getString("uuid"));
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    public static String getNameToUUID(UUID uuid) {
        if(isRegistred(uuid)) {
            try {
                ResultSet rs = MySQL.query("SELECT * FROM `userdata` WHERE `uuid`='" + uuid + "';");
                if(rs != null && rs.next()) {
                    return rs.getString("name");
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    public static Boolean isRegistred(UUID uuid) {
        ResultSet rs = MySQL.query("SELECT * FROM `userdata` WHERE `uuid`='" + uuid.toString() + "';");
        try {
            return rs != null && rs.next();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public static Boolean isRegistred(String name) {
        ResultSet rs = MySQL.query("SELECT * FROM `userdata` WHERE `name`='" + name + "';");
        try {
            return rs != null && rs.next();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public static UUID getConsoleUUID() {
        return new UUID(0L, 0L);
    }

}
