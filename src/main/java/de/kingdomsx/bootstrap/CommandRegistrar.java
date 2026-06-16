package de.kingdomsx.bootstrap;

import de.kingdomsx.KingdomsX;
import de.kingdomsx.commands.KingdomCommand;

public class CommandRegistrar {

    public static void register(KingdomsX plugin) {

        if (plugin.getCommand("kingdom") != null) {
            plugin.getCommand("kingdom")
                    .setExecutor(new KingdomCommand());
        }
    }
}
