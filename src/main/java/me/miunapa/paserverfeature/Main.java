package me.miunapa.paserverfeature;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

public class Main extends JavaPlugin {
    FileConfiguration config = this.getConfig();

    @Override
    public void onEnable() {
        this.saveDefaultConfig();
        FeatureStart featureStart = new FeatureStart();
        featureStart.init();
        getLogger().info("paserverfeature Start  Author:MiunaPA");
    }

    @Override
    public void onDisable() {
    }
}
