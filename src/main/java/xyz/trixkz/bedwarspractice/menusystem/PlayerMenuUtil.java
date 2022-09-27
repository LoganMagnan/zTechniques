package xyz.trixkz.bedwarspractice.menusystem;

import org.bukkit.entity.Player;

public class PlayerMenuUtil {

    private Player owner;

    public PlayerMenuUtil(Player owner) {
        this.owner = owner;
    }

    public Player getOwner() {
        return owner;
    }

    public void setOwner(Player owner) {
        this.owner = owner;
    }
}
