package xyz.trixkz.bedwarspractice.utils;

import net.md_5.bungee.api.ChatColor;
import net.minecraft.server.v1_8_R3.EntityInsentient;
import net.minecraft.server.v1_8_R3.EntityTypes;
import org.bukkit.GameMode;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import xyz.trixkz.bedwarspractice.Main;
import xyz.trixkz.bedwarspractice.kits.Kit;
import xyz.trixkz.bedwarspractice.managers.user.User;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class Utils {

    private Main main;

    public static boolean DEBUG_MESSAGE;
    public static String ADMIN_PERMISSION_NODE;
    public static String NO_PERMISSION_MESSAGE;
    public static String SPAWN_POINT_SET_MESSAGE;

    public Utils(Main main) {
        this.main = main;

        DEBUG_MESSAGE = main.getFiles().getSettingsConfig().getBoolean("debug");
        ADMIN_PERMISSION_NODE = main.getFiles().getSettingsConfig().getString("admin-permission-node");
        NO_PERMISSION_MESSAGE = main.getFiles().getSettingsConfig().getString("no-permission-message");
        SPAWN_POINT_SET_MESSAGE = main.getFiles().getSettingsConfig().getString("spawn-point-set-message");
    }

    public static String translate(String message) {
        return ChatColor.translateAlternateColorCodes('&', message);
    }

    public static List<String> translate(List<String> lines) {
        List<String> toReturn = new ArrayList<>();
        for (String line : lines) {
            toReturn.add(org.bukkit.ChatColor.translateAlternateColorCodes('&', line));
        }

        return toReturn;
    }

    public static List<String> translate(String[] lines) {
        List<String> toReturn = new ArrayList<>();
        for (String line : lines) {
            if (line != null) {
                toReturn.add(org.bukkit.ChatColor.translateAlternateColorCodes('&', line));
            }
        }

        return toReturn;
    }

    public static void debug(String message) {
        if (DEBUG_MESSAGE) {
            System.out.println(message);
        }
    }

    public String getMessage(String[] args, int number) {
        StringBuilder stringBuilder = new StringBuilder();

        for (int i = number; i < args.length; i++) {
            stringBuilder.append(args[i]).append(number >= args.length - 1 ? "" : " ");
        }

        return stringBuilder.toString();
    }

    public boolean isNumeric(String string) {
        return regexNumeric(string).length() == 0;
    }

    public String regexNumeric(String string) {
        return string.replaceAll("[0-9]", "").replaceFirst("\\.", "");
    }

    public void givePlayerKitItems(Player player) {
        User user = User.getByUUID(player.getUniqueId());

        player.getInventory().clear();
        player.setGameMode(GameMode.SURVIVAL);

        ItemStack modeItemStack = Items.mode.clone();

        ItemMeta modeItemMeta = modeItemStack.getItemMeta();

        modeItemMeta.setDisplayName(Utils.translate("&e&l» &dMode &7┃ " + user.getSettings().getMode().getName() + " &e&l«"));

        modeItemStack.setItemMeta(modeItemMeta);

        switch (user.getUserState()) {
            case THE_BRIDGE_BYPASS:
                break;
            case KNOCKBACK_CLUTCH:
                player.getInventory().setItem(6, modeItemStack);

                break;
            case LADDER_CLUTCH:
                break;
            case FIREBALL_JUMP:
                break;
            case TNT_JUMP:
                break;
            case SIDE_CLUTCH:
                break;
            case WALL_BLOCK_CLUTCH:
                break;
            case BLOCK_EXTENSIONS:
                break;
            case KNOCKBACK_WALL_CLUTCH:
                player.getInventory().setItem(6, modeItemStack);

                break;
            case HIT_BLOCK_CLUTCH:
                break;
            case SPEED_CLUTCH:
                break;
            case BED_BURROW:
                break;
            case JUMP_AROUND:
                break;
            case BRIDGE_START:
                break;
            case BLOCK_TOTEM:
                break;
            case HIGH_BLOCK_CLUTCH:
                break;
            case SAFE_TOWER:
                break;
            case ENDER_PEARL:
                break;
            case BROKEN_WALL_RUN:
                break;
            case BED_DEFEND:
                break;
        }

        switch (user.getUserState()) {
            case THE_BRIDGE_BYPASS:
            case KNOCKBACK_CLUTCH:
            case LADDER_CLUTCH:
            case FIREBALL_JUMP:
            case TNT_JUMP:
            case SIDE_CLUTCH:
            case WALL_BLOCK_CLUTCH:
            case BLOCK_EXTENSIONS:
            case KNOCKBACK_WALL_CLUTCH:
            case HIT_BLOCK_CLUTCH:
            case SPEED_CLUTCH:
            case BED_BURROW:
            case JUMP_AROUND:
            case BRIDGE_START:
            case BLOCK_TOTEM:
            case HIGH_BLOCK_CLUTCH:
            case SAFE_TOWER:
            case ENDER_PEARL:
            case BROKEN_WALL_RUN:
            case BED_DEFEND:
                for (Kit kit : this.main.getKits()) {
                    if (user.getUserState() == kit.getUserState()) {
                        for (ItemStack itemStack : kit.getInventoryContents(player)) {
                            player.getInventory().addItem(itemStack);
                        }
                    }
                }

                break;
        }

        player.getInventory().setItem(7, Items.settings);
        player.getInventory().setItem(8, Items.goBack);
    }

    public void registerEntity(String name, int id, Class<? extends EntityInsentient> nmsClass, Class<? extends EntityInsentient> customClass) {
        try {
            List<Map<?, ?>> dataMaps = new ArrayList<Map<?, ?>>();

            for (Field field : EntityTypes.class.getDeclaredFields()) {
                if (field.getType().getSimpleName().equals(Map.class.getSimpleName())) {
                    field.setAccessible(true);

                    dataMaps.add((Map<?, ?>) field.get(null));
                }
            }

            if (dataMaps.get(2).containsKey(id)) {
                dataMaps.get(0).remove(name);
                dataMaps.get(2).remove(id);
            }

            Method method = EntityTypes.class.getDeclaredMethod("a", Class.class, String.class, int.class);
            method.setAccessible(true);
            method.invoke(null, customClass, name, id);
        } catch (Exception exception) {
            exception.printStackTrace();
        }
    }

    public static Object getPrivateField(String fieldName, Class clazz, Object object) {
        Field field;
        Object o = null;

        try
        {
            field = clazz.getDeclaredField(fieldName);

            field.setAccessible(true);

            o = field.get(object);
        }
        catch(NoSuchFieldException e)
        {
            e.printStackTrace();
        }
        catch(IllegalAccessException e)
        {
            e.printStackTrace();
        }

        return o;
    }
}