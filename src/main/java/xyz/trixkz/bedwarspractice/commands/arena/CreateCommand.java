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

public class CreateCommand extends BaseCommand {

    private Main main;

    public CreateCommand(Main main) {
        this.main = main;
    }

    @Override
    public void executeAs(CommandSender sender, Command cmd, String label, String[] args) {
        Player player = (Player) sender;

        Arena arena = this.main.getArenaManager().getArena(args[1]);

        if (arena == null) {
            this.main.getArenaManager().createArena(args[1]);

            player.sendMessage(Utils.translate("&aSuccessfully created the arena called &a&l" + args[1]));
        } else {
            player.sendMessage(Utils.translate("&cThis arena already exists"));
        }
    }

    @Override
    public List<String> getTabCompletions(CommandSender sender, Command cmd, String label, String[] args) {
        List<String> tabCompletions = new ArrayList<String>();

        return tabCompletions;
    }
}
