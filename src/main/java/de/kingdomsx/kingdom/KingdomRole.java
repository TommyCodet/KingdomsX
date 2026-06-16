package de.kingdomsx.kingdom;

public enum KingdomRole {

    KING(5),
    CO_KING(4),
    GENERAL(3),
    OFFICER(2),
    MEMBER(1),
    RECRUIT(0);

    private final int power;

    KingdomRole(int power) {
        this.power = power;
    }

    public int getPower() {
        return power;
    }

    public boolean isHigherOrEqual(KingdomRole role) {
        return this.power >= role.power;
    }
}
