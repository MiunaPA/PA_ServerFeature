package me.miunapa.paserverfeature.command;

import java.util.HashMap;
import java.util.UUID;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import me.miunapa.paserverfeature.FeatureStart;
import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.ChatMessageType;
import net.md_5.bungee.api.chat.BaseComponent;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.Sound;

public class Suicide extends FeatureStart implements CommandExecutor {
    HashMap<UUID, Long> coolDown = new HashMap<UUID, Long>();

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (sender instanceof Player) {
            Player player = (Player) sender;
            if (coolDown.containsKey(player.getUniqueId())) {
                Long interval = System.currentTimeMillis() - coolDown.get(player.getUniqueId());
                Integer cdTime = config.getInt("SuicideCoolDown");
                if (interval <= 1000 * cdTime) {
                    BaseComponent[] baseComponent = TextComponent.fromLegacyText(
                            "§e指令冷卻中!請等待§c" + (cdTime - (int) (interval / 1000)) + " §e秒");
                    player.spigot().sendMessage(ChatMessageType.ACTION_BAR, baseComponent);
                    return true;
                }
            }
            player.playSound(player.getLocation(), Sound.ENTITY_PLAYER_DEATH, 1f, 1f);
            player.setHealth(0.0);
            coolDown.put(player.getUniqueId(), System.currentTimeMillis());
        } else {
            sender.sendMessage(ChatColor.RED + "只有玩家可以自殺");
        }
        return true;
    }

    public Suicide() {
        Bukkit.getPluginCommand("suicide").setExecutor(this);
    }
}
