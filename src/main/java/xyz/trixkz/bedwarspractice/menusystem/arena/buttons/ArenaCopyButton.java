package xyz.trixkz.bedwarspractice.menusystem.arena.buttons;

import com.boydti.fawe.util.EditSessionBuilder;
import com.boydti.fawe.util.TaskManager;
import com.sk89q.worldedit.EditSession;
import com.sk89q.worldedit.MaxChangedBlocksException;
import com.sk89q.worldedit.Vector;
import com.sk89q.worldedit.blocks.BaseBlock;
import com.sk89q.worldedit.blocks.BlockID;
import com.sk89q.worldedit.regions.CuboidRegion;
import lombok.AllArgsConstructor;
import org.bukkit.Material;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.inventory.ItemStack;
import xyz.trixkz.bedwarspractice.Main;
import xyz.trixkz.bedwarspractice.managers.arena.Arena;
import xyz.trixkz.bedwarspractice.managers.arena.StandaloneArena;
import xyz.trixkz.bedwarspractice.utils.CustomLocation;
import xyz.trixkz.bedwarspractice.utils.ItemBuilder;
import xyz.trixkz.bedwarspractice.utils.Utils;

import java.util.Arrays;

@AllArgsConstructor
public class ArenaCopyButton extends Button {

    private final Arena arena;
    private final StandaloneArena arenaCopy;

    @Override
    public ItemStack getButtonItem(Player player) {
        return new ItemBuilder(Material.PAPER)
                .name("&8")
                .lore(Utils.translate(
                        Arrays.asList(
                                "&bCopy Information&7:",
                                " &3&l▸ &fParent Arena: &b" + arena.getName(),
                                " &3&l▸ &f1st Spawn: &b" + Math.round(arenaCopy.getA().getX()) + "&7, &b" + Math.round(arenaCopy.getA().getY()) + "&7, &b" + Math.round(arenaCopy.getA().getZ()),
                                " &3&l▸ &fMin Location: &b" + Math.round(arenaCopy.getMin().getX()) + "&7, &b" + Math.round(arenaCopy.getMin().getY()) + "&7, &b" + Math.round(arenaCopy.getMin().getZ()),
                                " &3&l▸ &fMax Location: &b" + Math.round(arenaCopy.getMax().getX()) + "&7, &b" + Math.round(arenaCopy.getMax().getY()) + "&7, &b" + Math.round(arenaCopy.getMax().getZ()),
                                " ",
                                "&a&lLEFT CLICK &ato teleport to this copy!",
                                "&c&lRIGHT CLICK &cto delete this copy!"
                        ))
                )
                .build();
    }

    @Override
    public void clicked(Player player, int slot, ClickType clickType, int hotbarButton) {
        switch (clickType) {
            case LEFT:
                player.teleport(arenaCopy.getA().toBukkitLocation());
                break;
            case RIGHT:
                TaskManager.IMP.async(() -> {
                    EditSession editSession = new EditSessionBuilder(arenaCopy.getA().getWorld()).fastmode(true).allowedRegionsEverywhere().autoQueue(false).limitUnlimited().build();
                    CuboidRegion copyRegion = new CuboidRegion(
                            new Vector(arenaCopy.getMax().getX(), arenaCopy.getMax().getY(), arenaCopy.getMax().getZ()),
                            new Vector(arenaCopy.getMin().getX(), arenaCopy.getMin().getY(), arenaCopy.getMin().getZ())
                    );

                    try {
                        editSession.setBlocks(copyRegion, new BaseBlock(BlockID.AIR));
                    } catch (MaxChangedBlocksException e) {
                        e.getStackTrace();
                    }

                    editSession.flushQueue();
                });

                player.sendMessage(Utils.translate("Copy deleted"));

                break;
        }

        player.closeInventory();
    }

    private void removeStandaloneArenaCopy() {
        FileConfiguration fileConfig = Main.getInstance().getFiles().getArenaConfig();
        ConfigurationSection arenaSection = fileConfig.getConfigurationSection("arenas");
        if (arenaSection == null) {
            return;
        }

        arenaSection.getKeys(false).forEach(name -> {
            ConfigurationSection saSection = arenaSection.getConfigurationSection(name + ".standaloneArenas");
            if (saSection != null) {
                saSection.getKeys(false).forEach(id -> {
                    String saA = saSection.getString(id + ".a");
                    String saB = saSection.getString(id + ".b");
                    String saMin = saSection.getString(id + ".min");
                    String saMax = saSection.getString(id + ".max");

                    CustomLocation locSaA = CustomLocation.stringToLocation(saA);
                    CustomLocation locSaB = CustomLocation.stringToLocation(saB);
                    CustomLocation locSaMin = CustomLocation.stringToLocation(saMin);
                    CustomLocation locSaMax = CustomLocation.stringToLocation(saMax);

                    TaskManager.IMP.async(() -> {
                        EditSession editSession = new EditSessionBuilder(arenaCopy.getA().getWorld()).fastmode(true).allowedRegionsEverywhere().autoQueue(false).limitUnlimited().build();
                        CuboidRegion copyRegion = new CuboidRegion(
                                new Vector(arenaCopy.getMax().getX(), arenaCopy.getMax().getY(), arenaCopy.getMax().getZ()),
                                new Vector(arenaCopy.getMin().getX(), arenaCopy.getMin().getY(), arenaCopy.getMin().getZ())
                        );

                        try {
                            editSession.setBlocks(copyRegion, new BaseBlock(BlockID.AIR));
                        } catch (MaxChangedBlocksException e) {
                            e.getStackTrace();
                        }

                        editSession.flushQueue();
                    });
                });
            }
        });
    }
}
