package xyz.trixkz.bedwarspractice.managers;

import xyz.trixkz.bedwarspractice.Main;
import xyz.trixkz.bedwarspractice.commands.level.LevelCommand;
import xyz.trixkz.bedwarspractice.commands.setspawn.SetSpawnCommand;
import xyz.trixkz.bedwarspractice.commands.arena.ArenaCommand;
import xyz.trixkz.bedwarspractice.commands.xp.XpCommand;

public class CommandManager {

    private Main main;

    public CommandManager(Main main) {
        this.main = main;

        registerCommands();
    }

    private void registerCommands() {
        this.main.addCommand("arena", new ArenaCommand(this.main));
        this.main.addCommand("setspawn", new SetSpawnCommand(this.main));
        this.main.addCommand("level", new LevelCommand(this.main));
        this.main.addCommand("xp", new XpCommand(this.main));
    }
}
