package xyz.trixkz.bedwarspractice.managers.chunk;

import lombok.Getter;
import org.bukkit.Chunk;
import org.bukkit.scheduler.BukkitRunnable;
import xyz.trixkz.bedwarspractice.Main;
import xyz.trixkz.bedwarspractice.managers.arena.Arena;
import xyz.trixkz.bedwarspractice.managers.arena.StandaloneArena;
import xyz.trixkz.bedwarspractice.utils.CustomLocation;

public class ChunkManager {

    private Main main;

    @Getter
    private boolean chunksLoaded;

    public ChunkManager(Main main) {
        this.main = main;

        new BukkitRunnable() {
            @Override
            public void run() {
                loadChunks();
            }
        }.runTaskLater(this.main, 1L);
    }

    private void loadChunks() {
        this.main.getLogger().info("Starting chunk loading task!");

        CustomLocation spawnMin = this.main.getSpawnManager().getSpawnMin();
        CustomLocation spawnMax = this.main.getSpawnManager().getSpawnMax();

        if (spawnMin != null && spawnMax != null) {
            int spawnMinX = spawnMin.toBukkitLocation().getBlockX() >> 4;
            int spawnMinZ = spawnMin.toBukkitLocation().getBlockZ() >> 4;
            int spawnMaxX = spawnMax.toBukkitLocation().getBlockX() >> 4;
            int spawnMaxZ = spawnMax.toBukkitLocation().getBlockZ() >> 4;

            if (spawnMinX > spawnMaxX) {
                int lastSpawnMinX = spawnMinX;
                spawnMinX = spawnMaxX;
                spawnMaxX = lastSpawnMinX;
            }

            if (spawnMinZ > spawnMaxZ) {
                int lastSpawnMinZ = spawnMinZ;
                spawnMinZ = spawnMaxZ;
                spawnMaxZ = lastSpawnMinZ;
            }

            for (int x = spawnMinX; x <= spawnMaxX; x++) {
                for (int z = spawnMinZ; z <= spawnMaxZ; z++) {
                    Chunk chunk = spawnMin.toBukkitWorld().getChunkAt(x, z);
                    if (!chunk.isLoaded()) {
                        chunk.load();
                    }
                }
            }
        } else {
            this.main.getLogger().info(" ");
            this.main.getLogger().info("                ERROR     ERROR    ERROR                ");
            this.main.getLogger().info("Please make sure you set the Spawn Min & Max Locations!");
            this.main.getLogger().info("If you did not, remove 'spawnLocation' from settings.yml");
            this.main.getLogger().info("                ERROR     ERROR    ERROR                ");
            this.main.getLogger().info(" ");
        }

        for (Arena arena : this.main.getArenaManager().getArenas().values()) {
            if (!arena.isEnabled()) {
                continue;
            }
            int arenaMinX = arena.getMin().toBukkitLocation().getBlockX() >> 4;
            int arenaMinZ = arena.getMin().toBukkitLocation().getBlockZ() >> 4;
            int arenaMaxX = arena.getMax().toBukkitLocation().getBlockX() >> 4;
            int arenaMaxZ = arena.getMax().toBukkitLocation().getBlockZ() >> 4;

            if (arenaMinX > arenaMaxX) {
                int lastArenaMinX = arenaMinX;
                arenaMinX = arenaMaxX;
                arenaMaxX = lastArenaMinX;
            }

            if (arenaMinZ > arenaMaxZ) {
                int lastArenaMinZ = arenaMinZ;
                arenaMinZ = arenaMaxZ;
                arenaMaxZ = lastArenaMinZ;
            }

            for (int x = arenaMinX; x <= arenaMaxX; x++) {
                for (int z = arenaMinZ; z <= arenaMaxZ; z++) {
                    Chunk chunk = arena.getMin().toBukkitWorld().getChunkAt(x, z);
                    if (!chunk.isLoaded()) {
                        chunk.load();
                    }
                }
            }

            for (StandaloneArena saArena : arena.getStandaloneArenas()) {
                arenaMinX = saArena.getMin().toBukkitLocation().getBlockX() >> 4;
                arenaMinZ = saArena.getMin().toBukkitLocation().getBlockZ() >> 4;
                arenaMaxX = saArena.getMax().toBukkitLocation().getBlockX() >> 4;
                arenaMaxZ = saArena.getMax().toBukkitLocation().getBlockZ() >> 4;

                if (arenaMinX > arenaMaxX) {
                    int lastArenaMinX = arenaMinX;
                    arenaMinX = arenaMaxX;
                    arenaMaxX = lastArenaMinX;
                }

                if (arenaMinZ > arenaMaxZ) {
                    int lastArenaMinZ = arenaMinZ;
                    arenaMinZ = arenaMaxZ;
                    arenaMaxZ = lastArenaMinZ;
                }

                for (int x = arenaMinX; x <= arenaMaxX; x++) {
                    for (int z = arenaMinZ; z <= arenaMaxZ; z++) {
                        Chunk chunk = saArena.getMin().toBukkitWorld().getChunkAt(x, z);
                        if (!chunk.isLoaded()) {
                            chunk.load();
                        }
                    }
                }
            }
        }

        this.main.getLogger().info("Finished loading all the chunks!");
        this.chunksLoaded = true;
    }
}
