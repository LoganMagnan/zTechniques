package xyz.trixkz.bedwarspractice.commands.arena;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import xyz.trixkz.bedwarspractice.Main;
import xyz.trixkz.bedwarspractice.commands.BaseCommand;
import xyz.trixkz.bedwarspractice.managers.arena.Arena;
import xyz.trixkz.bedwarspractice.utils.Utils;

import java.util.ArrayList;
import java.util.List;

public class InfoCommand extends BaseCommand {

    private Main main;

    public InfoCommand(Main main) {
        this.main = main;
    }

    @Override
    public void executeAs(CommandSender sender, Command cmd, String label, String[] args) {
        Player player = (Player) sender;

        Arena arena = this.main.getArenaManager().getArena(args[1]);

        if (arena != null) {
            player.sendMessage(Utils.translate("&b&lArena Information"));
            player.sendMessage(Utils.translate(""));
            player.sendMessage(Utils.translate("  &7▸ &bName: &3" + arena.getName()));
            player.sendMessage(Utils.translate("  &7▸ &bState: " + (arena.isEnabled() ? "&aEnabled" : "&cDisabled")));
            player.sendMessage(Utils.translate("  &7▸ &b1st Spawn: &3" + Math.round(arena.getA().getX()) + "&7, &b" + Math.round(arena.getA().getY()) + "&7, &b" + Math.round(arena.getA().getZ())));
            player.sendMessage(Utils.translate("  &7▸ &bMin Location: &3" + Math.round(arena.getMin().getX()) + "&7, &b" + Math.round(arena.getMin().getY()) + "&7, &b" + Math.round(arena.getMin().getZ())));
            player.sendMessage(Utils.translate("  &7▸ &bMax Location: &3" + Math.round(arena.getMax().getX()) + "&7, &b" + Math.round(arena.getMax().getY()) + "&7, &b" + Math.round(arena.getMax().getZ())));
            player.sendMessage(Utils.translate("  &7▸ &bStandalone Arenas: &3" + arena.getStandaloneArenas().size()));
            player.sendMessage(Utils.translate("  &7▸ &bAvailable Arenas: &3" + (arena.getAvailableArenas().size() == 0 ? +1 : arena.getAvailableArenas().size())));
        } else {
            player.sendMessage(Utils.translate("&cThis arena does not exist"));
        }
    }

    @Override
    public List<String> getTabCompletions(CommandSender sender, Command cmd, String label, String[] args) {
        List<String> tabCompletions = new ArrayList<String>();

        return tabCompletions;
    }
}
