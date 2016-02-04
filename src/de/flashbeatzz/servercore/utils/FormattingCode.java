package de.flashbeatzz.servercore.utils;

import java.util.ArrayList;
import java.util.List;

public enum FormattingCode {
    BLACK("§0", "\u00A70", "Black", "black", "000000", "000000"),                   //
    DARK_BLUE("§1", "\u00A71", "Dark Blue", "dark_blue", "0000AA", "00002A"),       //
    DARK_GREEN("§2", "\u00A72", "Dark Green", "dark_green", "00AA00", "002A00"),    // prefix "good" messages
    DARK_AQUA("§3", "\u00A73", "Dark Aqua", "dark_aqua", "00AAAA", "002A2A"),       // prefix info messages
    DARK_RED("§4", "\u00A74", "Dark Red", "dark_red", "AA0000", "2A0000"),          // prefix "bad" messages
    DARK_PURPLE("§5", "\u00A75", "Dark Purple", "dark_purple", "AA000AA", "2A002A"),//
    GOLD("§6", "\u00A76", "Gold", "gold", "FFAA00", "2A2A00"),                      // hervorheben von variablen
    GRAY("§7", "\u00A77", "Gray", "gray", "AAAAAA", "2A2A2A"),                      //
    DARK_GRAY("§8", "\u00A78", "Dark Gray", "dark_gray", "555555", "151515"),       // prefix klammern
    BLUE("§9", "\u00A79", "Blue", "blue", "5555FF", "15153F"),

    GREEN("§a", "\u00A7a", "Green", "green", "55FF55", "153F15"),                   // "good" messages
    AQUA("§b", "\u00A7b", "Aqua", "aqua", "55FFFF", "153F3F"),                      // info messages
    RED("§c", "\u00A7c", "Red", "red", "FF5555", "3F1515"),                         // "bad" messages
    LIGHT_PURPLE("§d", "\u00A7d", "Light", "light_purple", "FF55FF", "3F153F"),     //
    YELLOW("§e", "\u00A7e", "Yellow", "yellow", "FFFF55", "3F3F15"),                //
    WHITE("§f", "\u00A7f", "White", "white", "FFFFFF", "3F3F3F"),                   //

    MAGIC("§k", "\u00A7k", "Magic", "magic"),                                       //
    BOLD("§l", "\u00A7l", "Bold", "bold"),                                          //
    STRIKETHROUGH("§m", "\u00A7m", "Strikethrough", "strikethrough"),               //
    UNDERLINE("§n", "\u00A7n", "Underline", "underline"),                           //
    ITALIC("§o", "\u00A7o", "Italic", "italic"),                                    //
    RESET("§r", "\u00A7r", "Reset", "reset"),                                       //

    EXTRA_LINE("\n", "\n", "Extra Line", "extra_line");                             //

    private String code, motdCode, officialName, technicalName, foregroundHEX, backgroundHEX;

    FormattingCode(String code, String motdCode, String officialName, String technicalName, String foregroundHEX, String backgroundHEX) {
        this.code = code;
        this.motdCode = motdCode;
        this.officialName = officialName;
        this.technicalName = technicalName;
        this.foregroundHEX = foregroundHEX;
        this.backgroundHEX = backgroundHEX;
    }

    FormattingCode(String code, String motdCode, String officialName, String technicalName) {
        this.code = code;
        this.motdCode = motdCode;
        this.officialName = officialName;
        this.technicalName = technicalName;
        this.foregroundHEX = null;
        this.backgroundHEX = null;
    }

    public String getCode() {
        return this.code;
    }

    public String getMotdCode() {
        return this.motdCode;
    }

    public String getOfficialName() {
        return this.officialName;
    }

    public String getTechnicalName() {
        return this.technicalName;
    }

    public String getForegroundHEX() {
        return this.foregroundHEX;
    }

    public String getBackgroundHEX() {
        return this.backgroundHEX;
    }


    public static List<String> getAllAvailableCodes() {
        List<String> list = new ArrayList<>();
        for(FormattingCode fc : FormattingCode.values()) {
            list.add(fc.getCode());
        }
        return list;
    }

}
