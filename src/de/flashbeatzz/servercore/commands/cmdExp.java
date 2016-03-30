package de.flashbeatzz.servercore.commands;

import de.flashbeatzz.servercore.utils.Data;
import de.flashbeatzz.servercore.utils.UUIDLibrary;
import de.flashbeatzz.servercore.utils.levelsystem.Level;
import de.flashbeatzz.servercore.utils.levelsystem.LevelSystem;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.sql.Timestamp;
import java.util.Date;
import java.util.UUID;

public class cmdExp implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender cs, Command cmd, String lable, String[] args) {

        String logName;
        if(!(cs instanceof Player)) {
            logName = "SERVER";
        } else {
            logName = cs.getName();
        }
        //    /exp <name> [<set/add/rem> amount]
        if(args.length == 3) {
            Integer amount = Integer.valueOf(args[2]);
            Player target = Bukkit.getPlayer(UUIDLibrary.getUUID(args[0]));
            if (args[1].equals("add")) {
                if (LevelSystem.addExp(target.getUniqueId(), amount)) {
                    Data.levelLog.addDefault("[" + new Timestamp(new Date().getTime()) + "]:", logName + " added " + amount + " exp to the account of " + target.getName());
                    cs.sendMessage("§8[§2XP§8] §aDu hast §6" + target.getName() + " " + amount + "§a Exp hinzugefügt!");
                    return true;
                }
                cs.sendMessage("§cDer Spieler §6" + args[0] + "§c existiert nicht!");
            } else if (args[1].equalsIgnoreCase("set")) {
                if (LevelSystem.setTotalExp(target.getUniqueId(), amount)) {
                    Data.levelLog.addDefault("[" + new Timestamp(new Date().getTime()) + "]:", logName + " set the exp of the account from " + target.getName() + " to " + amount);
                    cs.sendMessage("§8[§2XP§8] §aDu hast §6" + target.getName() + " " + amount + "§a Exp entfernt!");
                    return true;
                }
                cs.sendMessage("§cDer Spieler §6" + args[0] + "§c existiert nicht!");
            } else if (args[1].equalsIgnoreCase("remove") || args[1].equalsIgnoreCase("rem")) {
                if (LevelSystem.remExp(target.getUniqueId(), amount)) {
                    Data.levelLog.addDefault("[" + new Timestamp(new Date().getTime()) + "]:", logName + " removed " + amount + " exp from the account of " + target.getName());
                    cs.sendMessage("§8[§2XP§8] §aDu hast die Exp von §6" + target.getName() + "§a zu §6" + amount + "§a gesetzt!");
                    return true;
                }
                cs.sendMessage("§cDer Spieler §6" + args[0] + "§c existiert nicht!");
            } else {
                cs.sendMessage("§cFalsche Verwendung: /exp <Spieler> <set | add | rem> <Anzahl>");
                return true;
            }
        } else if(args.length == 1) {
            UUID uuid = UUIDLibrary.getUUID(args[0]);
            Player target = Bukkit.getPlayer(uuid);
            if(LevelSystem.exist(target.getUniqueId())) {
                cs.sendMessage("§8§l[]======>> §6§lLEVEL | XP - " + target.getName() + " §8§l<<======[]\n" +
                        "§dLevel:     §f" + LevelSystem.getLevel(uuid) + "\n" +
                        "§dLevel EXP: §f" + LevelSystem.getEXPLevel(uuid) + "/" + new Level(LevelSystem.getLevel(uuid)).MAX_EXP + "\n" +
                        "\n" +
                        "§dTotal EXP: §f" + LevelSystem.getTotalEXP(uuid) + "\n" +
                        "§8§l[]======>> §6§lLEVEL | XP - " + target.getName() + " §8§l<<======[]");
                return true;
            }
            cs.sendMessage("§cDer Spieler §6" + args[0] + "§c existiert nicht!");
        } else {
            cs.sendMessage("§cFalsche Verwendung: /exp <Spieler> <set | add | rem> <Anzahl>");
            return true;
        }

        return true;
    }

}
