package xyz.trixkz.bedwarspractice.commands.arena;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import xyz.trixkz.bedwarspractice.Main;
import xyz.trixkz.bedwarspractice.managers.arena.Arena;
import xyz.trixkz.bedwarspractice.menusystem.arena.ArenaManagerMenu;
import xyz.trixkz.bedwarspractice.utils.Utils;

public class ArenaCommand implements CommandExecutor {

    private Main main;

    public ArenaCommand(Main main) {
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
            player.sendMessage(Utils.translate("  &c/arena create <name>"));
            player.sendMessage(Utils.translate("  &c/arena delete <name>"));
            player.sendMessage(Utils.translate("  &c/arena enable <name>"));
            player.sendMessage(Utils.translate("  &c/arena disable <name>"));
            player.sendMessage(Utils.translate("  &c/arena info <name>"));
            player.sendMessage(Utils.translate("  &c/arena icon <name>"));
            player.sendMessage(Utils.translate("  &c/arena setspawnpoint <name>"));
            player.sendMessage(Utils.translate("  &c/arena setmin <name>"));
            player.sendMessage(Utils.translate("  &c/arena setmax <name>"));
            player.sendMessage(Utils.translate("  &c/arena list"));
            player.sendMessage(Utils.translate("  &c/arena save"));
            player.sendMessage(Utils.translate("  &c/arena manage"));
            player.sendMessage(Utils.translate("  &c/arena generate"));
        } else {
            switch (args[0]) {
                case "create":
                    new CreateCommand(main).executeAs(sender, cmd, label, args);

                    break;
                case "delete":
                    new DeleteCommand(main).executeAs(sender, cmd, label, args);

                    break;
                case "enable":
                case "disable":
                    new EnableAndDisableCommand(main).executeAs(sender, cmd, label, args);

                    break;
                case "info":
                    new InfoCommand(main).executeAs(sender, cmd, label, args);

                    break;
                case "icon":
                    new IconCommand(main).executeAs(sender, cmd, label, args);

                    break;
                case "setspawnpoint":
                    new SetSpawnPointCommand(main).executeAs(sender, cmd, label, args);

                    break;
                case "setmin":
                    new SetMinCommand(main).executeAs(sender, cmd, label, args);

                    break;
                case "setmax":
                    new SetMaxCommand(main).executeAs(sender, cmd, label, args);

                    break;
                case "list":
                    player.sendMessage(Utils.translate("&b&lArenas List &7(&3Total: " + this.main.getArenaManager().getArenas().size() + "&7)"));

                    for (Arena arena : this.main.getArenaManager().getArenas().values()) {
                        player.sendMessage(Utils.translate("  &7▸ &b" + arena.getName() + " &7(" + (arena.isEnabled() ? "&aEnabled" : "&cDisabled") + "&7)"));
                    }

                    break;
                case "save":
                    this.main.getArenaManager().reloadArenas();

                    player.sendMessage(Utils.translate("&aSuccessfully saved all of the arenas"));

                    break;
                case "manage":
                    if (this.main.getArenaManager().getArenas().size() == 0) {
                        player.sendMessage(Utils.translate("&cError: There are no arenas"));

                        return true;
                    }

                    new ArenaManagerMenu().openMenu(player);

                    break;
                case "generate":
                    new GenerateCommand(main).executeAs(sender, cmd, label, args);

                    break;
            }
        }

        return true;
    }
}
