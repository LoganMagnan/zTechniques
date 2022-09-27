package xyz.trixkz.bedwarspractice.menusystem.arena.pagination.settings;

import org.bukkit.entity.Player;
import xyz.trixkz.bedwarspractice.managers.block.UserBlock;
import xyz.trixkz.bedwarspractice.utils.Utils;
import java.util.concurrent.ThreadLocalRandom;

public class Settings {

    private int level;
    private double xp;
    private Mode mode;
    private boolean scoreboardEnabled;
    private boolean playerVisibilityEnabled;
    private boolean spectatingEnabled;
    private UserBlock currentBlock;

    public Settings(int level, double xp, Mode mode, boolean scoreboardEnabled, boolean playerVisibilityEnabled, boolean spectatingEnabled, UserBlock currentBlock) {
        this.level = level;
        this.xp = xp;
        this.mode = mode;
        this.scoreboardEnabled = scoreboardEnabled;
        this.playerVisibilityEnabled = playerVisibilityEnabled;
        this.spectatingEnabled = spectatingEnabled;
        this.currentBlock = currentBlock;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public void addLevel(Player player, int level) {
        this.level += level;

        player.sendMessage(Utils.translate("&a&l+1 level"));
    }

    public void removeLevel(int level) {
        if (!(this.level > 0)) {
            return;
        }

        this.level -= level;
    }

    public double getXp() {
        return xp;
    }

    public void setXp(double xp) {
        this.xp = xp;

        if (this.xp >= 1) {
            this.level += 1;
            this.xp = this.xp - (long) this.xp;
        }
    }

    public void addXp(Player player, double xp) {
        this.xp += xp;

        player.sendMessage(Utils.translate("&b&l+" + ((int) (xp * 100)) + "&b&l% xp"));

        if (this.xp >= 1) {
            this.level += 1;
            this.xp = this.xp - (long) this.xp;
        }
    }

    public void addRandomXp(Player player) {
        double xp = ThreadLocalRandom.current().nextDouble(0.01, 0.05);

        this.xp += xp;

        player.sendMessage(Utils.translate("&b&l+" + ((int) (xp * 100)) + "&b&l% xp"));

        if (this.xp >= 1) {
            this.level += 1;
            this.xp = this.xp - (long) this.xp;
        }
    }

    public void removeXp(double xp) {
        if (!(this.xp > 0)) {
            return;
        }

        this.xp -= xp;
    }

    public Mode getMode() {
        return mode;
    }

    public void setMode(Mode mode) {
        this.mode = mode;
    }

    public boolean isScoreboardEnabled() {
        return scoreboardEnabled;
    }

    public void setScoreboardEnabled(boolean scoreboardEnabled) {
        this.scoreboardEnabled = scoreboardEnabled;
    }

    public boolean isPlayerVisibilityEnabled() {
        return playerVisibilityEnabled;
    }

    public void setPlayerVisibilityEnabled(boolean playerVisibilityEnabled) {
        this.playerVisibilityEnabled = playerVisibilityEnabled;
    }

    public boolean isSpectatingEnabled() {
        return spectatingEnabled;
    }

    public void setSpectatingEnabled(boolean spectatingEnabled) {
        this.spectatingEnabled = spectatingEnabled;
    }

    public UserBlock getCurrentBlock() {
        return this.currentBlock;
    }

    public void setCurrentBlock(UserBlock currentBlock) {
        this.currentBlock = currentBlock;
    }
}
