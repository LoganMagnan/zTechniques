package xyz.trixkz.bedwarspractice.menusystem.arena;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.bukkit.entity.Player;
import xyz.trixkz.bedwarspractice.managers.arena.Arena;
import xyz.trixkz.bedwarspractice.menusystem.arena.buttons.ArenaGenerateButton;
import xyz.trixkz.bedwarspractice.menusystem.arena.buttons.Button;
import xyz.trixkz.bedwarspractice.utils.Utils;

import java.util.HashMap;
import java.util.Map;

@AllArgsConstructor
public class ArenaGenerationMenu extends Menu {

    private final Arena arena;

    @Getter private final int[] clonableAmounts = {1, 2, 3, 4, 5, 10, 15};

    @Override
    public String getTitle(Player player) {
        return Utils.translate("&8Arena Copies Generation");
    }

    @Override
    public Map<Integer, Button> getButtons(Player player) {
        Map<Integer, Button> buttons = new HashMap<>();

        for (int curr : clonableAmounts) {
            buttons.put(1 + buttons.size(), new ArenaGenerateButton(arena, curr));
        }

        return buttons;
    }
}
