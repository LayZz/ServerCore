package de.flashbeatzz.servercore.commands;

import de.flashbeatzz.servercore.utils.UUIDLibrary;
import de.flashbeatzz.servercore.utils.guildsystem.Guild;
import de.flashbeatzz.servercore.utils.guildsystem.GuildSystem;
import de.flashbeatzz.servercore.utils.guildsystem.InviteRunnable;
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.ComponentBuilder;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.UUID;

public class cmdGuild implements CommandExecutor {
    private static HashMap<UUID, Guild> list = new HashMap<>();

    @Override
    public boolean onCommand(CommandSender cs, Command cmd, String lable, String[] args) {
        /**
         * /guild create name tag
         * /guild disband name
         * /guild info name
         * /guild invite name...
         * /guild kick name...
         * /guild help
         * /guild setowner name
         * /guild money add amount
         * /guild accept
         * /guild decline
         */
        if(cs instanceof Player) {
            Player p = (Player) cs;
            UUID pUUID = p.getUniqueId();
            Guild pGuild = GuildSystem.getGuild(pUUID);
            if (args.length == 1) {
                if (args[0].equalsIgnoreCase("help")) {
                    p.sendMessage("§8§l[]======>> §6§lGuild - HELP §8§l<<======[]\n" +
                            "§d/guild help\n" +
                            "   §fDisplays the help-page\n" +
                            "§d/guild create <name>\n" +
                            "   §fCreates a new guild with specific tag\n" +
                            "§d/guild disband\n" +
                            "   §fDisbands your current guild §4(Leader only)\n" +
                            "§d/guild info\n" +
                            "   §fShows info about your own guild\n" +
                            "§d/guild info <name>\n" +
                            "   §fShows info about specific guild\n" +
                            "§d/guild invite <name>\n" +
                            "   §fSends invitation to a player §4(Leader only)\n" +
                            "§d/guild kick <name>\n" +
                            "   §fKicks a player out of your guild §4(Leader only)\n" +
                            "§d/guild setleader <name>\n" +
                            "   §fSets a new owner of your guild §4(Leader only)\n" +
                            "§d/guild money add <amount>\n" +
                            "   §fAdds money to your guild\n" +
                            "§d/guild accept\n" +
                            "   §fAccept an invitation\n" +
                            "§d/guild decline\n" +
                            "   §fDecline an invitation\n" +
                            "§8§l[]======>> §6§lGuild - HELP §8§l<<======[]");
                    return true;
                } else if (args[0].equalsIgnoreCase("accept")) {
                    if (InviteRunnable.pendingInvites.containsKey(pUUID)) {
                        Guild g = InviteRunnable.pendingInvites.get(pUUID);
                        InviteRunnable.pendingInvites.remove(pUUID);
                        if(g.addMember(pUUID)) {
                            g.broadcast("§7" + p.getName() + " joined your guild.");
                            p.sendMessage("§aYou joined guild \"" + g.getName() + " - " + g.getTag() + "\n.");
                            return true;
                        }
                        p.sendMessage("§cYou are already in a guild. Leave that first if you want to join another.");
                        return true;
                    }
                    p.sendMessage("§7You have no pending invitations.");
                    return true;
                } else if (args[0].equalsIgnoreCase("decline")) {
                    if (InviteRunnable.pendingInvites.containsKey(pUUID)) {
                        InviteRunnable.pendingInvites.remove(pUUID);
                        p.sendMessage("§7You declined the invitation.");
                        return true;
                    }
                    p.sendMessage("§7You have no pending invitations.");
                    return true;
                } else if (args[0].equalsIgnoreCase("disband")) {
                    if(pGuild != null) {
                        if(pGuild.getFounder().equals(pUUID)) {
                            p.sendMessage("§bDo you really want to disband your guild?\n" +
                                    new ComponentBuilder("YES").event(new ClickEvent(ClickEvent.Action.RUN_COMMAND, "/guild yes")).append("NO").event(new ClickEvent(ClickEvent.Action.RUN_COMMAND, "/guild no")));
                            list.put(pUUID, pGuild);
                            return true;
                        }
                        p.sendMessage("§cYou are not the leader of your guild.");
                        return true;
                    }
                    p.sendMessage("§cYou are not in a guild.");
                    return true;
                } else if(args[0].equalsIgnoreCase("info")) {
                    if(pGuild != null) {
                        String members = "";
                        for(UUID uuid : pGuild.getMembers()) {
                            members += "  - " + UUIDLibrary.getName(uuid) + "\n";
                        }
                        p.sendMessage("§8§l[]======>> §6§l" + pGuild.getName() + " - " + pGuild.getTag() + " §8§l<<======[]\n" +
                                "§dID:      §f" + pGuild.getId() +
                                "§dName:    §f" + pGuild.getName() +
                                "§dTag:     §f" + pGuild.getTag() +
                                "§dFounder: §f" + UUIDLibrary.getName(pGuild.getFounder()) +
                                "§dMoney:   §f" + pGuild.getGold() +
                                "§dMembers: §f\n" + members + "" +
                                "§8§l[]======>> §6§l\" + g.getName() + \" - \" + g.getTag() + \" §8§l<<======[]");
                        return true;
                    }
                    p.sendMessage("§cYou are not in a guild.");
                    return true;
                } else if(args[0].equalsIgnoreCase("yes")) {
                    if(list.containsKey(pUUID)) {
                        p.sendMessage("§aYou disbanned your guild.");
                        list.get(pUUID).disband();
                        return true;
                    }
                } else if(args[0].equalsIgnoreCase("no")) {
                    if(list.containsKey(pUUID)) {
                        p.sendMessage("§7You cancelled the disband.");
                        return true;
                    }
                }
                p.sendMessage("§cUsage: /guild help");
                return true;
            } else if (args.length == 2) {
                if (args[0].equalsIgnoreCase("invite")) {
                    if(pGuild != null) {
                        if(pGuild.getFounder().equals(pUUID)) {
                            if(!pGuild.getMembers().contains(UUIDLibrary.getUUID(args[1]))) {
                                Bukkit.getPlayer(args[1]).sendMessage("§7You got invited in the guild.\n" +
                                        new ComponentBuilder("ACCEPT").event(new ClickEvent(ClickEvent.Action.RUN_COMMAND, "/guild accept")).append("DECLINE").event(new ClickEvent(ClickEvent.Action.RUN_COMMAND, "/guild decline")));
                                p.sendMessage("§7You invited the player.");
                                new InviteRunnable(UUIDLibrary.getUUID(args[1]), pGuild);
                                return true;
                            }
                            p.sendMessage("§cThis player is already in your guild.");
                            return true;
                        }
                        p.sendMessage("§cYou are not the leader of your guild.");
                        return true;
                    }
                    p.sendMessage("§cYou are not in a guild.");
                    return true;
                } else if (args[0].equalsIgnoreCase("kick")) {
                    if(pGuild != null) {
                        if(pGuild.getFounder().equals(pUUID)) {
                            if(pGuild.getMembers().contains(UUIDLibrary.getUUID(args[1]))) {
                                pGuild.removeMember(UUIDLibrary.getUUID(args[1]));
                                p.sendMessage("§7You removed the player.");
                                return true;
                            }
                            p.sendMessage("§cThis player is not in your guild.");
                            return true;
                        }
                        p.sendMessage("§cYou are not the leader of your guild.");
                        return true;
                    }
                    p.sendMessage("§cYou are not in a guild.");
                    return true;
                } else if (args[0].equalsIgnoreCase("setleader")) {
                    if(pGuild != null) {
                        if(pGuild.getFounder().equals(pUUID)) {
                            if(pGuild.getMembers().contains(UUIDLibrary.getUUID(args[1]))) {
                                pGuild.setFounder(UUIDLibrary.getUUID(args[1]));
                                Bukkit.getPlayer(pGuild.getFounder()).sendMessage("§aYou are now the leader of your guild.");
                                p.sendMessage("§7You are not longer the leader of your guild.");
                                return true;
                            }
                            p.sendMessage("§cThis player is not in your guild.");
                            return true;
                        }
                        p.sendMessage("§cYou are not the leader of your guild.");
                        return true;
                    }
                    p.sendMessage("§cYou are not in a guild.");
                    return true;
                } else if(args[0].equalsIgnoreCase("info")) {
                    Guild g = GuildSystem.getGuild(args[1]);
                    if(g != null) {
                        String members = "";
                        for(UUID uuid : g.getMembers()) {
                            members += "  - " + UUIDLibrary.getName(uuid) + "\n";
                        }
                        p.sendMessage("§8§l[]======>> §6§l" + g.getName() + " - " + g.getTag() + " §8§l<<======[]\n" +
                                "§dID:      §f" + g.getId() +
                                "§dName:    §f" + g.getName() +
                                "§dTag:     §f" + g.getTag() +
                                "§dFounder: §f" + UUIDLibrary.getName(g.getFounder()) +
                                "§dMoney:   §f" + g.getGold() +
                                "§dMembers: §f\n" + members + "" +
                                "§8§l[]======>> §6§l" + g.getName() + " - " + g.getTag() + " §8§l<<======[]");
                        return true;
                    }
                    p.sendMessage("§cThis guild does not exist.");
                    return true;
                } else if (args[0].equalsIgnoreCase("create")) {
                    if (GuildSystem.newGuild(args[1], pUUID)) {
                        if(GuildSystem.getGuild(pUUID) == null) {
                            p.sendMessage("§aYou created a new guild successfully.");
                            return true;
                        }
                        p.sendMessage("§cYou are already in a guild.");
                        return true;
                    }
                    p.sendMessage("§cThe guild already exists.");
                    return true;
                }
                p.sendMessage("§cUsage: /guild help");
                return true;
            } else if (args.length == 3) {
                if (args[0].equalsIgnoreCase("money")) {
                    if (args[1].equalsIgnoreCase("add")) {
                        if(pGuild != null) {
                            //MONEY API NEEDED
                            pGuild.addGold(Double.valueOf(args[2]));
                            p.sendMessage("§7You successfully payed the amount.");
                            return true;
                        }
                        p.sendMessage("§cYou are not in a guild.");
                        return true;
                    }
                    p.sendMessage("§cUsage: /guild help");
                    return true;
                }
                p.sendMessage("§cUsage: /guild help");
                return true;
            } else {
                p.sendMessage("§cUsage: /guild help");
                return true;
            }
        }
        return true;
    }
}