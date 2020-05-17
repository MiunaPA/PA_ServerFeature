package me.miunapa.paserverfeature.command;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import net.md_5.bungee.api.ChatColor;

public class Invisible implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (sender instanceof Player) {
            Player player = (Player) sender;
            PotionEffect pe = new PotionEffect(PotionEffectType.INVISIBILITY, 3600, 1, true);
            player.removePotionEffect(PotionEffectType.INVISIBILITY);
            player.addPotionEffect(pe);
            player.sendMessage(ChatColor.LIGHT_PURPLE + "給予了三分鐘的無氣泡隱形");
        }
        return true;
    }

    public Invisible() {
        Bukkit.getPluginCommand("invisible").setExecutor(this);
    }
}
