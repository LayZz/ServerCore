package de.flashbeatzz.servercore.utils.guilde;

import de.flashbeatzz.servercore.utils.MySQL;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class Guilde {
    private String name;
    private Integer id;

    public Guilde(String name) {
        ResultSet rs = MySQL.query("SELECT * FROM `guildes` WHERE `name`='" + name + "';");
        try {
            if(rs != null && rs.next()) {
                this.name = name;
                this.id = rs.getInt("id");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public Guilde(Integer id) {
        ResultSet rs = MySQL.query("SELECT * FROM `guildes` WHERE `id`='" + id + "';");
        try {
            if(rs != null && rs.next()) {
                this.name = rs.getString("name");
                this.id = id;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public Integer getId() {
        return id;
    }

    public Tag getTagObject() {
        ResultSet rs = MySQL.query("SELECT * FROM `guildes` WHERE `name`='" + name + "' OR `id`='" + id + "';");
        try {
            if(rs != null && rs.next()) {
                return Tag.fromString(rs.getString("tag"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public String getTagString() {
        ResultSet rs = MySQL.query("SELECT * FROM `guildes` WHERE `name`='" + name + "' OR `id`='" + id + "';");
        try {
            if(rs != null && rs.next()) {
                return Tag.fromString(rs.getString("tag")).asString();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public Double getMoney() {
        ResultSet rs = MySQL.query("SELECT * FROM `guildes` WHERE `name`='" + name + "' OR `id`='" + id + "';");
        try {
            if(rs != null && rs.next()) {
                return rs.getDouble("money");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public void addMoney(Double amount) {
        MySQL.update("UPDATE `guildes` SET `money`='" + (getMoney() + amount) + "' WHERE `name`='" + this.name + "' OR `id`='" + id + "';");
    }

    public Boolean removeMoney(Double amount) {
        if(getMoney() - amount < 0) {
            MySQL.update("UPDATE `guildes` SET `money`='" + (getMoney() + amount) + "' WHERE `name`='" + this.name + "' OR `id`='" + id + "';");
        }
        return false;
    }

    public void setMoney(Double amount) {
        MySQL.update("UPDATE `guildes` SET `money`='" + amount + "' WHERE `name`='" + this.name + "' OR `id`='" + id + "';");
    }

    public void setTag(Tag tag) {
        MySQL.update("UPDATE `guildes` SET `tag`='" + tag.asString() + "' WHERE `name`='" + this.name + "' OR `id`='" + id + "';");
    }

    public void setTag(String str) {
        MySQL.update("UPDATE `guildes` SET `tag`='" + str + "' WHERE `name`='" + this.name + "' OR `id`='" + id + "';");
    }

    public List<UUID> getMembers() {
        List<UUID> uuid = new ArrayList<>();
        ResultSet rs = MySQL.query("SELECT * FROM `guilde_members` WHERE `guilde_id`='" + id + "';");
        try {
            if(rs != null && rs.next()) {
                uuid.add(UUID.fromString(rs.getString("uuid")));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        uuid.add(getFounder());
        return uuid;
    }

    public String getName() {
        return name;
    }

    public UUID getFounder() {
        ResultSet rs = MySQL.query("SELECT * FROM `guildes` WHERE `name`='" + name + "' OR `id`='" + id + "';");
        try {
            if(rs != null && rs.next()) {
                return UUID.fromString("founder_uuid");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public void setFounder(UUID founder) {
        MySQL.update("UPDATE `guildes` SET `founder_uuid`='" + founder.toString() + "' WHERE `name`='" + this.name + "';");
    }

    public void setName(String name) {
        MySQL.update("UPDATE `guildes` SET `name`='" + name + "' WHERE `name`='" + this.name + "' OR `id`='" + id + "';");
    }

    public Boolean addMember(UUID uuid) {
        Guilde g = GuildeSystem.getGuilde(uuid);
        if(g == null) {
            MySQL.update("INSERT INTO `guilde_members` (`uuid`, `guilde_name`) VALUES ('" + uuid.toString() + "', '" + this.name + "');");
            return true;
        }
        return false;
    }

    public Boolean containsMember(UUID uuid) {
        return getMembers().contains(uuid);
    }

    public void removeMember(UUID uuid) {
        if(getMembers().contains(uuid)) {
            MySQL.update("DELETE * FROM `guilde_members` WHERE `uuid`='" + uuid.toString() + "';");
        }
    }
}