package de.kingdomsx.kingdom.invite;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class InviteManager {

    private final Map<UUID, KingdomInvite> invites =
            new HashMap<>();

    public void invite(
            UUID target,
            UUID kingdomId
    ) {

        invites.put(
                target,
                new KingdomInvite(
                        kingdomId,
                        target
                )
        );
    }

    public KingdomInvite getInvite(UUID player) {
        return invites.get(player);
    }

    public void removeInvite(UUID player) {
        invites.remove(player);
    }

    public boolean hasInvite(UUID player) {
        return invites.containsKey(player);
    }
}
