package de.flashbeatzz.servercore.utils.events;

import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

public class MessageEvent extends Event {

    private String header;
    private String message;

    public MessageEvent(String header, String message) {
        this.header = header;
        this.message = message;
    }

    public String getHeader() {
        return header;
    }

    public String getMessage() {
        return message;
    }

    @Override
    public HandlerList getHandlers() {
        return null;
    }

}
