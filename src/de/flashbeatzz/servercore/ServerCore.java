package de.flashbeatzz.servercore;

import de.flashbeatzz.servercore.commands.cmdExp;
import de.flashbeatzz.servercore.commands.cmdGuilde;
import de.flashbeatzz.servercore.commands.cmdServerInfo;
import de.flashbeatzz.servercore.listener.MessageListener;
import de.flashbeatzz.servercore.utils.*;
import de.flashbeatzz.servercore.utils.guilde.GuildeSystem;
import de.flashbeatzz.servercore.utils.levelsystem.LevelSystem;
import de.flashbeatzz.servercore.utils.messages.Message;
import de.flashbeatzz.servercore.utils.messages.Messages;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

public class ServerCore extends JavaPlugin {
    public static ServerCore instance;

    @Override
    public void onDisable() {
        Data.console.info("ServerCore is disabling ...");

        Data.mySQL.closeConnection();
        sendMessage(SocketTarget.BUNGEECORD, "DISCONNECT", "", false);
        Data.console.info("ServerCore successfully disabled.");
    }

    @Override
    public void onLoad() {

    }

    @Override
    public void onEnable() {
        instance = this;
        Data.console.info("ServerCore is enabling ...");

        Data.levelLog = new Config("LevelLog", getDescription().getName());
        new LevelSystem();

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
        initMessages();
        Data.cfg = new Config("Config", getDescription().getName());

        createSocket();
        sendMessage(SocketTarget.BUNGEECORD, "CONNECT", Bukkit.getServer().getServerName(), false);

        new Thread(new SocketReadThread()).start();

        getCommand("exp").setExecutor(new cmdExp());
        getCommand("serverinfo").setExecutor(new cmdServerInfo());
        getCommand("guilde").setExecutor(new cmdGuilde());

        Bukkit.getPluginManager().registerEvents(new MessageListener(), this);
        Bukkit.getPluginManager().registerEvents(new GuildeSystem(), this);

        Data.console.info("ServerCore successfully enabled.");
    }

    private void initMessages() {
        ResultSet resultSet = MySQL.query("SELECT * FROM `messages`");
        try {
            while (resultSet.next()) {
                String tag = resultSet.getString("tag");
                String german = resultSet.getString("german");
                String english = resultSet.getString("english");

                Messages.messages.put(tag, new Message(tag, german, english));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void sendMessage(String target, String header, String message, boolean sendSelf) {
        String fString = target + "/§§/" + header + "/§§/" + message + "/§§/" + (sendSelf ? "TRUE" : "FALSE");
        printWriter.println(fString);
        printWriter.flush();
    }

    private static PrintWriter printWriter;
    private static Scanner scanner;

    public static void sendMessage(SocketTarget target, String header, String message, boolean sendSelf) {
        String fString = target.get() + "/§§/" + header + "/§§/" + message + "/§§/" + (sendSelf ? "TRUE" : "FALSE");
        printWriter.println(fString);
        printWriter.flush();
    }

    public static Socket socket;

    public void createSocket() {
        try {
            socket = new Socket("localhost", 19888);
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
