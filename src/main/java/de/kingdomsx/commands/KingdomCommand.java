package de.kingdomsx.commands;

import de.kingdomsx.bootstrap.ServiceRegistry;
import de.kingdomsx.kingdom.Kingdom;
import de.kingdomsx.kingdom.KingdomService;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class KingdomCommand implements CommandExecutor {

    private final KingdomService kingdomService =
            ServiceRegistry.getKingdomService();

    @Override
    public boolean onCommand(
            CommandSender sender,
            Command command,
            String label,
            String[] args
    ) {

        if (!(sender instanceof Player player)) {
            sender.sendMessage("Only players can use this command.");
            return true;
        }

        if (args.length == 0) {

            player.sendMessage("§6KingdomsX");
            player.sendMessage("§e/k create <name>");
            player.sendMessage("§e/k info");

            return true;
        }

        if (args[0].equalsIgnoreCase("create")) {

            if (args.length < 2) {
                player.sendMessage("§cUsage: /k create <name>");
                return true;
            }

            String name = args[1];

            if (kingdomService.createKingdom(player, name)) {

                player.sendMessage(
                        "§aKingdom created: §e" + name
                );

            } else {

                player.sendMessage(
                        "§cYou are already in a kingdom."
                );
            }

            return true;
        }

        if (args[0].equalsIgnoreCase("info")) {

            Kingdom kingdom =
                    ServiceRegistry.getKingdomManager()
                            .getKingdomByPlayer(
                                    player.getUniqueId()
                            );

            if (kingdom == null) {

                player.sendMessage(
                        "§cYou are not in a kingdom."
                );

                return true;
            }

            player.sendMessage("§6=== Kingdom Info ===");
            player.sendMessage("§eName: §f" + kingdom.getName());
            player.sendMessage("§eMembers: §f" + kingdom.getMembers().size());

            return true;
        }

        return true;
    }
}
