package me.miunapa.paserverfeature.feature;

import java.util.Map;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import me.miunapa.paserverfeature.SubFeature;
import net.md_5.bungee.api.ChatColor;

public class UHCPresent extends SubFeature implements Listener {
    public UHCPresent() {
        super("UHCPresent");
        pm.registerEvents(this, plugin);
    }

    public void onDisable() {

    }

    @EventHandler
    public void click(PlayerInteractEvent event) {
        Player player = event.getPlayer();
        ItemStack item = player.getInventory().getItemInMainHand();
        if (item.getType().equals(Material.PUMPKIN_PIE)) {
            Map<Enchantment, Integer> enchMap = item.getEnchantments();
            if (enchMap.containsKey(Enchantment.LOOT_BONUS_BLOCKS)) {
                if (enchMap.get(Enchantment.LOOT_BONUS_BLOCKS) == 87) {
                    item.removeEnchantment(Enchantment.LOOT_BONUS_BLOCKS);
                    item.addUnsafeEnchantment(Enchantment.LUCK, 87);
                    event.getPlayer().sendActionBar(ChatColor.RED + "替換獎品");
                }
            }
        }
    }

}
