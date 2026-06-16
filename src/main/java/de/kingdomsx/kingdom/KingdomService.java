package de.kingdomsx.kingdom;

import org.bukkit.entity.Player;

public class KingdomService {

    private final KingdomManager kingdomManager;

    public KingdomService(KingdomManager kingdomManager) {
        this.kingdomManager = kingdomManager;
    }

    public boolean createKingdom(Player player, String name) {

        if (kingdomManager.hasKingdom(player.getUniqueId())) {
            return false;
        }

        kingdomManager.createKingdom(
                player.getUniqueId(),
                name
        );

        return true;
    }
}
