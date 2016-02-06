package de.flashbeatzz.servercore.commands.ban_commands;

import de.flashbeatzz.servercore.ServerCore;
import de.flashbeatzz.servercore.utils.SocketTarget;
import de.flashbeatzz.servercore.utils.UUIDLibrary;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class cmdCheck implements CommandExecutor {


    @Override
    public boolean onCommand(CommandSender cs, Command cmd, String s, String[] args) {

        if(args.length == 1) {
            if(UUIDLibrary.isRegistred(args[0])) {
                if(cs instanceof Player) {
                    ServerCore.sendMessage(SocketTarget.BUNGEECORD, "CHECK", UUIDLibrary.getUUIDtoName(args[0]) + "<>" + ((Player)cs).getUniqueId());
                    return true;
                }
                ServerCore.sendMessage(SocketTarget.BUNGEECORD, "CHECK", UUIDLibrary.getUUIDtoName(args[0]) + "<>" + UUIDLibrary.getConsoleUUID());
                return true;
            } else {
                cs.sendMessage("§cDer Spieler §6" + args[0] + "§c existiert nicht!");
                return true;
            }
        } else {
            cs.sendMessage("§cFalsche Verwendung: /check <Spieler>");
            return true;
        }
    }
}
