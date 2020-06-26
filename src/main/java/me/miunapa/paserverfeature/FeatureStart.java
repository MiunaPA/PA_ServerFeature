package me.miunapa.paserverfeature;

import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.PluginManager;
import me.miunapa.paserverfeature.block.*;
import me.miunapa.paserverfeature.command.*;
import me.miunapa.paserverfeature.entity.*;
import me.miunapa.paserverfeature.feature.*;
import me.miunapa.paserverfeature.player.*;

public class FeatureStart {
    public Main plugin = Main.getPlugin(Main.class);
    public FileConfiguration config = plugin.getConfig();
    public PluginManager pm = Bukkit.getPluginManager();

    public void init() {
        // block
        new BlockPlace();
        // command
        new Hat();
        new PVP();
        new Rule();
        new Suicide();
        new LockChange();
        new Invisible();
        // entity
        new EntitySpawn();
        new EntityBreed();
        new ProtectVillage();
        // feature
        new BeeCount();
        new ItemSign();
        new NewPlayer();
        new Dispense();
        // player
        new PlayerDeath();
    }
}
