package xyz.trixkz.bedwarspractice.commands.arena;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import xyz.trixkz.bedwarspractice.Main;
import xyz.trixkz.bedwarspractice.commands.BaseCommand;
import xyz.trixkz.bedwarspractice.managers.arena.Arena;
import xyz.trixkz.bedwarspractice.runnable.arena.ArenaCommandRunnable;
import xyz.trixkz.bedwarspractice.utils.Utils;

import java.util.ArrayList;
import java.util.List;

public class GenerateCommand extends BaseCommand {

    private Main main;

    public GenerateCommand(Main main) {
        this.main = main;
    }

    @Override
    public void executeAs(CommandSender sender, Command cmd, String label, String[] args) {
        Player player = (Player) sender;

        Arena arena = this.main.getArenaManager().getArena(args[1]);

        if (args.length == 3) {
            int arenas = Integer.parseInt(args[2]);

            this.main.getServer().getScheduler().runTask(this.main, new ArenaCommandRunnable(this.main, arena, arenas));
            this.main.getArenaManager().setGeneratingArenaRunnable(this.main.getArenaManager().getGeneratingArenaRunnable() + 1);
        } else {
            player.sendMessage(Utils.translate("&cUsage: /arena generate <name> <copies>"));
        }
    }

    @Override
    public List<String> getTabCompletions(CommandSender sender, Command cmd, String label, String[] args) {
        List<String> tabCompletions = new ArrayList<String>();

        return tabCompletions;
    }
}
