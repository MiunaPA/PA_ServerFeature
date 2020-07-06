package me.miunapa.paserverfeature.entity;

import org.bukkit.entity.EntityType;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityBreedEvent;
import me.miunapa.paserverfeature.SubFeature;

public class EntityBreed extends SubFeature implements Listener {
    public EntityBreed() {
        super("EntityBreed");
        pm.registerEvents(this, plugin);
    }

    public void onDisable() {

    }

    @EventHandler
    public void EntitySpawnEvent(EntityBreedEvent event) {
        if (!config.getBoolean("EntityBreed.Villager")) {
            if (event.getEntityType() == EntityType.VILLAGER) {
                event.setCancelled(true);
            }
        }
    }
}
