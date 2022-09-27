package xyz.trixkz.bedwarspractice.utils;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import xyz.trixkz.bedwarspractice.Main;

import java.util.Arrays;

public class Items {

    private Main main;

    public Items(Main main) {
        this.main = main;
    }

    public static ItemStack practiceATechnique = new ItemBuilder(Material.DIAMOND_SWORD).name(Utils.translate("&e&l» &dTechniques &e&l«")).build();
    public static ItemStack blockSelector = new ItemBuilder(Material.DIAMOND).name(Utils.translate("&e&l» &dBlock Selector &e&l«")).build();
    public static ItemStack settings = new ItemBuilder(Material.REDSTONE_COMPARATOR).name(Utils.translate("&e&l» &dSettings &e&l«")).build();
    public static ItemStack mode = new ItemBuilder(Material.SLIME_BALL).name(Utils.translate("&e&l» &dMode &7┃ Mode &e&l«")).build();
    public static ItemStack goBack = new ItemBuilder(Material.BED).name(Utils.translate("&e&l» &dTo Spawn &e&l«")).build();
}