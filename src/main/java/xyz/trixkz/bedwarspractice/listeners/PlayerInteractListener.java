package xyz.trixkz.bedwarspractice.listeners;

import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.entity.Fireball;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import xyz.trixkz.bedwarspractice.Main;
import xyz.trixkz.bedwarspractice.managers.user.User;
import xyz.trixkz.bedwarspractice.managers.user.UserState;
import xyz.trixkz.bedwarspractice.menusystem.menu.BlockMenu;
import xyz.trixkz.bedwarspractice.menusystem.menu.PracticeATechniqueMenu;
import xyz.trixkz.bedwarspractice.menusystem.menu.SettingsMenu;
import xyz.trixkz.bedwarspractice.menusystem.arena.pagination.settings.Mode;
import xyz.trixkz.bedwarspractice.utils.Items;
import xyz.trixkz.bedwarspractice.utils.Utils;

public class PlayerInteractListener implements Listener {

    private Main main;

    public PlayerInteractListener(Main main) {
        this.main = main;
    }

    @EventHandler
    public void onPlayerInteract(PlayerInteractEvent event) {
        Player player = event.getPlayer();

        if (player.getGameMode() == GameMode.CREATIVE) {
            return;
        }

        User user = User.getByUUID(player.getUniqueId());

        ItemStack modeItemStack = Items.mode.clone();

        ItemMeta modeItemMeta = modeItemStack.getItemMeta();

        modeItemMeta.setDisplayName(Utils.translate("&e&l» &dMode &7┃ " + user.getSettings().getMode().getName() + " &e&l«"));

        modeItemStack.setItemMeta(modeItemMeta);

        if (event.getAction() == Action.RIGHT_CLICK_AIR || event.getAction() == Action.RIGHT_CLICK_BLOCK) {
            if (event.getItem().equals(Items.practiceATechnique)) {
                new PracticeATechniqueMenu(this.main.getPlayerMenuUtil(player), this.main).open();

                event.setCancelled(true);
            } else if (event.getItem().equals(Items.settings)) {
                new SettingsMenu(this.main.getPlayerMenuUtil(player), this.main).open();

                event.setCancelled(true);
            } else if (event.getItem().equals(Items.blockSelector)) {
                new BlockMenu().openMenu(player);

                event.setCancelled(true);
            } else if (event.getItem().equals(modeItemStack)) {
                switch (user.getSettings().getMode()) {
                    case EASY:
                        user.getSettings().setMode(Mode.MEDIUM);

                        modeItemMeta.setDisplayName(Utils.translate("&e&l» &dMode &7┃ " + user.getSettings().getMode().getName() + " &e&l«"));

                        modeItemStack.setItemMeta(modeItemMeta);

                        player.getInventory().setItem(6, modeItemStack);

                        break;
                    case MEDIUM:
                        user.getSettings().setMode(Mode.HARD);

                        modeItemMeta.setDisplayName(Utils.translate("&e&l» &dMode &7┃ " + user.getSettings().getMode().getName() + " &e&l«"));

                        modeItemStack.setItemMeta(modeItemMeta);

                        player.getInventory().setItem(6, modeItemStack);

                        break;
                    case HARD:
                        user.getSettings().setMode(Mode.EASY);

                        modeItemMeta.setDisplayName(Utils.translate("&e&l» &dMode &7┃ " + user.getSettings().getMode().getName() + " &e&l«"));

                        modeItemStack.setItemMeta(modeItemMeta);

                        player.getInventory().setItem(6, modeItemStack);

                        break;
                }
            } else if (event.getItem().equals(Items.goBack)) {
                user.setUserState(UserState.SPAWN);

                this.main.getGameManager().removeGame(this.main.getGameManager().getGameFromUUID(player.getUniqueId()));

                player.setGameMode(GameMode.SURVIVAL);
                player.teleport(player.getWorld().getSpawnLocation());

                player.sendMessage(Utils.translate("&aSending you to the spawn"));

                event.setCancelled(true);
            } else if (event.getItem().getType() == Material.FIREBALL) {
                ItemStack itemStack = event.getItem().clone();

                Fireball fireball = player.launchProjectile(Fireball.class);
                fireball.setShooter(player);
                fireball.setIsIncendiary(false);
                fireball.setBounce(false);
                fireball.setYield(2);
                fireball.setVelocity(fireball.getVelocity().multiply(2));

                if (itemStack.getAmount() == 1) {
                    player.setItemInHand(new ItemStack(Material.FIREBALL));
                } else {
                    itemStack.setAmount(itemStack.getAmount() - 1);

                    player.setItemInHand(itemStack);
                }
            }
        }
    }
}
