package xyz.trixkz.bedwarspractice.utils;

import java.io.*;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import xyz.trixkz.bedwarspractice.Main;

public class Files {

    private Main main;

    private File settingsConfigFile;
    private FileConfiguration settingsConfig;

    private File arenaConfigFile;
    private FileConfiguration arenaConfig;

    public Files(Main main) {
        this.main = main;
    }

    public void loadConfig() {
        if (!this.main.getDataFolder().exists()) {
            this.main.getDataFolder().mkdirs();
        }

        settingsConfigFile = new File(main.getDataFolder(), "settings.yml");

        if (!settingsConfigFile.exists()) {
            this.main.saveResource("settings.yml", false);
        }

        settingsConfig = YamlConfiguration.loadConfiguration(settingsConfigFile);

        arenaConfigFile = new File(main.getDataFolder(), "arenas.yml");

        if (!arenaConfigFile.exists()) {
            this.main.saveResource("arenas.yml", false);
        }

        arenaConfig = YamlConfiguration.loadConfiguration(arenaConfigFile);
    }

    public FileConfiguration loadUser(Player player) {
        File hookDataf = new File(main.getDataFolder() + File.separator + "Players" + File.separator + player.getUniqueId() + ".yml");
        // Bukkit.getLogger().severe("--<[ Path: " + hookDataf.getAbsolutePath());
        // Bukkit.getLogger().severe("--<[ Exists?: " + hookDataf.exists());
        if (!hookDataf.exists()) {
            // Bukkit.getLogger().severe("--<[ Creating new file: " + hookDataf.getName());
            hookDataf.getParentFile().mkdirs();
            try {
                hookDataf.createNewFile();
            } catch (IOException exception) {
                exception.printStackTrace();
            }
        }

        FileConfiguration hookData = new YamlConfiguration();

        try {
            hookData.load(hookDataf);
        } catch (IOException exception) {
            exception.printStackTrace();
        } catch (InvalidConfigurationException exception) {
            exception.printStackTrace();
        }

        return hookData;
    }

    public void saveUser(Player player, FileConfiguration yml) {
        File folder = new File(main.getDataFolder() + File.separator + "Players");
        File file = new File(folder, File.separator + player.getUniqueId() + ".yml");

        try {
            yml.save(file);
        } catch (IOException exception) {
            exception.printStackTrace();
        }
    }

    public FileConfiguration getSettingsConfig() {
        return this.settingsConfig;
    }

    public File getSettingsConfigFile() {
        return this.settingsConfigFile;
    }

    public FileConfiguration getArenaConfig() {
        return this.arenaConfig;
    }

    public File getArenaConfigFile() {
        return this.arenaConfigFile;
    }
}