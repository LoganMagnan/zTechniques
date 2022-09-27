package xyz.trixkz.bedwarspractice.commands.arena;

import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import xyz.trixkz.bedwarspractice.Main;
import xyz.trixkz.bedwarspractice.commands.BaseCommand;
import xyz.trixkz.bedwarspractice.managers.arena.Arena;
import xyz.trixkz.bedwarspractice.utils.Utils;

import java.util.ArrayList;
import java.util.List;

public class IconCommand extends BaseCommand {

    private Main main;

    public IconCommand(Main main) {
        this.main = main;
    }

    @Override
    public void executeAs(CommandSender sender, Command cmd, String label, String[] args) {
        Player player = (Player) sender;

        Arena arena = this.main.getArenaManager().getArena(args[1]);

        if (arena != null) {
            if (player.getItemInHand().getType() != Material.AIR) {
                String icon = player.getItemInHand().getType().name();

                int iconData = player.getItemInHand().getDurability();

                arena.setIcon(icon);
                arena.setIconData(iconData);

                player.sendMessage(Utils.translate("&aSuccessfully set the icon for the arena called &a&l" + args[1]));
            } else {
                player.sendMessage(Utils.translate("&cYou must be holding an item to set the arena icon"));
            }
        } else {
            player.sendMessage(Utils.translate("&cThis arena does not already exist"));
        }
    }

    @Override
    public List<String> getTabCompletions(CommandSender sender, Command cmd, String label, String[] args) {
        List<String> tabCompletions = new ArrayList<String>();

        return tabCompletions;
    }
}
