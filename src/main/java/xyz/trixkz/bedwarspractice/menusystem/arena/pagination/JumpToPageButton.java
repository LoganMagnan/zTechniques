package xyz.trixkz.bedwarspractice.menusystem.arena.pagination;

import lombok.AllArgsConstructor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import xyz.trixkz.bedwarspractice.menusystem.arena.buttons.Button;
import xyz.trixkz.bedwarspractice.utils.Utils;

import java.util.Arrays;

@AllArgsConstructor
public class JumpToPageButton extends Button {

	private int page;
	private PaginatedMenu menu;
	private boolean current;

	@Override
	public ItemStack getButtonItem(Player player) {
		ItemStack itemStack = new ItemStack(this.current ? Material.ENCHANTED_BOOK : Material.BOOK, this.page);
		ItemMeta itemMeta = itemStack.getItemMeta();

		itemMeta.setDisplayName(Utils.translate("&aPage " + this.page));

		if (this.current) {
			itemMeta.setLore(Arrays.asList(
					Utils.translate(""),
					Utils.translate("&aCurrent page")
			));
		}

		itemStack.setItemMeta(itemMeta);

		return itemStack;
	}

	@Override
	public void clicked(Player player, int i, ClickType clickType, int hb) {
		this.menu.modPage(player, this.page - this.menu.getPage());
		Button.playNeutral(player);
	}
}
