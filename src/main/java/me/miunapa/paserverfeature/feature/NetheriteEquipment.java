package me.miunapa.paserverfeature.feature;

import java.util.ArrayList;
import java.util.List;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryOpenEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.Recipe;
import org.bukkit.inventory.ShapedRecipe;
import me.miunapa.paserverfeature.SubFeature;
import net.md_5.bungee.api.ChatColor;

public class NetheriteEquipment extends SubFeature implements Listener {
    List<NamespacedKey> recipes = new ArrayList<NamespacedKey>();

    public NetheriteEquipment() {
        super("NetheriteEquipment");
        if (!config.getBoolean("NetheriteEquipment")) {
            addRecipe();
            pm.registerEvents(this, plugin);
        }
    }

    public void onDisable() {
        for (NamespacedKey recipe : recipes) {
            Bukkit.removeRecipe(recipe);
        }
    }

    @EventHandler
    public void onInventoryOpenEvent(InventoryOpenEvent event) {
        if (event.getInventory().getType() == InventoryType.SMITHING) {
            Player player = (Player) event.getPlayer();
            if (player.getGameMode() != GameMode.CREATIVE) {
                player.sendActionBar(ChatColor.RED + "伺服器關閉了獄髓裝備升級 請使用獄髓碇直接在工作台上合成裝備 (與一般裝備相同合成表)");
                event.setCancelled(true);
            }
        }
    }

    void addRecipe() {
        Material ingot = Material.NETHERITE_INGOT;
        Material stick = Material.STICK;
        // 頭盔 helmet
        ItemStack helmet = new ItemStack(Material.NETHERITE_HELMET);
        NamespacedKey helmetKey = new NamespacedKey(plugin, "pasf_netherite_helmet");
        ShapedRecipe helmetRecipe = new ShapedRecipe(helmetKey, helmet);
        helmetRecipe.shape("NNN", "N N");
        helmetRecipe.setIngredient('N', ingot);
        Bukkit.addRecipe((Recipe) helmetRecipe);
        recipes.add(helmetKey);
        // 胸甲 chestplate
        ItemStack chestplate = new ItemStack(Material.NETHERITE_CHESTPLATE);
        NamespacedKey chestplateKey = new NamespacedKey(plugin, "pasf_netherite_chestplate");
        ShapedRecipe chestplateRecipe = new ShapedRecipe(chestplateKey, chestplate);
        chestplateRecipe.shape("N N", "NNN", "NNN");
        chestplateRecipe.setIngredient('N', ingot);
        Bukkit.addRecipe((Recipe) chestplateRecipe);
        recipes.add(chestplateKey);
        // 護腿 leggings
        ItemStack leggings = new ItemStack(Material.NETHERITE_LEGGINGS);
        NamespacedKey leggingsKey = new NamespacedKey(plugin, "pasf_netherite_leggings");
        ShapedRecipe leggingsRecipe = new ShapedRecipe(leggingsKey, leggings);
        leggingsRecipe.shape("NNN", "N N", "N N");
        leggingsRecipe.setIngredient('N', ingot);
        Bukkit.addRecipe((Recipe) leggingsRecipe);
        recipes.add(leggingsKey);
        // 靴子 boots
        ItemStack boots = new ItemStack(Material.NETHERITE_BOOTS);
        NamespacedKey bootsKey = new NamespacedKey(plugin, "pasf_netherite_boots");
        ShapedRecipe bootsRecipe = new ShapedRecipe(bootsKey, boots);
        bootsRecipe.shape("N N", "N N");
        bootsRecipe.setIngredient('N', ingot);
        Bukkit.addRecipe((Recipe) bootsRecipe);
        recipes.add(bootsKey);
        // 劍 sword
        ItemStack sword = new ItemStack(Material.NETHERITE_SWORD);
        NamespacedKey swordKey = new NamespacedKey(plugin, "pasf_netherite_sword");
        ShapedRecipe swordRecipe = new ShapedRecipe(swordKey, sword);
        swordRecipe.shape("N", "N", "S");
        swordRecipe.setIngredient('N', ingot);
        swordRecipe.setIngredient('S', stick);
        Bukkit.addRecipe((Recipe) swordRecipe);
        recipes.add(swordKey);
        // 鏟 shovel
        ItemStack shovel = new ItemStack(Material.NETHERITE_SHOVEL);
        NamespacedKey shovelKey = new NamespacedKey(plugin, "pasf_netherite_shovel");
        ShapedRecipe shovelRecipe = new ShapedRecipe(shovelKey, shovel);
        shovelRecipe.shape("N", "S", "S");
        shovelRecipe.setIngredient('N', ingot);
        shovelRecipe.setIngredient('S', stick);
        Bukkit.addRecipe((Recipe) shovelRecipe);
        recipes.add(shovelKey);
        // 鎬 pickaxe
        ItemStack pickaxe = new ItemStack(Material.NETHERITE_PICKAXE);
        NamespacedKey pickaxeKey = new NamespacedKey(plugin, "pasf_netherite_pickaxe");
        ShapedRecipe pickaxeRecipe = new ShapedRecipe(pickaxeKey, pickaxe);
        pickaxeRecipe.shape("NNN", " S ", " S ");
        pickaxeRecipe.setIngredient('N', ingot);
        pickaxeRecipe.setIngredient('S', stick);
        Bukkit.addRecipe((Recipe) pickaxeRecipe);
        recipes.add(pickaxeKey);
        // 斧 axe
        ItemStack axe = new ItemStack(Material.NETHERITE_AXE);
        NamespacedKey axeKey = new NamespacedKey(plugin, "pasf_netherite_axe");
        ShapedRecipe axeRecipe = new ShapedRecipe(axeKey, axe);
        axeRecipe.shape("NN", "NS", " S");
        axeRecipe.setIngredient('N', ingot);
        axeRecipe.setIngredient('S', stick);
        Bukkit.addRecipe((Recipe) axeRecipe);
        recipes.add(axeKey);
        // 鋤 hoe
        ItemStack hoe = new ItemStack(Material.NETHERITE_HOE);
        NamespacedKey hoeKey = new NamespacedKey(plugin, "pasf_netherite_hoe");
        ShapedRecipe hoeRecipe = new ShapedRecipe(hoeKey, hoe);
        hoeRecipe.shape("NN", " S", " S");
        hoeRecipe.setIngredient('N', ingot);
        hoeRecipe.setIngredient('S', stick);
        Bukkit.addRecipe((Recipe) hoeRecipe);
        recipes.add(hoeKey);
    }
}
