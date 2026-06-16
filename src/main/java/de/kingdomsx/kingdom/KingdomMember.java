package de.kingdomsx.kingdom;

import java.util.UUID;

public class KingdomMember {

    private final UUID uniqueId;
    private KingdomRole role;

    public KingdomMember(UUID uniqueId, KingdomRole role) {
        this.uniqueId = uniqueId;
        this.role = role;
    }

    public UUID getUniqueId() {
        return uniqueId;
    }

    public KingdomRole getRole() {
        return role;
    }

    public void setRole(KingdomRole role) {
        this.role = role;
    }
}
