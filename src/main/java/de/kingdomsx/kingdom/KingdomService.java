package de.kingdomsx.kingdom;

import org.bukkit.entity.Player;

import java.util.UUID;

public class KingdomService {

    private final KingdomManager kingdomManager;

    public KingdomService(
            KingdomManager kingdomManager
    ) {
        this.kingdomManager = kingdomManager;
    }

    public boolean createKingdom(
            Player player,
            String name
    ) {

        if (kingdomManager.hasKingdom(
                player.getUniqueId()
        )) {
            return false;
        }

        if (name.length() < 3 ||
                name.length() > 16) {
            return false;
        }

        for (Kingdom kingdom :
                kingdomManager.getKingdoms()) {

            if (kingdom.getName()
                    .equalsIgnoreCase(name)) {
                return false;
            }
        }

        kingdomManager.createKingdom(
                player.getUniqueId(),
                name
        );

        return true;
    }

    public boolean leaveKingdom(
            Player player
    ) {

        Kingdom kingdom =
                kingdomManager.getKingdomByPlayer(
                        player.getUniqueId()
                );

        if (kingdom == null) {
            return false;
        }

        if (kingdom.getOwner()
                .equals(player.getUniqueId())) {
            return false;
        }

        kingdomManager.removeMember(
                kingdom,
                player.getUniqueId()
        );

        return true;
    }

    public boolean disbandKingdom(
            Player player
    ) {

        Kingdom kingdom =
                kingdomManager.getKingdomByPlayer(
                        player.getUniqueId()
                );

        if (kingdom == null) {
            return false;
        }

        if (!kingdomManager.isKing(
                kingdom,
                player.getUniqueId()
        )) {
            return false;
        }

        kingdomManager.deleteKingdom(
                kingdom.getId()
        );

        return true;
    }

    public boolean kickPlayer(
            Player executor,
            UUID target
    ) {

        Kingdom kingdom =
                kingdomManager.getKingdomByPlayer(
                        executor.getUniqueId()
                );

        if (kingdom == null) {
            return false;
        }

        if (!kingdomManager.hasRole(
                kingdom,
                executor.getUniqueId(),
                KingdomRole.GENERAL
        )) {
            return false;
        }

        if (executor.getUniqueId()
                .equals(target)) {
            return false;
        }

        KingdomMember member =
                kingdomManager.getMember(
                        kingdom,
                        target
                );

        if (member == null) {
            return false;
        }

        if (member.getRole() ==
                KingdomRole.KING) {
            return false;
        }

        kingdomManager.kickMember(
                kingdom,
                target
        );

        return true;
    }

    public boolean promotePlayer(
            Player executor,
            UUID target
    ) {

        Kingdom kingdom =
                kingdomManager.getKingdomByPlayer(
                        executor.getUniqueId()
                );

        if (kingdom == null) {
            return false;
        }

        if (!kingdomManager.isKing(
                kingdom,
                executor.getUniqueId()
        )) {
            return false;
        }

        return kingdomManager.promoteMember(
                kingdom,
                target
        );
    }

    public boolean demotePlayer(
            Player executor,
            UUID target
    ) {

        Kingdom kingdom =
                kingdomManager.getKingdomByPlayer(
                        executor.getUniqueId()
                );

        if (kingdom == null) {
            return false;
        }

        if (!kingdomManager.isKing(
                kingdom,
                executor.getUniqueId()
        )) {
            return false;
        }

        KingdomMember member =
                kingdomManager.getMember(
                        kingdom,
                        target
                );

        if (member == null) {
            return false;
        }

        if (member.getRole() ==
                KingdomRole.KING) {
            return false;
        }

        return kingdomManager.demoteMember(
                kingdom,
                target
        );
    }
}
