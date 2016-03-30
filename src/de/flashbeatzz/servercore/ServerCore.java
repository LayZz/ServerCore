package de.flashbeatzz.servercore;

import de.flashbeatzz.servercore.commands.cmdExp;
import de.flashbeatzz.servercore.commands.cmdGuild;
import de.flashbeatzz.servercore.commands.cmdServerInfo;
import de.flashbeatzz.servercore.listener.MessageListener;
import de.flashbeatzz.servercore.listener.PlayerPreLoginListener;
import de.flashbeatzz.servercore.listener.PlayerQuitListener;
import de.flashbeatzz.servercore.utils.*;
import de.flashbeatzz.servercore.utils.guildsystem.GuildSystem;
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
        Data.console.info("Enabling ServerCore...");

        Data.console.info("Creating config files if they don't exist...");
        createConfigFiles();
        Data.console.info("Config files created!");

        Data.mySQL = new MySQL();
        Data.console.info("Creating MySQL connection...");
        if (!Data.mySQL.openConnection()) {
            Data.console.info("Error while connecting to MySQL Database... Shutdown server!");
            Bukkit.getServer().shutdown();
        }
        Data.console.info("MySQL successfully connected!");

        Data.console.info("Initializing messages...");
        initMessages();
        Data.console.info(Messages.messages.size() + " messages created!");

        Data.console.info("Creating socket...");
        try {
            createSocket();
            Data.console.info("Socket created! Listening on " + socket.getLocalAddress().toString() + ":" + socket.getLocalPort());
        } catch (IOException e) {
            e.printStackTrace();
        }
        sendMessage(SocketTarget.BUNGEECORD, "CONNECT", Bukkit.getServer().getServerName(), false);
        new Thread(new SocketReadThread()).start();

        Data.console.info("Registering commands...");
        getCommand("exp").setExecutor(new cmdExp());
        getCommand("serverinfo").setExecutor(new cmdServerInfo());
        getCommand("guildsystem").setExecutor(new cmdGuild());
        Data.console.info("Commands registered!");

        Data.console.info("Registering events...");
        Bukkit.getPluginManager().registerEvents(new MessageListener(), this);
        Bukkit.getPluginManager().registerEvents(new GuildSystem(), this);
        Bukkit.getPluginManager().registerEvents(new PlayerPreLoginListener(), this);
        Bukkit.getPluginManager().registerEvents(new PlayerQuitListener(), this);
        Data.console.info("Events registered!");

        Data.console.info("ServerCore successfully enabled.");
    }

    private void createConfigFiles() {
        Data.mysqlCfg = new Config("MySQL", getDescription().getName());
        Data.mysqlCfg.addDefault("MySQL.Host", "localhost");
        Data.mysqlCfg.addDefault("MySQL.User", "root");
        Data.mysqlCfg.addDefault("MySQL.Password", "pass");
        Data.mysqlCfg.addDefault("MySQL.Database", "db");
        Data.mysqlCfg.addDefault("MySQL.Port", 3306);
        Data.mysqlCfg.copyAndSave(true);

        //Data.levelLog = new Config("LevelLog", getDescription().getName());
        //Data.cfg = new Config("Config", getDescription().getName());
    }

    private void initMessages() {
        ResultSet resultSet = MySQL.query("SELECT * FROM `messages`");
        try {
            while (resultSet != null && resultSet.next()) {
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

    public void createSocket() throws IOException {
        socket = new Socket("localhost", 19888);
        printWriter = new PrintWriter(socket.getOutputStream());
        scanner = new Scanner(socket.getInputStream());
    }

    public static ServerCore getInstance() {
        return instance;
    }
}
