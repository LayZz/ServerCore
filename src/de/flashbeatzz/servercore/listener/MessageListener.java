package de.flashbeatzz.servercore.listener;

import de.flashbeatzz.servercore.utils.UUIDLibrary;
import de.flashbeatzz.servercore.utils.events.MessageEvent;
import de.flashbeatzz.servercore.utils.guilde.Guilde;
import de.flashbeatzz.servercore.utils.guilde.GuildeSystem;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

import java.util.UUID;

public class MessageListener implements Listener {

    @EventHandler
    public void onMessageRecieve(MessageEvent e) {
        if (e.getHeader().contains("GUILDECHAT")) {
            Integer id = Integer.valueOf(e.getHeader().split("-")[1]);
            Guilde g = GuildeSystem.getGuilde(id);
            String[] msg = e.getMessage().split("§-§");
            if (UUIDLibrary.getNameToUUID(UUID.fromString(msg[0])).equals(GuildeSystem.getGuilde(id))) {
                g.broadcast("§f§l[§4G§f§l]§c " + UUIDLibrary.getNameToUUID(UUID.fromString(msg[0])) + "§4§l:§f " + msg[1]);
            } else {
                g.broadcast("§f§l[§4G§f§l]§f " + UUIDLibrary.getNameToUUID(UUID.fromString(msg[0])) + "§4§l:§f " + msg[1]);
            }

        }
    }

}
