package de.flashbeatzz.servercore.commands.ban_commands;

import de.flashbeatzz.servercore.ServerCore;
import de.flashbeatzz.servercore.utils.SocketTarget;
import de.flashbeatzz.servercore.utils.UUIDLibrary;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class cmdTempBan implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender cs, Command cmd, String s, String[] args) {

        if(args.length >= 2) {
            String reason = "";
            for(int i = 2; i <= args.length - 1; i++) {
                reason += args[i] + " ";
            }
            if(reason.equals("")) {
                reason = "Gebannt!";
            }
            reason = reason.trim();
            if(cs instanceof Player) {
                ServerCore.sendMessage(SocketTarget.BUNGEECORD, "TEMPBAN", UUIDLibrary.getUUIDtoName(cs.getName()) + "<>" + UUIDLibrary.getUUIDtoName(args[0]) + "<>" + args[1] + "<>" + reason);
                return true;
            }
            ServerCore.sendMessage(SocketTarget.BUNGEECORD, "TEMPBAN", UUIDLibrary.getConsoleUUID() + "<>" + UUIDLibrary.getUUIDtoName(args[0]) + "<>" + args[1] + "<>" + reason);
            return true;
        } else {
            cs.sendMessage("§cFalsche Verwendung: /tempban <Spieler> <Zeit> (z.B. 1h30m) [Grund]");
            return true;
        }

    }

}
