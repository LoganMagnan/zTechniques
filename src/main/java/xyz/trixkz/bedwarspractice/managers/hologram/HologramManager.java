package xyz.trixkz.bedwarspractice.managers.hologram;

import io.netty.util.internal.ConcurrentSet;
import org.bukkit.entity.Player;
import xyz.trixkz.bedwarspractice.Main;

import java.util.Set;

public class HologramManager {

    private final Set<Hologram> holograms = new ConcurrentSet<>();

    public HologramManager(Main main) {
//        int update = 15;
//
//        if (main.getSpawnManager().getHologramsLeaderboardsLocation() == null) {
//            main.getLogger().info("[Leaderboard] Please set leaderboard hologram using /setspawn hololeaderboard and then restart.");
//        } else {
//            Hologram hologram = new TopHologram(main.getSpawnManager().getHologramsLeaderboardsLocation().toBukkitLocation(), update);
//            if (main.getKits().size() >= 1) {
//                hologram.start("Top Stats");
//            }
//            holograms.add(hologram);
//        }
    }

    public void show(Player player) {
        holograms.forEach(nyaHologram -> nyaHologram.show(player));
    }

    public void hide(Player player) {
        holograms.forEach(nyaHologram -> nyaHologram.hide(player));
    }
}