package me.miunapa.paserverfeature.feature;

import org.bukkit.GameMode;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import me.miunapa.paserverfeature.SubFeature;
import net.md_5.bungee.api.ChatColor;

public class SpawnerChange extends SubFeature implements Listener {
    public SpawnerChange() {
        super("SpawnerChange");
        pm.registerEvents(this, plugin);
    }

    public void onDisable() {

    }

    @EventHandler
    public void click(PlayerInteractEvent event) {
        if (!config.getBoolean("SpawnerChange")) {
            Player player = event.getPlayer();
            Action action = event.getAction();
            if (action == Action.RIGHT_CLICK_BLOCK && player.getGameMode() != GameMode.CREATIVE) {
                if (isEgg(player.getInventory().getItemInMainHand())
                        && isSpawner(event.getClickedBlock())) {
                    event.setCancelled(true);
                    player.sendActionBar(ChatColor.RED + "你不能修改生怪磚的怪物");
                }
            }
        }
    }

    boolean isEgg(ItemStack item) {
        String name = (item == null) ? "" : item.getType().toString();
        return !(!name.contains("MONSTER_EGG") && !name.endsWith("SPAWN_EGG"));
    }

    boolean isSpawner(Block block) {
        String name = (block.getType() == null) ? "" : block.getType().toString();
        return !(!name.equals("MOB_SPAWNER") && !name.equals("SPAWNER"));
    }
}
