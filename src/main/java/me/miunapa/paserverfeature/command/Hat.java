package me.miunapa.paserverfeature.command;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;
import me.miunapa.paserverfeature.SubFeature;
import net.md_5.bungee.api.ChatColor;
import org.bukkit.Material;
import org.bukkit.Tag;
import org.bukkit.Bukkit;

public class Hat extends SubFeature implements CommandExecutor {
    public Hat() {
        super("Hat");
        Bukkit.getPluginCommand("hat").setExecutor(this);
    }

    public void onDisable() {

    }

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
            if (Tag.SHULKER_BOXES.isTagged(hand.getType())) {
                player.sendMessage(ChatColor.RED + "請特別注意 不要嘗試直接右鍵打開頭上的界伏盒 有可能會消失(不補償)");
            }
            inv.setHelmet(hand);
            inv.setItemInMainHand(hat);
            player.updateInventory();
            player.sendActionBar(ChatColor.GREEN + "帽子穿好囉~");
        }
        return true;
    }
}
