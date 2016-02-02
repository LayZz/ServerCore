package de.flashbeatzz.servercore.permissions;

import de.flashbeatzz.servercore.utils.Data;
import de.flashbeatzz.servercore.utils.MySQL;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

public class PermissionGroup {
    private List<String> permissions = new ArrayList<>();
    private String nameFormat, chatFormat, name;
    private List<PermissionGroup> subGroups = new ArrayList<>();
    private List<Player> players = new ArrayList<>();
    private Boolean op, canBuild, isDefault;

    public static HashMap<String, PermissionGroup> groupMap = new HashMap<>();

    public static void loadPermissionGroups() {
        for(String str : Data.permConf.getYamlConfiguration().getConfigurationSection("Groups").getKeys(false)) {
            groupMap.put(str, new PermissionGroup(str));
        }
    }

    public static PermissionGroup GET_BY_STRING(String str) {
        for(PermissionGroup pg : groupMap.values()) {
            if(pg.toString().equalsIgnoreCase(str)) {
                return pg;
            }
        }
        return null;
    }

    public static PermissionGroup DEFAULT_GROUP() {
        for(PermissionGroup pg : groupMap.values()) {
            if(pg.isDefault) {
                return pg;
            }
        }
        return null;
    }

    private PermissionGroup(String name) {
        this.name = name;
        this.permissions = Data.permConf.getStringList("Groups." + name + ".permissions");
        this.nameFormat = Data.permConf.getString("Groups." + name + ".nameFormat");
        this.chatFormat = Data.permConf.getString("Groups." + name + ".chatFormat");
        this.op = Data.permConf.getBoolean("Groups." + name + ".op");
        this.canBuild = Data.permConf.getBoolean("Groups." + name + ".build");
        this.isDefault = Data.permConf.getBoolean("Groups." +  name + ".default");
        this.subGroups.addAll(Data.permConf.getStringList("Groups." + name + ".subGroups").stream().map(PermissionGroup::GET_BY_STRING).collect(Collectors.toList()));
        ResultSet rs = MySQL.query("SELECT * FROM `users` WHERE `group`='" + this.name + "';");
        try {
            if(rs != null && rs.next()) {
                this.players.add(Bukkit.getPlayer(rs.getString("name")));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public Boolean canBuild() {
        return this.canBuild;
    }

    public Boolean op() {
        return this.op;
    }

    public List<Player> getPlayers() {
        return this.players;
    }

    public void addPlayer(Player p) {
        this.players.add(p);
    }

    public void remPlayer(Player p) {
        if(this.players.contains(p)) {
            this.players.remove(p);
        }
    }

    public List<String> getPermissions() {
        return this.permissions;
    }

    public List<PermissionGroup> getSubGroups() {
        return this.subGroups;
    }

    public String getNameFormat() {
        return this.nameFormat;
    }

    public String getChatFormat() {
        return this.chatFormat;
    }

    public void addPermission(String perm) {
        if(!this.permissions.contains(perm)) {
            this.permissions.add(perm);
        }
        if(!Data.permConf.getStringList("Groups." + name + ".permissions").contains(perm)) {
            Data.permConf.getStringList("Groups." + name + ".permissions").add(perm);
        }
    }

    public void addPermission(Permission perm) {
        if(!this.permissions.contains(perm.getPermissionToString())) {
            this.permissions.add(perm.getPermissionToString());
        }
        if(!Data.permConf.getStringList("Groups." + name + ".permissions").contains(perm.getPermissionToString())) {
            Data.permConf.getStringList("Groups." + name + ".permissions").add(perm.getPermissionToString());
        }
    }

    public void remPermission(String perm) {
        if(this.permissions.contains(perm)) {
            this.permissions.remove(perm);
        }
        if(Data.permConf.getStringList("Groups." + name + ".permissions").contains(perm)) {
            Data.permConf.getStringList("Groups." + name + ".permissions").remove(perm);
        }
    }

    public void remPermissions(Permission perm) {
        if(this.permissions.contains(perm.getPermissionToString())) {
            this.permissions.remove(perm.getPermissionToString());
        }
        if(Data.permConf.getStringList("Groups." + name + ".permissions").contains(perm.getPermissionToString())) {
            Data.permConf.getStringList("Groups." + name + ".permissions").remove(perm.getPermissionToString());
        }
    }

    public String asString() {
        return Data.firstCharToUpper(this.name.toLowerCase());
    }

    public Boolean isDefault() {
        return this.isDefault;
    }

}
