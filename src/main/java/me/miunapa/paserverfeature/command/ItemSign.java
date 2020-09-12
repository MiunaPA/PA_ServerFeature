package me.miunapa.paserverfeature.command;

import java.util.ArrayList;
import java.util.List;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.PrepareItemCraftEvent;
import org.bukkit.inventory.AnvilInventory;
import org.bukkit.inventory.CartographyInventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;
import org.bukkit.inventory.meta.ItemMeta;
import me.miunapa.paserverfeature.SubFeature;
import net.md_5.bungee.api.ChatColor;

public class ItemSign extends SubFeature implements Listener, CommandExecutor, TabCompleter {
    public ItemSign() {
        super("ItemSign");
        pm.registerEvents(this, plugin);
        Bukkit.getPluginCommand("signature").setExecutor(this);
    }

    public void onDisable() {

    }

    @EventHandler
    public void onPrepareItemCraftEvent(PrepareItemCraftEvent event) {
        if (event.getRecipe() != null) {
            ItemStack item = event.getInventory().getResult();
            if (item.getType() == Material.FILLED_MAP) {
                ItemMeta meta = item.getItemMeta();
                if (hasSign(meta)) {
                    item.setAmount(1);
                }
            }
        }
    }

    @EventHandler
    public void onInventoryClickEvent(InventoryClickEvent event) {
        if (event.getClickedInventory() instanceof CartographyInventory) {
            ItemStack item = event.getCurrentItem();
            if (item.getType() == Material.FILLED_MAP) {
                ItemMeta meta = item.getItemMeta();
                Player player = (Player) event.getWhoClicked();
                if (!isOwnSign(meta, player.getName())) {
                    if (event.getSlot() == 2) {
                        player.sendMessage(ChatColor.RED + "你不是地圖署名持有人 無法複製地圖");
                        Bukkit.getScheduler().runTask(plugin, new Runnable() {
                            @Override
                            public void run() {
                                player.closeInventory();
                            }
                        });
                    }
                }

            }
        } else if (event.getClickedInventory() instanceof AnvilInventory) {
            ItemStack item = event.getCurrentItem();
            if (item.getType() != Material.AIR) {
                ItemMeta meta = item.getItemMeta();
                Player player = (Player) event.getWhoClicked();
                if (!isOwnSign(meta, player.getName())) {
                    if (event.getSlot() == 2) {
                        player.sendMessage(ChatColor.RED + "你不是該物品的署名持有人 無法使用鐵砧");
                        player.setLevel(player.getLevel() + 1);
                        Bukkit.getScheduler().runTask(plugin, new Runnable() {
                            @Override
                            public void run() {
                                player.closeInventory();
                            }
                        });
                    }
                }
            }
        }
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (sender instanceof Player) {
            Player player = (Player) sender;
            PlayerInventory inv = player.getInventory();
            ItemStack item = inv.getItemInMainHand();
            if (item.getType() == Material.AIR) {
                player.sendMessage(ChatColor.GRAY + "你手上沒有東西...");
                return true;
            }
            if (args.length != 0) {
                if (args[0].equals("add")) {
                    signAdd(player);
                } else if (args[0].equals("remove")) {
                    signRemove(player);
                } else if (args[0].equals("fremove")) {
                    if (player.hasPermission("paserverfeature.signatureforce") || player.isOp()) {
                        signRemove(player, true);
                    } else {
                        player.sendMessage(ChatColor.RED + "你沒有權限使用強制移除署名");
                    }
                } else {
                    player.sendMessage(ChatColor.LIGHT_PURPLE + "署名指令錯誤! " + ChatColor.GOLD
                            + "為物品署名：" + ChatColor.RED + "/signature add" + ChatColor.GOLD
                            + " 解除署名：" + ChatColor.RED + "/signature remove");
                }
            } else {
                player.sendMessage(ChatColor.GOLD + "為物品署名：" + ChatColor.RED + "/signature add"
                        + ChatColor.GOLD + " 解除署名：" + ChatColor.RED + "/signature remove");
            }
        } else {
            sender.sendMessage(ChatColor.RED + "只有玩家能使用該指令");
        }
        return true;
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String alias,
            String[] args) {
        if (command.getName().equalsIgnoreCase("signature") && args.length == 1) {
            List<String> tabList = new ArrayList<String>();
            tabList.add("add");
            tabList.add("remove");
            return tabList;
        }
        return null;
    }

    void signAdd(Player player) {
        PlayerInventory inv = player.getInventory();
        ItemStack item = inv.getItemInMainHand();
        ItemMeta meta = item.getItemMeta();
        if (hasSign(meta)) {
            player.sendMessage(ChatColor.RED + "本物品已經署名過了!");
            return;
        }
        List<String> lore = meta.getLore();
        lore.add(ChatColor.GRAY + "已署名");
        lore.add(ChatColor.GRAY + player.getName());
        meta.setLore(lore);
        item.setItemMeta(meta);
        player.updateInventory();
        player.sendMessage(ChatColor.GREEN + "已將物品署名! " + ChatColor.RED + player.getName());
    }

    void signRemove(Player player) {
        signRemove(player, false);
    }

    void signRemove(Player player, boolean forceRemove) {
        PlayerInventory inv = player.getInventory();
        ItemStack item = inv.getItemInMainHand();
        ItemMeta meta = item.getItemMeta();
        if (!hasSign(meta)) {
            player.sendMessage(ChatColor.RED + "本物品沒有署名!");
            return;
        }
        if (!isOwnSign(meta, player.getName())) {
            if (forceRemove) {
                player.sendMessage(ChatColor.RED + "強制解除署名模式");
            } else {
                player.sendMessage(ChatColor.RED + "只能由署名的玩家解除署名");
                return;
            }
        }
        List<String> lore = meta.getLore();
        Integer row = lore.size();
        for (int i = 0; i < row - 1; i++) {
            if (ChatColor.stripColor(lore.get(i)).equals("已署名")) {
                lore.remove(i);
                lore.remove(i);
                meta.setLore(lore);
                item.setItemMeta(meta);
                player.updateInventory();
                player.sendMessage(ChatColor.LIGHT_PURPLE + "已將物品解除署名!");
                return;
            }
        }
    }

    boolean isOwnSign(ItemMeta meta, String playerName) {
        if (!hasSign(meta)) {
            return true;
        }
        if (getSignName(meta).equals(playerName)) {
            return true;
        } else {
            return false;
        }
    }

    boolean hasSign(ItemMeta meta) {
        if (meta.hasLore()) {
            List<String> lore = meta.getLore();
            Integer row = lore.size();
            for (int i = 0; i < row - 1; i++) {
                if (ChatColor.stripColor(lore.get(i)).equals("已署名")) {
                    return true;
                }
            }
        }
        return false;
    }

    String getSignName(ItemMeta meta) {
        if (meta.hasLore()) {
            List<String> lore = meta.getLore();
            Integer row = lore.size();
            for (int i = 0; i < row - 1; i++) {
                if (ChatColor.stripColor(lore.get(i)).equals("已署名")) {
                    return ChatColor.stripColor(lore.get(i + 1));
                }
            }
        }
        return null;
    }
}
