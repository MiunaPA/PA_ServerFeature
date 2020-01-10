package me.miunapa.paserverfeature;

import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.PluginManager;
import me.miunapa.paserverfeature.block.*;
import me.miunapa.paserverfeature.command.*;
import me.miunapa.paserverfeature.player.*;

public class FeatureStart {
    public static Plugin plugin = Bukkit.getPluginManager().getPlugin("PAServerFeature");
    public static FileConfiguration config = plugin.getConfig();
    public static PluginManager pm = Bukkit.getPluginManager();

    public void init() {
        // block
        new BlockPlace();
        // new DispensePlanting();
        // command
        new Hat();
        new PlayerIP();
        new Suicide();
        // entity
        // player
        new Fishing();
        new PlayerDeath();
    }
}
