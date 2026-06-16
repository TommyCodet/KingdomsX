package de.kingdomsx.commands;

import de.kingdomsx.bootstrap.ServiceRegistry;
import de.kingdomsx.kingdom.Kingdom;
import de.kingdomsx.kingdom.KingdomMember;
import de.kingdomsx.kingdom.KingdomRole;
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
            player.sendMessage("§e/k kick <player>");
            player.sendMessage("§e/k promote <player>");
            player.sendMessage("§e/k demote <player>");

            return true;
        }

        if (args[0].equalsIgnoreCase("create")) {

            if (args.length < 2) {
                player.sendMessage("§cUsage: /k create <name>");
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
                return true;
            }

            Kingdom kingdom =
                    ServiceRegistry
                            .getKingdomManager()
                            .getKingdomByPlayer(
                                    player.getUniqueId()
                            );

            if (kingdom == null) {
                player.sendMessage("§cNo kingdom.");
                return true;
            }

            KingdomMember self =
                    kingdom.getMember(
                            player.getUniqueId()
                    );

            if (self == null ||
                    !self.getRole().isHigherOrEqual(
                            KingdomRole.OFFICER
                    )) {

                player.sendMessage(
                        "§cOfficer required."
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

            player.sendMessage(
                    "§aInvite sent."
            );

            target.sendMessage(
                    "§eYou were invited to §6"
                            + kingdom.getName()
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

            player.sendMessage(
                    kingdomService.leaveKingdom(player)
                            ? "§aYou left the kingdom."
                            : "§cUnable to leave."
            );

            return true;
        }

        if (args[0].equalsIgnoreCase("disband")) {

            player.sendMessage(
                    kingdomService.disbandKingdom(player)
                            ? "§aKingdom disbanded."
                            : "§cOnly the king can disband."
            );

            return true;
        }

        if (args[0].equalsIgnoreCase("kick")) {

            if (args.length < 2) {
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

            player.sendMessage(
                    kingdomService.kickPlayer(
                            player,
                            target.getUniqueId()
                    )
                            ? "§aPlayer kicked."
                            : "§cUnable to kick player."
            );

            return true;
        }

        if (args[0].equalsIgnoreCase("promote")) {

            if (args.length < 2) {
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

            player.sendMessage(
                    kingdomService.promotePlayer(
                            player,
                            target.getUniqueId()
                    )
                            ? "§aPlayer promoted."
                            : "§cUnable to promote."
            );

            return true;
        }

        if (args[0].equalsIgnoreCase("demote")) {

            if (args.length < 2) {
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

            player.sendMessage(
                    kingdomService.demotePlayer(
                            player,
                            target.getUniqueId()
                    )
                            ? "§aPlayer demoted."
                            : "§cUnable to demote."
            );

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
                player.sendMessage("§cNo kingdom.");
                return true;
            }

            player.sendMessage(
                    "§6Members:"
            );

            kingdom.getMembers()
                    .values()
                    .forEach(member ->
                            player.sendMessage(
                                    "§7" +
                                    member.getUniqueId() +
                                    " §8- §e" +
                                    member.getRole()
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
                player.sendMessage("§cNo kingdom.");
                return true;
            }

            player.sendMessage("§6=== Kingdom ===");
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

        return true;
    }
}
