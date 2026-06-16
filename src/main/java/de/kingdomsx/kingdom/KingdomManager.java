package de.kingdomsx.kingdom;

import java.util.*;

public class KingdomManager {

    private final Map<UUID, Kingdom> kingdoms;
    private final Map<UUID, UUID> playerKingdoms;

    public KingdomManager() {
        this.kingdoms = new HashMap<>();
        this.playerKingdoms = new HashMap<>();
    }

    public Kingdom createKingdom(UUID owner, String name) {

        Kingdom kingdom = new Kingdom(
                UUID.randomUUID(),
                name,
                owner
        );

        kingdom.getMembers().put(
                owner,
                new KingdomMember(
                        owner,
                        KingdomRole.KING
                )
        );

        kingdoms.put(
                kingdom.getId(),
                kingdom
        );

        playerKingdoms.put(
                owner,
                kingdom.getId()
        );

        return kingdom;
    }

    public Kingdom getKingdom(UUID kingdomId) {
        return kingdoms.get(kingdomId);
    }

    public Kingdom getKingdomByPlayer(UUID player) {

        UUID kingdomId = playerKingdoms.get(player);

        if (kingdomId == null)
            return null;

        return kingdoms.get(kingdomId);
    }

    public boolean hasKingdom(UUID player) {
        return playerKingdoms.containsKey(player);
    }

    public Collection<Kingdom> getKingdoms() {
        return kingdoms.values();
    }

    public void deleteKingdom(UUID kingdomId) {

        Kingdom kingdom = kingdoms.remove(kingdomId);

        if (kingdom == null)
            return;

        for (UUID member : kingdom.getMembers().keySet()) {
            playerKingdoms.remove(member);
        }
    }
}
