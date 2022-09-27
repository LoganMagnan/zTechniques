package xyz.trixkz.bedwarspractice.managers.arena;

import lombok.Getter;
import lombok.Setter;
import org.bukkit.Material;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;
import xyz.trixkz.bedwarspractice.Main;
import xyz.trixkz.bedwarspractice.kits.Kit;
import xyz.trixkz.bedwarspractice.utils.CustomLocation;

import java.io.IOException;
import java.util.*;
import java.util.concurrent.ThreadLocalRandom;

public class ArenaManager {

    private Main main;

    @Getter
    private final Map<String, Arena> arenas = new HashMap<>();
    @Getter private final Map<StandaloneArena, UUID> arenaMatchUUIDs = new HashMap<>();

    @Getter @Setter
    private int generatingArenaRunnable;

    public ArenaManager(Main main) {
        this.main = main;

        this.loadArenas();
    }

    private void loadArenas() {
        FileConfiguration fileConfig = this.main.getFiles().getArenaConfig();
        ConfigurationSection arenaSection = fileConfig.getConfigurationSection("arenas");
        if (arenaSection == null) {
            return;
        }

        arenaSection.getKeys(false).forEach(name -> {
            String icon = arenaSection.getString(name + ".icon") == null ? Material.PAPER.name() : arenaSection.getString(name + ".icon");
            int iconData = arenaSection.getInt(name + ".icon-data");
            String spawnpoint = arenaSection.getString(name + ".spawnpoint");
            String min = arenaSection.getString(name + ".min");
            String max = arenaSection.getString(name + ".max");

            CustomLocation locA = CustomLocation.stringToLocation(spawnpoint);
            CustomLocation locMin = CustomLocation.stringToLocation(min);
            CustomLocation locMax = CustomLocation.stringToLocation(max);

            List<StandaloneArena> standaloneArenas = new ArrayList<>();
            ConfigurationSection saSection = arenaSection.getConfigurationSection(name + ".standaloneArenas");
            if (saSection != null) {
                saSection.getKeys(false).forEach(id -> {
                    String saA = saSection.getString(id + ".spawnpoint");
                    String saMin = saSection.getString(id + ".min");
                    String saMax = saSection.getString(id + ".max");

                    CustomLocation locSaA = CustomLocation.stringToLocation(saA);
                    CustomLocation locSaMin = CustomLocation.stringToLocation(saMin);
                    CustomLocation locSaMax = CustomLocation.stringToLocation(saMax);

                    StandaloneArena standaloneArena = new StandaloneArena(locSaA, locSaMin, locSaMax/*, locSaBuildMax, locSaDeadZone*/);
                    this.main.getChunkRestorationManager().copy(standaloneArena);

                    standaloneArenas.add(standaloneArena);
                });
            }

            boolean enabled = arenaSection.getBoolean(name + ".enabled", false);

            Arena arena = new Arena(name, standaloneArenas, new ArrayList<>(standaloneArenas), icon, iconData, locA, locMin, locMax, enabled);
            this.arenas.put(name, arena);
        });
    }

    public void reloadArenas() {
        this.saveArenas();
        this.arenas.clear();
        this.loadArenas();
    }

    public void saveArenas() {
        FileConfiguration fileConfig = this.main.getFiles().getArenaConfig();

        fileConfig.set("arenas", null);
        arenas.forEach((arenaName, arena) -> {
            String icon = arena.getIcon();
            int iconData = arena.getIconData();
            String spawnpoint = CustomLocation.locationToString(arena.getA());
            String min = CustomLocation.locationToString(arena.getMin());
            String max = CustomLocation.locationToString(arena.getMax());

            String arenaRoot = "arenas." + arenaName;

            fileConfig.set(arenaRoot + ".icon", icon);
            fileConfig.set(arenaRoot + ".icon-data", iconData);
            fileConfig.set(arenaRoot + ".spawnpoint", spawnpoint);
            fileConfig.set(arenaRoot + ".min", min);
            fileConfig.set(arenaRoot + ".max", max);
            fileConfig.set(arenaRoot + ".enabled", arena.isEnabled());
            fileConfig.set(arenaRoot + ".standaloneArenas", null);

            int i = 0;
            if (arena.getStandaloneArenas() != null) {
                for (StandaloneArena saArena : arena.getStandaloneArenas()) {
                    String saA = CustomLocation.locationToString(saArena.getA());
                    String saMin = CustomLocation.locationToString(saArena.getMin());
                    String saMax = CustomLocation.locationToString(saArena.getMax());

                    String standAloneRoot = arenaRoot + ".standaloneArenas." + i;

                    fileConfig.set(standAloneRoot + ".spawnpoint", saA);
                    fileConfig.set(standAloneRoot + ".min", saMin);
                    fileConfig.set(standAloneRoot + ".max", saMax);

                    i++;
                }
            }
        });

        try {
            this.main.getFiles().getArenaConfig().save(this.main.getFiles().getArenaConfigFile());
        } catch (IOException exception) {
            exception.printStackTrace();
        }
    }

    public void createArena(String name) {
        this.arenas.put(name, new Arena(name));
    }

    public void deleteArena(String name) {
        this.arenas.remove(name);
    }

    public Arena getArena(String name) {
        return this.arenas.get(name);
    }

    public Arena getRandomArena(Kit kit) {
        List<Arena> enabledArenas = new ArrayList<>();

        for (Arena arena : this.arenas.values()) {
            if (!arena.isEnabled()) continue;

            enabledArenas.add(arena);
        }

        if (enabledArenas.size() == 0) {
            return null;
        }

        return enabledArenas.get(ThreadLocalRandom.current().nextInt(enabledArenas.size()));
    }

    public void removeArenaMatchUUID(StandaloneArena arena) {
        this.arenaMatchUUIDs.remove(arena);
    }

    public UUID getArenaGameUUID(StandaloneArena arena) {
        return this.arenaMatchUUIDs.get(arena);
    }

    public void setArenaGameUUID(StandaloneArena arena, UUID matchUUID) {
        this.arenaMatchUUIDs.put(arena, matchUUID);
    }
}
