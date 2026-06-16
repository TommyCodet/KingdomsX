package de.kingdomsx.kingdom;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class Kingdom {

    private final UUID id;
    private String name;

    private final UUID owner;

    private final Map<UUID, KingdomMember> members;

    public Kingdom(UUID id, String name, UUID owner) {
        this.id = id;
        this.name = name;
        this.owner = owner;
        this.members = new HashMap<>();
    }

    public UUID getId() {
        return id;
    }

    public UUID getOwner() {
        return owner;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Map<UUID, KingdomMember> getMembers() {
        return members;
    }
}
