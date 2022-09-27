package xyz.trixkz.bedwarspractice.managers.hologram;

import lombok.Getter;
import net.minecraft.server.v1_8_R3.EntityArmorStand;
import net.minecraft.server.v1_8_R3.PacketPlayOutEntityDestroy;
import net.minecraft.server.v1_8_R3.PacketPlayOutEntityMetadata;
import net.minecraft.server.v1_8_R3.PacketPlayOutSpawnEntityLiving;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.craftbukkit.v1_8_R3.CraftWorld;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftPlayer;
import org.bukkit.entity.Player;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

@Getter
public abstract class Hologram {

    private final Map<EntityArmorStand, String> armorStands = new ConcurrentHashMap<>();

    private final List<String> lines = new ArrayList<>();
    private final Location location;
    private final int time;
    private int actualTime;

    public Hologram(Location location, int time) {
        this.location = location;
        this.time = time;
        this.actualTime = time;
    }

    protected void start(String name) {
        Timer timer = new Timer("Frost - " + name + " Hologram Worker");
        update();
        updateLines();

        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                try {
                    tick();
                } catch (Exception exception) {
                    exception.printStackTrace();
                }
            }
        }, 1000L, 1000L);
    }

    public void tick() {
        actualTime--;

        if (actualTime < 0) {
            actualTime = time;

            update();
        }

        lines.clear();
        updateLines();

        Map<EntityArmorStand, String> armors = new HashMap<>(armorStands);
        armorStands.clear();
        double y = location.getY();

        for (String s : lines) {
            EntityArmorStand stand = new EntityArmorStand(((CraftWorld) location.getWorld()).getHandle(), location.getX(), y, location.getZ());
            stand.setCustomName(s);
            stand.setCustomNameVisible(!s.equalsIgnoreCase("<void>"));
            stand.setInvisible(true);
            stand.setGravity(false);
            stand.setSmall(true);
            armorStands.put(stand, s);
            y -= 0.25;
        }

        Bukkit.getOnlinePlayers().forEach(player -> hide(player, armors));
        Bukkit.getOnlinePlayers().forEach(this::show);
    }

    public abstract void update();

    public abstract void updateLines();

    protected void show(Player player) {
        show(player, armorStands);
    }

    protected void hide(Player player) {
        hide(player, armorStands);
    }

    protected void show(Player player, Map<EntityArmorStand, String> armorStands) {
        armorStands.forEach((armorStand, line) -> {
            armorStand.setCustomName(line);
            PacketPlayOutSpawnEntityLiving armorStandPacket = new PacketPlayOutSpawnEntityLiving(armorStand);
            ((CraftPlayer) player).getHandle().playerConnection.networkManager.channel.write(armorStandPacket);

            PacketPlayOutEntityMetadata packet2 = new PacketPlayOutEntityMetadata(armorStand.getId(), armorStand.getDataWatcher(), true);
            ((CraftPlayer) player).getHandle().playerConnection.networkManager.channel.write(packet2);
        });
    }

    protected void hide(Player player, Map<EntityArmorStand, String> armorStands) {
        armorStands.forEach((armorStand, line) -> {
            PacketPlayOutEntityDestroy packet = new PacketPlayOutEntityDestroy(armorStand.getId());
            ((CraftPlayer) player).getHandle().playerConnection.networkManager.channel.write(packet);
        });
    }
}