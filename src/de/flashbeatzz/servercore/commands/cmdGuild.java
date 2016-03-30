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
         * /guildsystem create name tag
         * /guildsystem disband name
         * /guildsystem info name
         * /guildsystem invite name...
         * /guildsystem kick name...
         * /guildsystem help
         * /guildsystem setowner name
         * /guildsystem money add amount
         * /guildsystem accept
         * /guildsystem decline
         */
        if(cs instanceof Player) {
            Player p = (Player) cs;
            if (args.length == 1) {
                if (args[0].equalsIgnoreCase("help")) {
                    p.sendMessage("§8§l[]======>> §6§lGuild - HELP §8§l<<======[]\n" +
                            "§d/guildsystem help\n" +
                            "   §fDisplays the help-page\n" +
                            "§d/guildsystem create <name>\n" +
                            "   §fCreates a new guildsystem with specific tag\n" +
                            "§d/guildsystem disband\n" +
                            "   §fDisbands your current guildsystem §4(Leader only)\n" +
                            "§d/guildsystem info\n" +
                            "   §fShows info about your own guildsystem\n" +
                            "§d/guildsystem info <name>\n" +
                            "   §fShows info about specific guildsystem\n" +
                            "§d/guildsystem invite <name>\n" +
                            "   §fSends invitation to a player §4(Leader only)\n" +
                            "§d/guildsystem kick <name>\n" +
                            "   §fKicks a player out of your guildsystem §4(Leader only)\n" +
                            "§d/guildsystem setleader <name>\n" +
                            "   §fSets a new owner of your guildsystem §4(Leader only)\n" +
                            "§d/guildsystem money add <amount>\n" +
                            "   §fAdds money to your guildsystem\n" +
                            "§d/guildsystem accept\n" +
                            "   §fAccept an invitation\n" +
                            "§d/guildsystem decline\n" +
                            "   §fDecline an invitation\n" +
                            "§8§l[]======>> §6§lGuild - HELP §8§l<<======[]");
                    return true;
                } else if (args[0].equalsIgnoreCase("accept")) {
                    if (InviteRunnable.pendingInvites.containsKey(p.getUniqueId())) {
                        Guild g = InviteRunnable.pendingInvites.get(p.getUniqueId());
                        InviteRunnable.pendingInvites.remove(p.getUniqueId());
                        if(g.addMember(p.getUniqueId())) {
                            g.broadcast("§7" + p.getName() + " joined your guildsystem.");
                            p.sendMessage("§aYou joined guildsystem \"" + g.getName() + " - " + g.getTag() + "\n.");
                            return true;
                        }
                        p.sendMessage("§cYou are already in a guildsystem. Leave that first if you want to join another.");
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
                    Guild g = GuildSystem.getGuild(p.getUniqueId());
                    if(g != null) {
                        if(g.getFounder().equals(p.getUniqueId())) {
                            p.sendMessage("§bDo you really want to disband your guildsystem?\n" +
                                    new ComponentBuilder("YES").event(new ClickEvent(ClickEvent.Action.RUN_COMMAND, "/guildsystem yes")).append("NO").event(new ClickEvent(ClickEvent.Action.RUN_COMMAND, "/guildsystem no")));
                            list.put(p.getUniqueId(), g);
                            return true;
                        }
                        p.sendMessage("§cYou are not the leader of your guildsystem.");
                        return true;
                    }
                    p.sendMessage("§cYou are not in a guildsystem.");
                    return true;
                } else if(args[0].equalsIgnoreCase("info")) {
                    Guild g = GuildSystem.getGuild(p.getUniqueId());
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
                                "§8§l[]======>> §6§l\" + g.getName() + \" - \" + g.getTag() + \" §8§l<<======[]");
                        return true;
                    }
                    p.sendMessage("§cYou are not in a guildsystem.");
                    return true;
                } else if(args[0].equalsIgnoreCase("yes")) {
                    if(list.containsKey(p.getUniqueId())) {
                        p.sendMessage("§aYou disbanned your guildsystem.");
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
                    Guild g = GuildSystem.getGuild(p.getUniqueId());
                    if(g != null) {
                        if(g.getFounder().equals(p.getUniqueId())) {
                            if(!g.getMembers().contains(UUIDLibrary.getUUID(args[1]))) {
                                Bukkit.getPlayer(args[1]).sendMessage("§7You got invited in the guildsystem.\n" +
                                        new ComponentBuilder("ACCEPT").event(new ClickEvent(ClickEvent.Action.RUN_COMMAND, "/guildsystem accept")).append("DECLINE").event(new ClickEvent(ClickEvent.Action.RUN_COMMAND, "/guildsystem decline")));
                                p.sendMessage("§7You invited the player.");
                                new InviteRunnable(UUIDLibrary.getUUID(args[1]), g);
                                return true;
                            }
                            p.sendMessage("§cThis player is already in your guildsystem.");
                            return true;
                        }
                        p.sendMessage("§cYou are not the leader of your guildsystem.");
                        return true;
                    }
                    p.sendMessage("§cYou are not in a guildsystem.");
                    return true;
                } else if (args[0].equalsIgnoreCase("kick")) {
                    Guild g = GuildSystem.getGuild(p.getUniqueId());
                    if(g != null) {
                        if(g.getFounder().equals(p.getUniqueId())) {
                            if(g.getMembers().contains(UUIDLibrary.getUUID(args[1]))) {
                                g.removeMember(UUIDLibrary.getUUID(args[1]));
                                p.sendMessage("§7You removed the player.");
                                return true;
                            }
                            p.sendMessage("§cThis player is not in your guildsystem.");
                            return true;
                        }
                        p.sendMessage("§cYou are not the leader of your guildsystem.");
                        return true;
                    }
                    p.sendMessage("§cYou are not in a guildsystem.");
                    return true;
                } else if (args[0].equalsIgnoreCase("setleader")) {
                    Guild g = GuildSystem.getGuild(p.getUniqueId());
                    if(g != null) {
                        if(g.getFounder().equals(p.getUniqueId())) {
                            if(g.getMembers().contains(UUIDLibrary.getUUID(args[1]))) {
                                g.setFounder(UUIDLibrary.getUUID(args[1]));
                                Bukkit.getPlayer(g.getFounder()).sendMessage("§aYou are now the leader of your guildsystem.");
                                p.sendMessage("§7You are not longer the leader of your guildsystem.");
                                return true;
                            }
                            p.sendMessage("§cThis player is not in your guildsystem.");
                            return true;
                        }
                        p.sendMessage("§cYou are not the leader of your guildsystem.");
                        return true;
                    }
                    p.sendMessage("§cYou are not in a guildsystem.");
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
                    p.sendMessage("§cThis guildsystem doesnt exist.");
                    return true;
                } else if (args[0].equalsIgnoreCase("create")) {
                    if (GuildSystem.newGuild(args[1], p.getUniqueId())) {
                        p.sendMessage("§aYou created a new guildsystem successfully.");
                        return true;
                    }
                    p.sendMessage("§cThe guildsystem already exists.");
                    return true;
                }
            } else if (args.length == 3) {
                if (args[0].equalsIgnoreCase("money")) {
                    if (args[1].equalsIgnoreCase("add")) {
                        Guild g = GuildSystem.getGuild(p.getUniqueId());
                        if(g != null) {
                            //MONEY API NEEDED
                            g.addGold(Double.valueOf(args[2]));
                            p.sendMessage("§7You successfully payed the amount.");
                            return true;
                        }
                        p.sendMessage("§cYou are not in a guildsystem.");
                        return true;
                    }
                }
            } else {
                p.sendMessage("§cUsage: /guildsystem help");
                return true;
            }
        }
        return true;
    }
}