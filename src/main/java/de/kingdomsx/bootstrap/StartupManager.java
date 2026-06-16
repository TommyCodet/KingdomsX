package de.kingdomsx.bootstrap;

import de.kingdomsx.KingdomsX;

public class StartupManager {

    private final KingdomsX plugin;

    public StartupManager(KingdomsX plugin) {
        this.plugin = plugin;
    }

    public void start() {

        plugin.getLogger().info("Loading configuration...");
        plugin.getLogger().info("Loading services...");

        CommandRegistrar.register(plugin);

        
        plugin.getLogger().info("Startup completed.");
    }

    public void shutdown() {

        plugin.getLogger().info("Saving data...");
        plugin.getLogger().info("Shutdown completed.");
    }
}
