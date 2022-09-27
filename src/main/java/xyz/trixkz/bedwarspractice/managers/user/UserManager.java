package xyz.trixkz.bedwarspractice.managers.user;

import com.mongodb.client.MongoCursor;
import org.bson.Document;
import org.bson.conversions.Bson;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerPreLoginEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.scheduler.BukkitRunnable;
import xyz.trixkz.bedwarspractice.Main;
import xyz.trixkz.bedwarspractice.kits.Kit;

public class UserManager implements Listener {

    private Main main;

    public UserManager(Main main) {
        this.main = main;
    }

    @EventHandler
    public void onAsyncPlayerPreLogin(AsyncPlayerPreLoginEvent event) {
        User user = new User(event.getUniqueId(), event.getName());

        User.getUsers().put(event.getUniqueId(), user);
    }

    @EventHandler
    public void onPlayerQuit(PlayerQuitEvent event) {
        Player player = event.getPlayer();

        User user = User.getByUUID(player.getUniqueId());

        if (user != null) {
            user.save();
        }
    }

    public void updatePlayerVisibility() {
        new BukkitRunnable() {
            @Override
            public void run() {
                main.getServer().getOnlinePlayers().forEach(player -> {
                    User user = User.getByUUID(player.getUniqueId());

                    if (user.getSettings().isPlayerVisibilityEnabled()) {
                        main.getServer().getOnlinePlayers().forEach(onlinePlayer -> main.getServer().getScheduler().runTask(main, () -> {
                            if (onlinePlayer == null) {
                                return;
                            }

                            User userOnlinePlayer = User.getByUUID(onlinePlayer.getUniqueId());

                            if (userOnlinePlayer == null) {
                                return;
                            }

                            player.showPlayer(onlinePlayer);
                        }));
                    } else {
                        main.getServer().getOnlinePlayers().forEach(onlinePlayer -> main.getServer().getScheduler().runTask(main, () -> {
                            if (onlinePlayer == null) {
                                return;
                            }

                            User userOnlinePlayer = User.getByUUID(onlinePlayer.getUniqueId());

                            if (userOnlinePlayer == null) {
                                return;
                            }

                            player.hidePlayer(onlinePlayer);
                        }));
                    }
                });
            }
        }.runTaskAsynchronously(this.main);
    }
}
