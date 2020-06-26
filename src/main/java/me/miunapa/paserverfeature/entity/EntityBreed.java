package me.miunapa.paserverfeature.entity;

import org.bukkit.entity.EntityType;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityBreedEvent;
import me.miunapa.paserverfeature.FeatureStart;

public class EntityBreed extends FeatureStart implements Listener {
    @EventHandler
    public void EntitySpawnEvent(EntityBreedEvent event) {
        if (!config.getBoolean("EntityBreed.Villager")) {
            if (event.getEntityType() == EntityType.VILLAGER) {
                event.setCancelled(true);
            }
        }
    }

    public EntityBreed() {
        pm.registerEvents(this, plugin);
    }
}
