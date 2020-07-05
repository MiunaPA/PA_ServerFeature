package me.miunapa.paserverfeature.entity;

import org.bukkit.Bukkit;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.CreatureSpawnEvent;
import org.bukkit.event.entity.CreatureSpawnEvent.SpawnReason;
import me.miunapa.paserverfeature.FeatureStart;
import net.md_5.bungee.api.ChatColor;

public class EntitySpawn extends FeatureStart implements Listener {
    Integer pigCount = 0;
    Integer ironGolemCount = 0;

    @EventHandler
    public void EntitySpawnEvent(CreatureSpawnEvent event) {
        if (event.getEntityType().equals(EntityType.IRON_GOLEM)) {
            Double random = Math.random();
            Double chance = config.getDouble("EntitySpawn.IronGlorm");
            if (random > chance && event.getSpawnReason() == SpawnReason.VILLAGE_DEFENSE) {
                event.setCancelled(true);
            } else {
                ironGolemCount += 1;
            }
            if (ironGolemCount >= config.getInt("EntitySpawn.IronGlorm_Count")) {
                ironGolemCount = 0;
                Integer clearCount = 0;
                for (Entity e : Bukkit.getWorld("world").getEntities()) {
                    if (e.getType() == EntityType.IRON_GOLEM) {
                        if (e.getCustomName() == null) {
                            LivingEntity entity = (LivingEntity) e;
                            entity.setHealth(0.0);
                            clearCount += 1;
                        }
                    } else if (e.getType() == EntityType.SNOWBALL) {
                        e.remove();
                    }
                }
                for (Player player : Bukkit.getServer().getOnlinePlayers()) {
                    player.sendActionBar(ChatColor.RED + "已自動清潔鐵巨人 : " + ChatColor.GREEN
                            + clearCount.toString());
                }
            }
        } else if (event.getEntityType().equals(EntityType.ZOMBIFIED_PIGLIN)) {
            Double random = Math.random();
            Double chance = config.getDouble("EntitySpawn.ZombifiedPiglin");
            if (random > chance && event.getSpawnReason() == SpawnReason.NETHER_PORTAL) {
                event.setCancelled(true);
            } else {
                pigCount += 1;
            }
            if (pigCount >= config.getInt("EntitySpawn.ZombifiedPiglin_Count")) {
                pigCount = 0;
                Integer clearCount = 0;
                for (Entity e : Bukkit.getWorld("world").getEntities()) {
                    if (e.getType() == EntityType.ZOMBIFIED_PIGLIN) {
                        if (e.getCustomName() == null) {
                            LivingEntity entity = (LivingEntity) e;
                            entity.setHealth(0.0);
                            clearCount += 1;
                        }
                    } else if (e.getType() == EntityType.SNOWBALL) {
                        e.remove();
                    }
                }
                for (Player player : Bukkit.getServer().getOnlinePlayers()) {
                    player.sendActionBar(
                            ChatColor.RED + "已自動清潔豬人 : " + ChatColor.GREEN + clearCount.toString());
                }
            }
        }
    }

    public EntitySpawn() {
        pm.registerEvents(this, plugin);
    }
}
