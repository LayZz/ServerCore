package de.flashbeatzz.servercore.commands;

import de.flashbeatzz.servercore.utils.UUIDLibrary;
import de.flashbeatzz.servercore.utils.guilde.Guilde;
import de.flashbeatzz.servercore.utils.guilde.GuildeSystem;
import de.flashbeatzz.servercore.utils.guilde.InviteRunnable;
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.ComponentBuilder;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.UUID;

public class cmdGuilde implements CommandExecutor {
    private static HashMap<UUID, Guilde> list = new HashMap<>();

    @Override
    public boolean onCommand(CommandSender cs, Command cmd, String lable, String[] args) {
        /**
         * /guilde create name tag
         * /guilde disband name
         * /guilde info name
         * /guilde invite name...
         * /guilde kick name...
         * /guilde help
         * /guilde setowner name
         * /guilde money add amount
         * /guilde accept
         * /guilde decline
         */
        if(cs instanceof Player) {
            Player p = (Player) cs;
            if (args.length == 1) {
                if (args[0].equalsIgnoreCase("help")) {
                    p.sendMessage("§8§l[]======>> §6§lGUILDE - HELP §8§l<<======[]\n" +
                            "§d/guilde help\n" +
                            "   §fDisplays the help-page\n" +
                            "§d/guilde create <name>\n" +
                            "   §fCreates a new guilde with specific tag\n" +
                            "§d/guilde disband\n" +
                            "   §fDisbands your current guilde §4(Leader only)\n" +
                            "§d/guilde info\n" +
                            "   §fShows info about your own guilde\n" +
                            "§d/guilde info <name>\n" +
                            "   §fShows info about specific guilde\n" +
                            "§d/guilde invite <name>\n" +
                            "   §fSends invitation to a player §4(Leader only)\n" +
                            "§d/guilde kick <name>\n" +
                            "   §fKicks a player out of your guilde §4(Leader only)\n" +
                            "§d/guilde setleader <name>\n" +
                            "   §fSets a new owner of your guilde §4(Leader only)\n" +
                            "§d/guilde money add <amount>\n" +
                            "   §fAdds money to your guilde\n" +
                            "§d/guilde accept\n" +
                            "   §fAccept an invitation\n" +
                            "§d/guilde decline\n" +
                            "   §fDecline an invitation\n" +
                            "§8§l[]======>> §6§lGUILDE - HELP §8§l<<======[]");
                    return true;
                } else if (args[0].equalsIgnoreCase("accept")) {
                    if (InviteRunnable.pendingInvites.containsKey(p.getUniqueId())) {
                        Guilde g = InviteRunnable.pendingInvites.get(p.getUniqueId());
                        InviteRunnable.pendingInvites.remove(p.getUniqueId());
                        if(g.addMember(p.getUniqueId())) {
                            g.broadcast("§7" + p.getName() + " joined your guilde.");
                            p.sendMessage("§aYou joined guilde \"" + g.getName() + " - " + g.getTagString() + "\n.");
                            return true;
                        }
                        p.sendMessage("§cYou are already in a guilde. Leave that first if you want to join another.");
                        return true;
                    }
                    p.sendMessage("§7You have no pending invitations.");
                    return true;
                } else if (args[0].equalsIgnoreCase("decline")) {
                    if (InviteRunnable.pendingInvites.containsKey(p.getUniqueId())) {
                        InviteRunnable.pendingInvites.remove(p.getUniqueId());
                        p.sendMessage("§7You declined the invitation.");
                        return true;
                    }
                    p.sendMessage("§7You have no pending invitations.");
                    return true;
                } else if (args[0].equalsIgnoreCase("disband")) {
                    Guilde g = GuildeSystem.getGuilde(p.getUniqueId());
                    if(g != null) {
                        if(g.getFounder().equals(p.getUniqueId())) {
                            p.sendMessage("§bDo you really want to disband your guilde?\n" +
                                    new ComponentBuilder("YES").event(new ClickEvent(ClickEvent.Action.RUN_COMMAND, "/guilde yes")).append("NO").event(new ClickEvent(ClickEvent.Action.RUN_COMMAND, "/guilde no")));
                            list.put(p.getUniqueId(), g);
                            return true;
                        }
                        p.sendMessage("§cYou are not the leader of your guilde.");
                        return true;
                    }
                    p.sendMessage("§cYou are not in a guilde.");
                    return true;
                } else if(args[0].equalsIgnoreCase("info")) {
                    Guilde g = GuildeSystem.getGuilde(p.getUniqueId());
                    if(g != null) {
                        String members = "";
                        for(UUID uuid : g.getMembers()) {
                            members += "  - " + UUIDLibrary.getNameToUUID(uuid) + "\n";
                        }
                        p.sendMessage("§8§l[]======>> §6§l" + g.getName() + " - " + g.getTagString() + " §8§l<<======[]\n" +
                                "§dID:      §f" + g.getId() +
                                "§dName:    §f" + g.getName() +
                                "§dTag:     §f" + g.getTagString() +
                                "§dFounder: §f" + UUIDLibrary.getNameToUUID(g.getFounder()) +
                                "§dMoney:   §f" + g.getMoney() +
                                "§dMembers: §f\n" + members + "" +
                                "§8§l[]======>> §6§l\" + g.getName() + \" - \" + g.getTagString() + \" §8§l<<======[]");
                        return true;
                    }
                    p.sendMessage("§cYou are not in a guilde.");
                    return true;
                } else if(args[0].equalsIgnoreCase("yes")) {
                    if(list.containsKey(p.getUniqueId())) {
                        p.sendMessage("§aYou disbanned your guilde.");
                        list.get(p.getUniqueId()).disband();
                        return true;
                    }
                } else if(args[0].equalsIgnoreCase("no")) {
                    if(list.containsKey(p.getUniqueId())) {
                        p.sendMessage("§7You cancelled the disband.");
                        return true;
                    }
                }
            } else if (args.length == 2) {
                if (args[0].equalsIgnoreCase("invite")) {
                    Guilde g = GuildeSystem.getGuilde(p.getUniqueId());
                    if(g != null) {
                        if(g.getFounder().equals(p.getUniqueId())) {
                            if(!g.getMembers().contains(UUIDLibrary.getUUIDtoName(args[1]))) {
                                Bukkit.getPlayer(args[1]).sendMessage("§7You got invited in the guilde.\n" +
                                        new ComponentBuilder("ACCEPT").event(new ClickEvent(ClickEvent.Action.RUN_COMMAND, "/guilde accept")).append("DECLINE").event(new ClickEvent(ClickEvent.Action.RUN_COMMAND, "/guilde decline")));
                                p.sendMessage("§7You invited the player.");
                                new InviteRunnable(UUIDLibrary.getUUIDtoName(args[1]), g);
                                return true;
                            }
                            p.sendMessage("§cThis player is already in your guilde.");
                            return true;
                        }
                        p.sendMessage("§cYou are not the leader of your guilde.");
                        return true;
                    }
                    p.sendMessage("§cYou are not in a guilde.");
                    return true;
                } else if (args[0].equalsIgnoreCase("kick")) {
                    Guilde g = GuildeSystem.getGuilde(p.getUniqueId());
                    if(g != null) {
                        if(g.getFounder().equals(p.getUniqueId())) {
                            if(g.getMembers().contains(UUIDLibrary.getUUIDtoName(args[1]))) {
                                g.removeMember(UUIDLibrary.getUUIDtoName(args[1]));
                                p.sendMessage("§7You removed the player.");
                                return true;
                            }
                            p.sendMessage("§cThis player is not in your guilde.");
                            return true;
                        }
                        p.sendMessage("§cYou are not the leader of your guilde.");
                        return true;
                    }
                    p.sendMessage("§cYou are not in a guilde.");
                    return true;
                } else if (args[0].equalsIgnoreCase("setleader")) {
                    Guilde g = GuildeSystem.getGuilde(p.getUniqueId());
                    if(g != null) {
                        if(g.getFounder().equals(p.getUniqueId())) {
                            if(g.getMembers().contains(UUIDLibrary.getUUIDtoName(args[1]))) {
                                g.setFounder(UUIDLibrary.getUUIDtoName(args[1]));
                                Bukkit.getPlayer(g.getFounder()).sendMessage("§aYou are now the leader of your guilde.");
                                p.sendMessage("§7You are not longer the leader of your guilde.");
                                return true;
                            }
                            p.sendMessage("§cThis player is not in your guilde.");
                            return true;
                        }
                        p.sendMessage("§cYou are not the leader of your guilde.");
                        return true;
                    }
                    p.sendMessage("§cYou are not in a guilde.");
                    return true;
                } else if(args[0].equalsIgnoreCase("info")) {
                    Guilde g = GuildeSystem.getGuilde(args[1]);
                    if(g != null) {
                        String members = "";
                        for(UUID uuid : g.getMembers()) {
                            members += "  - " + UUIDLibrary.getNameToUUID(uuid) + "\n";
                        }
                        p.sendMessage("§8§l[]======>> §6§l" + g.getName() + " - " + g.getTagString() + " §8§l<<======[]\n" +
                                "§dID:      §f" + g.getId() +
                                "§dName:    §f" + g.getName() +
                                "§dTag:     §f" + g.getTagString() +
                                "§dFounder: §f" + UUIDLibrary.getNameToUUID(g.getFounder()) +
                                "§dMoney:   §f" + g.getMoney() +
                                "§dMembers: §f\n" + members + "" +
                                "§8§l[]======>> §6§l\" + g.getName() + \" - \" + g.getTagString() + \" §8§l<<======[]");
                        return true;
                    }
                    p.sendMessage("§cThis guilde doesnt exist.");
                    return true;
                } else if (args[0].equalsIgnoreCase("create")) {
                    if (GuildeSystem.newGuilde(args[1], p.getUniqueId())) {
                        p.sendMessage("§aYou created a new guilde successfully.");
                        return true;
                    }
                    p.sendMessage("§cThe guilde already exists.");
                    return true;
                }
            } else if (args.length == 3) {
                if (args[0].equalsIgnoreCase("money")) {
                    if (args[1].equalsIgnoreCase("add")) {
                        Guilde g = GuildeSystem.getGuilde(p.getUniqueId());
                        if(g != null) {
                            //MONEY API NEEDED
                            g.addMoney(Double.valueOf(args[2]));
                            p.sendMessage("§7You successfully payed the amount.");
                            return true;
                        }
                        p.sendMessage("§cYou are not in a guilde.");
                        return true;
                    }
                }
            } else {
                p.sendMessage("§cUsage: /guilde help");
                return true;
            }
        }
        return true;
    }
}