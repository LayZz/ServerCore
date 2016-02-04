package de.flashbeatzz.servercore.commands.ban_commands;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class cmdBan implements CommandExecutor{

    @Override
    public boolean onCommand(CommandSender cs, Command cmd, String s, String[] args) {

        if(args.length >= 2) {
            Player p = Bukkit.getPlayer(args[1]);

        } else {
            cs.sendMessage("§cFalsche Verwendung: /ban <Spieler> [Grund]");
        }

        return true;
    }
}
