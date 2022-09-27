package xyz.trixkz.bedwarspractice.runnable.arena;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import xyz.trixkz.bedwarspractice.Main;
import xyz.trixkz.bedwarspractice.managers.arena.Arena;
import xyz.trixkz.bedwarspractice.managers.arena.StandaloneArena;
import xyz.trixkz.bedwarspractice.utils.CustomLocation;

@Getter
@AllArgsConstructor
public class ArenaCommandRunnable implements Runnable {

    private Main main;

    private final Arena copiedArena;

    private int times;

    @Override
    public void run() {
        this.duplicateArena(this.copiedArena, 10000, 10000);
    }

    private void duplicateArena(Arena arena, int offsetX, int offsetZ) {
        new DuplicateArenaRunnable(Main.getInstance(), arena, offsetX, offsetZ, 500, 500) {
            @Override
            public void onComplete() {
                double minX = arena.getMin().getX() + this.getOffsetX();
                double minZ = arena.getMin().getZ() + this.getOffsetZ();
                double maxX = arena.getMax().getX() + this.getOffsetX();
                double maxZ = arena.getMax().getZ() + this.getOffsetZ();

                double aX = arena.getA().getX() + this.getOffsetX();
                double aZ = arena.getA().getZ() + this.getOffsetZ();

                CustomLocation min = new CustomLocation(minX, arena.getMin().getY(), minZ, arena.getMin().getYaw(), arena.getMin().getPitch());
                CustomLocation max = new CustomLocation(maxX, arena.getMax().getY(), maxZ, arena.getMax().getYaw(), arena.getMax().getPitch());
                CustomLocation a = new CustomLocation(aX, arena.getA().getY(), aZ, arena.getA().getYaw(), arena.getA().getPitch());

                StandaloneArena standaloneArena = new StandaloneArena(a, min, max);

                arena.addStandaloneArena(standaloneArena);
                arena.addAvailableArena(standaloneArena);

                String arenaPasteMessage = "[Standalone Arena] - " + arena.getName() + " placed at " + (int) minX + ", " + (int) minZ + ". " + ArenaCommandRunnable.this.times + " copies remaining.";

                if (--ArenaCommandRunnable.this.times > 0) {
                    ArenaCommandRunnable.this.main.getServer().getLogger().info(arenaPasteMessage);
                    for (Player player : Bukkit.getOnlinePlayers()) {
                        if (player.isOp()) {
                            player.sendMessage(ChatColor.GREEN + arenaPasteMessage);
                        }
                    }
                    ArenaCommandRunnable.this.duplicateArena(arena, (int) Math.round(maxX), (int) Math.round(maxZ));
                } else {
                    for (Player player : Bukkit.getOnlinePlayers()) {
                        if (player.isOp()) {
                            player.sendMessage(ChatColor.GREEN + "All the copies for " + ArenaCommandRunnable.this.copiedArena.getName() + " have been pasted successfully!");
                        }
                    }
                    ArenaCommandRunnable.this.main.getServer().getLogger().info("All the copies for " + ArenaCommandRunnable.this.copiedArena.getName() + " have been pasted successfully!");
                    ArenaCommandRunnable.this.main.getArenaManager().setGeneratingArenaRunnable(ArenaCommandRunnable.this.main.getArenaManager().getGeneratingArenaRunnable() - 1);
                    ArenaCommandRunnable.this.main.getArenaManager().reloadArenas();
                }
            }
        }.run();
    }
}
