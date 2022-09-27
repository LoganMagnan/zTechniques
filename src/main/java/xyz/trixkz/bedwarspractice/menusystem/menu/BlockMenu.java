package xyz.trixkz.bedwarspractice.menusystem.menu;

import lombok.AllArgsConstructor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.inventory.ItemStack;
import xyz.trixkz.bedwarspractice.Main;
import xyz.trixkz.bedwarspractice.managers.block.UserBlock;
import xyz.trixkz.bedwarspractice.managers.user.User;
import xyz.trixkz.bedwarspractice.menusystem.arena.Menu;
import xyz.trixkz.bedwarspractice.menusystem.arena.buttons.Button;
import xyz.trixkz.bedwarspractice.utils.ItemBuilder;
import xyz.trixkz.bedwarspractice.utils.Utils;

import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;

public class BlockMenu extends Menu {

    @Override
    public String getTitle(Player player) {
        return Utils.translate("&eSelect a block...");
    }

    @Override
    public int getSize() {
        return 54;
    }

    @Override
    public Map<Integer, Button> getButtons(Player player) {
        Map<Integer, Button> buttons = new HashMap<Integer, Button>();

        User user = User.getByUUID(player.getUniqueId());

        AtomicInteger atomicInteger = new AtomicInteger(0);

        Main.getInstance().getBlockManager().getUserBlocks().stream().sorted(Comparator.comparingInt(UserBlock :: getLevel)).forEach(userBlock -> {
            buttons.put(atomicInteger.getAndIncrement(), new BlocksButton(user, userBlock));
        });

        for (int i = 45; i < 54; i++) {
            buttons.put(i, this.getPlaceholderButton());
        }

        buttons.put(49, new CloseButton());

        return buttons;
    }

    @AllArgsConstructor
    public class BlocksButton extends Button {

        private User user;
        private UserBlock userBlock;

        @Override
        public ItemStack getButtonItem(Player player) {
            List<String> lore = new ArrayList<String>();

            lore.addAll(this.userBlock.getLore());

            if (this.user.getSettings().getCurrentBlock() != this.userBlock) {
                if (this.user.getSettings().getLevel() >= this.userBlock.getLevel()) {
                    lore.add(Utils.translate("&aClick to select"));
                } else {
                    lore.add(Utils.translate("&cYou need to be level &c&l" + this.userBlock.getLevel() + " &cto select this block"));
                }
            } else {
                lore.add(Utils.translate("&cYou already have this block selected"));
            }

            return new ItemBuilder(this.userBlock.getBlockType()).name(Utils.translate(this.userBlock.getName())).lore(lore).durability(this.userBlock.getBlockData()).build();
        }

        @Override
        public void clicked(Player player, int slot, ClickType clickType, int hotbarButton) {
            if (this.user.getSettings().getCurrentBlock() != this.userBlock) {
                if (this.user.getSettings().getLevel() >= this.userBlock.getLevel()) {
                    player.sendMessage(Utils.translate("&aYou have selected &a&l" + this.userBlock.getName()));

                    this.user.getSettings().setCurrentBlock(this.userBlock);
                } else {
                    player.sendMessage(Utils.translate("&cYou need to be level &c&l" + this.userBlock.getLevel() + " &cto select this block"));
                }
            } else {
                player.sendMessage(Utils.translate("&cYou already have this block selected"));
            }
        }
    }

    public class CloseButton extends Button {

        @Override
        public ItemStack getButtonItem(Player player) {
            return new ItemBuilder(Material.BARRIER)
                    .name("&cClick to close menu...")
                    .lore(Arrays.asList("", "&9Click to close this menu"))
                    .build();
        }

        @Override
        public void clicked(Player player, int slot, ClickType clickType, int hotbarButton) {
            if (clickType == ClickType.RIGHT) {
                return;
            }

            player.closeInventory();
        }
    }
}
