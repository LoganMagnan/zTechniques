package xyz.trixkz.bedwarspractice.managers.game;

import lombok.Getter;
import lombok.Setter;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import xyz.trixkz.bedwarspractice.kits.Kit;
import xyz.trixkz.bedwarspractice.managers.arena.Arena;
import xyz.trixkz.bedwarspractice.managers.arena.StandaloneArena;
import xyz.trixkz.bedwarspractice.utils.TimeUtils;

import java.util.*;

@Getter
@Setter
public class Game {

    private final Set<Location> placedBlocksLocations = new HashSet<>();

    private UUID gameID = UUID.randomUUID();
    private final Arena arena;
    private final Kit kit;
    private String starting = "Starting";

    private Player player;

    private StandaloneArena standaloneArena;
    private GameState gameState = GameState.SPAWN;
    private int durationTimer = 0;

    public Game(Arena arena, Kit kit, Player player) {
        this.arena = arena;
        this.kit = kit;
        this.player = player;
    }

    public UUID setGameID(UUID uuid) {
        return this.gameID = uuid;
    }

    public String getDuration() {
        return TimeUtils.formatIntoMMSS(durationTimer);
    }

    public boolean isStarting() {
        return this.gameState == GameState.STARTING;
    }

    public boolean isPlaying() {
        return this.gameState == GameState.PLAYING;
    }

    public void addPlacedBlock(Block block) {
        this.placedBlocksLocations.add(block.getLocation());
    }

    public void removePlacedBlock(Block block) {
        this.placedBlocksLocations.remove(block.getLocation());
    }

    public void incrementDuration() {
        this.durationTimer++;
    }

    public boolean isBreakable(Block block) {
        Material material = block.getType();

        if (this.placedBlocksLocations.contains(block.getLocation())) {
            return true;
        }

        return false;
    }

    public boolean isPlaceable(Player player, Game game) {
        double minX = game.getStandaloneArena().getMin().getX();
        double minZ = game.getStandaloneArena().getMin().getZ();
        double maxX = game.getStandaloneArena().getMax().getX();
        double maxZ = game.getStandaloneArena().getMax().getZ();

        if (minX > maxX) {
            double lastMinX = minX;
            minX = maxX;
            maxX = lastMinX;
        }

        if (minZ > maxZ) {
            double lastMinZ = minZ;
            minZ = maxZ;
            maxZ = lastMinZ;
        }

        if (player.getLocation().getX() >= minX && player.getLocation().getX() <= maxX && player.getLocation().getZ() >= minZ && player.getLocation().getZ() <= maxZ) {
            return true;
        }

        return false;
    }
}
