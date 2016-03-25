package de.flashbeatzz.servercore.utils.guilde;

public class Tag {
    String tag;

    public static Tag fromString(String str) {
        return new Tag(str);
    }

    private Tag(String str) {
        tag = str;
    }

    public String asString() {
        return tag;
    }

}
