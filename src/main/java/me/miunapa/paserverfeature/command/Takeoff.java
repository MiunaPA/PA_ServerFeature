package me.miunapa.paserverfeature.command;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;
import me.miunapa.paserverfeature.SubFeature;
import net.md_5.bungee.api.ChatColor;
import org.bukkit.Material;
import java.util.ArrayList;
import java.util.List;
import org.bukkit.Bukkit;

public class Takeoff extends SubFeature implements CommandExecutor, TabCompleter {
    public Takeoff() {
        super("Takeoff");
        Bukkit.getPluginCommand("takeoff").setExecutor(this);
    }

    public void onDisable() {

    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (sender instanceof Player) {
            Player player = (Player) sender;
            if (args.length != 1) {
                player.sendMessage(ChatColor.RED
                        + "脫裝備 指令格式如下 /takeoff <helmet | chestplate | leggings | boots>");
                return true;
            }
            String part = args[0];
            if (part.equalsIgnoreCase("helmet") || part.equalsIgnoreCase("頭")) {
                takeoffEquipment(player, "helmet");
            } else if (part.equalsIgnoreCase("chestplate") || part.equalsIgnoreCase("胸")) {
                takeoffEquipment(player, "chestplate");
            } else if (part.equalsIgnoreCase("leggings") || part.equalsIgnoreCase("褲")) {
                takeoffEquipment(player, "leggings");
            } else if (part.equalsIgnoreCase("boots") || part.equalsIgnoreCase("鞋")) {
                takeoffEquipment(player, "boots");
            } else {
                player.sendMessage(ChatColor.RED
                        + "脫裝備 指令格式如下 /takeoff <helmet | chestplate | leggings | boots>");
            }
        }
        return true;
    }

    void takeoffEquipment(Player player, String part) {
        PlayerInventory inv = player.getInventory();
        ItemStack hand = inv.getItemInMainHand();
        if (hand.getType() != Material.AIR) {
            player.sendActionBar(ChatColor.RED + "你手上不能拿任何東西");
            return;
        }
        ItemStack equip = null;
        switch (part) {
            case "helmet":
                equip = inv.getHelmet();
                break;
            case "chestplate":
                equip = inv.getChestplate();
                break;
            case "leggings":
                equip = inv.getLeggings();
                break;
            case "boots":
                equip = inv.getBoots();
                break;
        }
        if (equip == null) {
            player.sendActionBar(ChatColor.RED + "你要脫下的欄位沒有穿任何東西");
            return;
        }
        switch (part) {
            case "helmet":
                inv.setItemInMainHand(inv.getHelmet());
                inv.setHelmet(hand);
                break;
            case "chestplate":
                inv.setItemInMainHand(inv.getChestplate());
                inv.setChestplate(hand);
                break;
            case "leggings":
                inv.setItemInMainHand(inv.getLeggings());
                inv.setLeggings(hand);
                break;
            case "boots":
                inv.setItemInMainHand(inv.getBoots());
                inv.setBoots(hand);
                break;
        }
        player.updateInventory();
        player.sendActionBar(ChatColor.GREEN + "已脫下裝備");
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String alias,
            String[] args) {
        if (command.getName().equalsIgnoreCase("takeoff") && args.length == 1) {
            List<String> tabList = new ArrayList<String>();
            tabList.add("helmet");
            tabList.add("chestplate");
            tabList.add("leggings");
            tabList.add("boots");
            return tabList;
        }
        return null;
    }
}
