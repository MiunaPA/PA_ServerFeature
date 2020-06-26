package me.miunapa.paserverfeature.command;

import java.io.File;
import java.io.IOException;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.UUID;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Arrow;
import org.bukkit.entity.Firework;
import org.bukkit.entity.LightningStrike;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.entity.Projectile;
import org.bukkit.entity.ThrownPotion;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.AreaEffectCloudApplyEvent;
import org.bukkit.event.entity.EntityCombustByEntityEvent;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.PotionSplashEvent;
import org.bukkit.event.player.PlayerFishEvent;
import org.bukkit.event.weather.LightningStrikeEvent;
import org.bukkit.metadata.FixedMetadataValue;
import me.miunapa.paserverfeature.FeatureStart;
import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.ChatMessageType;
import net.md_5.bungee.api.chat.TextComponent;

public class PVP extends FeatureStart implements Listener, CommandExecutor {
    HashMap<UUID, Long> pvpCoolDown = new HashMap<UUID, Long>();
    File pvpFile = new File(plugin.getDataFolder(), "pvp_status.yml");
    YamlConfiguration pvp = YamlConfiguration.loadConfiguration(pvpFile);

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (sender instanceof Player) {
            Player player = (Player) sender;
            String playerUUID = player.getUniqueId().toString();
            if (args.length != 0) {
                if (pvp.getBoolean(playerUUID)) {
                    player.sendMessage(ChatColor.GREEN + "你目前的PVP是開的!");
                } else {
                    player.sendMessage(ChatColor.RED + "你目前的PVP是關的!");
                }
                return true;
            }
            if (!player.hasPermission("paserverfeature.pvpbypass")) {
                if (pvpCoolDown.containsKey(player.getUniqueId())) {
                    Long interval =
                            System.currentTimeMillis() - pvpCoolDown.get(player.getUniqueId());
                    if (interval <= config.getInt("PVPCoolDown") * 1000) {
                        Long cd = config.getInt("PVPCoolDown")
                                - (interval - (interval % 1000)) / 1000;
                        player.sendMessage(
                                ChatColor.RED + "PVP切換冷卻中! 請等待 " + ChatColor.YELLOW + cd + " 秒");
                        return true;
                    }
                }
            }
            if (pvp.getBoolean(playerUUID) == true) {
                player.sendMessage(ChatColor.LIGHT_PURPLE + "關閉 PVP!");
                pvp.set(playerUUID, false);
                savePvpFile();
                pvpCoolDown.put(player.getUniqueId(), System.currentTimeMillis());
            } else {
                player.sendMessage(ChatColor.YELLOW + "開啟 PVP!");
                pvp.set(playerUUID, true);
                savePvpFile();
                pvpCoolDown.put(player.getUniqueId(), System.currentTimeMillis());
            }
            savePvpFile();
        } else {
            sender.sendMessage(ChatColor.RED + "只有玩家能使用該指令");
        }
        return true;
    }

    public void savePvpFile() {
        try {
            pvp.save(pvpFile);
            pvp = YamlConfiguration.loadConfiguration(pvpFile);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public boolean pvpDisableDetect(Player damager, Player injured, String typeMessage) {
        String damagerUUID = damager.getUniqueId().toString();
        String injuredUUID = injured.getUniqueId().toString();
        if (damager.hasPermission("paserverfeature.pvpbypass")) {
            damager.spigot().sendMessage(ChatMessageType.ACTION_BAR,
                    TextComponent.fromLegacyText(ChatColor.DARK_RED + "PVP Bypass "
                            + injured.getName() + " (" + ChatColor.GRAY + typeMessage + ")"));
            return false;
        }
        if (!pvp.getBoolean(injuredUUID) && !pvp.getBoolean(damagerUUID)) {
            damager.spigot().sendMessage(ChatMessageType.ACTION_BAR,
                    TextComponent.fromLegacyText(
                            ChatColor.DARK_RED + injured.getName() + " 與你 " + ChatColor.GOLD
                                    + "都關閉了PVP " + ChatColor.GRAY + "(" + typeMessage + ")"));
            return true;
        } else if (!pvp.getBoolean(injuredUUID)) {
            damager.spigot().sendMessage(ChatMessageType.ACTION_BAR,
                    TextComponent
                            .fromLegacyText(ChatColor.DARK_RED + injured.getName() + ChatColor.GOLD
                                    + " 關閉了PVP " + ChatColor.GRAY + "(" + typeMessage + ")"));
            return true;
        } else if (!pvp.getBoolean(damagerUUID)) {
            damager.spigot().sendMessage(ChatMessageType.ACTION_BAR,
                    TextComponent.fromLegacyText(ChatColor.DARK_RED + "你關閉了PVP  " + ChatColor.GRAY
                            + "(" + typeMessage + ") {" + injured.getName() + "}"));
            return true;
        } else {
            return false;
        }
    }

    public boolean pvpDisableDetect(Player injured, String typeMessage) {
        String injuredUUID = injured.getUniqueId().toString();
        if (!pvp.getBoolean(injuredUUID)) {
            return true;
        } else {
            return false;
        }
    }

    @EventHandler(ignoreCancelled = true, priority = EventPriority.LOWEST)
    public void onHit(EntityDamageByEntityEvent event) { // 被打
        if (event.getDamager() instanceof Player && event.getEntity() instanceof Player) {
            event.setCancelled(pvpDisableDetect((Player) event.getDamager(),
                    (Player) event.getEntity(), "直接攻擊"));
        } else if (event.getDamager() instanceof Projectile) {
            Projectile arrow = (Projectile) event.getDamager();
            if (arrow.getShooter() instanceof Player && event.getEntity() instanceof Player) {
                Player shooter = (Player) arrow.getShooter();
                Player injured = (Player) event.getEntity();
                if (shooter.getUniqueId() != injured.getUniqueId()) {
                    event.setCancelled(pvpDisableDetect(shooter, injured, "投射物"));
                }
            }
        } else if (event.getDamager() instanceof ThrownPotion) {
            ThrownPotion potion = (ThrownPotion) event.getDamager();
            if (potion.getShooter() instanceof Player && event.getEntity() instanceof Player) {
                Player shooter = (Player) potion.getShooter();
                Player injured = (Player) event.getEntity();
                if (shooter.getUniqueId() != injured.getUniqueId()) {
                    event.setCancelled(pvpDisableDetect(shooter, injured, "藥水"));
                }
            }
        } else if (event.getDamager() instanceof LightningStrike
                && event.getDamager().getMetadata("TRIDENT").size() >= 1
                && event.getEntity() instanceof Player) {
            event.setCancelled(pvpDisableDetect((Player) event.getEntity(), "三叉戟閃電"));
        } else if (event.getDamager() instanceof Firework && event.getEntity() instanceof Player) {
            event.setCancelled(pvpDisableDetect((Player) event.getEntity(), "煙火"));
        }
    }

    @EventHandler(ignoreCancelled = true, priority = EventPriority.LOWEST) // 被燃燒箭矢射到
    public void onFlameArrow(EntityCombustByEntityEvent event) {
        if (event.getCombuster() instanceof Arrow) {
            Arrow arrow = (Arrow) event.getCombuster();
            if (arrow.getShooter() instanceof Player && event.getEntity() instanceof Player) {
                Player shooter = (Player) arrow.getShooter();
                Player injured = (Player) event.getEntity();
                if (shooter.getUniqueId() != injured.getUniqueId()) {
                    event.setCancelled(pvpDisableDetect(shooter, injured, "燃燒箭矢"));
                }
            }
        }
    }

    @EventHandler(ignoreCancelled = true, priority = EventPriority.LOWEST) // 飛濺藥水
    public void onPotionSplash(PotionSplashEvent event) {
        if (event.getPotion().getShooter() instanceof Player) {
            Player damager = (Player) event.getPotion().getShooter();
            Collection<LivingEntity> affected = event.getAffectedEntities();
            for (LivingEntity injuredEntity : affected) {
                if (injuredEntity instanceof Player) {
                    Player injured = (Player) injuredEntity;
                    if (damager.getUniqueId() != injured.getUniqueId()) {
                        if (pvpDisableDetect(damager, (Player) injured, "飛濺藥水")) {
                            event.setIntensity(injuredEntity, 0);
                        }
                    }
                }
            }
        }
    }

    @EventHandler(ignoreCancelled = true, priority = EventPriority.LOWEST) // 滯留藥水
    public void onCloudEffects(AreaEffectCloudApplyEvent event) {
        if (event.getEntity().getSource() instanceof Player) {
            Player damager = (Player) event.getEntity().getSource();
            Iterator<LivingEntity> it = event.getAffectedEntities().iterator();
            while (it.hasNext()) {
                LivingEntity injuredEntity = it.next();
                if (injuredEntity instanceof Player && injuredEntity != null) {
                    Player injured = (Player) injuredEntity;
                    if (damager.getUniqueId() != injured.getUniqueId()) {
                        if (pvpDisableDetect(damager, (Player) injured, "滯留藥水")) {
                            it.remove();
                        }
                    }
                }
            }
        }
    }

    @EventHandler(ignoreCancelled = true, priority = EventPriority.LOWEST) // 魚竿
    public void onPlayerFishing(PlayerFishEvent event) {
        if (event.getCaught() instanceof Player) {
            Player damager = event.getPlayer();
            Player injured = (Player) event.getCaught();
            if (damager.getInventory().getItemInMainHand().getType() == Material.FISHING_ROD
                    || damager.getInventory().getItemInOffHand()
                            .getType() == Material.FISHING_ROD) {
                event.setCancelled(pvpDisableDetect(damager, injured, "魚竿"));
            }
        }
    }

    @EventHandler(ignoreCancelled = true, priority = EventPriority.LOWEST)
    public void onLightningStrike(LightningStrikeEvent event) {
        if (event.getCause() == LightningStrikeEvent.Cause.TRIDENT) {
            event.getLightning().setMetadata("TRIDENT",
                    new FixedMetadataValue(plugin, event.getLightning().getLocation()));
        }
    }

    public PVP() {
        pm.registerEvents(this, plugin);
        Bukkit.getPluginCommand("pvp").setExecutor(this);
        if (!pvpFile.exists()) {
            savePvpFile();
        }
    }
}
