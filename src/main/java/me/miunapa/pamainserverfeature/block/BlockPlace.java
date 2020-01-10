package me.miunapa.pamainserverfeature.block;

import me.miunapa.pamainserverfeature.FeatureStart;
import java.util.ArrayList;
import java.util.List;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockPlaceEvent;
import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.ChatMessageType;
import net.md_5.bungee.api.chat.BaseComponent;
import net.md_5.bungee.api.chat.TextComponent;

public class BlockPlace extends FeatureStart implements Listener {
    List<Material> trippedLogs = new ArrayList<Material>();
    List<Material> axes = new ArrayList<Material>();

    @EventHandler
    public void onTrippedLog(BlockPlaceEvent event) {
        if (!config.getBoolean("TrippedLog")) {
            if (trippedLogs.contains(event.getBlock().getType())) {
                Player player = event.getPlayer();
                Material playerTool = player.getInventory().getItemInMainHand().getType();
                if (axes.contains(playerTool)) {
                    BaseComponent[] baseComponent = TextComponent.fromLegacyText(ChatColor.GOLD
                            + "若要取得剝皮原木" + ChatColor.WHITE + " 請將原木or木塊 排成同壓力版合成方式 取得");
                    player.spigot().sendMessage(ChatMessageType.ACTION_BAR, baseComponent);
                    event.setCancelled(true);
                }
            }
        }
    }

    public void trippedLogListInit() {
        trippedLogs.add(Material.STRIPPED_ACACIA_LOG);
        trippedLogs.add(Material.STRIPPED_ACACIA_WOOD);
        trippedLogs.add(Material.STRIPPED_BIRCH_LOG);
        trippedLogs.add(Material.STRIPPED_BIRCH_WOOD);
        trippedLogs.add(Material.STRIPPED_DARK_OAK_LOG);
        trippedLogs.add(Material.STRIPPED_DARK_OAK_WOOD);
        trippedLogs.add(Material.STRIPPED_JUNGLE_LOG);
        trippedLogs.add(Material.STRIPPED_JUNGLE_WOOD);
        trippedLogs.add(Material.STRIPPED_OAK_LOG);
        trippedLogs.add(Material.STRIPPED_OAK_WOOD);
        trippedLogs.add(Material.STRIPPED_SPRUCE_LOG);
        trippedLogs.add(Material.STRIPPED_SPRUCE_WOOD);
        axes.add(Material.WOODEN_AXE);
        axes.add(Material.STONE_AXE);
        axes.add(Material.IRON_AXE);
        axes.add(Material.GOLDEN_AXE);
        axes.add(Material.DIAMOND_AXE);
    }

    public BlockPlace() {
        plugin.getConfig().addDefault("TrippedLog", false);
        plugin.getConfig().options().copyDefaults(true);
        trippedLogListInit();
        pm.registerEvents(this, plugin);
    }
}
