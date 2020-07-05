package me.miunapa.paserverfeature.player;

import java.util.ArrayList;
import java.util.List;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerLoginEvent;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import me.miunapa.paserverfeature.FeatureStart;
import net.md_5.bungee.api.ChatColor;

public class NewPlayer extends FeatureStart implements Listener {

    @EventHandler
    public void onPlayerLoginEvent(PlayerLoginEvent event) {
        Player player = event.getPlayer();
        if (!player.hasPlayedBefore()) {
            if (config.getBoolean("NewPlayer.paper")) {
                getNewPlayerPaper(player);
            }
        }
    }

    public NewPlayer() {
        pm.registerEvents(this, plugin);
    }

    void getNewPlayerPaper(Player player) {
        ItemStack item = new ItemStack(Material.PAPER);
        ItemMeta itemMeta = item.getItemMeta();
        List<String> lore = new ArrayList<String>();
        itemMeta.setDisplayName(ChatColor.GREEN + "歡迎 " + ChatColor.RED + player.getName()
                + ChatColor.GREEN + " 加入 " + ChatColor.AQUA + "來自風平浪靜的伺服器");
        lore.add(ChatColor.YELLOW + "請先輸入 " + ChatColor.RED + "/rules");
        lore.add(ChatColor.GOLD + "查看伺服器新手教學喔");
        lore.add(ChatColor.LIGHT_PURPLE + "新玩家一定要看!!");
        itemMeta.setLore(lore);
        itemMeta.addEnchant(Enchantment.ARROW_DAMAGE, 1, true);
        itemMeta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
        item.setItemMeta(itemMeta);
        player.getInventory().addItem(item);
    }
}
