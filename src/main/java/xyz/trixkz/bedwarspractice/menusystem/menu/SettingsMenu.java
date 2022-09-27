package xyz.trixkz.bedwarspractice.menusystem.menu;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;
import xyz.trixkz.bedwarspractice.Main;
import xyz.trixkz.bedwarspractice.managers.user.User;
import xyz.trixkz.bedwarspractice.menusystem.Menu;
import xyz.trixkz.bedwarspractice.menusystem.PlayerMenuUtil;
import xyz.trixkz.bedwarspractice.menusystem.arena.pagination.settings.Mode;
import xyz.trixkz.bedwarspractice.utils.ItemBuilder;
import xyz.trixkz.bedwarspractice.utils.Utils;

import java.util.ArrayList;
import java.util.List;

public class SettingsMenu extends Menu {

    private Main main;

    public SettingsMenu(PlayerMenuUtil playerMenuUtil, Main main) {
        super(playerMenuUtil);

        this.main = main;
    }

    @Override
    public String getMenuName() {
        return Utils.translate("&eChange a setting...");
    }

    @Override
    public int getSlots() {
        return 27;
    }

    @Override
    public void handleMenu(InventoryClickEvent event) {
        Player player = (Player) event.getWhoClicked();

        User user = User.getByUUID(player.getUniqueId());

        if (event.getView().getTitle().equalsIgnoreCase(Utils.translate("&eChange a setting..."))) {
            switch (event.getCurrentItem().getType()) {
                case PAINTING:
                    user.getSettings().setScoreboardEnabled(!user.getSettings().isScoreboardEnabled());

                    player.updateInventory();

                    break;
                case EYE_OF_ENDER:
                    user.getSettings().setPlayerVisibilityEnabled(!user.getSettings().isPlayerVisibilityEnabled());

                    this.main.getUserManager().updatePlayerVisibility();

                    player.updateInventory();

                    break;
                case SLIME_BALL:
                    switch (user.getSettings().getMode()) {
                        case EASY:
                            user.getSettings().setMode(Mode.MEDIUM);

                            break;
                        case MEDIUM:
                            user.getSettings().setMode(Mode.HARD);

                            break;
                        case HARD:
                            user.getSettings().setMode(Mode.EASY);

                            break;
                    }

                    player.updateInventory();

                    break;
            }
        }
    }

    @Override
    public void setMenuItems() {
        Player player = User.getPlayer();

        User user = User.getByUUID(player.getUniqueId());

        List<String> scoreboardEnabledLore = new ArrayList<String>();
        scoreboardEnabledLore.add(Utils.translate(""));
        scoreboardEnabledLore.add(Utils.translate("&9If enabled, you will be"));
        scoreboardEnabledLore.add(Utils.translate("&9able to see the scoreboard"));
        scoreboardEnabledLore.add(Utils.translate(""));
        scoreboardEnabledLore.add(Utils.translate((user.getSettings().isScoreboardEnabled() ? "&a▶ " : "") + "&9Show scoreboard"));
        scoreboardEnabledLore.add(Utils.translate((!user.getSettings().isScoreboardEnabled() ? "&c▶ " : "") + "&9Hide scoreboard"));

        ItemStack scoreboardEnabledItemStack = new ItemBuilder(Material.PAINTING).name(Utils.translate("&dScoreboard")).lore(scoreboardEnabledLore).build();

        List<String> playerVisibilityLore = new ArrayList<String>();
        playerVisibilityLore.add(Utils.translate(""));
        playerVisibilityLore.add(Utils.translate("&9If enabled, you will be"));
        playerVisibilityLore.add(Utils.translate("&9able to see all of the players"));
        playerVisibilityLore.add(Utils.translate(""));
        playerVisibilityLore.add(Utils.translate((user.getSettings().isPlayerVisibilityEnabled() ? "&a▶ " : "") + "&9Show all players"));
        playerVisibilityLore.add(Utils.translate((!user.getSettings().isPlayerVisibilityEnabled() ? "&c▶ " : "") + "&9Hide all players"));

        ItemStack playerVisibilityItemStack = new ItemBuilder(Material.EYE_OF_ENDER).name(Utils.translate("&dPlayer Visibility")).lore(playerVisibilityLore).build();

        List<String> modeLore = new ArrayList<String>();
        modeLore.add(Utils.translate(""));

        switch (user.getSettings().getMode()) {
            case EASY:
                modeLore.add(Utils.translate("&a▶ &9Easy"));
                modeLore.add(Utils.translate("&9Medium"));
                modeLore.add(Utils.translate("&9Hard"));

                break;
            case MEDIUM:
                modeLore.add(Utils.translate("&9Easy"));
                modeLore.add(Utils.translate("&c▶ &9Medium"));
                modeLore.add(Utils.translate("&9Hard"));

                break;
            case HARD:
                modeLore.add(Utils.translate("&9Easy"));
                modeLore.add(Utils.translate("&9Medium"));
                modeLore.add(Utils.translate("&4▶ &9Hard"));

                break;
        }

        ItemStack modeItemStack = new ItemBuilder(Material.SLIME_BALL).name(Utils.translate("&dMode")).lore(modeLore).build();

        this.inventory.setItem(11, scoreboardEnabledItemStack);
        this.inventory.setItem(13, playerVisibilityItemStack);
        this.inventory.setItem(15, modeItemStack);

        this.setFillerGlass();
    }
}
