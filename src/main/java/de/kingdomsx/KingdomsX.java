package de.kingdomsx;

import de.kingdomsx.bootstrap.StartupManager;
import org.bukkit.plugin.java.JavaPlugin;

public final class KingdomsX extends JavaPlugin {

    private static KingdomsX instance;

    private StartupManager startupManager;

    @Override
    public void onEnable() {

        instance = this;

        saveDefaultConfig();

        startupManager = new StartupManager(this);
        startupManager.start();

        getLogger().info("KingdomsX enabled.");
    }

    @Override
    public void onDisable() {

        if (startupManager != null) {
            startupManager.shutdown();
        }

        getLogger().info("KingdomsX disabled.");
    }

    public static KingdomsX getInstance() {
        return instance;
    }

    public StartupManager getStartupManager() {
        return startupManager;
    }
}
