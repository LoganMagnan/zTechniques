package xyz.trixkz.bedwarspractice.managers.leaderboard;

import com.google.common.collect.Lists;
import com.mongodb.client.MongoCursor;
import org.bson.Document;
import org.bukkit.Bukkit;
import xyz.trixkz.bedwarspractice.Main;
import xyz.trixkz.bedwarspractice.kits.Kit;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.UUID;

public class LeaderboardManager {

    private Main main;

    private final Set<Leaderboard> leaderboards = new HashSet<>();

    public LeaderboardManager(Main main) {
        this.main = main;
    }

//    public void createLeaderboards() {
//        for (Kit kit : this.main.getKits()) {
//            try (MongoCursor<Document> iterator = this.main.getPlayerManager().getPlayersSortByLadderElo(kit)) {
//                while (iterator.hasNext()) {
//                    try {
//                        Document document = iterator.next();
//                        UUID uuid = UUID.fromString(document.getString("uuid"));
//                        if (!document.containsKey("stats")) {
//                            continue;
//                        }
//
//                        Document statistics = (Document) document.get("stats");
//                        int amount = PracticePlayerData.DEFAULT_ELO;
//                        if (statistics.containsKey(kit.getName())) {
//                            Document ladder = (Document) statistics.get(kit.getName());
//                            amount = ladder.getInteger("elo");
//                        }
//
//                        Leaderboard leaderboard = new Leaderboard(amount, uuid, kit);
//                        leaderboards.add(leaderboard);
//                    } catch (Exception e) {
//                        e.printStackTrace();
//                    }
//                }
//            }
//        }
//    }
//
//    public void updateLeaderboards() {
//        this.leaderboards.clear();
//        Bukkit.getServer().getScheduler().runTaskAsynchronously(this.main, this::createLeaderboards);
//    }
//
//    public List<Leaderboard> getKitLeaderboards(Kit kit) {
//        List<Leaderboard> leaderboardsKit = Lists.newArrayList();
//        this.leaderboards.stream().filter(leaderboard -> leaderboard.getKit() == kit).forEach(leaderboardsKit::add);
//
//        return leaderboardsKit;
//    }
}