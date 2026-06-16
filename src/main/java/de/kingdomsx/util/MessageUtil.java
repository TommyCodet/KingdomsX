package de.kingdomsx.util;

import org.bukkit.command.CommandSender;

public final class MessageUtil {

    private MessageUtil() {}

    public static void send(
            CommandSender sender,
            String message
    ) {
        sender.sendMessage(message);
    }
}
