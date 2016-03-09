package de.flashbeatzz.servercore.commands.ban_commands;

import de.flashbeatzz.servercore.ServerCore;
import de.flashbeatzz.servercore.utils.SocketTarget;
import de.flashbeatzz.servercore.utils.UUIDLibrary;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class cmdUnban implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender cs, Command cmd, String s, String[] args) {

        if(args.length == 1) {
            if(cs instanceof Player) {
                ServerCore.sendMessage(SocketTarget.BUNGEECORD, "UNBAN",  UUIDLibrary.getUUIDtoName(args[0]) + "<>" + UUIDLibrary.getUUIDtoName(cs.getName()), false);
                return true;
            }
            ServerCore.sendMessage(SocketTarget.BUNGEECORD, "UNBAN", UUIDLibrary.getUUIDtoName(args[0]) + "<>" + UUIDLibrary.getConsoleUUID(), false);
            return true;
        } else {
            cs.sendMessage("§cFalsche Verwendung: /unban <Spieler>");
            return true;
        }

    }

}
