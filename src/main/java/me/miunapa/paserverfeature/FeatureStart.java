package me.miunapa.paserverfeature;

import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.PluginManager;
import me.miunapa.paserverfeature.command.*;
import me.miunapa.paserverfeature.entity.*;
import me.miunapa.paserverfeature.feature.*;
import me.miunapa.paserverfeature.player.*;

public class FeatureStart {
    public Main plugin = Main.getPlugin(Main.class);
    public FileConfiguration config = plugin.getConfig();
    public PluginManager pm = Bukkit.getPluginManager();

    public void init() {
        // command
        new Hat();
        new Invisible();
        new ItemSign();
        new LockChange();
        new PVP();
        new Rule();
        new Suicide();
        // entity
        new EntityBreed();
        new EntitySpawn();
        new ProtectVillage();
        // feature
        new BeeCount();
        new NetheriteEquipment();
        new SpawnerChange();
        new StrippedLog();
        new WitherDispense();
        // player
        new NewPlayer();
        new PlayerDeath();
    }
}
