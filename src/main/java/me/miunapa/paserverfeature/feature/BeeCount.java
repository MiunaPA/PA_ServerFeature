package me.miunapa.paserverfeature.feature;

import org.bukkit.Material;
import org.bukkit.block.Beehive;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import me.miunapa.paserverfeature.SubFeature;
import net.md_5.bungee.api.ChatColor;

public class BeeCount extends SubFeature implements Listener {
    public BeeCount() {
        super("BeeCount");
        pm.registerEvents(this, plugin);
    }

    public void onDisable() {

    }

    @EventHandler
    public void click(PlayerInteractEvent event) {
        Player player = event.getPlayer();
        Action action = event.getAction();
        if (action == Action.RIGHT_CLICK_BLOCK) {
            ItemStack item = player.getInventory().getItemInMainHand();
            if (item.getType().equals(Material.BONE)) {
                Block block = event.getClickedBlock();
                if (block.getType().equals(Material.BEEHIVE)
                        || block.getType().equals(Material.BEE_NEST)) {
                    Beehive beehive = (Beehive) (block.getState());
                    player.sendActionBar(ChatColor.GOLD + "這個蜂箱裡面有 " + ChatColor.YELLOW
                            + beehive.getEntityCount() + ChatColor.GOLD + " 隻蜜蜂" + ChatColor.RED
                            + "(x:" + beehive.getX() + " y:" + beehive.getY() + " z:"
                            + beehive.getZ() + ")");
                }
            }
        }
    }
}
