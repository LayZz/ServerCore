package de.flashbeatzz.servercore;

import de.flashbeatzz.servercore.utils.Config;
import de.flashbeatzz.servercore.utils.Data;
import de.flashbeatzz.servercore.utils.MySQL;
import de.flashbeatzz.servercore.utils.SocketTarget;
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
        sendMessage(SocketTarget.BUNGEECORD, "SYSTEM", "DISCONNECT " + Bukkit.getServer().getServerName());

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

        Data.mySQL = new MySQL();
        if(!Data.mySQL.openConnection()) {
            Bukkit.getServer().shutdown();
        }
        Data.cfg = new Config("Config", getDescription().getName());

        createSocket();
        sendMessage(SocketTarget.BUNGEECORD, "SYSTEM", "CONNECT " + Bukkit.getServer().getServerName());

        Data.console.info("ServerCore successfully enabled.");
    }

    public static void sendMessage(String target, String header, String message) {
        PrintWriter printWriter;
        try {
            printWriter = new PrintWriter(socket.getOutputStream());
            printWriter.print(target + "\n" + header + "\n" + message);
            printWriter.flush();
            printWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void sendMessage(SocketTarget target, String header, String message) {
        PrintWriter printWriter = null;
        try {
            printWriter = new PrintWriter(socket.getOutputStream());
            printWriter.print(target.get() + "\n" + header + "\n" + message);
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
