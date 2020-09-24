package me.miunapa.paserverfeature.entity;

import org.bukkit.entity.Villager;
import org.bukkit.entity.Zombie;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityTransformEvent;
import org.bukkit.event.entity.EntityTransformEvent.TransformReason;
import me.miunapa.paserverfeature.SubFeature;

public class ProtectVillage extends SubFeature implements Listener {
    public ProtectVillage() {
        super("ProtectVillage");
        pm.registerEvents(this, plugin);
    }

    public void onDisable() {

    }

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

    @EventHandler
    public void onEntityTransformEvent(EntityTransformEvent event) {
        if (config.contains("ProtectVillage.lightning")) {
            if (config.getBoolean("ProtectVillage.lightning")) {
                if (event.getEntity() instanceof Villager) {
                    if (event.getTransformReason() == TransformReason.LIGHTNING) {
                        event.setCancelled(true);
                    }
                }
            }
        }
    }
}
