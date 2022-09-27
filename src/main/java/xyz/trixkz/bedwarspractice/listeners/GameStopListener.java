package xyz.trixkz.bedwarspractice.listeners;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import xyz.trixkz.bedwarspractice.Main;
import xyz.trixkz.bedwarspractice.listeners.events.GameStopEvent;
import xyz.trixkz.bedwarspractice.managers.game.Game;
import xyz.trixkz.bedwarspractice.managers.game.GameState;
import xyz.trixkz.bedwarspractice.managers.user.User;
import xyz.trixkz.bedwarspractice.managers.user.UserState;
import xyz.trixkz.bedwarspractice.utils.Items;

public class GameStopListener implements Listener {

    private Main main;

    public GameStopListener(Main main) {
        this.main = main;
    }

    @EventHandler
    public void onGameStop(GameStopEvent event) {
        Game game = event.getGame();

        Player player = game.getPlayer();

        User user = User.getByUUID(player.getUniqueId());

        user.setUserState(UserState.SPAWN);

        game.setGameState(GameState.STOPPING);

        this.main.getChunkRestorationManager().reset(game.getStandaloneArena());

        game.getArena().addAvailableArena(game.getStandaloneArena());

        this.main.getArenaManager().removeArenaMatchUUID(game.getStandaloneArena());

        player.getInventory().clear();

        player.teleport(player.getWorld().getSpawnLocation());

        user.stopTimer();

        player.getInventory().setItem(0, Items.practiceATechnique);
        player.getInventory().setItem(4, Items.blockSelector);
        player.getInventory().setItem(8, Items.settings);
    }
}
