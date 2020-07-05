package me.miunapa.paserverfeature.feature;

import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockDispenseEvent;
import me.miunapa.paserverfeature.FeatureStart;

public class WitherDispense extends FeatureStart implements Listener {

    @EventHandler
    public void click(BlockDispenseEvent event) {
        if (!config.getBoolean("Dispense.WitherSkull")) {
            if (event.getItem().getType() == Material.WITHER_SKELETON_SKULL) {
                event.setCancelled(true);
            }
        }

    }

    public WitherDispense() {
        pm.registerEvents(this, plugin);
    }
}
