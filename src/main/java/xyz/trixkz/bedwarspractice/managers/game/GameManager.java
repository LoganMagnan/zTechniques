package xyz.trixkz.bedwarspractice.managers.game;

import lombok.Getter;
import xyz.trixkz.bedwarspractice.Main;
import xyz.trixkz.bedwarspractice.listeners.events.GameStartEvent;
import xyz.trixkz.bedwarspractice.listeners.events.GameStopEvent;

import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

public class GameManager {

    @Getter private final Map<UUID, Game> games = new ConcurrentHashMap<>();

//    public List<ItemStack> getKitItems(Player player, Kit kit, Match match) {
//        List<ItemStack> toReturn = new ArrayList<>();
//        PracticePlayerData practicePlayerData = this.plugin.getPlayerManager().getPlayerData(player.getUniqueId());
//        if (!match.getKit().isSumo()) {
//            toReturn.add(this.plugin.getItemManager().getDefaultBook());
//            for (PlayerKit playerKit : practicePlayerData.getKits().get(kit.getName())) {
//                if (playerKit != null) {
//                    final ItemStack itemStack = new ItemStack(Material.ENCHANTED_BOOK);
//                    final ItemMeta itemMeta = itemStack.getItemMeta();
//                    itemMeta.setDisplayName(CC.translate(playerKit.getName()));
//                    itemMeta.setLore(Collections.singletonList(ChatColor.GRAY + "Right Click to receive this kit."));
//                    itemStack.setItemMeta(itemMeta);
//                    toReturn.remove(this.plugin.getItemManager().getDefaultBook());
//                    toReturn.add(itemStack);
//                    player.getInventory().setItem(8, this.plugin.getItemManager().getDefaultBook());
//                }
//            }
//        }
//
//        return toReturn;
//    }

    public Game getGameFromUUID(UUID uuid) {
        return this.games.get(uuid);
    }

    public void createGame(Game game) {
        this.games.put(game.getGameID(), game);

        Main.getInstance().getServer().getPluginManager().callEvent(new GameStartEvent(game));
    }

    public void removeGame(Game game) {
        this.games.remove(game.getGameID());

        Main.getInstance().getServer().getPluginManager().callEvent(new GameStopEvent(game));
    }
}
