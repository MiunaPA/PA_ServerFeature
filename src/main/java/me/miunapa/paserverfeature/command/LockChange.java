package me.miunapa.paserverfeature.command;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import org.bukkit.Bukkit;
import org.bukkit.Tag;
import org.bukkit.block.Sign;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import me.miunapa.paserverfeature.SubFeature;
import net.md_5.bungee.api.ChatColor;

public class LockChange extends SubFeature implements Listener, CommandExecutor, TabCompleter {
    HashMap<String, String> changeName = new HashMap<String, String>();

    public LockChange() {
        super("LockChange");
        pm.registerEvents(this, plugin);
        Bukkit.getPluginCommand("lc").setExecutor(this);
    }

    public void onDisable() {

    }

    @EventHandler
    public void click(PlayerInteractEvent event) {
        Player player = event.getPlayer();
        Action action = event.getAction();
        if (action == Action.RIGHT_CLICK_BLOCK) {
            if (Tag.SIGNS.isTagged(event.getClickedBlock().getType())) {
                Sign sign = (Sign) event.getClickedBlock().getState();
                boolean edit = false;
                for (int i = 0; i < sign.getLines().length; i++) {
                    if (changeName.containsKey(sign.getLine(i))) {
                        player.sendMessage(ChatColor.GREEN + "已更改ID " + ChatColor.YELLOW
                                + sign.getLine(i) + ChatColor.GOLD + " ----> " + ChatColor.YELLOW
                                + changeName.get(sign.getLine(i)));
                        sign.setLine(i, changeName.get(sign.getLine(i)));
                        edit = true;
                    }
                }
                if (edit) {
                    sign.update();
                }
            }
        }
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (args.length >= 1) {
            if (args[0].equals("add")) {
                if (args.length == 3) {
                    changeName.put(args[1], args[2]);
                    sender.sendMessage(ChatColor.GOLD + "已設定完成更改ID " + ChatColor.YELLOW + args[1]
                            + ChatColor.GOLD + " ----> " + ChatColor.YELLOW + args[2]);
                } else {
                    sender.sendMessage(ChatColor.RED + "/lc add <舊ID> <新ID>");
                }
            } else if (args[0].equals("clear")) {
                changeName = new HashMap<String, String>();
            }
        } else {
            sender.sendMessage(ChatColor.RED + "/lc add <舊ID> <新ID>" + ChatColor.GOLD + " 或是 "
                    + ChatColor.RED + "/lc clear");
        }
        return true;
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String alias,
            String[] args) {
        if (command.getName().equalsIgnoreCase("lc") && args.length == 1) {
            List<String> tabList = new ArrayList<String>();
            tabList.add("add");
            tabList.add("clear");
            return tabList;
        }
        return null;
    }
}
