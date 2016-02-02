package de.flashbeatzz.servercore;

import com.google.common.collect.Lists;
import de.flashbeatzz.servercore.permissions.Permission;
import de.flashbeatzz.servercore.permissions.PermissionGroup;
import de.flashbeatzz.servercore.utils.Config;
import de.flashbeatzz.servercore.utils.Data;
import de.flashbeatzz.servercore.utils.MySQL;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;

public class ServerCore extends JavaPlugin {
    public static ServerCore instance;

    @Override
    public void onDisable() {
        Data.console.info("ServerCore is disabling ...");

        Data.mySQL.closeConnection();

        Data.console.info("ServerCore successfully disabled.");
    }

    @Override
    public void onLoad() {

    }

    @Override
    public void onEnable() {
        instance = this;
        Data.console.info("ServerCore is enabling ...");

        Data.mysqlCfg = new Config("MySQL", getDescription().getName());
        Data.mysqlCfg.addDefault("MySQL.Host", "localhost");
        Data.mysqlCfg.addDefault("MySQL.User", "root");
        Data.mysqlCfg.addDefault("MySQL.Password", "pass");
        Data.mysqlCfg.addDefault("MySQL.Database", "db");
        Data.mysqlCfg.addDefault("MySQL.Port", 3306);
        Data.mysqlCfg.copyAndSave(true);

        Data.permConf = new Config("MySQL", getDescription().getName());

        Data.permConf.addDefault("Groups.Default.build", false);
        Data.permConf.addDefault("Groups.Default.op", false);
        Data.permConf.addDefault("Groups.Default.default", true);
        Data.permConf.addDefault("Groups.Default.permissions", Lists.newArrayList("api.*"));
        Data.permConf.addDefault("Groups.Default.nameFormat", "§7Default");
        Data.permConf.addDefault("Groups.Default.chatFormat", "§f");
        Data.permConf.addDefault("Groups.Default.subGroups", Lists.newArrayList());

        Data.permConf.addDefault("Groups.Admin.build", true);
        Data.permConf.addDefault("Groups.Admin.op", true);
        Data.permConf.addDefault("Groups.Admin.default", false);
        Data.permConf.addDefault("Groups.Admin.permissions", Lists.newArrayList("api.*"));
        Data.permConf.addDefault("Groups.Admin.nameFormat", "§4Admin");
        Data.permConf.addDefault("Groups.Admin.chatFormat", "§c");
        Data.permConf.addDefault("Groups.Admin.subGroups", Lists.newArrayList());
        Data.permConf.copyAndSave(true);

        Data.mySQL = new MySQL();
        if(!Data.mySQL.openConnection()) {
            Bukkit.getServer().shutdown();
        }
        Data.cfg = new Config("Config", getDescription().getName());

        PermissionGroup.loadPermissionGroups();
        Permission.loadPermissions();

        Data.console.info("ServerCore successfully enabled.");
    }

    public static void sendMessage(String target, String header, String message) {
        PrintWriter printWriter = null;
        try {
            printWriter = new PrintWriter(socket.getOutputStream());
            printWriter.print(target + "\n" + header + "\n" + message);
            printWriter.flush();
            printWriter.close();
        } catch (IOException e) {
        e.printStackTrace();
    }
    }

    static Socket socket;

    public void createSocket() {
        try {
            socket = new Socket("localhost", 19888);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static ServerCore getInstance() {
        return instance;
    }
}
