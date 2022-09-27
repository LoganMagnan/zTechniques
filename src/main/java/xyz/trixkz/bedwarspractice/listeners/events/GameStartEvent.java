package xyz.trixkz.bedwarspractice.listeners.events;

import xyz.trixkz.bedwarspractice.managers.game.Game;

public class GameStartEvent extends GameEvent {

    public GameStartEvent(Game game) {
        super(game);
    }
}
