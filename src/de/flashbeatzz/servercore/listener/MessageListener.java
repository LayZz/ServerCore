package de.flashbeatzz.servercore.listener;

import de.flashbeatzz.servercore.utils.UUIDLibrary;
import de.flashbeatzz.servercore.utils.events.MessageEvent;
import de.flashbeatzz.servercore.utils.guilde.GuildeSystem;
import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

import java.util.UUID;

public class MessageListener implements Listener {

    @EventHandler
    public void onMessageRecieve(MessageEvent e) {
        if(e.getHeader().contains("GUILDECHAT")) {
            Integer id = Integer.valueOf(e.getHeader().split("-")[1]);
            String[] msg = e.getMessage().split("§-§");
            for(UUID uuid : GuildeSystem.getGuilde(id).getMembers()) {
                if (UUIDLibrary.getNameToUUID(UUID.fromString(msg[0])).equals(GuildeSystem.getGuilde(id))) {
                    Bukkit.getPlayer(uuid).sendMessage("§f§l[§4G§f§l]§c " + UUIDLibrary.getNameToUUID(UUID.fromString(msg[0])) + "§4§l:§f " + msg[1]);
                } else {
                    Bukkit.getPlayer(uuid).sendMessage("§f§l[§4G§f§l]§f " + UUIDLibrary.getNameToUUID(UUID.fromString(msg[0])) + "§4§l:§f " + msg[1]);
                }
            }
        }
    }

}
