package me.miunapa.paserverfeature.entity;

import org.bukkit.entity.Villager;
import org.bukkit.entity.Zombie;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import me.miunapa.paserverfeature.FeatureStart;

public class ProtectVillage extends FeatureStart implements Listener {
    @EventHandler
    public void onHit(EntityDamageByEntityEvent event) {
        if (config.contains("ProtectVillage.zombie")) {
            if (config.getBoolean("ProtectVillage.zombie")) {
                if (event.getDamager() instanceof Zombie && event.getEntity() instanceof Villager) {
                    event.setCancelled(true);
                }
            }
        }
    }

    public ProtectVillage() {
        pm.registerEvents(this, plugin);
    }
}
