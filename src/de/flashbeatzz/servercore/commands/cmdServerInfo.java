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
            long maxRAM = runtime.maxMemory();
            long freeRAM = runtime.freeMemory();
            long usedRAM = maxRAM - freeRAM;
            String os = System.getProperty("os.name");
            String os_version = System.getProperty("os.version");
            cs.sendMessage("§8§l[]======>> §6§lSERVER - INFO §8§l<<======[]\n" +
                    "§dBetriebssystem:  §f" + os + "\n" +
                    "§dVersion:         §f" + os_version + "\n" +
                    "\n" +
                    "§dZugeteilter RAM: §f" + allocatedRAM + "\n" +
                    "§dMaximaler RAM:   §f" + maxRAM + "\n" +
                    "§dBenutzter RAM:   §f" + usedRAM + "\n" +
                    "§dFreier RAM:      §f" + freeRAM + "\n" +
                    "§8§l[]======>> §6§lSERVER - INFO §8§l<<======[]");
            return true;
        }
        cs.sendMessage("§cFalsche Verwendung: /serverinfo");
        return true;
    }

}
