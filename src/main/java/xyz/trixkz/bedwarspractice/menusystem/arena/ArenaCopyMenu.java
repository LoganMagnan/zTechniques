package xyz.trixkz.bedwarspractice.menusystem.arena;

import lombok.AllArgsConstructor;
import org.bukkit.entity.Player;
import xyz.trixkz.bedwarspractice.Main;
import xyz.trixkz.bedwarspractice.managers.arena.Arena;
import xyz.trixkz.bedwarspractice.managers.arena.StandaloneArena;
import xyz.trixkz.bedwarspractice.menusystem.arena.buttons.ArenaCopyButton;
import xyz.trixkz.bedwarspractice.menusystem.arena.buttons.Button;
import xyz.trixkz.bedwarspractice.utils.Utils;

import java.util.HashMap;
import java.util.Map;

@AllArgsConstructor
public class ArenaCopyMenu extends Menu {

    private final Arena arena;

    @Override
    public String getTitle(Player player) {
        return Utils.translate("&8Arena Copies");
    }

    @Override
    public Map<Integer, Button> getButtons(Player player) {
        Map<Integer, Button> buttons = new HashMap<>();

        for (StandaloneArena arenaCopy : Main.getInstance().getArenaManager().getArena(arena.getName()).getStandaloneArenas()) {
            buttons.put(buttons.size(), new ArenaCopyButton(arena, arenaCopy));
        }

        return buttons;
    }
}
