package de.kingdomsx.bootstrap;

import de.kingdomsx.kingdom.KingdomManager;
import de.kingdomsx.kingdom.KingdomService;
import de.kingdomsx.kingdom.invite.InviteManager;

public final class ServiceRegistry {

    private static KingdomManager kingdomManager;
    private static KingdomService kingdomService;
    private static InviteManager inviteManager;

    private ServiceRegistry() {}

    public static void initialize() {

        kingdomManager = new KingdomManager();

        kingdomService =
                new KingdomService(
                        kingdomManager
                );

        inviteManager =
                new InviteManager();
    }

    public static KingdomManager getKingdomManager() {
        return kingdomManager;
    }

    public static KingdomService getKingdomService() {
        return kingdomService;
    }

    public static InviteManager getInviteManager() {
        return inviteManager;
    }
}
