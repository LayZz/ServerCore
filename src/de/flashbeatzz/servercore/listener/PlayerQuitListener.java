package de.flashbeatzz.servercore.listener;

import de.flashbeatzz.servercore.utils.UserData;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;

public class PlayerQuitListener implements Listener {

    @EventHandler
    public void onPlayerQuit(PlayerQuitEvent event) {
        UserData.removeUserData(event.getPlayer().getUniqueId());
    }

}
