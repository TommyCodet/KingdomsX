package de.kingdomsx.commands;

import de.kingdomsx.util.MessageUtil;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

public class KingdomCommand implements CommandExecutor {

    @Override
    public boolean onCommand(
            CommandSender sender,
            Command command,
            String label,
            String[] args
    ) {

        MessageUtil.send(
                sender,
                "§6KingdomsX §7| §aPlugin erfolgreich geladen."
        );

        return true;
    }
}
