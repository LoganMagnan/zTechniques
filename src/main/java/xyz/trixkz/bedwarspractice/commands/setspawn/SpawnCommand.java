package xyz.trixkz.bedwarspractice.commands.setspawn;

import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import xyz.trixkz.bedwarspractice.Main;
import xyz.trixkz.bedwarspractice.commands.BaseCommand;
import xyz.trixkz.bedwarspractice.managers.arena.Arena;
import xyz.trixkz.bedwarspractice.utils.Utils;

import java.util.ArrayList;
import java.util.List;

public class SpawnCommand extends BaseCommand {

    private Main main;

    public SpawnCommand(Main main) {
        this.main = main;
    }

    @Override
    public void executeAs(CommandSender sender, Command cmd, String label, String[] args) {
        Player player = (Player) sender;

        Location location = player.getLocation();

        int x = location.getBlockX();
        int y = location.getBlockY();
        int z = location.getBlockZ();

        player.getWorld().setSpawnLocation(x, y, z);

        player.sendMessage(Utils.translate(Utils.SPAWN_POINT_SET_MESSAGE));
    }

    @Override
    public List<String> getTabCompletions(CommandSender sender, Command cmd, String label, String[] args) {
        List<String> tabCompletions = new ArrayList<String>();

        return tabCompletions;
    }
}
