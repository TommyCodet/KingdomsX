package de.kingdomsx.kingdom;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

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

        if (kingdomId == null) {
            return null;
        }

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

        if (kingdom == null) {
            return;
        }

        for (UUID member : kingdom.getMembers().keySet()) {
            playerKingdoms.remove(member);
        }
    }

    public void addMember(
            Kingdom kingdom,
            UUID player
    ) {

        kingdom.getMembers().put(
                player,
                new KingdomMember(
                        player,
                        KingdomRole.RECRUIT
                )
        );

        playerKingdoms.put(
                player,
                kingdom.getId()
        );
    }

    public void removeMember(
            Kingdom kingdom,
            UUID player
    ) {

        kingdom.getMembers().remove(player);
        playerKingdoms.remove(player);
    }

    public KingdomMember getMember(
            Kingdom kingdom,
            UUID player
    ) {
        return kingdom.getMembers().get(player);
    }

    public boolean isKing(
            Kingdom kingdom,
            UUID player
    ) {

        KingdomMember member =
                getMember(
                        kingdom,
                        player
                );

        if (member == null) {
            return false;
        }

        return member.getRole() ==
                KingdomRole.KING;
    }

    public void kickMember(
            Kingdom kingdom,
            UUID player
    ) {

        kingdom.getMembers().remove(
                player
        );

        playerKingdoms.remove(
                player
        );
    }
}
