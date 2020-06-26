package me.miunapa.paserverfeature.player;

import me.miunapa.paserverfeature.FeatureStart;
import org.bukkit.GameRule;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;

public class PlayerDeath extends FeatureStart implements Listener {

    @EventHandler
    public void onPlayerDeath(PlayerDeathEvent event) {
        Player player = event.getEntity();
        if (config.getBoolean("DeathClearExp") == true
                && player.getWorld().getGameRuleValue(GameRule.KEEP_INVENTORY) == true) {
            Integer level = player.getLevel();
            String worldName = event.getEntity().getLocation().getWorld().getName();
            if (worldName.equals("world")) {
                if (level > 13) {
                    level -= 3;
                    player.setLevel(level);
                    player.sendMessage("§d你死亡了...  §7損失了§e3§7等經驗 剩下§e" + level + "§7等");
                } else if (level <= 13 && level > 10) {
                    Integer less = level - 10;
                    level = 10;
                    player.setLevel(level);
                    player.sendMessage("§d你死亡了...  §7損失了§e" + less + "§7等經驗 剩下§e" + level + "§7等");
                } else {
                    player.sendMessage("§7因為等級未超過10等所以沒有死亡經驗懲罰");
                }
            } else {
                if (level > 20) {
                    level -= 10;
                    player.setLevel(level);
                    player.sendMessage("§d你死亡了...  §7損失了§e10§7等經驗 剩下§e" + level + "§7等");
                } else if (level <= 20 && level > 10) {
                    Integer less = level - 10;
                    level = 10;
                    player.setLevel(level);
                    player.sendMessage("§d你死亡了...  §7損失了§e" + less + "§7等經驗 剩下§e" + level + "§7等");
                } else {
                    player.sendMessage("§7因為等級未超過10等所以沒有死亡經驗懲罰");

                }
            }
        }
    }

    public PlayerDeath() {
        pm.registerEvents(this, plugin);
        plugin.getConfig().addDefault("DeathClearExp", true);
        plugin.getConfig().options().copyDefaults(true);
        plugin.saveConfig();
        plugin.reloadConfig();

    }

    public Double getLevelTotalExp(Integer level, Integer nowExp) {
        Double result = 0.0;
        if (level >= 0 && level <= 16) {
            result = Math.pow(level, 2) + 6 * level;
        } else if (level >= 17 && level <= 31) {
            result = 2.5 * Math.pow(level, 2) - 40.5 * level + 360;
        } else {
            result = 4.5 * Math.pow(level, 2) - 162.5 * level + 2220;
        }
        return result;
    }
}
