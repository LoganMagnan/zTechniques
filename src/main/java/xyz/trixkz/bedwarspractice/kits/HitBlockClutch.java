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

public class HitBlockClutch extends Kit {

    @Override
    public String getName() {
        return Utils.translate("&dHit Block Clutch");
    }

    @Override
    public List<String> getLore() {
        List<String> lore = new ArrayList<String>();

        String stars = "";

        for (int i = 0; i < this.getDifficultyLevel(); i++) {
            stars += "⭑";
        }

        lore.add(Utils.translate(""));
        lore.add(Utils.translate("&9Hit a player and then"));
        lore.add(Utils.translate("&9place a block on their bridger"));
        lore.add(Utils.translate(""));
        lore.add(Utils.translate("&fDifficulty: &b" + stars));

        return lore;
    }

    @Override
    public Material getMaterial() {
        return Material.STONE_SWORD;
    }

    @Override
    public UserState getUserState() {
        return UserState.HIT_BLOCK_CLUTCH;
    }

    @Override
    public List<String> getArenas() {
        List<String> arenas = new ArrayList<String>();

        arenas.add("HitBlockClutch");

        return arenas;
    }

    @Override
    public List<ItemStack> getInventoryContents(Player player) {
        List<ItemStack> inventoryContents = new ArrayList<ItemStack>();

        User user = User.getByUUID(player.getUniqueId());

        inventoryContents.add(new ItemBuilder(user.getSettings().getCurrentBlock().getBlockType()).durability(user.getSettings().getCurrentBlock().getBlockData()).amount(64).build());
        inventoryContents.add(new ItemBuilder(user.getSettings().getCurrentBlock().getBlockType()).durability(user.getSettings().getCurrentBlock().getBlockData()).amount(64).build());

        return inventoryContents;
    }

    @Override
    public int getDifficultyLevel() {
        return 5;
    }

    @Override
    public boolean isEnabled() {
        return false;
    }
}
