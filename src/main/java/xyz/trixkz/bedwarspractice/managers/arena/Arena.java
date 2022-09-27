package xyz.trixkz.bedwarspractice.managers.arena;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import xyz.trixkz.bedwarspractice.utils.CustomLocation;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@RequiredArgsConstructor
public class Arena {

    private final String name;

    private List<StandaloneArena> standaloneArenas;
    private List<StandaloneArena> availableArenas;

    private String icon;
    private int iconData;

    private CustomLocation a;

    private CustomLocation min;
    private CustomLocation max;

    private boolean enabled;

    public StandaloneArena getAvailableArena() {
        StandaloneArena arena = this.availableArenas.get(0);

        this.availableArenas.remove(0);

        return arena;
    }

    public void addStandaloneArena(StandaloneArena arena) {
        this.standaloneArenas.add(arena);
    }

    public void addAvailableArena(StandaloneArena arena) {
        this.availableArenas.add(arena);
    }
}
