package de.kingdomsx.bootstrap;

import de.kingdomsx.kingdom.KingdomManager;
import de.kingdomsx.kingdom.KingdomService;

public final class ServiceRegistry {

    private static KingdomManager kingdomManager;
    private static KingdomService kingdomService;

    private ServiceRegistry() {}

    public static void initialize() {

        kingdomManager = new KingdomManager();

        kingdomService = new KingdomService(
                kingdomManager
        );
    }

    public static KingdomManager getKingdomManager() {
        return kingdomManager;
    }

    public static KingdomService getKingdomService() {
        return kingdomService;
    }
}
