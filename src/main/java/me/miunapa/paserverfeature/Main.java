package me.miunapa.paserverfeature;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

public class Main extends JavaPlugin {
    FileConfiguration config = this.getConfig();
    FeatureStart featureStart;

    @Override
    public void onEnable() {
        this.saveDefaultConfig();
        featureStart = new FeatureStart();
        getLogger().info("PAServerFeature Start  Author:MiunaPA");
    }

    @Override
    public void onDisable() {

    }
}
