package xyz.trixkz.bedwarspractice.runnable;

import lombok.RequiredArgsConstructor;
import org.bukkit.Material;
import org.bukkit.block.BlockFace;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.Vector;
import xyz.trixkz.bedwarspractice.Main;
import xyz.trixkz.bedwarspractice.customentities.CustomZombie;
import xyz.trixkz.bedwarspractice.customentities.types.CustomEntityTypes;
import xyz.trixkz.bedwarspractice.managers.game.Game;
import xyz.trixkz.bedwarspractice.managers.game.GameState;
import xyz.trixkz.bedwarspractice.managers.user.User;
import xyz.trixkz.bedwarspractice.utils.CustomLocation;
import xyz.trixkz.bedwarspractice.utils.Utils;
import java.text.DecimalFormat;

@RequiredArgsConstructor
public class GameRunnable extends BukkitRunnable {

    private final Game game;

    private int seconds;

    private boolean messageSent = false;

    private int hitDelay;

    private boolean entitiesSpawned = false;

    @Override
    public void run() {
        Player player = this.game.getPlayer();

        User user = User.getByUUID(player.getUniqueId());

        DecimalFormat decimalFormat = new DecimalFormat("0.00");

        CustomLocation spawnPointLocation = game.getStandaloneArena() != null ? game.getStandaloneArena().getA() : game.getArena().getA();

        switch (this.game.getGameState()) {
            case SPAWN:
                break;
            case PLAYING:
                user.startTimer();

                break;
            case STOPPING:
                this.cancel();

                Main.getInstance().getChunkRestorationManager().reset(this.game.getStandaloneArena());

                this.game.setGameState(GameState.SPAWN);

                user.stopTimer();

                break;
        }

        switch (user.getUserState()) {
            case SPAWN:
                break;
            case THE_BRIDGE_BYPASS:
                if (!(player.getLocation().distance(spawnPointLocation.toBukkitLocation()) > 4)) {
                    return;
                }

                if (!(player.getLocation().getY() < spawnPointLocation.toBukkitLocation().getY() - 6)) {
                    if (this.seconds == 50) {
                        Main.getInstance().getChunkRestorationManager().reset(this.game.getStandaloneArena());

                        player.teleport(spawnPointLocation.toBukkitLocation());

                        Main.getInstance().getUtils().givePlayerKitItems(player);

                        player.sendMessage(Utils.translate("&cYou did not bypass, please try again"));
                        player.sendMessage(Utils.translate("&aOld time: &a&l" + decimalFormat.format(user.getElapsedTime())));

                        user.resetTimer();

                        player.sendMessage(Utils.translate("&aNew timer started"));

                        this.seconds = 0;
                        this.messageSent = false;

                        return;
                    }

                    if (!messageSent) {
                        player.sendMessage(Utils.translate("&cYou have &c&l6 &cseconds to go below the following Y level: &c&l" + (spawnPointLocation.toBukkitLocation().getY() - 10)));

                        this.messageSent = true;
                    }

                    this.seconds++;

                    return;
                }

                this.seconds = 0;
                this.messageSent = false;

                this.isOnEndPortal(player, user, spawnPointLocation, decimalFormat);
                this.getVoidDeath(player, user, spawnPointLocation, decimalFormat, 20);

                break;
            case KNOCKBACK_CLUTCH:
            case KNOCKBACK_WALL_CLUTCH:
                if (player.getLocation().distance(spawnPointLocation.toBukkitLocation()) > 4) {
                    if (this.hitDelay == 20) {
                        player.setVelocity(this.getRandomDirection(player));

                        this.hitDelay = 0;
                    }

                    this.hitDelay++;
                }

                this.isOnGoldPressurePlate(player, user, spawnPointLocation, decimalFormat);
                this.getVoidDeath(player, user, spawnPointLocation, decimalFormat, 5);

                break;
            case LADDER_CLUTCH:
                if (this.isOnLadder(player) && this.isOnDiamondBlock(player, Material.LADDER)) {
                    Main.getInstance().getChunkRestorationManager().reset(this.game.getStandaloneArena());

                    player.teleport(spawnPointLocation.toBukkitLocation());

                    Main.getInstance().getUtils().givePlayerKitItems(player);

                    player.sendMessage(Utils.translate("&aOld time: &a&l" + decimalFormat.format(user.getElapsedTime())));

                    user.resetTimer();

                    player.sendMessage(Utils.translate("&aNew timer started"));

                    return;
                }

                if (this.isOnLadder(player)) {
                    Main.getInstance().getChunkRestorationManager().reset(this.game.getStandaloneArena());

                    player.teleport(spawnPointLocation.toBukkitLocation());

                    Main.getInstance().getUtils().givePlayerKitItems(player);

                    player.sendMessage(Utils.translate("&aSuccess, you ladder clutched in: &a&l" + decimalFormat.format(user.getElapsedTime())));

                    if (user.getStatistics().getLadderClutch() < user.getElapsedTime()) {
                        user.getStatistics().setLadderClutch(user.getElapsedTime());
                    }

                    user.resetTimer();

                    user.getSettings().addRandomXp(player);

                    player.sendMessage(Utils.translate("&aNew timer started"));

                    return;
                }

                if (player.getLocation().getY() <= spawnPointLocation.toBukkitLocation().getY() - 20 || this.isOnDiamondBlock(player, Material.DIAMOND_BLOCK)) {
                    Main.getInstance().getChunkRestorationManager().reset(this.game.getStandaloneArena());

                    player.teleport(spawnPointLocation.toBukkitLocation());

                    Main.getInstance().getUtils().givePlayerKitItems(player);

                    player.sendMessage(Utils.translate("&aOld time: &a&l" + decimalFormat.format(user.getElapsedTime())));

                    user.resetTimer();

                    player.sendMessage(Utils.translate("&aNew timer started"));

                    return;
                }

                break;
            case FIREBALL_JUMP:
            case TNT_JUMP:
            case WALL_BLOCK_CLUTCH:
            case BLOCK_EXTENSIONS:
            case SPEED_CLUTCH:
            case JUMP_AROUND:
            case BRIDGE_START:
            case ENDER_PEARL:
            case BROKEN_WALL_RUN:
                if (player.getLocation().getY() > spawnPointLocation.toBukkitLocation().getY() + 5) {
                    Main.getInstance().getChunkRestorationManager().reset(this.game.getStandaloneArena());

                    player.teleport(spawnPointLocation.toBukkitLocation());

                    Main.getInstance().getUtils().givePlayerKitItems(player);

                    player.sendMessage(Utils.translate("&aOld time: &a&l" + decimalFormat.format(user.getElapsedTime())));

                    user.resetTimer();

                    player.sendMessage(Utils.translate("&aNew timer started"));

                    return;
                }

                this.isOnGoldPressurePlate(player, user, spawnPointLocation, decimalFormat);
                this.getVoidDeath(player, user, spawnPointLocation, decimalFormat, 5);

                break;
            case SIDE_CLUTCH:
                break;
            case HIT_BLOCK_CLUTCH:
                break;
            case BED_BURROW:
                break;
            case BLOCK_TOTEM:
                break;
            case HIGH_BLOCK_CLUTCH:
                this.isOnGoldPressurePlate(player, user, spawnPointLocation, decimalFormat);
                this.getVoidDeath(player, user, spawnPointLocation, decimalFormat, 20);

                break;
            case SAFE_TOWER:
                break;
            case BED_DEFEND:
                if (!this.entitiesSpawned) {
                    this.entitiesSpawned = true;

                    for (int i = 0; i < 5; i++) {
                        CustomEntityTypes.spawnEntity(new CustomZombie(player.getWorld(), user), player.getLocation());
                    }
                }

//                if (user.getElapsedTime() >= 30) {
//                    Main.getInstance().getChunkRestorationManager().reset(game.getStandaloneArena());
//
//                    player.teleport(spawnPointLocation.toBukkitLocation());
//
//                    Main.getInstance().getUtils().givePlayerKitItems(player);
//
//                    player.sendMessage(Utils.translate("&aSuccess, you completed the course in: &a&l" + decimalFormat.format(user.getElapsedTime())));
//
//                    if (user.getStatistics().getBedDefend() < user.getElapsedTime()) {
//                        user.getStatistics().setBedDefend(user.getElapsedTime());
//                    }
//
//                    user.resetTimer();
//
//                    user.getSettings().addRandomXp(player);
//
//                    player.sendMessage(Utils.translate("&aNew timer started"));
//
//                    this.entitiesSpawned = false;
//
//                    return;
//                }

                this.getVoidDeath(player, user, spawnPointLocation, decimalFormat, 5);

                break;
        }
    }

    private Vector getRandomDirection(Player player) {
        Vector vector = new Vector();
        vector.setX(Math.random() * 2 - 1D);
        vector.setY(Math.random() + 0.5);

        User user = User.getByUUID(player.getUniqueId());

        switch (user.getSettings().getMode()) {
            case EASY:
                vector.normalize().multiply(0.4);

                break;
            case MEDIUM:
                vector.normalize().multiply(0.6);

                break;
            case HARD:
                vector.normalize().multiply(0.8);

                break;
        }

        return vector;
    }

    private void isOnGoldPressurePlate(Player player, User user, CustomLocation spawnPointLocation, DecimalFormat decimalFormat) {
        if (player.getLocation().getBlock().getType() == Material.GOLD_PLATE) {
            Main.getInstance().getChunkRestorationManager().reset(this.game.getStandaloneArena());

            player.teleport(spawnPointLocation.toBukkitLocation());

            Main.getInstance().getUtils().givePlayerKitItems(player);

            player.sendMessage(Utils.translate("&aSuccess, you completed the course in: &a&l" + decimalFormat.format(user.getElapsedTime())));

            switch (user.getUserState()) {
                case KNOCKBACK_CLUTCH:
                    if (user.getStatistics().getKnockbackClutch() < user.getElapsedTime()) {
                        user.getStatistics().setKnockbackClutch(user.getElapsedTime());
                    }

                    break;
                case KNOCKBACK_WALL_CLUTCH:
                    if (user.getStatistics().getKnockbackWallClutch() < user.getElapsedTime()) {
                        user.getStatistics().setKnockbackWallClutch(user.getElapsedTime());
                    }

                    break;
                case FIREBALL_JUMP:
                    if (user.getStatistics().getFireballJump() < user.getElapsedTime()) {
                        user.getStatistics().setFireballJump(user.getElapsedTime());
                    }

                    break;
                case TNT_JUMP:
                    if (user.getStatistics().getTntJump() < user.getElapsedTime()) {
                        user.getStatistics().setTntJump(user.getElapsedTime());
                    }

                    break;
                case WALL_BLOCK_CLUTCH:
                    if (user.getStatistics().getWallBlockClutch() < user.getElapsedTime()) {
                        user.getStatistics().setWallBlockClutch(user.getElapsedTime());
                    }

                    break;
                case BLOCK_EXTENSIONS:
                    if (user.getStatistics().getBlockExtensions() < user.getElapsedTime()) {
                        user.getStatistics().setBlockExtensions(user.getElapsedTime());
                    }

                    break;
                case SPEED_CLUTCH:
                    if (user.getStatistics().getSpeedClutch() < user.getElapsedTime()) {
                        user.getStatistics().setSpeedClutch(user.getElapsedTime());
                    }

                    break;
                case JUMP_AROUND:
                    if (user.getStatistics().getJumpAround() < user.getElapsedTime()) {
                        user.getStatistics().setJumpAround(user.getElapsedTime());
                    }

                    break;
                case BRIDGE_START:
                    if (user.getStatistics().getBridgeStart() < user.getElapsedTime()) {
                        user.getStatistics().setBridgeStart(user.getElapsedTime());
                    }

                    break;
                case ENDER_PEARL:
                    if (user.getStatistics().getEnderPearl() < user.getElapsedTime()) {
                        user.getStatistics().setEnderPearl(user.getElapsedTime());
                    }

                    break;
                case BROKEN_WALL_RUN:
                    if (user.getStatistics().getBrokenWallRun() < user.getElapsedTime()) {
                        user.getStatistics().setBrokenWallRun(user.getElapsedTime());
                    }

                    break;
                case SIDE_CLUTCH:
                    break;
                case HIT_BLOCK_CLUTCH:
                    break;
                case BED_BURROW:
                    break;
                case BLOCK_TOTEM:
                    break;
                case HIGH_BLOCK_CLUTCH:
                    if (user.getStatistics().getHighBlockClutch() < user.getElapsedTime()) {
                        user.getStatistics().setBrokenWallRun(user.getElapsedTime());
                    }

                    break;
                case SAFE_TOWER:
                    break;
                case BED_DEFEND:
                    break;
            }

            user.resetTimer();

            user.getSettings().addRandomXp(player);

            player.sendMessage(Utils.translate("&aNew timer started"));

            return;
        }
    }

    private void isOnEndPortal(Player player, User user, CustomLocation spawnPointLocation, DecimalFormat decimalFormat) {
        if (player.getLocation().getBlock().getType() == Material.ENDER_PORTAL) {
            Main.getInstance().getChunkRestorationManager().reset(this.game.getStandaloneArena());

            player.teleport(spawnPointLocation.toBukkitLocation());

            Main.getInstance().getUtils().givePlayerKitItems(player);

            player.sendMessage(Utils.translate("&aSuccess, you completed the course in: &a&l" + decimalFormat.format(user.getElapsedTime())));

            switch (user.getUserState()) {
                case THE_BRIDGE_BYPASS:
                    if (user.getStatistics().getTheBridgeBypass() < user.getElapsedTime()) {
                        user.getStatistics().setTheBridgeBypass(user.getElapsedTime());
                    }

                    break;
            }

            user.resetTimer();

            user.getSettings().addRandomXp(player);

            player.sendMessage(Utils.translate("&aNew timer started"));

            return;
        }
    }

    private void getVoidDeath(Player player, User user, CustomLocation spawnPointLocation, DecimalFormat decimalFormat, int voidDeath) {
        if (player.getLocation().getY() <= spawnPointLocation.toBukkitLocation().getY() - voidDeath) {
            Main.getInstance().getChunkRestorationManager().reset(this.game.getStandaloneArena());

            player.teleport(spawnPointLocation.toBukkitLocation());

            Main.getInstance().getUtils().givePlayerKitItems(player);

            player.sendMessage(Utils.translate("&aOld time: &a&l" + decimalFormat.format(user.getElapsedTime())));

            user.resetTimer();

            player.sendMessage(Utils.translate("&aNew timer started"));

            return;
        }
    }

    private boolean isOnLadder(Player player) {
        if (player.getLocation().clone().subtract(0, 0.1, 0).getBlock().getType() == Material.LADDER) {
            return true;
        }

        return false;
    }

    private boolean isOnDiamondBlock(Player player, Material material) {
        if (player.getLocation().getBlock().getRelative(BlockFace.DOWN).getType() == material) {
            return true;
        }

        return false;
    }
}
