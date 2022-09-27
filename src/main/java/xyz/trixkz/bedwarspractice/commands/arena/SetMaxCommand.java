package xyz.trixkz.bedwarspractice.commands.arena;

import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import xyz.trixkz.bedwarspractice.Main;
import xyz.trixkz.bedwarspractice.commands.BaseCommand;
import xyz.trixkz.bedwarspractice.managers.arena.Arena;
import xyz.trixkz.bedwarspractice.utils.CustomLocation;
import xyz.trixkz.bedwarspractice.utils.Utils;

import java.util.ArrayList;
import java.util.List;

public class SetMaxCommand extends BaseCommand {

    private Main main;

    public SetMaxCommand(Main main) {
        this.main = main;
    }

    @Override
    public void executeAs(CommandSender sender, Command cmd, String label, String[] args) {
        Player player = (Player) sender;

        Arena arena = this.main.getArenaManager().getArena(args[1]);

        if (arena != null) {
            Location location = player.getLocation();

            arena.setMax(CustomLocation.fromBukkitLocation(location));

            player.sendMessage(Utils.translate("&aSuccessfully set the maximum position for the arena called &a&l" + args[1]));
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
