package me.miunapa.paserverfeature.feature;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.entity.Turtle;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import me.miunapa.paserverfeature.SubFeature;
import net.md_5.bungee.api.ChatColor;

public class TurtleCount extends SubFeature implements Listener {
    public TurtleCount() {
        super("TurtleCount");
        pm.registerEvents(this, plugin);
    }

    public void onDisable() {

    }

    @EventHandler
    public void click(PlayerInteractEntityEvent event) {
        Player player = event.getPlayer();
        Entity entity = event.getRightClicked();
        if (player.getInventory().getItemInMainHand().getType() == Material.BONE) {
            if (entity.getType() == EntityType.TURTLE) {
                Turtle turtle = (Turtle) entity;
                Location loc = turtle.getHome();
                player.sendActionBar(ChatColor.DARK_GREEN + "這隻海龜的重生點 " + ChatColor.RED + "(x:"
                        + loc.getX() + " y:" + loc.getY() + " z:" + loc.getZ() + ")");
            }
        }
    }
}
