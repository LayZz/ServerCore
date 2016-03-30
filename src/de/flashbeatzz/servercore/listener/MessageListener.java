package de.flashbeatzz.servercore.listener;

import de.flashbeatzz.servercore.utils.UUIDLibrary;
import de.flashbeatzz.servercore.utils.events.MessageEvent;
import de.flashbeatzz.servercore.utils.guildsystem.Guild;
import de.flashbeatzz.servercore.utils.guildsystem.GuildSystem;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

import java.util.UUID;

public class MessageListener implements Listener {

    @EventHandler
    public void onMessageRecieve(MessageEvent e) {
        if (e.getHeader().contains("GUILDECHAT")) {
            Integer id = Integer.valueOf(e.getHeader().split("-")[1]);
            Guild g = GuildSystem.getGuild(id);
            if(g != null) {
                String[] msg = e.getMessage().split("§-§");
                if (UUID.fromString(msg[0]).equals(g.getFounder())) {
                    g.broadcast("§f§l[§4G§f§l]§c " + UUIDLibrary.getName(UUID.fromString(msg[0])) + "§4§l:§f " + msg[1]);
                } else {
                    g.broadcast("§f§l[§4G§f§l]§f " + UUIDLibrary.getName(UUID.fromString(msg[0])) + "§4§l:§f " + msg[1]);
                }
            }

        }
    }

}
