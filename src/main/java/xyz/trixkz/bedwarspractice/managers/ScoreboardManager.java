package xyz.trixkz.bedwarspractice.managers;

import assemble.AssembleAdapter;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import xyz.trixkz.bedwarspractice.Main;
import xyz.trixkz.bedwarspractice.kits.Kit;
import xyz.trixkz.bedwarspractice.listeners.events.GameStopEvent;
import xyz.trixkz.bedwarspractice.managers.user.User;
import xyz.trixkz.bedwarspractice.managers.user.UserState;
import xyz.trixkz.bedwarspractice.utils.Items;
import xyz.trixkz.bedwarspractice.utils.Utils;

import java.lang.reflect.InvocationTargetException;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

public class ScoreboardManager implements AssembleAdapter, Listener {

    private Main main;

    public ScoreboardManager(Main main) {
        this.main = main;
    }

    @Override
    public String getTitle(Player player) {
        return Utils.translate("&d&lTechniques");
    }

    @Override
    public List<String> getLines(Player player) {
        List<String> lines = new ArrayList<String>();

        User user = User.getByUUID(player.getUniqueId());

        if (!user.getSettings().isScoreboardEnabled()) {
            return null;
        }

        DecimalFormat decimalFormat = new DecimalFormat("0.00");

//        Object entityPlayer;
//
//        int ping = 0;
//
//        try {
//            entityPlayer = player.getClass().getMethod("getHandle").invoke(player);
//            ping = (int) entityPlayer.getClass().getField("ping").get(entityPlayer);
//        } catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException | NoSuchMethodException | SecurityException | NoSuchFieldException exception) {
//            exception.printStackTrace();
//        }

        lines.add(Utils.translate("&7&m----------------------"));
//        lines.add(Utils.translate("&bYou: &f" + player.getName()));
//        lines.add(Utils.translate("&bPing: &f" + ping));
        lines.add(Utils.translate("&fOnline: &d" + this.main.getServer().getOnlinePlayers().size()));
        lines.add(Utils.translate("&fPlaying: &d" + this.main.getGameManager().getGames().size()));
        lines.add(Utils.translate(""));

        if (this.main.getGameManager().getGameFromUUID(player.getUniqueId()) == null) {
            lines.add(Utils.translate("&fLevel: &d" + user.getSettings().getLevel()));

            String finishedProgress = "";

            int notFinishedProgress = 10;

            for (int i = 0; i < user.getSettings().getXp() * 100; i++) {
                if (i % 10 == 0) {
                    finishedProgress += "⬛";

                    notFinishedProgress--;
                }
            }

            String leftOverProgress = "";

            for (int i = 1; i <= notFinishedProgress; i++) {
                leftOverProgress += "⬛";
            }

            lines.add(Utils.translate("&8" + finishedProgress + "&7" + leftOverProgress + " &7(" + ((int) (user.getSettings().getXp() * 100)) + "%&7)"));
            lines.add(Utils.translate(""));
        }

        lines.add(Utils.translate("&fCurrent Technique:"));

        switch (user.getUserState()) {
            case THE_BRIDGE_BYPASS:
            case KNOCKBACK_CLUTCH:
            case LADDER_CLUTCH:
            case FIREBALL_JUMP:
            case TNT_JUMP:
            case SIDE_CLUTCH:
            case WALL_BLOCK_CLUTCH:
            case BLOCK_EXTENSIONS:
            case KNOCKBACK_WALL_CLUTCH:
            case HIT_BLOCK_CLUTCH:
            case SPEED_CLUTCH:
            case BED_BURROW:
            case JUMP_AROUND:
            case BRIDGE_START:
            case BLOCK_TOTEM:
            case HIGH_BLOCK_CLUTCH:
            case SAFE_TOWER:
            case ENDER_PEARL:
            case BROKEN_WALL_RUN:
            case BED_DEFEND:
                for (Kit kit : this.main.getKits()) {
                    if (user.getUserState() == kit.getUserState()) {
                        lines.add(Utils.translate("  &fName: &d" + kit.getName()));
                    }
                }

                break;
        }

        switch (user.getUserState()) {
            case THE_BRIDGE_BYPASS:
                break;
            case KNOCKBACK_CLUTCH:
                lines.add(Utils.translate("  &fMode: &d" + user.getSettings().getMode().getName()));

                break;
            case LADDER_CLUTCH:
                break;
            case FIREBALL_JUMP:
                break;
            case TNT_JUMP:
                break;
            case SIDE_CLUTCH:
                break;
            case WALL_BLOCK_CLUTCH:
                break;
            case BLOCK_EXTENSIONS:
                break;
            case KNOCKBACK_WALL_CLUTCH:
                lines.add(Utils.translate("  &fMode: &d" + user.getSettings().getMode().getName()));

                break;
            case HIT_BLOCK_CLUTCH:
                break;
            case SPEED_CLUTCH:
                break;
            case BED_BURROW:
                break;
            case JUMP_AROUND:
                break;
            case BRIDGE_START:
                break;
            case BLOCK_TOTEM:
                break;
            case HIGH_BLOCK_CLUTCH:
                break;
            case SAFE_TOWER:
                break;
            case ENDER_PEARL:
                break;
            case BROKEN_WALL_RUN:
                break;
            case BED_DEFEND:
                break;
        }

        if (user.getUserState() == UserState.SPAWN) {
            lines.add(Utils.translate("  &dNone"));
        }

        if (this.main.getGameManager().getGameFromUUID(player.getUniqueId()) != null) {
            lines.add(Utils.translate(""));
            lines.add(Utils.translate("&fTime: &d" + decimalFormat.format(user.getElapsedTime())));
        }

        lines.add(Utils.translate(""));
        lines.add(Utils.translate("&dtilly.rip"));
        lines.add(Utils.translate("&7&m----------------------"));

        return lines;
    }

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {
        Player player = event.getPlayer();

        event.setJoinMessage(null);

        player.getInventory().clear();

        player.teleport(player.getWorld().getSpawnLocation());

        player.getInventory().setItem(0, Items.practiceATechnique);
        player.getInventory().setItem(4, Items.blockSelector);
        player.getInventory().setItem(8, Items.settings);

        this.main.getUserManager().updatePlayerVisibility();
        this.main.getHologramManager().show(player);
    }

    @EventHandler
    public void onPlayerQuit(PlayerQuitEvent event) {
        Player player = event.getPlayer();

        User user = User.getByUUID(player.getUniqueId());

        if (user.getUserState() == UserState.SPAWN) {
            return;
        }

        this.main.getGameManager().removeGame(this.main.getGameManager().getGames().get(player.getUniqueId()));
        this.main.getHologramManager().hide(player);
    }
}
