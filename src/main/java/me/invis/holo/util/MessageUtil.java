package me.invis.holo.util;

import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.HoverEvent;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.entity.Player;

public class MessageUtil {

    public static void clickable(Player player, String content, String command, String hover) {
        TextComponent component = new TextComponent(content);

        component.setClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, command));
        component.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, TextComponent.fromLegacyText(hover)));

        player.spigot().sendMessage(component);
    }

}
