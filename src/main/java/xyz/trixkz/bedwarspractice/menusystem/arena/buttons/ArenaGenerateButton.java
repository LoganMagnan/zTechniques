package xyz.trixkz.bedwarspractice.menusystem.arena.buttons;

import lombok.AllArgsConstructor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.inventory.ItemStack;
import xyz.trixkz.bedwarspractice.managers.arena.Arena;
import xyz.trixkz.bedwarspractice.utils.ItemBuilder;
import xyz.trixkz.bedwarspractice.utils.Utils;
import java.util.Arrays;

@AllArgsConstructor
public class ArenaGenerateButton extends Button {

    private final Arena arena;
    private final int currentCopyAmount;

    @Override
    public ItemStack getButtonItem(Player player) {
        return new ItemBuilder(Material.PAPER)
                .name("&aCreate " + currentCopyAmount + " Arena Copies")
                .lore(Utils.translate(
                        Arrays.asList(
                                " ",
                                "&7Clicking here will generate &b&l" + currentCopyAmount,
                                "&7arenas for the map &b" + arena.getName() + "&7!",
                                " ",
                                "&a&lLEFT CLICK &ato generate arenas")
                ))
                .amount(currentCopyAmount)
                .build();
    }

    @Override
    public void clicked(Player player, int slot, ClickType clickType, int hotbarButton) {
        player.performCommand("arena generate " + arena.getName() + " " + currentCopyAmount);

        player.sendMessage(Utils.translate("&b&lGENERATING ARENAS&b..."));
        player.sendMessage(Utils.translate(" "));
        player.sendMessage(Utils.translate("&fBedwars is currently generating copies for:"));
        player.sendMessage(Utils.translate(" &3&l▸ &fArena: &b" + arena.getName()));
        player.sendMessage(Utils.translate(" &3&l▸ &fCopies: &b" + currentCopyAmount));
        player.sendMessage(Utils.translate(" "));
        player.sendMessage(Utils.translate("&7&oYou can check the progress in console."));

        player.closeInventory();
    }
}
