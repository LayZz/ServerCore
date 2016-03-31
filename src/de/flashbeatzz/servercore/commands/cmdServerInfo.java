package de.flashbeatzz.servercore.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

import java.lang.management.ManagementFactory;
import java.lang.management.MemoryUsage;

public class cmdServerInfo implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender cs, Command cmd, String lable, String[] args) {

        if (args.length == 0) {
            /*Runtime runtime = Runtime.getRuntime();
            long allocatedRAM = runtime.totalMemory();
            long maxRAM = runtime.maxMemory() / 1024;
            long freeRAM = runtime.freeMemory();
            long usedRAM = maxRAM - freeRAM;*/
            MemoryUsage heapMemoryUsage = ManagementFactory.getMemoryMXBean().getHeapMemoryUsage();
            long maxRAM = heapMemoryUsage.getMax();
            long freeRAM = heapMemoryUsage.getUsed();
            long usedRAM = maxRAM - freeRAM;
            String os = System.getProperty("os.name");
            String os_version = System.getProperty("os.version");
            Integer gb = 1024*1024*1024;
            cs.sendMessage("§8§l[]======>> §6§lSERVER - INFO §8§l<<======[]\n" +
                    "§dBetriebssystem:  §f" + os + "\n" +
                    "§dVersion:         §f" + os_version + "\n" +
                    "\n" +
                    "§dMaximaler RAM:   §f" + maxRAM / gb + " GB\n" +
                    "§dBenutzter RAM:   §f" + usedRAM / gb + " GB\n" +
                    "§dFreier RAM:      §f" + freeRAM / gb + " GB\n" +
                    "§8§l[]======>> §6§lSERVER - INFO §8§l<<======[]");
            return true;
        }
        cs.sendMessage("§cFalsche Verwendung: /serverinfo");
        return true;
    }

}
