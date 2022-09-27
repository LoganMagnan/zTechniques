package xyz.trixkz.bedwarspractice;

import assemble.Assemble;
import assemble.AssembleStyle;
import lombok.Getter;
import org.bukkit.Chunk;
import org.bukkit.command.CommandExecutor;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;
import xyz.trixkz.bedwarspractice.kits.*;
import xyz.trixkz.bedwarspractice.listeners.*;
import xyz.trixkz.bedwarspractice.managers.*;
import xyz.trixkz.bedwarspractice.managers.arena.ArenaManager;
import xyz.trixkz.bedwarspractice.managers.block.BlockManager;
import xyz.trixkz.bedwarspractice.managers.chunk.ChunkManager;
import xyz.trixkz.bedwarspractice.managers.chunk.ChunkRestorationManager;
import xyz.trixkz.bedwarspractice.managers.game.Game;
import xyz.trixkz.bedwarspractice.managers.game.GameManager;
import xyz.trixkz.bedwarspractice.managers.hologram.HologramManager;
import xyz.trixkz.bedwarspractice.managers.leaderboard.LeaderboardManager;
import xyz.trixkz.bedwarspractice.managers.user.UserManager;
import xyz.trixkz.bedwarspractice.menusystem.PlayerMenuUtil;
import xyz.trixkz.bedwarspractice.menusystem.arena.buttons.ButtonListener;
import xyz.trixkz.bedwarspractice.utils.Files;
import xyz.trixkz.bedwarspractice.utils.Utils;

import javax.persistence.EntityListeners;
import java.util.*;

public class Main extends JavaPlugin {

    @Getter private static Main instance;

    @Getter private Files files;
    @Getter private Utils utils;

    @Getter private DatabaseManager databaseManager;
    @Getter private UserManager userManager;
    @Getter private ScoreboardManager scoreboardManager;
    @Getter private ArenaManager arenaManager;
    @Getter private SpawnManager spawnManager;
    @Getter private ChunkManager chunkManager;
    @Getter private ChunkRestorationManager chunkRestorationManager;
    @Getter private GameManager gameManager;
    @Getter private HologramManager hologramManager;
    @Getter private LeaderboardManager leaderboardManager;
    @Getter private BlockManager blockManager;

    @Getter private List<Kit> kits = new ArrayList<Kit>();

    private TheBridgeBypass theBridgeBypass;
    private KnockbackClutch knockbackClutch;
    private LadderClutch ladderClutch;
    private FireballJump fireballJump;
    private TNTJump tntJump;
    private SideClutch sideClutch;
    private WallBlockClutch wallBlockClutch;
    private BlockExtensions blockExtensions;
    private KnockbackWallClutch knockbackWallClutch;
    private HitBlockClutch hitBlockClutch;
    private SpeedClutch speedClutch;
    private BedBurrow bedBurrow;
    private JumpAround jumpAround;
    private BridgeStart bridgeStart;
    private BlockTotem blockTotem;
    private HighBlockClutch highBlockClutch;
    private SafeTower safeTower;
    private EnderPearl enderPearl;
    private BrokenWallRun brokenWallRun;
    private BedDefend bedDefend;

    private static HashMap<Player, PlayerMenuUtil> playerMenuUtilMap = new HashMap<Player, PlayerMenuUtil>();

    public void onEnable() {
        // Initialize the instance variable
        instance = this;

        // Initialize the files variable
        this.files = new Files(this);
        this.files.loadConfig();

        // Initialize the utils variable
        this.utils = new Utils(this);

        // Setup the scoreboard
        Assemble assemble = new Assemble(this, new ScoreboardManager(this));
        assemble.setTicks(2);
        assemble.setAssembleStyle(AssembleStyle.VIPER);

        this.theBridgeBypass = new TheBridgeBypass();
        this.knockbackClutch = new KnockbackClutch();
        this.ladderClutch = new LadderClutch();
        this.fireballJump = new FireballJump();
        this.tntJump = new TNTJump();
        this.sideClutch = new SideClutch();
        this.wallBlockClutch = new WallBlockClutch();
        this.blockExtensions = new BlockExtensions();
        this.knockbackWallClutch = new KnockbackWallClutch();
        this.hitBlockClutch = new HitBlockClutch();
        this.speedClutch = new SpeedClutch();
        this.bedBurrow = new BedBurrow();
        this.jumpAround = new JumpAround();
        this.bridgeStart = new BridgeStart();
        this.blockTotem = new BlockTotem();
        this.highBlockClutch = new HighBlockClutch();
        this.safeTower = new SafeTower();
        this.enderPearl = new EnderPearl();
        this.brokenWallRun = new BrokenWallRun();
        this.bedDefend = new BedDefend();

        if (this.theBridgeBypass.isEnabled()) {
            this.kits.add(this.theBridgeBypass);
        }

        if (this.knockbackClutch.isEnabled()) {
            this.kits.add(this.knockbackClutch);
        }

        if (this.ladderClutch.isEnabled()) {
            this.kits.add(this.ladderClutch);
        }

        if (this.fireballJump.isEnabled()) {
            this.kits.add(this.fireballJump);
        }

        if (this.tntJump.isEnabled()) {
            this.kits.add(this.tntJump);
        }

        if (this.sideClutch.isEnabled()) {
            this.kits.add(this.sideClutch);
        }

        if (this.wallBlockClutch.isEnabled()) {
            this.kits.add(this.wallBlockClutch);
        }

        if (this.blockExtensions.isEnabled()) {
            this.kits.add(this.blockExtensions);
        }

        if (this.knockbackWallClutch.isEnabled()) {
            this.kits.add(this.knockbackWallClutch);
        }

        if (this.hitBlockClutch.isEnabled()) {
            this.kits.add(this.hitBlockClutch);
        }

        if (this.speedClutch.isEnabled()) {
            this.kits.add(this.speedClutch);
        }

        if (this.bedBurrow.isEnabled()) {
            this.kits.add(this.bedBurrow);
        }

        if (this.jumpAround.isEnabled()) {
            this.kits.add(this.jumpAround);
        }

        if (this.bridgeStart.isEnabled()) {
            this.kits.add(this.bridgeStart);
        }

        if (this.blockTotem.isEnabled()) {
            this.kits.add(this.blockTotem);
        }

        if (this.highBlockClutch.isEnabled()) {
            this.kits.add(this.highBlockClutch);
        }

        if (this.safeTower.isEnabled()) {
            this.kits.add(this.safeTower);
        }

        if (this.enderPearl.isEnabled()) {
            this.kits.add(this.enderPearl);
        }

        if (this.brokenWallRun.isEnabled()) {
            this.kits.add(this.brokenWallRun);
        }

        if (this.bedDefend.isEnabled()) {
            this.kits.add(this.bedDefend);
        }

        Utils.debug("Making database connection");
        this.makeDatabaseConnection();
        Utils.debug("Initiating managers");
        this.initiateManagers();
        Utils.debug("Registering events");
        this.registerEvents();
    }

    public void onDisable() {
        instance = null;

        this.arenaManager.saveArenas();
        this.spawnManager.saveConfig();

        for (Chunk chunk : this.getServer().getWorld("world").getLoadedChunks()) {
            chunk.unload(true);
        }

        for (Map.Entry<UUID, Game> entry : this.gameManager.getGames().entrySet()) {
            Game game = entry.getValue();

            this.chunkRestorationManager.reset(game.getStandaloneArena());
        }

        for (Entity entity : this.getServer().getWorld("world").getEntities()) {
            if (entity.getType() != EntityType.PLAYER && entity.getType() != EntityType.ITEM_FRAME) {
                entity.remove();
            }
        }
    }

    private void makeDatabaseConnection() {
        databaseManager = new DatabaseManager(
                files.getSettingsConfig().getString("mongodb.host"),
                files.getSettingsConfig().getInt("mongodb.port"),
                files.getSettingsConfig().getString("mongodb.database"),
                files.getSettingsConfig().getBoolean("mongodb.auth.enabled") ? true : false,
                files.getSettingsConfig().getString("mongodb.auth.username"),
                files.getSettingsConfig().getString("mongodb.auth.password"),
                files.getSettingsConfig().getString("mongodb.auth.auth-database"));
    }

    private void initiateManagers() {
        new CommandManager(this);

        this.userManager = new UserManager(this);
        this.scoreboardManager = new ScoreboardManager(this);
        this.chunkRestorationManager = new ChunkRestorationManager();
        this.arenaManager = new ArenaManager(this);
        this.chunkManager = new ChunkManager(this);
        this.spawnManager = new SpawnManager(this);
        this.gameManager = new GameManager();
        this.hologramManager = new HologramManager(this);
        this.leaderboardManager = new LeaderboardManager(this);
        this.blockManager = new BlockManager(this);
    }

    private void registerEvents() {
        this.addEvent(new MenuListener());
        this.addEvent(new ButtonListener());
        this.addEvent(this.userManager);
        this.addEvent(this.scoreboardManager);
        this.addEvent(new RandomListeners(this));
        this.addEvent(new PlayerInteractListener(this));
        this.addEvent(new GameStartListener(this));
        this.addEvent(new GameStopListener(this));
        this.addEvent(new CustomEntityListener());
    }

    public PlayerMenuUtil getPlayerMenuUtil(Player player) {
        PlayerMenuUtil playerMenuUtil;

        if (playerMenuUtilMap.containsKey(player)) {
            return playerMenuUtilMap.get(player);
        } else {
            playerMenuUtil = new PlayerMenuUtil(player);

            playerMenuUtilMap.put(player, playerMenuUtil);

            return playerMenuUtil;
        }
    }

    public void addCommand(String commandName, CommandExecutor commandExecutor) {
        getCommand(commandName).setExecutor(commandExecutor);
        System.out.println("Enabled the /" + commandName + " command");
    }

    public void addEvent(Listener event) {
        getServer().getPluginManager().registerEvents(event, this);
        System.out.println("Enabled the " + event.toString() + " event");
    }
}
