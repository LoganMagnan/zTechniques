package xyz.trixkz.bedwarspractice.listeners.events;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;
import xyz.trixkz.bedwarspractice.managers.game.Game;

@Getter
@RequiredArgsConstructor
public class GameEvent extends Event {

    private static final HandlerList HANDLERS = new HandlerList();
    private final Game game;

    public static HandlerList getHandlerList() {
        return HANDLERS;
    }

    @Override
    public HandlerList getHandlers() {
        return HANDLERS;
    }
}
