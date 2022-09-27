package xyz.trixkz.bedwarspractice.managers.user;

import com.mongodb.client.MongoCollection;
import com.mongodb.client.model.Filters;
import com.mongodb.client.model.ReplaceOptions;
import lombok.Getter;
import lombok.Setter;
import org.bson.Document;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;
import xyz.trixkz.bedwarspractice.Main;
import xyz.trixkz.bedwarspractice.menusystem.arena.pagination.settings.Mode;
import xyz.trixkz.bedwarspractice.menusystem.arena.pagination.settings.Settings;
import xyz.trixkz.bedwarspractice.statistics.Statistics;
import java.util.HashMap;
import java.util.UUID;

public class User {

    @Getter private MongoCollection<Document> mongoCollection = Main.getInstance().getDatabaseManager().getMongoDatabase().getCollection("users");
    @Getter private static HashMap<UUID, User> users = new HashMap<UUID, User>();
    @Getter private static UUID uuid;
    @Getter private String name;
    @Getter private Settings settings;
    @Getter private Statistics statistics;
    @Getter @Setter private boolean loaded;
    private static Player player;
    @Getter @Setter private UserState userState = UserState.SPAWN;
    private Timer timer;

    public User(UUID uuid, String name) {
        this.uuid = uuid;
        this.name = name;

        load();
    }

    public void load() {
        new Thread(() -> {
            Document document = Main.getInstance().getDatabaseManager().getPlayers().find(Filters.eq("uuid", uuid.toString())).first();

            if (document == null) {
                save();
            }

            if (name == null) {
                name = document.getString("name");
            }

            this.settings = new Settings(0, 0, Mode.EASY, true, true, true, Main.getInstance().getBlockManager().getUserBlocks().get(0));
            this.statistics = new Statistics(Main.getInstance());

            try {
                Document settingsDocument = (Document) document.get("settings");

                this.settings.setLevel(settingsDocument.getInteger("level"));
                this.settings.setXp(settingsDocument.getDouble("xp"));
                this.settings.setMode(Mode.valueOf(settingsDocument.getString("mode")));
                this.settings.setScoreboardEnabled(settingsDocument.getBoolean("scoreboard-enabled"));
                this.settings.setPlayerVisibilityEnabled(settingsDocument.getBoolean("player-visibility-enabled"));
                this.settings.setSpectatingEnabled(settingsDocument.getBoolean("spectating-enabled"));
                this.settings.setCurrentBlock(Main.getInstance().getBlockManager().getBlockByName(settingsDocument.getString("current-block")));

                Document statisticsDocument = (Document) document.get("statistics");

                this.statistics.setTheBridgeBypass(statisticsDocument.getDouble("the-bridge-bypass"));
                this.statistics.setKnockbackClutch(statisticsDocument.getDouble("knockback-clutch"));
                this.statistics.setLadderClutch(statisticsDocument.getDouble("ladder-clutch"));
                this.statistics.setFireballJump(statisticsDocument.getDouble("fireball-jump"));
                this.statistics.setTntJump(statisticsDocument.getDouble("tnt-jump"));
                this.statistics.setSideClutch(statisticsDocument.getDouble("side-clutch"));
                this.statistics.setWallBlockClutch(statisticsDocument.getDouble("wall-block-clutch"));
                this.statistics.setBlockExtensions(statisticsDocument.getDouble("block-extensions"));
                this.statistics.setKnockbackWallClutch(statisticsDocument.getDouble("knockback-wall-clutch"));
                this.statistics.setHitBlockClutch(statisticsDocument.getDouble("hit-block-clutch"));
                this.statistics.setSpeedClutch(statisticsDocument.getDouble("speed-clutch"));
                this.statistics.setBedBurrow(statisticsDocument.getDouble("bed-burrow"));
                this.statistics.setJumpAround(statisticsDocument.getDouble("jump-around"));
                this.statistics.setBridgeStart(statisticsDocument.getDouble("bridge-start"));
                this.statistics.setBlockTotem(statisticsDocument.getDouble("block-totem"));
                this.statistics.setHighBlockClutch(statisticsDocument.getDouble("high-block-clutch"));
                this.statistics.setSafeTower(statisticsDocument.getDouble("safe-tower"));
                this.statistics.setEnderPearl(statisticsDocument.getDouble("ender-pearl"));
                this.statistics.setBrokenWallRun(statisticsDocument.getDouble("broken-wall-run"));
                this.statistics.setBedDefend(statisticsDocument.getDouble("bed-defend"));
            } catch (NullPointerException exception) {

            }
            player = Main.getInstance().getServer().getPlayer(uuid);
            loaded = true;
        }).start();
    }

    public void save() {
        new Thread(() -> {
            Document document = new Document()
                    .append("uuid", uuid.toString())
                    .append("name", name);

            Document settingsDocument = new Document()
                    .append("level", this.settings.getLevel())
                    .append("xp", this.settings.getXp())
                    .append("mode", this.settings.getMode().toString())
                    .append("scoreboard-enabled", this.settings.isScoreboardEnabled())
                    .append("player-visibility-enabled", this.settings.isPlayerVisibilityEnabled())
                    .append("spectating-enabled", this.settings.isSpectatingEnabled())
                    .append("current-block", this.settings.getCurrentBlock().getName());

            Document statisticsDocument = new Document()
                    .append("the-bridge-bypass", this.statistics.getTheBridgeBypass())
                    .append("knockback-clutch", this.statistics.getKnockbackClutch())
                    .append("ladder-clutch", this.statistics.getLadderClutch())
                    .append("fireball-jump", this.statistics.getFireballJump())
                    .append("tnt-jump", this.statistics.getTntJump())
                    .append("side-clutch", this.statistics.getSideClutch())
                    .append("wall-block-clutch", this.statistics.getWallBlockClutch())
                    .append("block-extensions", this.statistics.getBlockExtensions())
                    .append("knockback-wall-clutch", this.statistics.getKnockbackWallClutch())
                    .append("hit-block-clutch", this.statistics.getHitBlockClutch())
                    .append("speed-clutch", this.statistics.getSpeedClutch())
                    .append("bed-burrow", this.statistics.getBedBurrow())
                    .append("jump-around", this.statistics.getJumpAround())
                    .append("bridge-start", this.statistics.getBridgeStart())
                    .append("block-totem", this.statistics.getBlockTotem())
                    .append("high-block-clutch", this.statistics.getHighBlockClutch())
                    .append("safe-tower", this.statistics.getSafeTower())
                    .append("ender-pearl", this.statistics.getEnderPearl())
                    .append("broken-wall-run", this.statistics.getBrokenWallRun())
                    .append("bed-defend", this.statistics.getBedDefend());

            document.put("uuid", uuid.toString());
            document.put("name", name);
            document.put("settings", settingsDocument);
            document.put("statistics", statisticsDocument);

            Main.getInstance().getDatabaseManager().getPlayers().replaceOne(Filters.eq("uuid", uuid.toString()), document, new ReplaceOptions().upsert(true));
        }).start();
    }

    public Player getPlayerFromUniqueId() {
        return Bukkit.getPlayer(this.uuid);
    }

    public static Player getPlayer() {
        player = Main.getInstance().getServer().getPlayer(uuid);

        return player;
    }

    public static User getByUUID(UUID uuid) {
        if (users.containsKey(uuid)) {
            return users.get(uuid);
        }

        return null;
    }

    public void startTimer() {
        if (this.timer == null) {
            this.timer = new Timer(Main.getInstance());
        }
    }

    public void stopTimer() {
        if (this.timer != null) {
            this.timer.cancel();
            this.timer = null;
        }
    }

    public void resetTimer() {
        if (this.timer != null) {
            this.timer.setElapsedTime(0);
        }
    }

    public double getElapsedTime() {
        return this.timer.getElapsedTime();
    }
}

class Timer extends BukkitRunnable {

    private Main main;

    private double elapsedTime = 0.00D;

    public Timer(Main main) {
        this.main = main;

        this.runTaskTimer(main, 0, 1);
    }

    public void run() {
        this.elapsedTime += 0.05;
    }

    public double getElapsedTime() {
        return this.elapsedTime;
    }

    public void setElapsedTime(double elapsedTime) {
        this.elapsedTime = elapsedTime;
    }
}