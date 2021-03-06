package me.miunapa.paserverfeature;

import java.util.ArrayList;
import java.util.List;
import org.bukkit.plugin.java.JavaPlugin;
import me.miunapa.paserverfeature.command.*;
import me.miunapa.paserverfeature.entity.*;
import me.miunapa.paserverfeature.feature.*;
import me.miunapa.paserverfeature.player.*;


public class Main extends JavaPlugin {
    static List<SubFeature> features;

    @Override
    public void onEnable() {
        this.saveDefaultConfig();
        initFeature();
    }

    public void initFeature() {
        features = new ArrayList<SubFeature>();
        // command
        features.add(new Hat());
        features.add(new Invisible());
        features.add(new ItemSign());
        features.add(new LockChange());
        features.add(new PVP());
        features.add(new Rule());
        features.add(new Suicide());
        features.add(new Roll());
        features.add(new Takeoff());
        // entity
        features.add(new EntityBreed());
        features.add(new EntitySpawn());
        features.add(new ProtectVillage());
        // feature
        features.add(new BeeCount());
        features.add(new DisableNetheriteBreak());
        features.add(new NetheriteEquipment());
        features.add(new SpawnerChange());
        features.add(new StrippedLog());
        features.add(new TurtleCount());
        features.add(new UHCPresent());
        features.add(new WitherDispense());
        // player
        features.add(new PlayerDeath());
    }

    @Override
    public void onDisable() {
        for (SubFeature subFeature : features) {
            subFeature.onDisable();
        }
    }
}
