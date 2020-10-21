package me.miunapa.paserverfeature.feature;

import org.bukkit.Material;
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
                    org.bukkit.block.Beehive beehiveState =
                            (org.bukkit.block.Beehive) (block.getState());
                    org.bukkit.block.data.type.Beehive beehiveData =
                            (org.bukkit.block.data.type.Beehive) block.getBlockData();
                    String honeyText = "";
                    if (beehiveData.getHoneyLevel() == beehiveData.getMaximumHoneyLevel()) {
                        honeyText = "蜂蜜已滿";
                    } else if (beehiveData.getHoneyLevel() == 0) {
                        honeyText = "沒有蜂蜜";
                    } else {
                        honeyText = "蜂蜜量:" + beehiveData.getHoneyLevel() + "/"
                                + beehiveData.getMaximumHoneyLevel();
                    }
                    player.sendActionBar(ChatColor.LIGHT_PURPLE + "蜂箱裡面有 " + ChatColor.RED + "蜜蜂x"
                            + beehiveState.getEntityCount() + "," + honeyText + ChatColor.GRAY
                            + " [x:" + beehiveState.getX() + " y:" + beehiveState.getY() + " z:"
                            + beehiveState.getZ() + "]");
                }
            }
        }
    }
}
