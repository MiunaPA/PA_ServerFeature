package me.miunapa.paserverfeature.entity;

import java.util.ArrayList;
import java.util.List;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Item;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.CreatureSpawnEvent;
import org.bukkit.event.entity.CreatureSpawnEvent.SpawnReason;
import me.miunapa.paserverfeature.SubFeature;
import net.md_5.bungee.api.ChatColor;

public class EntitySpawn extends SubFeature implements Listener, CommandExecutor {
    Integer pigCount = 0;
    Integer ironGolemCount = 0;
    List<Material> cleanItemList = new ArrayList<Material>();

    public EntitySpawn() {
        super("EntitySpawn");
        pm.registerEvents(this, plugin);
        Bukkit.getPluginCommand("es").setExecutor(this);
        cleanItemList.add(Material.BAMBOO); // 竹子
        cleanItemList.add(Material.SUGAR_CANE); // 甘蔗
        cleanItemList.add(Material.MELON_SLICE); // 西瓜片
        cleanItemList.add(Material.PUMPKIN); // 南瓜
        cleanItemList.add(Material.CACTUS); // 仙人掌
        cleanItemList.add(Material.KELP); // 海帶
    }

    public void onDisable() {

    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (args.length == 1) {
            if (args[0].equals("irongolem")) {
                sender.sendMessage(
                        ChatColor.YELLOW + "鐵巨人Count : " + ChatColor.RED + ironGolemCount);
                return true;
            } else if (args[0].equals("pigzombie")) {
                sender.sendMessage(
                        ChatColor.YELLOW + "殭屍豬布林Count : " + ChatColor.RED + ironGolemCount);
                return true;
            }
        }
        sender.sendMessage(ChatColor.RED + "/es <irongolem | pigzombie>");
        return true;
    }

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
                broadcastActionBar("&c已自動清潔鐵巨人 : &a" + clearCount.toString());
            }
        } else if (event.getEntityType().equals(EntityType.ZOMBIFIED_PIGLIN)) {
            Double random = Math.random();
            Double chance = config.getDouble("EntitySpawn.ZombifiedPiglin");
            if (random > chance && event.getSpawnReason() == SpawnReason.NETHER_PORTAL) {
                event.setCancelled(true);
                return;
            }
            pigCount += 1;
            if (!config.getBoolean("EntitySpawn.ZombifiedPiglin_GoldenSword")) {
                if (event.getEntity().getEquipment().getItemInMainHand()
                        .getType() == Material.GOLDEN_SWORD) {
                    event.getEntity().getEquipment().setItemInMainHandDropChance(0f);
                }
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
                    }
                }
                broadcastActionBar("&c已自動清潔殭屍豬布林 : &a" + clearCount.toString());
                if (config.getBoolean("EntitySpawn.CleanItem")) {
                    Bukkit.getScheduler().runTaskLater(plugin, new Runnable() {
                        @Override
                        public void run() {
                            cleanItemProcess();
                            cleanSnowballAndExp();
                        }
                    }, 400);
                }
            }
        }
    }

    void cleanItemProcess() {
        Integer count = 0;
        for (World w : Bukkit.getWorlds()) {
            for (Entity e : w.getEntities()) {
                if (e.getType() == EntityType.DROPPED_ITEM) {
                    Item item = (Item) e;
                    if (cleanItemList.contains(item.getItemStack().getType())) {
                        count += 1;
                    }
                }
            }
        }
        if (count >= 400) {
            broadcastActionBar("&1偵測到掉落物過多!&7(" + count + ") &4即將在 30 秒後清除地板上的指定掉落物", true);
            Bukkit.getScheduler().runTaskLater(plugin, new Runnable() {
                @Override
                public void run() {
                    broadcastActionBar("&4即將在 20 秒後清除地板上的指定掉落物");
                }
            }, 200);
            Bukkit.getScheduler().runTaskLater(plugin, new Runnable() {
                @Override
                public void run() {
                    broadcastActionBar("&4即將在 10 秒後清除地板上的指定掉落物");
                }
            }, 400);
            Bukkit.getScheduler().runTaskLater(plugin, new Runnable() {
                @Override
                public void run() {
                    cleanItem();
                }
            }, 600);
        }
    }

    void cleanItem() {
        Integer cleanCount = 0;
        for (World w : Bukkit.getWorlds()) {
            for (Entity e : w.getEntities()) {
                if (e.getType() == EntityType.DROPPED_ITEM) {
                    Item item = (Item) e;
                    if (cleanItemList.contains(item.getItemStack().getType())) {
                        item.remove();
                        cleanCount += 1;
                    }
                }
            }
        }
        broadcastActionBar("&d已清除指定掉落物 共 &c" + cleanCount + " &d個", true);
    }

    void cleanSnowballAndExp() {
        Integer snowballCount = 0;
        Integer expOrbCount = 0;
        for (World w : Bukkit.getWorlds()) {
            for (Entity e : w.getEntities()) {
                if (e.getType() == EntityType.SNOWBALL) {
                    snowballCount += 1;
                } else if (e.getType() == EntityType.EXPERIENCE_ORB) {
                    expOrbCount += 1;
                }
            }
        }
        if (snowballCount >= 100) {
            for (World w : Bukkit.getWorlds()) {
                for (Entity e : w.getEntities()) {
                    if (e.getType() == EntityType.SNOWBALL) {
                        e.remove();
                    }
                }
            }
        }
        if (expOrbCount >= 100) {
            for (World w : Bukkit.getWorlds()) {
                for (Entity e : w.getEntities()) {
                    if (e.getType() == EntityType.EXPERIENCE_ORB) {
                        e.remove();
                    }
                }
            }
        }
    }

    void broadcastActionBar(String text) {
        for (Player player : Bukkit.getServer().getOnlinePlayers()) {
            player.sendActionBar(ChatColor.translateAlternateColorCodes('&', text));
        }
    }

    void broadcastActionBar(String text, boolean showInConsole) {
        for (Player player : Bukkit.getServer().getOnlinePlayers()) {
            player.sendActionBar(ChatColor.translateAlternateColorCodes('&', text));
        }
        plugin.getLogger().info(ChatColor.translateAlternateColorCodes('&', text));
    }
}
