package xyz.trixkz.bedwarspractice.menusystem.arena.buttons;

import lombok.AllArgsConstructor;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.inventory.ItemStack;
import xyz.trixkz.bedwarspractice.Main;
import xyz.trixkz.bedwarspractice.managers.arena.Arena;
import xyz.trixkz.bedwarspractice.menusystem.arena.ArenaCopyMenu;
import xyz.trixkz.bedwarspractice.menusystem.arena.ArenaGenerationMenu;
import xyz.trixkz.bedwarspractice.utils.ItemBuilder;
import xyz.trixkz.bedwarspractice.utils.Utils;
import java.util.Arrays;

@AllArgsConstructor
public class ArenaButton extends Button {

    private final Arena arena;

    @Override
    public ItemStack getButtonItem(Player player) {
        return new ItemBuilder(Material.valueOf(arena.getIcon()))
                .durability(arena.getIconData())
                .name("&b" + arena.getName())
                .lore(Utils.translate(
                                Arrays.asList(
                                        " ",
                                        "&bArena Information&7:",
                                        " &3&l▸ &fState: " + (arena.isEnabled() ? "&aEnabled" : "&cDisabled"),
                                        " &3&l▸ &fType: &b" + (arena.getAvailableArenas().size() == 0 ? "Shared" : "Standalone"),
                                        " &3&l▸ &fCopies: &b" + (arena.getStandaloneArenas().size() == 0 ? "Not Required!" : arena.getStandaloneArenas().size()),
                                        " &3&l▸ &fAvailable: &b" + (arena.getAvailableArenas().size() == 0 ? +1 : arena.getAvailableArenas().size()),
                                        " ",
                                        (arena.getStandaloneArenas().size() == 0 ? "&4&l&mMIDDLE CLICK &4&mto see arena copies" : "&6&lMIDDLE CLICK &6to see arena copies"),
                                        "&a&lLEFT CLICK &ato teleport to arena",
                                        "&b&lRIGHT CLICK &bto generate standalone arenas")
                        )
                )
                .hideFlags()
                .build();
    }

    @Override
    public void clicked(Player player, int slot, ClickType clickType, int hotbarButton) {
        playSuccess(player);
        switch (clickType) {
            case LEFT:
                player.teleport(arena.getA().toBukkitLocation());
                break;
            case RIGHT:
                Bukkit.getScheduler().runTaskLaterAsynchronously(Main.getInstance(), () -> new ArenaGenerationMenu(arena).openMenu(player), 1L);
                break;
            case MIDDLE:
                if (arena.getStandaloneArenas().size() >= 1) {
                    Bukkit.getScheduler().runTaskLaterAsynchronously(Main.getInstance(), () -> new ArenaCopyMenu(arena).openMenu(player), 1L);
                }
                break;
        }

        player.closeInventory();
    }
}
