package me.miunapa.paserverfeature.feature;

import java.util.ArrayList;
import java.util.List;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import me.miunapa.paserverfeature.SubFeature;
import net.md_5.bungee.api.ChatColor;

public class DisableNetheriteBreak extends SubFeature implements Listener {

    List<Location> placeList = new ArrayList<Location>();

    public DisableNetheriteBreak() {
        super("DisableNetheriteBreak");
        if (config.getBoolean("DisableNetheriteBreak")) {
            pm.registerEvents(this, plugin);
        }
    }

    public void onDisable() {

    }

    @EventHandler
    public void onBlockBreakEvent(BlockBreakEvent event) {
        Location loc = event.getBlock().getLocation();
        if (placeList.contains(loc)) {
            event.getPlayer().sendMessage(ChatColor.RED + "此位置被挖掘了 將無法再次挖掘遠古遺骸 " + ChatColor.GRAY
                    + "x:" + loc.getBlockX() + " y:" + loc.getBlockY() + " z:" + loc.getBlockZ());
            placeList.remove(loc);
            return;
        }
        if (event.getBlock().getWorld().getName().equals("world_nether")
                && event.getBlock().getType() == Material.ANCIENT_DEBRIS
                && event.getPlayer().getGameMode() == GameMode.SURVIVAL) {
            event.getPlayer().sendMessage(ChatColor.RED + "本伺服的地獄不可挖掘遠古遺骸! 請至資源服挖掘");
            event.setDropItems(false);
        }
    }

    @EventHandler
    public void onBlockPlaceEvent(BlockPlaceEvent event) {
        if (event.getBlock().getWorld().getName().equals("world_nether")
                && event.getBlock().getType() == Material.ANCIENT_DEBRIS
                && event.getPlayer().getGameMode() == GameMode.SURVIVAL) {
            Location loc = event.getBlock().getLocation();
            placeList.add(loc);
            event.getPlayer().sendMessage(ChatColor.RED + "本位置暫時可挖掘遠古遺骸 " + ChatColor.GRAY + "x:"
                    + loc.getBlockX() + " y:" + loc.getBlockY() + " z:" + loc.getBlockZ());
            event.setCancelled(true);
        }
    }
}
