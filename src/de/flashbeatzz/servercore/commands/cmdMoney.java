package de.flashbeatzz.servercore.commands;

import de.flashbeatzz.servercore.utils.currency.CurrencyManager;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

public class cmdMoney implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String s, String[] args) {

        if (args.length == 0) {
            if (sender instanceof Player) {
                final Player player = (Player) sender;
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        player.sendMessage("§7Du besitzt §e" + CurrencyManager.get(player.getUniqueId()).getNewMoney() + " §7Gil");
                    }
                }).start();
            } else {
                sender.sendMessage("§4Nur Spieler können diesen Befehl benutzen!");
            }

            return true;
        }

        // 'MONEY HELP' COMMAND
        if (args[0].equalsIgnoreCase("help")) {
            List<String> helpStrings = new ArrayList<>();

            helpStrings.add("§7- /money [Spielername]");
            helpStrings.add("§7- /money balance [Spielername]");
            helpStrings.add("§7- /money pay <Spielername> <Anzahl>");
            helpStrings.add("§7- /money set <Spielername> <Anzahl>");
            helpStrings.add("§7- /money give <Spielername> <Anzahl>");
            helpStrings.add("§7- /money take <Spielername> <Anzahl>");
            helpStrings.add("§7- /money set <Spielername> <Anzahl>");
            helpStrings.add("§7- /money top [Anzahl] -- TODO");
            helpStrings.add("§7- /money top -- TODO");
            helpStrings.add("§7- /money daily [Spielername] -- TODO");
            helpStrings.add("§7- /money daily -- TODO");
            helpStrings.add("§7- /money daily top [Anzahl] -- TODO");
            helpStrings.add("§7- /money daily top -- TODO");

            int site = 1;

            if (args.length > 1) {
                try {
                    site = Integer.parseInt(args[1]);

                    if (site < 1) {
                        site = 1;
                    }
                } catch (Exception e) {

                }
            }

            int from = (site - 1) + 5 * site;
            int to = (site - 1) + 5 * (site + 1);

            sender.sendMessage("§e------- §a§lMafiMoney §aHelp §7[Seite " + site + "] §e-------");

            for (int i = from; i < to; i++) {
                try {
                    sender.sendMessage(helpStrings.get(i));
                } catch (Exception e) {

                }
            }

            if (helpStrings.size() >= to) {
                sender.sendMessage("§7Nächste Seite: §e/money help " + (site + 1));
            }

            return true;
        }

        return true;
    }

}
