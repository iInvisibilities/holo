package me.invis.holo.command;

import me.invis.holo.Holo;
import me.invis.holo.manager.Hologram;
import me.invis.holo.setting.Constants;
import me.invis.holo.util.ArrayUtil;
import me.invis.holo.util.ColorUtil;
import me.invis.holo.util.MessageUtil;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.Arrays;

public class HoloCommand implements CommandExecutor {



    @Override
    public boolean onCommand(CommandSender sender, Command command, String s, String[] args) {
        if(!(sender instanceof Player)) return true;

        if(args.length == 0 || args[0].equalsIgnoreCase("help")) {
            sender.sendMessage(Constants.USAGE);
            return true;
        }

        switch (args[0]) {
            case "list":
                sender.sendMessage(ChatColor.DARK_AQUA + "Current holograms:");
                Holo.getHoloManager().getHolograms().forEach((name, holo) -> MessageUtil.clickable((Player) sender,
                        ChatColor.AQUA + " - " + name,
                        "/holo teleport " + name,
                        "Click here to teleport!"));
                break;
            default:
                if(args.length < 2) {
                    sender.sendMessage(Constants.USAGE);
                    break;
                }

                String holoName = args[1];

                switch (args[0]) {
                    case "create":
                        if(args.length < 3) {
                            sender.sendMessage(ChatColor.RED + "Please define the first line!");
                            return true;
                        }

                        if(Holo.getHoloManager().getHolograms().containsKey(holoName)) {
                            sender.sendMessage(ChatColor.RED + "A hologram with the same name already exists!");
                            return true;
                        }

                        String firstLine = ColorUtil.t(ArrayUtil.fromArray(Arrays.copyOfRange(args, 2, args.length)));
                        Hologram newHologram = new Hologram(((Player) sender).getLocation());
                        newHologram.addLines(firstLine);

                        Holo.getHoloManager().saveHologram(holoName, newHologram);

                        sender.sendMessage(ChatColor.GREEN + "Created new hologram named " + ChatColor.GRAY + holoName + ChatColor.GREEN + " with first line:");
                        sender.sendMessage(ChatColor.GREEN + " - " + ChatColor.RESET + firstLine);

                        Holo.getHoloManager().updated();
                        break;
                    case "line":
                        if(args.length < 4) {
                            sender.sendMessage(Constants.HOLOGRAM_MANAGEMENT_USAGE);
                            break;
                        }

                        String newLine = ColorUtil.t(ArrayUtil.fromArray(Arrays.copyOfRange(args, 3, args.length)));
                        Hologram hologram = Holo.getHoloManager().get(holoName);

                        switch (args[2]) {
                            case "add":
                                hologram.addLines(newLine);

                                sender.sendMessage(ChatColor.GREEN + "Added new line to " + ChatColor.GRAY + holoName + "!");
                                sender.sendMessage(ChatColor.AQUA + " - " + ChatColor.RESET + newLine);
                                break;
                            case "set":
                                int lineIndex = Integer.parseInt(args[3]);

                                try {
                                    newLine = ColorUtil.t(ArrayUtil.fromArray(Arrays.copyOfRange(args, 4, args.length)));
                                }
                                catch (StringIndexOutOfBoundsException exception) {
                                    if(hologram.getContent().size() <= 1) {
                                        sender.sendMessage("");
                                        sender.sendMessage(ChatColor.RED + "You can't delete the only left line in this hologram!");
                                        sender.sendMessage(ChatColor.RED + "In order to delete this hologram, please use");
                                        MessageUtil.clickable((Player) sender,
                                                ChatColor.RED + "/holo delete " + holoName,
                                                "/holo delete " + holoName,
                                                "Click here to delete the hologram!");
                                        sender.sendMessage("");
                                        return true;
                                    }

                                    hologram.removeLine(lineIndex);
                                    sender.sendMessage(ChatColor.GREEN + "Removed line number " + lineIndex + " from hologram " + ChatColor.GRAY + holoName + "!");
                                    break;
                                }

                                hologram.setLine(lineIndex, newLine);

                                sender.sendMessage(ChatColor.GREEN + "Changed line " + lineIndex + " in " + ChatColor.GRAY + holoName);
                                sender.sendMessage(ChatColor.GREEN + "to " + ChatColor.RESET + newLine + ChatColor.GREEN + "!");
                                break;
                            default:
                                sender.sendMessage(Constants.HOLOGRAM_MANAGEMENT_USAGE);
                                return true;
                        }

                        Holo.getHoloManager().saveHologram(holoName, hologram);
                        Holo.getHoloManager().updated();
                        break;
                    case "delete":
                        Holo.getHoloManager().removeHologram(holoName);
                        sender.sendMessage(ChatColor.AQUA + "Deleted " + ChatColor.GRAY + holoName + ChatColor.AQUA + " if it existed!");

                        Holo.getHoloManager().updated();
                        break;
                    case "teleport":
                        ((Player) sender).teleport(Holo.getHoloManager().get(holoName).getLocation());
                        sender.sendMessage(ChatColor.AQUA + "Teleported to " + ChatColor.GRAY + holoName);
                        break;
                }
        }

        return true;
    }
}
