package xyz.trixkz.bedwarspractice.listeners;

import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.entity.Zombie;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.CreatureSpawnEvent;
import org.bukkit.event.entity.EntityTargetEvent;

public class CustomEntityListener implements Listener {

    @EventHandler
    public void onEntityTarget(EntityTargetEvent event) {
        if (!(event.getEntity() instanceof Zombie) || !(event.getTarget() instanceof Player)) {
            return;
        }

        Player player = (Player) event.getTarget();

        Entity entity = event.getEntity();

        if (!entity.getCustomName().contains(player.getName())) {
            event.setCancelled(true);
        }
    }

    @EventHandler
    public void onCreatureSpawn(CreatureSpawnEvent event) {
        if (event.getEntity().getCustomName() == null) {
            event.setCancelled(true);
        }
    }
}
