package de.flashbeatzz.servercore.commands.ban_commands;

import de.flashbeatzz.servercore.ServerCore;
import de.flashbeatzz.servercore.utils.SocketTarget;
import de.flashbeatzz.servercore.utils.UUIDLibrary;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class cmdKick implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender cs, Command cmd, String s, String[] args) {

        if(args.length >= 1) {
            String reason = "";
            for(int i = 1; i < args.length + 1; i++) {
                reason += args[i] + " ";
            }
            if(reason.equals("")) {
                reason = "Gekickt!";
            }
            reason = reason.trim();
            if(cs instanceof Player) {
                ServerCore.sendMessage(SocketTarget.BUNGEECORD, "KICK", ((Player) cs).getUniqueId() + "<>" + Bukkit.getServer().getPlayer(args[0]).getUniqueId() + "<>" + reason);
                return true;
            }
            ServerCore.sendMessage(SocketTarget.BUNGEECORD, "KICK", UUIDLibrary.getConsoleUUID() + "<>" + Bukkit.getServer().getPlayer(args[0]).getUniqueId() + "<>" + reason);
            return true;
        } else {
            cs.sendMessage("§cFalsche Verwendung: /kick <Spieler> [Grund]");
            return true;
        }

    }

}
