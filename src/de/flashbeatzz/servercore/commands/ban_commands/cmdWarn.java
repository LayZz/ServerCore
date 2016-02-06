package de.flashbeatzz.servercore.commands.ban_commands;

import de.flashbeatzz.servercore.ServerCore;
import de.flashbeatzz.servercore.utils.SocketTarget;
import de.flashbeatzz.servercore.utils.UUIDLibrary;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class cmdWarn implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender cs, Command cmd, String s, String[] args) {

        if(args.length >= 1) {
            String reason = "";
            for(int i = 2; i <= args.length; i++) {
                reason += args[i+1] + " ";
            }
            if(reason.equals("")) {
                cs.sendMessage("§8[§4WARN§8] §cBitte gib einen Grund an!");
                return true;
            }
            reason = reason.trim();
            if(cs instanceof Player) {
                ServerCore.sendMessage(SocketTarget.BUNGEECORD, "WARN", ((Player) cs).getUniqueId() + "<>" + Bukkit.getServer().getPlayer(args[0]).getUniqueId() + "<>" + reason);
                return true;
            }
            ServerCore.sendMessage(SocketTarget.BUNGEECORD, "WARN", UUIDLibrary.getConsoleUUID() + "<>" + Bukkit.getServer().getPlayer(args[0]).getUniqueId() + "<>" + reason);
            return true;
        } else {
            cs.sendMessage("§cFalsche Verwendung: /warn <Spieler> <Grund>");
            return true;
        }

    }

}
