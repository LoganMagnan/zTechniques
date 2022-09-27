package xyz.trixkz.bedwarspractice.listeners;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import xyz.trixkz.bedwarspractice.Main;
import xyz.trixkz.bedwarspractice.managers.game.Game;
import xyz.trixkz.bedwarspractice.listeners.events.GameStartEvent;
import xyz.trixkz.bedwarspractice.managers.game.GameState;
import xyz.trixkz.bedwarspractice.runnable.GameRunnable;
import xyz.trixkz.bedwarspractice.utils.CustomLocation;
import xyz.trixkz.bedwarspractice.utils.Utils;

public class GameStartListener implements Listener {

    private Main main;

    public GameStartListener(Main main) {
        this.main = main;
    }

    @EventHandler
    public void onGameStart(GameStartEvent event) {
        Game game = event.getGame();

        Player player = game.getPlayer();

        game.setGameState(GameState.PLAYING);

        if (game.getArena().getAvailableArenas().size() > 0) {
            game.setStandaloneArena(game.getArena().getAvailableArena());

            this.main.getArenaManager().setArenaGameUUID(game.getStandaloneArena(), game.getGameID());
        } else {
            player.sendMessage(Utils.translate("&cError: There are no arenas already made"));

            this.main.getGameManager().removeGame(game);

            return;
        }

        CustomLocation spawnPointLocation = game.getStandaloneArena() != null ? game.getStandaloneArena().getA() : game.getArena().getA();

        player.teleport(spawnPointLocation.toBukkitLocation());

        new GameRunnable(game).runTaskTimer(this.main, 2L, 2L);
    }
}
