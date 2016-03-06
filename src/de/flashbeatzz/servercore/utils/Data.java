package de.flashbeatzz.servercore.utils;

import de.flashbeatzz.servercore.ServerCore;
import org.bukkit.Bukkit;
import org.bukkit.event.Listener;
import org.bukkit.plugin.Plugin;

import java.util.HashMap;
import java.util.logging.Logger;

public class Data {

    public static Config mysqlCfg;
    public static Config cfg;
    public static MySQL mySQL;
    public static Config levelLog;
    public static Logger console = ServerCore.getInstance().getLogger();
    public static HashMap<Listener, Plugin> listener = new HashMap<>();

    public static boolean regListener(Listener clazz, Plugin plugin) {
        if(!(listener.containsValue(plugin) && listener.containsKey(clazz))) {
            Bukkit.getPluginManager().registerEvents(clazz, plugin);
            listener.put(clazz, plugin);
            return true;
        }
        return false;
    }

    public static boolean unregListener(Listener clazz, Plugin plugin) {
        if(listener.containsKey(clazz)) {
            listener.remove(clazz, plugin);
            return true;
        }
        return false;
    }

    public static String firstCharToUpper(String str) {
        char[] stringArray = str.toCharArray();
        stringArray[0] = Character.toUpperCase(stringArray[0]);
        return new String(stringArray);
    }

}
