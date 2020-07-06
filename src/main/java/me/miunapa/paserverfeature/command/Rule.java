package me.miunapa.paserverfeature.command;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import me.miunapa.paserverfeature.SubFeature;
import net.md_5.bungee.api.ChatColor;
import org.bukkit.Bukkit;

public class Rule extends SubFeature implements CommandExecutor {
    public Rule() {
        super("Rule");
        Bukkit.getPluginCommand("rule").setExecutor(this);
        Bukkit.getPluginCommand("rules").setExecutor(this);
    }

    public void onDisable() {

    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (sender instanceof Player) {
            Player player = (Player) sender;
            for (String s : config.getStringList("rule")) {
                s = ChatColor.translateAlternateColorCodes('&', s);
                player.sendMessage(s);
            }
        }
        return true;
    }
}
