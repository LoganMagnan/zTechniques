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
public class PageButton extends Button {

	private int mod;
	private PaginatedMenu menu;

	@Override
	public ItemStack getButtonItem(Player player) {
		ItemStack itemStack = new ItemStack(Material.CARPET);
		ItemMeta itemMeta = itemStack.getItemMeta();

		if (this.hasNext(player)) {
			itemMeta.setDisplayName(this.mod > 0 ? Utils.translate("&aNext page") : Utils.translate("&cPrevious page"));
		} else {
			itemMeta.setDisplayName(Utils.translate((this.mod > 0 ? "Last page" : "First page")));
		}

		itemMeta.setLore(Arrays.asList(
				Utils.translate(""),
				Utils.translate("&aRight click to"),
				Utils.translate("&ajump to a page")
		));

		itemStack.setItemMeta(itemMeta);

		return itemStack;
	}

	@Override
	public void clicked(Player player, int i, ClickType clickType, int hb) {
		if (clickType == ClickType.RIGHT) {
			new ViewAllPagesMenu(this.menu).openMenu(player);
			playNeutral(player);
		} else {
			if (hasNext(player)) {
				this.menu.modPage(player, this.mod);
				Button.playNeutral(player);
			} else {
				Button.playFail(player);
			}
		}
	}

	private boolean hasNext(Player player) {
		int pg = this.menu.getPage() + this.mod;
		return pg > 0 && this.menu.getPages(player) >= pg;
	}
}
