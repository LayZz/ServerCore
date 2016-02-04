package de.flashbeatzz.servercore.utils;

public enum SocketTarget {

    BUNGEECORD("BUNGEECORD"),
    SPIGOT("SPIGOT"),
    ALL("ALL");

    String name;

    SocketTarget(String name) {
        this.name = name;
    }

    public String get() {
        return name;
    }

}
