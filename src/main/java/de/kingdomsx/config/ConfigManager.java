package de.kingdomsx.config;

import de.kingdomsx.KingdomsX;
import org.bukkit.configuration.file.FileConfiguration;

public class ConfigManager {

    private final KingdomsX plugin;

    public ConfigManager(KingdomsX plugin) {
        this.plugin = plugin;
    }

    public FileConfiguration getConfig() {
        return plugin.getConfig();
    }

    public boolean isDebugEnabled() {
        return getConfig().getBoolean("debug");
    }

    public int getMaxClaims() {
        return getConfig().getInt(
                "claims.max-claims-per-kingdom"
        );
    }
}
