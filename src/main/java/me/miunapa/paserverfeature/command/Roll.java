package me.miunapa.paserverfeature.command;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import me.miunapa.paserverfeature.SubFeature;
import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.chat.ComponentBuilder;
import net.md_5.bungee.api.chat.HoverEvent;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.chat.HoverEvent.Action;
import net.md_5.bungee.api.chat.hover.content.Text;

public class Roll extends SubFeature implements CommandExecutor {
    public Roll() {
        super("Roll");
        Bukkit.getPluginCommand("roll").setExecutor(this);
    }

    public void onDisable() {

    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (sender instanceof Player) {
            Player player = (Player) sender;
            ItemStack item = player.getInventory().getItemInMainHand();
            if (item.getType() != Material.LAPIS_LAZULI) {
                player.sendActionBar(ChatColor.DARK_PURPLE + "" + ChatColor.BOLD + "請手持一個青金石才能使用");
                return true;
            }
            item.setAmount(item.getAmount() - 1);
            int min = 1;
            int max = 6;
            if (args.length != 0) {
                if (args.length == 2) {
                    try {
                        min = Integer.parseInt(args[0]);
                        max = Integer.parseInt(args[1]);
                        if (min >= max) {
                            player.sendActionBar(
                                    ChatColor.DARK_PURPLE + "" + ChatColor.BOLD + "最大值需大於最小值");
                            return true;
                        }
                    } catch (Exception e) {
                        player.sendActionBar(
                                ChatColor.DARK_PURPLE + "" + ChatColor.BOLD + "最小值與最大值 需輸入整數數字才行");
                        return true;
                    }
                } else {
                    player.sendActionBar(ChatColor.DARK_PURPLE + "" + ChatColor.BOLD + "若要指定數值 請輸入 "
                            + ChatColor.RED + "/roll <最小值> <最大值>");
                    return true;
                }
            }
            List<Player> playerList = new ArrayList<Player>();
            for (Player other : Bukkit.getOnlinePlayers()) {
                if (other.getLocation().distance(player.getLocation()) <= 20) {
                    playerList.add(other);
                }
            }
            Integer number = new Random().nextInt(max) + min;
            TextComponent tc = new TextComponent(ChatColor.GREEN + player.getName() + ChatColor.GOLD
                    + " 骰出了 " + ChatColor.LIGHT_PURPLE + number.toString());
            HoverEvent hoverEvent = new HoverEvent(Action.SHOW_TEXT, createContentText(""));
            hoverEvent.addContent(createContentText("範圍 : ", ChatColor.AQUA));
            hoverEvent.addContent(createContentText(min + "-" + max, ChatColor.RED));
            String playerListText = "";
            for (Player p : playerList) {
                playerListText += p.getName() + " ";
            }
            hoverEvent.addContent(createContentText("\n對象 : ", ChatColor.GREEN));
            hoverEvent.addContent(createContentText(playerListText, ChatColor.GRAY));
            hoverEvent.addContent(createContentText(
                    "\n時間 : " + new SimpleDateFormat("yyyy/MM/dd HH:mm:ss").format(new Date()),
                    ChatColor.WHITE));
            tc.setHoverEvent(hoverEvent);
            for (Player p : playerList) {
                p.sendMessage(tc);
            }
        } else {
            sender.sendMessage(ChatColor.RED + "只有玩家可使用此功能");
        }
        return true;
    }

    Text createContentText(String text) {
        return new Text(new ComponentBuilder(text).create());
    }

    Text createContentText(String text, ChatColor color) {
        return new Text(new ComponentBuilder(text).color(color).create());
    }
}
