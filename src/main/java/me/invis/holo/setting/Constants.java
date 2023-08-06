package me.invis.holo.setting;

import me.invis.holo.Holo;
import org.bukkit.ChatColor;

import java.util.Arrays;

public class Constants {

    public static final String[] USAGE = new String[] {
            "",
            ChatColor.AQUA + "/holo create <name> <first line>",
            ChatColor.AQUA + "/holo delete <name>",
            ChatColor.AQUA + "/holo teleport <name>",
            ChatColor.AQUA + "/holo list",
            "",
            ChatColor.DARK_AQUA + " Hologram management ",
            "",
            ChatColor.AQUA + "/holo line <name> add <new line>",
            ChatColor.AQUA + "/holo line <name> set <line index> <new line>",
            ChatColor.GRAY + "  (to remove a line you can do /holo set <name> <line-index> **LEAVE EMPTY**)",
            ""
    };

    public static final String[] HOLOGRAM_MANAGEMENT_USAGE = Arrays.copyOfRange(USAGE, 7, USAGE.length);

    public static final double SPACING = Holo.getProvidingPlugin(Holo.class).getConfig().getDouble("spacing");

}
