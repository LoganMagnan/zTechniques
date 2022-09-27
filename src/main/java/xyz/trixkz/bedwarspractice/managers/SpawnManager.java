package xyz.trixkz.bedwarspractice.managers;

import lombok.Getter;
import lombok.Setter;
import org.bukkit.configuration.file.FileConfiguration;
import xyz.trixkz.bedwarspractice.Main;
import xyz.trixkz.bedwarspractice.utils.CustomLocation;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class SpawnManager {

    private Main main;

    private CustomLocation spawnLocation;
    private CustomLocation spawnMin;
    private CustomLocation spawnMax;

    private CustomLocation hologramsLeaderboardsLocation;

    public SpawnManager(Main main) {
        this.main = main;

        this.loadConfig();
    }

    private void loadConfig() {
        FileConfiguration config = this.main.getFiles().getSettingsConfig();

        if (config.contains("spawn-location")) {
            try {
                this.spawnLocation = CustomLocation.stringToLocation(config.getString("spawn-location"));
                this.spawnMin = CustomLocation.stringToLocation(config.getString("spawn-min"));
                this.spawnMax = CustomLocation.stringToLocation(config.getString("spawn-max"));
            } catch (NullPointerException e) {
                this.main.getLogger().info("spawnMin and spawnMax locations not found!");
            }
        }

        if (config.contains("holograms-leaderboards-location")) {
            this.hologramsLeaderboardsLocation = CustomLocation.stringToLocation(config.getString("holograms-leaderboards-location"));
        }
    }

    public void saveConfig() {
        FileConfiguration config = this.main.getFiles().getSettingsConfig();
        config.set("spawn-location", CustomLocation.locationToString(this.spawnLocation));
        config.set("spawn-min", CustomLocation.locationToString(this.spawnMin));
        config.set("spawn-max", CustomLocation.locationToString(this.spawnMax));

        config.set("holograms-leaderboards-location", CustomLocation.locationToString(this.hologramsLeaderboardsLocation));

        try {
            this.main.getFiles().getSettingsConfig().save(this.main.getFiles().getSettingsConfigFile());
        } catch (IOException exception) {
            exception.printStackTrace();
        }
    }

    public List<String> fromLocations(List<CustomLocation> locations) {
        List<String> toReturn = new ArrayList<>();
        for (CustomLocation location : locations) {
            toReturn.add(CustomLocation.locationToString(location));
        }

        return toReturn;
    }
}
