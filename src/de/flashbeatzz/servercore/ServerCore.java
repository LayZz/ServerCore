package de.flashbeatzz.servercore;

import de.flashbeatzz.servercore.commands.ban_commands.*;
import de.flashbeatzz.servercore.utils.*;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

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

        new Thread(new SocketReadThread()).start();

        getCommand("ban").setExecutor(new cmdBan());
        getCommand("check").setExecutor(new cmdCheck());
        getCommand("kick").setExecutor(new cmdKick());
        getCommand("tempban").setExecutor(new cmdTempBan());
        getCommand("unban").setExecutor(new cmdUnban());
        getCommand("warn").setExecutor(new cmdWarn());

        Data.console.info("ServerCore successfully enabled.");
    }

    public static void sendMessage(String target, String header, String message) {
        String fString = target + "/§§/" + header + "/§§/" + message;
        printWriter.println(fString);
        printWriter.flush();
    }

    private static PrintWriter printWriter;
    private static Scanner scanner;

    public static void sendMessage(SocketTarget target, String header, String message) {
        String fString = target.get() + "/§§/" + header + "/§§/" + message;
        printWriter.println(fString);
        printWriter.flush();
    }

    public static Socket socket;

    public void createSocket() {
        try {
            socket = new Socket("109.230.231.247", 19888);
            printWriter = new PrintWriter(socket.getOutputStream());
            scanner = new Scanner(socket.getInputStream());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static ServerCore getInstance() {
        return instance;
    }
}
