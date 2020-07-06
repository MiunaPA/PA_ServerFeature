package me.miunapa.paserverfeature.feature;

import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockDispenseEvent;
import me.miunapa.paserverfeature.SubFeature;

public class WitherDispense extends SubFeature implements Listener {
    public WitherDispense() {
        super("WitherDispense");
        pm.registerEvents(this, plugin);
    }

    public void onDisable() {

    }

    @EventHandler
    public void click(BlockDispenseEvent event) {
        if (!config.getBoolean("Dispense.WitherSkull")) {
            if (event.getItem().getType() == Material.WITHER_SKELETON_SKULL) {
                event.setCancelled(true);
            }
        }
    }
}
