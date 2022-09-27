package xyz.trixkz.bedwarspractice.kits;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import xyz.trixkz.bedwarspractice.managers.user.UserState;
import java.util.List;

public abstract class Kit {

    public abstract String getName();

    public abstract List<String> getLore();

    public abstract Material getMaterial();

    public abstract UserState getUserState();

    public abstract List<String> getArenas();

    public abstract List<ItemStack> getInventoryContents(Player player);

    public abstract int getDifficultyLevel();

    public abstract boolean isEnabled();

    public ItemStack makeItemStack() {
        ItemStack itemStack = new ItemStack(this.getMaterial());
        itemStack.setAmount(1);

        ItemMeta itemMeta = itemStack.getItemMeta();
        itemMeta.setDisplayName(this.getName());
        itemMeta.setLore(this.getLore());

        itemStack.setItemMeta(itemMeta);

        return itemStack;
    }
}
