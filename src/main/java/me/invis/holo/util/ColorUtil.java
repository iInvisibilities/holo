package me.invis.holo.util;

import org.bukkit.ChatColor;

public class ColorUtil {

    public static String t(String input) {
        return ChatColor.translateAlternateColorCodes('&', input);
    }

}
