package me.miunapa.paserverfeature.command;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;
import net.md_5.bungee.api.ChatColor;
import org.bukkit.Material;
import java.util.ArrayList;
import java.util.List;
import org.bukkit.Bukkit;

public class Hat implements CommandExecutor {
    List<Material> shulkerBox = new ArrayList<Material>();

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (sender instanceof Player) {
            Player player = (Player) sender;
            PlayerInventory inv = player.getInventory();
            ItemStack hand = inv.getItemInMainHand();
            ItemStack hat = inv.getHelmet();
            if (hand.getType() == Material.AIR) {
                player.sendActionBar(ChatColor.GRAY + "你手上沒有東西...");
                return true;
            }
            if (hand.getAmount() != 1) {
                player.sendActionBar(ChatColor.RED + "你手上物品數量超過一個 無法穿上");
                return true;
            }
            if (shulkerBox.contains(hand.getType())) {
                player.sendMessage(ChatColor.RED + "請特別注意 不要嘗試直接右鍵打開頭上的界伏盒 有可能會消失(不補償)");
            }
            inv.setHelmet(hand);
            inv.setItemInMainHand(hat);
            player.updateInventory();
            player.sendActionBar(ChatColor.GREEN + "帽子穿好囉~");
        }
        return true;
    }

    public Hat() {
        shulkerBox.add(Material.SHULKER_BOX);
        shulkerBox.add(Material.WHITE_SHULKER_BOX);
        shulkerBox.add(Material.ORANGE_SHULKER_BOX);
        shulkerBox.add(Material.MAGENTA_SHULKER_BOX);
        shulkerBox.add(Material.LIGHT_BLUE_SHULKER_BOX);
        shulkerBox.add(Material.YELLOW_SHULKER_BOX);
        shulkerBox.add(Material.LIME_SHULKER_BOX);
        shulkerBox.add(Material.PINK_SHULKER_BOX);
        shulkerBox.add(Material.GRAY_SHULKER_BOX);
        shulkerBox.add(Material.LIGHT_GRAY_SHULKER_BOX);
        shulkerBox.add(Material.CYAN_SHULKER_BOX);
        shulkerBox.add(Material.PURPLE_SHULKER_BOX);
        shulkerBox.add(Material.BLUE_SHULKER_BOX);
        shulkerBox.add(Material.BROWN_SHULKER_BOX);
        shulkerBox.add(Material.GREEN_SHULKER_BOX);
        shulkerBox.add(Material.RED_SHULKER_BOX);
        shulkerBox.add(Material.BLACK_SHULKER_BOX);
        Bukkit.getPluginCommand("hat").setExecutor(this);
    }
}
