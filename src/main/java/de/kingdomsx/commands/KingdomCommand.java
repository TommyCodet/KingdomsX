package de.kingdomsx.commands;

import de.kingdomsx.bootstrap.ServiceRegistry;
import de.kingdomsx.kingdom.Kingdom;
import de.kingdomsx.kingdom.KingdomService;
import de.kingdomsx.kingdom.invite.InviteManager;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class KingdomCommand implements CommandExecutor {

    private final KingdomService kingdomService =
            ServiceRegistry.getKingdomService();

    private final InviteManager inviteManager =
            ServiceRegistry.getInviteManager();

    @Override
    public boolean onCommand(
            CommandSender sender,
            Command command,
            String label,
            String[] args
    ) {

        if (!(sender instanceof Player player)) {
            return true;
        }

        if (args.length == 0) {

            player.sendMessage("§6KingdomsX");
            player.sendMessage("§e/k create <name>");
            player.sendMessage("§e/k invite <player>");
            player.sendMessage("§e/k join");
            player.sendMessage("§e/k leave");
            player.sendMessage("§e/k members");
            player.sendMessage("§e/k info");
            player.sendMessage("§e/k disband");

            return true;
        }

        if (args[0].equalsIgnoreCase("create")) {

            if (args.length < 2) {
                player.sendMessage(
                        "§cUsage: /k create <name>"
                );
                return true;
            }

            if (kingdomService.createKingdom(
                    player,
                    args[1]
            )) {

                player.sendMessage(
                        "§aKingdom created."
                );

            } else {

                player.sendMessage(
                        "§cCould not create kingdom."
                );
            }

            return true;
        }

        if (args[0].equalsIgnoreCase("invite")) {

            if (args.length < 2) {
                player.sendMessage(
                        "§cUsage: /k invite <player>"
                );
                return true;
            }

            Kingdom kingdom =
                    ServiceRegistry
                            .getKingdomManager()
                            .getKingdomByPlayer(
                                    player.getUniqueId()
                            );

            if (kingdom == null) {

                player.sendMessage(
                        "§cYou are not in a kingdom."
                );

                return true;
            }

            Player target =
                    Bukkit.getPlayer(args[1]);

            if (target == null) {

                player.sendMessage(
                        "§cPlayer not found."
                );

                return true;
            }

            inviteManager.invite(
                    target.getUniqueId(),
                    kingdom.getId()
            );

            target.sendMessage(
                    "§eYou were invited to §6"
                            + kingdom.getName()
            );

            player.sendMessage(
                    "§aInvite sent."
            );

            return true;
        }

        if (args[0].equalsIgnoreCase("join")) {

            if (!inviteManager.hasInvite(
                    player.getUniqueId()
            )) {

                player.sendMessage(
                        "§cNo invite found."
                );

                return true;
            }

            var invite =
                    inviteManager.getInvite(
                            player.getUniqueId()
                    );

            Kingdom kingdom =
                    ServiceRegistry
                            .getKingdomManager()
                            .getKingdom(
                                    invite.getKingdomId()
                            );

            if (kingdom == null) {
                return true;
            }

            ServiceRegistry
                    .getKingdomManager()
                    .addMember(
                            kingdom,
                            player.getUniqueId()
                    );

            inviteManager.removeInvite(
                    player.getUniqueId()
            );

            player.sendMessage(
                    "§aJoined kingdom."
            );

            return true;
        }

        if (args[0].equalsIgnoreCase("leave")) {

            if (kingdomService.leaveKingdom(
                    player
            )) {

                player.sendMessage(
                        "§aYou left the kingdom."
                );

            } else {

                player.sendMessage(
                        "§cUnable to leave."
                );
            }

            return true;
        }

        if (args[0].equalsIgnoreCase("members")) {

            Kingdom kingdom =
                    ServiceRegistry
                            .getKingdomManager()
                            .getKingdomByPlayer(
                                    player.getUniqueId()
                            );

            if (kingdom == null) {

                player.sendMessage(
                        "§cNo kingdom."
                );

                return true;
            }

            player.sendMessage(
                    "§6Members:"
            );

            kingdom.getMembers()
                    .values()
                    .forEach(member ->
                            player.sendMessage(
                                    "§e" +
                                    member.getUniqueId()
                            ));

            return true;
        }

        if (args[0].equalsIgnoreCase("info")) {

            Kingdom kingdom =
                    ServiceRegistry
                            .getKingdomManager()
                            .getKingdomByPlayer(
                                    player.getUniqueId()
                            );

            if (kingdom == null) {

                player.sendMessage(
                        "§cNo kingdom."
                );

                return true;
            }

            player.sendMessage(
                    "§6=== Kingdom ==="
            );

            player.sendMessage(
                    "§eName: §f" +
                            kingdom.getName()
            );

            player.sendMessage(
                    "§eMembers: §f" +
                            kingdom.getMembers().size()
            );

            return true;
        }

        if (args[0].equalsIgnoreCase("disband")) {

            if (kingdomService.disbandKingdom(
                    player
            )) {

                player.sendMessage(
                        "§aKingdom disbanded."
                );

            } else {

                player.sendMessage(
                        "§cOnly the king can disband."
                );
            }

            return true;
        }

        return true;
    }
}
