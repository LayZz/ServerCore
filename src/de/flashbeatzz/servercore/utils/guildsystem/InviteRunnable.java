package de.flashbeatzz.servercore.utils.guildsystem;

import de.flashbeatzz.servercore.ServerCore;
import org.bukkit.Bukkit;

import java.util.HashMap;
import java.util.UUID;

public class InviteRunnable implements Runnable {
    public static HashMap<UUID, Guild> pendingInvites = new HashMap<>();
    private UUID uuid;
    private Integer counter = 0;

    public InviteRunnable(UUID uuid, Guild g) {
        if(!pendingInvites.containsKey(uuid)) {
            this.uuid = uuid;
            pendingInvites.put(uuid, g);
            Bukkit.getScheduler().runTaskLater(ServerCore.getInstance(), this, 600L);
        }
    }

    @Override
    public void run() {
        if(pendingInvites.containsKey(uuid)) {
            pendingInvites.remove(uuid);
        }
    }
}
