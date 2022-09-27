package xyz.trixkz.bedwarspractice.commands.xp;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import xyz.trixkz.bedwarspractice.Main;
import xyz.trixkz.bedwarspractice.utils.Utils;

public class XpCommand implements CommandExecutor {

    private Main main;

    public XpCommand(Main main) {
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
            player.sendMessage(Utils.translate("  &c/xp"));
            player.sendMessage(Utils.translate("  &c/xp set <player> <amount>"));
            player.sendMessage(Utils.translate("  &c/xp add <player> <amount>"));
            player.sendMessage(Utils.translate("  &c/xp remove <player> <amount>"));
        } else {
            switch (args[0]) {
                case "set":
                    new SetCommand(this.main).executeAs(sender, cmd, label, args);

                    break;
                case "add":
                    new AddCommand(this.main).executeAs(sender, cmd, label, args);

                    break;
                case "remove":
                    new RemoveCommand(this.main).executeAs(sender, cmd, label, args);

                    break;
            }
        }

        return true;
    }
}
