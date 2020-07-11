package me.miunapa.paserverfeature.item;

import org.bukkit.Material;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Fox;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.inventory.ItemStack;
import me.miunapa.paserverfeature.SubFeature;
import net.md_5.bungee.api.ChatColor;

public class FoxTowel extends SubFeature implements Listener {
    public FoxTowel() {
        super("WhiteFox");
        pm.registerEvents(this, plugin);
    }

    public void onDisable() {

    }

    @EventHandler
    public void clickFox(PlayerInteractEntityEvent event) {
        Player player = event.getPlayer();
        Entity entity = event.getRightClicked();
        if (player.getInventory().getItemInMainHand().getType() == Material.FLOWER_BANNER_PATTERN) {
            ItemStack item = player.getInventory().getItemInMainHand();
            if (item.getItemMeta().getDisplayName().equals("§a狐狸包巾")) {
                if (entity.getType() == EntityType.FOX) {
                    Fox fox = (Fox) entity;
                    if (fox.getFoxType() == org.bukkit.entity.Fox.Type.RED) {
                        fox.setFoxType(org.bukkit.entity.Fox.Type.SNOW);
                        player.sendActionBar(ChatColor.WHITE + "狐狸變成白色的了");
                    } else {
                        fox.setFoxType(org.bukkit.entity.Fox.Type.RED);
                        player.sendActionBar(ChatColor.RED + "狐狸變成紅色的了");
                    }
                    item.setAmount(0);
                }
            }
        }
    }
}
