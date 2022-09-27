package xyz.trixkz.bedwarspractice.listeners;

import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.entity.TNTPrimed;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityExplodeEvent;
import org.bukkit.event.entity.FoodLevelChangeEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.event.weather.WeatherChangeEvent;
import org.bukkit.inventory.ItemStack;
import xyz.trixkz.bedwarspractice.Main;
import xyz.trixkz.bedwarspractice.kits.Kit;
import xyz.trixkz.bedwarspractice.managers.arena.StandaloneArena;
import xyz.trixkz.bedwarspractice.managers.game.Game;
import xyz.trixkz.bedwarspractice.managers.user.User;
import xyz.trixkz.bedwarspractice.managers.user.UserState;
import xyz.trixkz.bedwarspractice.utils.CustomLocation;
import xyz.trixkz.bedwarspractice.utils.Items;
import xyz.trixkz.bedwarspractice.utils.Utils;

import java.text.DecimalFormat;
import java.util.concurrent.BlockingQueue;

public class RandomListeners implements Listener {

    private Main main;

    public RandomListeners(Main main) {
        this.main = main;
    }

    @EventHandler
    public void onBlockPlace(BlockPlaceEvent event) {
        Player player = event.getPlayer();

        User user = User.getByUUID(player.getUniqueId());

        if (player.getGameMode() == GameMode.CREATIVE) {
            return;
        }

        if (user.getUserState() == UserState.SPAWN) {
            event.setCancelled(true);

            return;
        }

        Game game = this.main.getGameManager().getGames().get(player.getUniqueId());

        Block block = event.getBlock();

        if (!game.isPlaceable(player, game)) {
            event.setCancelled(true);
        }

        game.addPlacedBlock(block);

        if (block.getType() == Material.TNT) {
            ItemStack itemStack = event.getItemInHand().clone();

            TNTPrimed tntPrimed = (TNTPrimed) player.getWorld().spawnEntity(block.getLocation(), EntityType.PRIMED_TNT);
            tntPrimed.setFuseTicks(40);

            event.setCancelled(true);

            if (itemStack.getAmount() == 1) {
                player.setItemInHand(new ItemStack(Material.TNT));
            } else {
                itemStack.setAmount(itemStack.getAmount() - 1);

                player.setItemInHand(itemStack);
            }
        }
    }

    @EventHandler
    public void onBlockBreak(BlockBreakEvent event) {
        Player player = event.getPlayer();

        User user = User.getByUUID(player.getUniqueId());

        if (player.getGameMode() == GameMode.CREATIVE) {
            return;
        }

        if (user.getUserState() == UserState.SPAWN) {
            event.setCancelled(true);

            return;
        }

        Game game = this.main.getGameManager().getGames().get(player.getUniqueId());

        Block block = event.getBlock();

        if (!game.isBreakable(block)) {
            event.setCancelled(true);
        }

        if (user.getUserState() == UserState.BED_DEFEND) {
            if (block.getType() == Material.WOOL || block.getType() == Material.WOOD || block.getType() == Material.ENDER_STONE || block.getType() == Material.BED) {
                event.setCancelled(false);
            }
        }
    }

    @EventHandler
    public void onEntityExplode(EntityExplodeEvent event) {
        event.blockList().clear();
    }

    @EventHandler
    public void onInventoryClick(InventoryClickEvent event) {
        Player player = (Player) event.getWhoClicked();

        if (player.getGameMode() == GameMode.CREATIVE) {
            return;
        }

        event.setCancelled(true);
    }

    @EventHandler
    public void onEntityDamage(EntityDamageEvent event) {
        if (!(event.getEntity() instanceof Player)) {
            return;
        }

        Player player = (Player) event.getEntity();

        if (this.main.getGameManager().getGameFromUUID(player.getUniqueId()) == null) {
            event.setCancelled(true);

            return;
        }

        if (event.getCause() == EntityDamageEvent.DamageCause.FALL || event.getCause() == EntityDamageEvent.DamageCause.FIRE || event.getCause() == EntityDamageEvent.DamageCause.FIRE_TICK || event.getCause() == EntityDamageEvent.DamageCause.LAVA) {
            event.setCancelled(true);

            return;
        }

        event.setDamage(0.0D);
    }

    @EventHandler
    public void onEntityDamageByEntity(EntityDamageByEntityEvent event) {
        if (event.getEntity() instanceof Player && event.getDamager() instanceof Player) {
            event.setCancelled(true);

            return;
        }
    }

    @EventHandler
    public void onFoodLevelChange(FoodLevelChangeEvent event) {
        event.setCancelled(true);
    }

    @EventHandler
    public void onPlayerDropItem(PlayerDropItemEvent event) {
        event.setCancelled(true);
    }

    @EventHandler
    public void onWeatherChange(WeatherChangeEvent event) {
        event.setCancelled(true);
    }
}
