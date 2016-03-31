package de.flashbeatzz.servercore.utils.events;

import de.flashbeatzz.servercore.utils.levelsystem.Level;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

import java.util.UUID;

public class LevelChangeEvent extends Event {
    private Level newLevel;
    private Level oldLevel;
    private UUID uuid;
    private static final HandlerList handlers = new HandlerList();

    public LevelChangeEvent(UUID uuid, Integer old, Integer newL) {
        this.uuid = uuid;
        this.newLevel = new Level(newL);
        this.oldLevel = new Level(old);
    }

    public Level getOldLevel() {
        return oldLevel;
    }

    public Level getNewLevel() {
        return newLevel;
    }

    public Player getPlayer() {
        return Bukkit.getPlayer(uuid);
    }

    public Boolean higher() {
        return newLevel.toInt() > oldLevel.toInt();
    }

    public HandlerList getHandlers() {
        return handlers;
    }

    public static HandlerList getHandlerList() {
        return handlers;
    }
}
