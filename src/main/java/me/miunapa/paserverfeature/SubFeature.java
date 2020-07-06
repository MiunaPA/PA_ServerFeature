package me.miunapa.paserverfeature;

import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.PluginManager;

public abstract class SubFeature {
    public Main plugin = Main.getPlugin(Main.class);
    public FileConfiguration config = plugin.getConfig();
    public PluginManager pm = Bukkit.getPluginManager();
    private final String name;

    public SubFeature(String name) {
        this.name = name;
    }

    public abstract void onDisable();

    public String getName() {
        return name;
    }
}
