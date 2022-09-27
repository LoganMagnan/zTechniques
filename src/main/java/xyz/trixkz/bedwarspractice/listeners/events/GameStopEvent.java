package xyz.trixkz.bedwarspractice.listeners.events;

import lombok.Getter;
import xyz.trixkz.bedwarspractice.managers.game.Game;

@Getter
public class GameStopEvent extends GameEvent {

    public GameStopEvent(Game game) {
        super(game);
    }
}