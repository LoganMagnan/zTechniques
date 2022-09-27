package xyz.trixkz.bedwarspractice.managers.hologram;

import org.bukkit.Location;
import xyz.trixkz.bedwarspractice.Main;
import xyz.trixkz.bedwarspractice.kits.Kit;
import xyz.trixkz.bedwarspractice.managers.leaderboard.Leaderboard;

import java.util.Comparator;
import java.util.stream.Collectors;

public class TopHologram /* extends Hologram */ {

    private int i = 0;
    private Kit kit;
    private Kit nextKit;

    public TopHologram(Location location, int time) {
//        super(location, time);
    }

//    @Override
//    public void update() {
//        this.i++;
//
//        try {
//            this.kit = Main.getInstance().getKits().get(i);
//
//            if (this.kit == null) {
//                this.i = 0;
//            }
//        } catch (IndexOutOfBoundsException exception) {
//            this.i = 0;
//
//            this.kit = Main.getInstance().getKits().get(0);
//        }
//
//        try {
//            this.nextKit = Main.getInstance().getKits().get(i + 1);
//        } catch (IndexOutOfBoundsException exception) {
//            this.nextKit = Main.getInstance().getKits().get(0);
//        }
//    }
//
//    @Override
//    public void updateLines() {
//        final ConfigCursor configCursor = new ConfigCursor(this.plugin.getSettingsConfig(), "HOLOGRAM");
//
//        for (String s : configCursor.getStringList("FORMAT.LINES")) {
//            if (s.equalsIgnoreCase("<top>")) {
//                int index = 0;
//                for (Leaderboard leaderboard : Main.getInstance().getLeaderboardManager().getKitLeaderboards(kit)
//                        .stream()
//                        .sorted(Comparator.comparingInt(Leaderboard::getPlayerElo).reversed())
//                        .limit(10).collect(Collectors.toList())) {
//                    getLines().add(CC.translate(configCursor.getString("FORMAT.FORMAT")
//                            .replace("<number>", String.valueOf(index + 1))
//                            .replace("<name>", Bukkit.getOfflinePlayer(leaderboard.getPlayerUuid()).getName())
//                            .replace("<value>", String.valueOf(leaderboard.getPlayerElo()))
//                            .replace("<elo_rating>", RatingUtil.getRankByElo(leaderboard.getPlayerElo()).getName())
//                    ));
//                    index++;
//                }
//            } else {
//                getLines().add(CC.translate(s
//                        .replaceAll("<kit>", kit.getName())
//                        .replaceAll("<nextKit>", nextKit.getName())
//                        .replaceAll("<update>", String.valueOf(getActualTime()))
//                ));
//            }
//        }
//    }
}
