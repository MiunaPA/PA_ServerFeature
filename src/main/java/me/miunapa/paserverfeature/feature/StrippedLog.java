package me.miunapa.paserverfeature.feature;

import me.miunapa.paserverfeature.FeatureStart;
import java.util.ArrayList;
import java.util.List;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.Recipe;
import org.bukkit.inventory.ShapedRecipe;
import net.md_5.bungee.api.ChatColor;

public class StrippedLog extends FeatureStart implements Listener {
    List<Wood> logs = new ArrayList<Wood>();
    List<Material> axes = new ArrayList<Material>();

    @EventHandler
    public void onTrippedLog(BlockPlaceEvent event) {
        Player player = event.getPlayer();
        Material playerTool = player.getInventory().getItemInMainHand().getType();
        if (axes.contains(playerTool)) {
            if (isStrippedLog(event.getBlock().getType())) {
                player.sendActionBar(
                        ChatColor.GOLD + "若要取得剝皮原木" + ChatColor.WHITE + " 請將原木or木塊 排成同壓力版合成方式 來取得");
                event.setCancelled(true);
            }
        }
    }

    void logList() {
        logs.add(new Wood("oak", Material.OAK_LOG, Material.OAK_WOOD, Material.STRIPPED_OAK_LOG,
                Material.STRIPPED_OAK_WOOD)); // 橡木
        logs.add(new Wood("spruce", Material.SPRUCE_LOG, Material.SPRUCE_WOOD,
                Material.STRIPPED_SPRUCE_LOG, Material.STRIPPED_SPRUCE_WOOD)); // 杉木
        logs.add(new Wood("birch", Material.BIRCH_LOG, Material.BIRCH_WOOD,
                Material.STRIPPED_BIRCH_LOG, Material.STRIPPED_BIRCH_WOOD)); // 樺木
        logs.add(new Wood("jungle", Material.JUNGLE_LOG, Material.JUNGLE_WOOD,
                Material.STRIPPED_JUNGLE_LOG, Material.STRIPPED_JUNGLE_WOOD)); // 叢林木
        logs.add(new Wood("acacia", Material.ACACIA_LOG, Material.ACACIA_WOOD,
                Material.STRIPPED_ACACIA_LOG, Material.STRIPPED_ACACIA_WOOD)); // 相思木
        logs.add(new Wood("dark_oak", Material.DARK_OAK_LOG, Material.DARK_OAK_WOOD,
                Material.STRIPPED_DARK_OAK_LOG, Material.STRIPPED_DARK_OAK_WOOD)); // 黑橡木
        logs.add(new Wood("crimson", Material.CRIMSON_STEM, Material.CRIMSON_HYPHAE,
                Material.STRIPPED_CRIMSON_STEM, Material.STRIPPED_CRIMSON_HYPHAE)); // 緋紅蕈柄
        logs.add(new Wood("warped", Material.WARPED_STEM, Material.WARPED_HYPHAE,
                Material.STRIPPED_WARPED_STEM, Material.STRIPPED_WARPED_HYPHAE)); // 緋紅蕈柄
    }

    void axeList() {
        axes.add(Material.WOODEN_AXE);
        axes.add(Material.STONE_AXE);
        axes.add(Material.IRON_AXE);
        axes.add(Material.GOLDEN_AXE);
        axes.add(Material.DIAMOND_AXE);
        axes.add(Material.NETHERITE_AXE); // 獄髓斧
    }

    boolean isStrippedLog(Material block) {
        for (Wood wood : logs) {
            if (block == wood.getStrippedLog() || block == wood.getStrippedWood()) {
                return true;
            }
        }
        return false;
    }

    void logRecipe() {
        for (Wood wood : logs) {
            // 剝皮原木
            ItemStack strippedLog = new ItemStack(wood.getStrippedLog());
            NamespacedKey strippedLogKey = new NamespacedKey(plugin, "pasf_log_" + wood.getName());
            ShapedRecipe strippedLogRecipe = new ShapedRecipe(strippedLogKey, strippedLog);
            strippedLogRecipe.shape(new String[] {"NN"});
            strippedLogRecipe.setIngredient('N', wood.getLog());
            Bukkit.addRecipe((Recipe) strippedLogRecipe);
            // 剝皮木塊
            ItemStack strippedWood = new ItemStack(wood.getStrippedWood());
            NamespacedKey strippedWoodKey =
                    new NamespacedKey(plugin, "pasf_wood_" + wood.getName());
            ShapedRecipe strippedWoodRecipe = new ShapedRecipe(strippedWoodKey, strippedWood);
            strippedWoodRecipe.shape(new String[] {"NN"});
            strippedWoodRecipe.setIngredient('N', wood.getWood());
            Bukkit.addRecipe((Recipe) strippedWoodRecipe);
        }
    }

    public StrippedLog() {
        if (!config.getBoolean("StrippedLog")) {
            logList();
            axeList();
            logRecipe();
            pm.registerEvents(this, plugin);
        }
    }

    class Wood {
        String name;
        Material log; // 一般的原木
        Material wood; // 木塊
        Material strippedLog; // 剝皮原木
        Material strippedWood; // 剝皮木塊

        Wood(String name, Material log, Material wood, Material strippedLog,
                Material strippedWood) {
            this.name = name;
            this.log = log;
            this.wood = wood;
            this.strippedLog = strippedLog;
            this.strippedWood = strippedWood;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public Material getLog() {
            return log;
        }

        public void setLog(Material log) {
            this.log = log;
        }

        public Material getWood() {
            return wood;
        }

        public void setWood(Material wood) {
            this.wood = wood;
        }

        public Material getStrippedLog() {
            return strippedLog;
        }

        public void setStrippedLog(Material strippedLog) {
            this.strippedLog = strippedLog;
        }

        public Material getStrippedWood() {
            return strippedWood;
        }

        public void setStrippedWood(Material strippedWood) {
            this.strippedWood = strippedWood;
        }
    }
}
