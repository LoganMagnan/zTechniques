package xyz.trixkz.bedwarspractice.menusystem.arena.pagination.settings;

import xyz.trixkz.bedwarspractice.utils.Utils;

public enum Mode {

    EASY(Utils.translate("&aEASY")),
    MEDIUM(Utils.translate("&cMEDIUM")),
    HARD(Utils.translate("&4HARD"));

    private String name;

    Mode(String name) {
        this.name = name;
    }

    public String getName() {
        return this.name;
    }
}
