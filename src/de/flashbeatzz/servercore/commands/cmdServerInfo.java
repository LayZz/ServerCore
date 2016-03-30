package de.flashbeatzz.servercore.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

public class cmdServerInfo implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender cs, Command cmd, String lable, String[] args) {

        if(args.length == 0) {
            Runtime runtime = Runtime.getRuntime();
            long allocatedRAM = runtime.totalMemory();
            long maxRAM = runtime.maxMemory()/1024;
            long freeRAM = runtime.freeMemory();
            long usedRAM = maxRAM - freeRAM;
            String os = System.getProperty("os.name");
            String os_version = System.getProperty("os.version");
            cs.sendMessage("§8§l[]======>> §6§lSERVER - INFO §8§l<<======[]\n" +
                    "§dBetriebssystem:  §f" + os + "\n" +
                    "§dVersion:         §f" + os_version + "\n" +
                    "\n" +
                    "§dZugeteilter RAM: §f" + toGB(allocatedRAM) + " GB\n" +
                    "§dMaximaler RAM:   §f" + toGB(maxRAM) + " GB\n" +
                    "§dBenutzter RAM:   §f" + toGB(usedRAM) + " GB\n" +
                    "§dFreier RAM:      §f" + toGB(freeRAM) + " GB\n" +
                    "§8§l[]======>> §6§lSERVER - INFO §8§l<<======[]");
            return true;
        }
        cs.sendMessage("§cFalsche Verwendung: /serverinfo");
        return true;
    }

    public static String toGB(long bytes) {
        int u = 0;
        for (;bytes > 1024*1024; bytes >>= 10) {
            u++;
        }
        if (bytes > 1024)
            u++;
        return String.format("%.1f %cB", bytes/1024f, " kMGTPE".charAt(u));
    }

}
