package me.miunapa.paserverfeature.feature;

import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import me.miunapa.paserverfeature.SubFeature;
import net.md_5.bungee.api.ChatColor;

public class DisableNetheriteBreak extends SubFeature implements Listener {

    public DisableNetheriteBreak() {
        super("DisableNetheriteBreak");
        if (config.getBoolean("DisableNetheriteBreak")) {
            pm.registerEvents(this, plugin);
        }
    }

    public void onDisable() {

    }

    @EventHandler
    public void onInventoryOpenEvent(BlockBreakEvent event) {
        if (event.getBlock().getWorld().getName().equals("world_nether")
                || event.getBlock().getType() == Material.ANCIENT_DEBRIS) {
            event.getPlayer().sendMessage(ChatColor.RED + "本伺服的地獄不可挖掘遠古遺骸! 請至資源服挖掘");
            event.setDropItems(false);
        }
    }
}
