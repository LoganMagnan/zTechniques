package xyz.trixkz.bedwarspractice.menusystem.menu;

import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import xyz.trixkz.bedwarspractice.Main;
import xyz.trixkz.bedwarspractice.managers.game.Game;
import xyz.trixkz.bedwarspractice.kits.Kit;
import xyz.trixkz.bedwarspractice.managers.arena.Arena;
import xyz.trixkz.bedwarspractice.managers.user.User;
import xyz.trixkz.bedwarspractice.menusystem.Menu;
import xyz.trixkz.bedwarspractice.menusystem.PlayerMenuUtil;
import xyz.trixkz.bedwarspractice.utils.Utils;
import java.util.concurrent.ThreadLocalRandom;

public class PracticeATechniqueMenu extends Menu {

    private Main main;

    public PracticeATechniqueMenu(PlayerMenuUtil playerMenuUtil, Main main) {
        super(playerMenuUtil);

        this.main = main;
    }

    @Override
    public String getMenuName() {
        return Utils.translate("&eClick a technique to practice...");
    }

    @Override
    public int getSlots() {
        return 36;
    }

    @Override
    public void handleMenu(InventoryClickEvent event) {
        Player player = (Player) event.getWhoClicked();

        if (event.getView().getTitle().equalsIgnoreCase(Utils.translate("&eClick a technique to practice..."))) {
            for (Kit kit : this.main.getKits()) {
                if (event.getCurrentItem().equals(kit.makeItemStack())) {
                    int random = ThreadLocalRandom.current().nextInt(kit.getArenas().size());

                    Arena arena = this.main.getArenaManager().getArena(kit.getArenas().get(random));

                    Game game = new Game(arena, kit, player);
                    game.setGameID(player.getUniqueId());

                    User user = User.getByUUID(player.getUniqueId());
                    user.setUserState(kit.getUserState());

                    this.main.getUtils().givePlayerKitItems(player);
                    this.main.getGameManager().createGame(game);
                }
            }
        }
    }

    @Override
    public void setMenuItems() {
        this.inventory.setItem(30, this.FILLER_GLASS);
        this.inventory.setItem(31, this.FILLER_GLASS);
        this.inventory.setItem(32, this.FILLER_GLASS);

        for (int i = 0; i < 10; i++) {
            if (this.inventory.getItem(i) == null) {
                this.inventory.setItem(i, this.FILLER_GLASS);
            }
        }

        this.inventory.setItem(17, this.FILLER_GLASS);
        this.inventory.setItem(18, this.FILLER_GLASS);
        this.inventory.setItem(26, this.FILLER_GLASS);
        this.inventory.setItem(27, this.FILLER_GLASS);
        this.inventory.setItem(35, this.FILLER_GLASS);

        for (int i = 26; i < 36; i++) {
            if (this.inventory.getItem(i) == null) {
                this.inventory.setItem(i, this.FILLER_GLASS);
            }
        }

        for (Kit kit : this.main.getKits()) {
            this.inventory.addItem(kit.makeItemStack());
        }

        this.setFillerGlass();
    }
}
