package de.kingdomsx.kingdom;

import org.bukkit.entity.Player;

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

        if (kingdom == null)
            return false;

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

        if (kingdom == null)
            return false;

        if (!kingdom.getOwner()
                .equals(player.getUniqueId())) {
            return false;
        }

        kingdomManager.deleteKingdom(
                kingdom.getId()
        );

        return true;
    }
}
