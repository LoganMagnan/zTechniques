package xyz.trixkz.bedwarspractice.managers.leaderboard;

import xyz.trixkz.bedwarspractice.kits.Kit;

import java.util.UUID;

public class Leaderboard {

    private int playerElo;
    private UUID playerUuid;
    private Kit kit;

    public Leaderboard(int playerElo, UUID playerUuid, Kit kit) {
        this.playerElo = playerElo;
        this.playerUuid = playerUuid;
        this.kit = kit;
    }

    public int getPlayerElo() {
        return playerElo;
    }

    public void setPlayerElo(int playerElo) {
        this.playerElo = playerElo;
    }

    public UUID getPlayerUuid() {
        return playerUuid;
    }

    public void setPlayerUuid(UUID playerUuid) {
        this.playerUuid = playerUuid;
    }

    public Kit getKit() {
        return kit;
    }

    public void setKit(Kit kit) {
        this.kit = kit;
    }
}