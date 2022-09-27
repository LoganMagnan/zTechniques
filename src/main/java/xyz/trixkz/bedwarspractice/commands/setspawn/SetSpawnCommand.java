package xyz.trixkz.bedwarspractice.commands.setspawn;

import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import xyz.trixkz.bedwarspractice.Main;
import xyz.trixkz.bedwarspractice.commands.arena.*;
import xyz.trixkz.bedwarspractice.managers.arena.Arena;
import xyz.trixkz.bedwarspractice.menusystem.arena.ArenaManagerMenu;
import xyz.trixkz.bedwarspractice.utils.Utils;

public class SetSpawnCommand implements CommandExecutor {

    private Main main;

    public SetSpawnCommand(Main main) {
        this.main = main;
    }

    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        Player player = (Player) sender;

        if (!player.hasPermission(Utils.ADMIN_PERMISSION_NODE)) {
            player.sendMessage(Utils.translate(Utils.NO_PERMISSION_MESSAGE));

            return true;
        }

        if (args.length == 0) {
            player.sendMessage(Utils.translate("&cUsage:"));
            player.sendMessage(Utils.translate("  &c/setspawn spawn"));
            player.sendMessage(Utils.translate("  &c/setspawn holograms"));
        } else {
            switch (args[0]) {
                case "spawn":
                    new SpawnCommand(this.main).executeAs(sender, cmd, label, args);

                    break;
                case "holograms":
                    break;
            }
        }

        return true;
    }
}
