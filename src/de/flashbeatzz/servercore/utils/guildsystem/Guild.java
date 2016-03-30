package de.flashbeatzz.servercore.utils.guildsystem;

import de.flashbeatzz.servercore.utils.MySQL;
import org.bukkit.Bukkit;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class Guild {
    private String name;
    private Integer id;
    private String tag;
    private Double gold;
    private UUID founder;
    private List<UUID> members = new ArrayList<>();

    public Guild(String name) {
        ResultSet rs = MySQL.query("SELECT * FROM `guilds` WHERE `name`='" + name + "';");
        try {
            if(rs != null && rs.next()) {
                this.name = name;
                this.id = rs.getInt("id");
                this.tag = rs.getString("tag");
                this.gold = rs.getDouble("gold");
                this.founder = UUID.fromString(rs.getString("founder_uuid"));
                GuildSystem.getguildMap().put(this.name, this);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        ResultSet rs1 = MySQL.query("SELECT * FROM `userdata` WHERE `guild_id`='" + id + "';");
        try {
            if(rs1 != null && rs1.next()) {
                members.add(UUID.fromString("uuid"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        members.add(founder);
    }

    public Guild(Integer id) {
        ResultSet rs = MySQL.query("SELECT * FROM `guilds` WHERE `id`='" + id + "';");
        try {
            if(rs != null && rs.next()) {
                this.name = rs.getString("name");
                this.id = id;
                GuildSystem.getguildMap().put(this.name, this);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public Integer getId() {
        return id;
    }

    public String getTag() {
        return tag;
    }

    public Double getGold() {
        return gold;
    }

    public void broadcast(String msg) {
        for(UUID u : getMembers()) {
            Bukkit.getPlayer(u).sendMessage(msg);
        }
    }

    public void addGold(Double amount) {
        gold += amount;
        MySQL.update("UPDATE `guilds` SET `gold`='" + gold + "' WHERE `name`='" + this.name + "' OR `id`='" + this.id + "';");
    }

    public Boolean removeGold(Double amount) {
        if(gold - amount < 0) {
            gold -= amount;
            MySQL.update("UPDATE `guilds` SET `money`='" + gold + "' WHERE `name`='" + this.name + "' OR `id`='" + this.id + "';");
        }
        return false;
    }

    public void setGold(Double amount) {
        gold = amount;
        MySQL.update("UPDATE `guilds` SET `money`='" + gold + "' WHERE `name`='" + this.name + "' OR `id`='" + this.id + "';");
    }

    public Boolean setTag(String str) {
        tag = str;
        ResultSet rs = MySQL.query("SELECT * FROM `guilds` WHERE `tag`='" + str + "';");
        try {
            return !(rs != null && rs.next());
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public List<UUID> getMembers() {
        return members;
    }

    public void disband() {
        MySQL.update("DELETE * FROM `guilds` WHERE `id`='" + id + "';");
        for(UUID uuid : members) {
            MySQL.query("UPDATE `userdata` SET `guild_id`='-1' WHERE `uuid`='" + uuid.toString() + "';");
        }
    }

    public String getName() {
        return name;
    }

    public UUID getFounder() {
        return founder;
    }

    public void setFounder(UUID founder) {
        this.founder = founder;
        MySQL.update("UPDATE `guilds` SET `founder_uuid`='" + founder.toString() + "' WHERE `id`='" + id + "';");
    }

    public Boolean setName(String name) {
        this.name = name;
        if(!GuildSystem.exist(name)) {
            MySQL.update("UPDATE `guilds` SET `name`='" + name + "' WHERE `name`='" + this.name + "' OR `id`='" + id + "';");
            return true;
        }
        return false;
    }

    public Boolean addMember(UUID uuid) {
        Guild g = GuildSystem.getGuild(uuid);
        if(g == null) {
            MySQL.update("UPDATE `userdata` SET `guild_id`='" + this.id + "' WHERE `uuid`='" + uuid.toString() + "';");
            members.add(uuid);
            return true;
        }
        return false;
    }

    public Boolean containsMember(UUID uuid) {
        return members.contains(uuid);
    }

    public void removeMember(UUID uuid) {
        if(members.contains(uuid)) {
            MySQL.update("UPDATE `userdata` SET `guild_id`='-1' WHERE `uuid`='" + uuid.toString() + "';");
        }
    }

}