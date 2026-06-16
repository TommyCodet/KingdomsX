package de.kingdomsx.kingdom.invite;

import java.util.UUID;

public class KingdomInvite {

    private final UUID kingdomId;
    private final UUID target;
    private final long createdAt;

    public KingdomInvite(
            UUID kingdomId,
            UUID target
    ) {
        this.kingdomId = kingdomId;
        this.target = target;
        this.createdAt = System.currentTimeMillis();
    }

    public UUID getKingdomId() {
        return kingdomId;
    }

    public UUID getTarget() {
        return target;
    }

    public long getCreatedAt() {
        return createdAt;
    }
}
