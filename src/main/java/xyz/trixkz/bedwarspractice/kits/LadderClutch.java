package xyz.trixkz.bedwarspractice.kits;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import xyz.trixkz.bedwarspractice.managers.user.User;
import xyz.trixkz.bedwarspractice.managers.user.UserState;
import xyz.trixkz.bedwarspractice.utils.Utils;

import java.util.ArrayList;
import java.util.List;

public class LadderClutch extends Kit {

    @Override
    public String getName() {
        return Utils.translate("&dLadder Clutch");
    }

    @Override
    public List<String> getLore() {
        List<String> lore = new ArrayList<String>();

        String stars = "";

        for (int i = 0; i < this.getDifficultyLevel(); i++) {
            stars += "⭑";
        }

        lore.add(Utils.translate(""));
        lore.add(Utils.translate("&9Practice placing ladders"));
        lore.add(Utils.translate("&9on jagged paths"));
        lore.add(Utils.translate(""));
        lore.add(Utils.translate("&fDifficulty: &b" + stars));

        return lore;
    }

    @Override
    public Material getMaterial() {
        return Material.LADDER;
    }

    @Override
    public UserState getUserState() {
        return UserState.LADDER_CLUTCH;
    }

    @Override
    public List<String> getArenas() {
        List<String> arenas = new ArrayList<String>();

        arenas.add("LadderClutch");

        return arenas;
    }

    @Override
    public List<ItemStack> getInventoryContents(Player player) {
        List<ItemStack> inventoryContents = new ArrayList<ItemStack>();

        inventoryContents.add(new ItemStack(Material.LADDER, 1));

        return inventoryContents;
    }

    @Override
    public int getDifficultyLevel() {
        return 3;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
