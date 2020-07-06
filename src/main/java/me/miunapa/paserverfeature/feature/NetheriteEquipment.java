package me.miunapa.paserverfeature.feature;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryOpenEvent;
import org.bukkit.event.inventory.InventoryType;
import me.miunapa.paserverfeature.SubFeature;
import net.md_5.bungee.api.ChatColor;

public class NetheriteEquipment extends SubFeature implements Listener {
    public NetheriteEquipment() {
        super("NetheriteEquipment");
        if (!config.getBoolean("NetheriteEquipment")) {
            pm.registerEvents(this, plugin);
        }
    }

    public void onDisable() {

    }

    @EventHandler
    public void onInventoryOpenEvent(InventoryOpenEvent event) {
        if (event.getInventory().getType() == InventoryType.SMITHING) {
            Player player = (Player) event.getPlayer();
            player.sendActionBar(ChatColor.RED + "伺服器關閉了獄髓裝備升級 請使用獄髓碇直接在合成台上合成裝備 (與一般裝備相同合成表)");
            event.setCancelled(true);
        }
    }
}
