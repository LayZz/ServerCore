package de.flashbeatzz.servercore.listener;

import de.flashbeatzz.servercore.utils.UserData;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerPreLoginEvent;

public class PlayerPreLoginListener implements Listener {

    @EventHandler
    public void onPlayerPreLogin(AsyncPlayerPreLoginEvent event) {
        UserData userData = new UserData(event.getName(), event.getUniqueId());
        if(userData.isValid()) {
            event.allow();
        } else {
            event.disallow(AsyncPlayerPreLoginEvent.Result.KICK_OTHER, "§4Could not create valid UserData. Please try again later!");
        }
    }

}
