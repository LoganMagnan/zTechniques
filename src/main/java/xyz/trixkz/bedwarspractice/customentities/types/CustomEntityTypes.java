package xyz.trixkz.bedwarspractice.customentities.types;

import net.minecraft.server.v1_8_R3.Entity;
import net.minecraft.server.v1_8_R3.EntityTypes;
import org.bukkit.Location;
import org.bukkit.craftbukkit.v1_8_R3.CraftWorld;
import xyz.trixkz.bedwarspractice.Main;
import xyz.trixkz.bedwarspractice.customentities.CustomZombie;

import java.util.Map;

public enum CustomEntityTypes {

    CUSTOM_ZOMBIE("Bed Defend Zombie", 54, CustomZombie.class);

    CustomEntityTypes(String name, int id, Class<? extends Entity> clazz) {
        this.addToMaps(name, id, clazz);
    }

    public static Entity spawnEntity(Entity entity, Location location) {
        entity.setLocation(location.getX(), location.getY(), location.getZ(), location.getYaw(), location.getPitch());

        ((CraftWorld) location.getWorld()).getHandle().addEntity(entity);

        return entity;
    }

    private void addToMaps(String name, int id, Class clazz) {
        ((Map) Main.getInstance().getUtils().getPrivateField("c", EntityTypes.class, null)).put(name, clazz);
        ((Map) Main.getInstance().getUtils().getPrivateField("d", EntityTypes.class, null)).put(clazz, name);
        ((Map) Main.getInstance().getUtils().getPrivateField("f", EntityTypes.class, null)).put(clazz, id);
    }
}
