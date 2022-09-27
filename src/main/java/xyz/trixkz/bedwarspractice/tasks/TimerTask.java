package xyz.trixkz.bedwarspractice.tasks;

import org.bukkit.scheduler.BukkitRunnable;

public class TimerTask extends BukkitRunnable {

    private int time;

    public TimerTask(int time) {
        this.time = time;
    }

    public void run() {
        time--;

        if (time <= 0) {
            this.cancel();

            return;
        }
    }

    public int getTime() {
        return this.time;
    }
}
