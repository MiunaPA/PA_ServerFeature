package me.miunapa.paserverfeature.item;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.raid.RaidTriggerEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import me.miunapa.paserverfeature.SubFeature;
import net.md_5.bungee.api.ChatColor;

public class RaidItem extends SubFeature implements Listener {
    public RaidItem() {
        super("RaidItem");
        if (config.getBoolean("RaidItem")) {
            pm.registerEvents(this, plugin);
        }
    }

    public void onDisable() {

    }

    @EventHandler
    public void onRaidTriggerEvent(RaidTriggerEvent event) {
        Player player = event.getPlayer();
        if (player.getInventory().getItemInMainHand().getType() == Material.BRICK) {
            ItemStack item = player.getInventory().getItemInMainHand();
            if (item.getItemMeta().getDisplayName().equals("§a1450呼叫器")) {
                player.sendActionBar(ChatColor.DARK_RED + "你觸發了突襲");
                Block block = event.getRaid().getLocation().getBlock();
                plugin.getLogger()
                        .info(ChatColor.RED + "觸發突襲 " + ChatColor.GREEN + player.getName() + " "
                                + ChatColor.GOLD + block.getX() + " " + block.getY() + " "
                                + block.getZ());
                item.setAmount(item.getAmount() - 1);
                return;
            }
        }
        for (PotionEffect effect : player.getActivePotionEffects()) {
            if (effect.getType().equals(PotionEffectType.BAD_OMEN)) {
                PotionEffect badOmen = new PotionEffect(PotionEffectType.BAD_OMEN,
                        effect.getDuration() - 200, effect.getAmplifier());
                Bukkit.getScheduler().runTaskLater(plugin, new Runnable() {
                    @Override
                    public void run() {
                        player.addPotionEffect(badOmen);
                    }
                }, 200);
                break;
            }
        }
        event.setCancelled(true);
    }
}
