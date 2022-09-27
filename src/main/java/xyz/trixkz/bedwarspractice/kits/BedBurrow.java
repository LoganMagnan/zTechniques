package xyz.trixkz.bedwarspractice.kits;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import xyz.trixkz.bedwarspractice.managers.user.User;
import xyz.trixkz.bedwarspractice.managers.user.UserState;
import xyz.trixkz.bedwarspractice.utils.ItemBuilder;
import xyz.trixkz.bedwarspractice.utils.Utils;

import java.util.ArrayList;
import java.util.List;

public class BedBurrow extends Kit {

    @Override
    public String getName() {
        return Utils.translate("&dBed Burrow");
    }

    @Override
    public List<String> getLore() {
        List<String> lore = new ArrayList<String>();

        String stars = "";

        for (int i = 0; i < this.getDifficultyLevel(); i++) {
            stars += "⭑";
        }

        lore.add(Utils.translate(""));
        lore.add(Utils.translate("&9Block yourself into"));
        lore.add(Utils.translate("&9a bed defense while"));
        lore.add(Utils.translate("&9being hit by a player"));
        lore.add(Utils.translate(""));
        lore.add(Utils.translate("&fDifficulty: &b" + stars));

        return lore;
    }

    @Override
    public Material getMaterial() {
        return Material.SHEARS;
    }

    @Override
    public UserState getUserState() {
        return UserState.BED_BURROW;
    }

    @Override
    public List<String> getArenas() {
        List<String> arenas = new ArrayList<String>();

        arenas.add("BedBurrow");

        return arenas;
    }

    @Override
    public List<ItemStack> getInventoryContents(Player player) {
        List<ItemStack> inventoryContents = new ArrayList<ItemStack>();

        User user = User.getByUUID(player.getUniqueId());

        inventoryContents.add(new ItemStack(Material.WOOD_SWORD, 1));
        inventoryContents.add(new ItemStack(Material.SHEARS, 1));
        inventoryContents.add(new ItemStack(Material.WOOD_PICKAXE, 1));
        inventoryContents.add(new ItemStack(Material.WOOD_AXE, 1));
        inventoryContents.add(new ItemBuilder(user.getSettings().getCurrentBlock().getBlockType()).durability(user.getSettings().getCurrentBlock().getBlockData()).amount(64).build());
        inventoryContents.add(new ItemBuilder(user.getSettings().getCurrentBlock().getBlockType()).durability(user.getSettings().getCurrentBlock().getBlockData()).amount(64).build());

        return inventoryContents;
    }

    @Override
    public int getDifficultyLevel() {
        return 4;
    }

    @Override
    public boolean isEnabled() {
        return false;
    }
}
