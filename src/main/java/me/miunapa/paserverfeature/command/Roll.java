package me.miunapa.paserverfeature.command;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
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
                player.sendActionBar(ChatColor.DARK_RED + "請先手持青金石 才能使用");
                return true;
            }
            int min = 1;
            int max = 6;
            int repeat = 1;
            // 數值判斷
            if (args.length != 0) {
                if (args.length >= 2) {
                    try {
                        min = Integer.parseInt(args[0]);
                        max = Integer.parseInt(args[1]);
                    } catch (NumberFormatException e) {
                        player.sendActionBar(ChatColor.DARK_PURPLE + "最小值與最大值 需輸入整數數字才行");
                        return true;
                    }
                    if (args.length == 3) {
                        try {
                            repeat = Integer.parseInt(args[2]);
                        } catch (Exception e) {
                            player.sendActionBar(ChatColor.DARK_PURPLE + "次數需輸入整數數字才行(1-10)");
                            return true;
                        }
                    }
                } else {
                    player.sendActionBar(ChatColor.DARK_PURPLE + "若要指定數值 請輸入 " + ChatColor.RED
                            + "/roll <最小值> <最大值> [重複次數]");
                    return true;
                }
            }
            if (min >= max) {
                player.sendActionBar(ChatColor.DARK_PURPLE + "最大值需大於最小值");
                return true;
            }
            if (min < 1 || max < 1) {
                player.sendActionBar(ChatColor.DARK_PURPLE + "最大值與最小值需>=1");
                return true;
            }
            if (repeat < 1 || repeat > 10) {
                player.sendActionBar(ChatColor.DARK_PURPLE + "次數只能在1-10次");
                return true;
            }
            if (item.getAmount() < repeat) {
                player.sendActionBar(ChatColor.DARK_RED + "青金石不足 需要 " + ChatColor.YELLOW + repeat
                        + ChatColor.DARK_RED + "個 才能使用");
                return true;
            }
            // 程序開始
            item.setAmount(item.getAmount() - repeat);
            List<Player> playerList = new ArrayList<Player>();
            String playerListText = "";
            for (Player other : Bukkit.getOnlinePlayers()) {
                if (other.getWorld() == player.getWorld()) {
                    if (other.getLocation().distance(player.getLocation()) <= 20
                            && other.getWorld() == player.getWorld()) {
                        playerList.add(other);
                        playerListText += other.getName() + " ";
                    }
                }
            }
            String numberText = "";
            for (int i = 0; i < repeat; i++) {
                Integer number = getRandom(min, max);
                numberText += number.toString() + " ";
            }
            TextComponent tc = new TextComponent(ChatColor.GREEN + player.getName() + ChatColor.GOLD
                    + " 骰出了 " + ChatColor.LIGHT_PURPLE + numberText);
            HoverEvent hoverEvent = new HoverEvent(Action.SHOW_TEXT, createContentText(""));
            hoverEvent.addContent(createContentText("數值 : ", ChatColor.AQUA));
            hoverEvent.addContent(createContentText(numberText, ChatColor.YELLOW));
            hoverEvent.addContent(createContentText("\n範圍 : ", ChatColor.LIGHT_PURPLE));
            hoverEvent.addContent(createContentText(min + "-" + max, ChatColor.RED));
            hoverEvent.addContent(createContentText("\n對象 : ", ChatColor.GREEN));
            hoverEvent.addContent(createContentText(playerListText, ChatColor.GRAY));
            hoverEvent.addContent(createContentText(
                    "\n時間 : " + new SimpleDateFormat("yyyy/MM/dd HH:mm:ss.SS").format(new Date()),
                    ChatColor.DARK_GRAY));
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

    int getRandom(int min, int max) {
        return (int) (Math.random() * (max - min + 1)) + min;
    }
}
