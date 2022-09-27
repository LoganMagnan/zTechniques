package xyz.trixkz.bedwarspractice.menusystem.arena;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import xyz.trixkz.bedwarspractice.Main;
import xyz.trixkz.bedwarspractice.menusystem.arena.buttons.ArenaButton;
import xyz.trixkz.bedwarspractice.menusystem.arena.buttons.Button;
import xyz.trixkz.bedwarspractice.menusystem.arena.pagination.PageButton;
import xyz.trixkz.bedwarspractice.menusystem.arena.pagination.PaginatedMenu;
import xyz.trixkz.bedwarspractice.utils.ItemBuilder;
import xyz.trixkz.bedwarspractice.utils.Utils;

import java.util.HashMap;
import java.util.Map;

public class ArenaManagerMenu extends PaginatedMenu {

    @Override
    public String getPrePaginatedTitle(Player player) {
        return Utils.translate("&8Arena Management");
    }

    @Override
    public Map<Integer, Button> getAllPagesButtons(Player player) {
        Map<Integer, Button> buttons = new HashMap<>();
        Main.getInstance().getArenaManager().getArenas().forEach((s, arena) -> buttons.put(buttons.size(), new ArenaButton(arena)));

        return buttons;
    }

    @Override
    public Map<Integer, Button> getGlobalButtons(Player player) {
        Map<Integer, Button> buttons = new HashMap<>();

        buttons.put(0, new PageButton(-1, this));
        buttons.put(8, new PageButton(1, this));

        bottomTopButtons(false, buttons, new ItemBuilder(Material.STAINED_GLASS_PANE).name(" ").durability(15).build());

        return buttons;
    }

    @Override
    public int getSize() {
        return 4 * 9;
    }

    @Override
    public int getMaxItemsPerPage(Player player) {
        return 9 * 2;
    }
}
