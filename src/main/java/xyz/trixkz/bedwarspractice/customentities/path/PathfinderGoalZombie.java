package xyz.trixkz.bedwarspractice.customentities.path;

import net.minecraft.server.v1_8_R3.EntityInsentient;
import net.minecraft.server.v1_8_R3.EntityLiving;
import net.minecraft.server.v1_8_R3.PathfinderGoal;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftPlayer;
import xyz.trixkz.bedwarspractice.managers.user.User;

public class PathfinderGoalZombie extends PathfinderGoal {

    private EntityInsentient entityInsentient;
    private User user;
    private EntityLiving entityLiving;
    private double speed;
    private float distance;
    private double x;
    private double y;
    private double z;

    public PathfinderGoalZombie(EntityInsentient entityInsentient, User user, double speed, float distance) {
        this.entityInsentient = entityInsentient;
        this.user = user;
        this.speed = speed;
        this.distance = distance;

        this.a(1);
    }

    @Override
    public boolean a() {
        this.entityLiving = ((CraftPlayer) user.getPlayer()).getHandle();

        if (this.entityLiving == null || !this.entityLiving.isAlive()) {
            return false;
        } else if (this.entityInsentient.getCustomName() == null) {
            return false;
        } else if (this.entityLiving.h(this.entityInsentient) > (double) (this.distance * this.distance)) {
            if (user.getPlayerFromUniqueId() == null) {
                this.entityInsentient.die();
            } else {
                this.entityInsentient.setLocation(user.getPlayerFromUniqueId().getLocation().getX(), user.getPlayer().getLocation().getY(), user.getPlayer().getLocation().getZ(), this.entityLiving.yaw, this.entityLiving.pitch);
            }

            return false;
        } else {
            this.x = this.entityLiving.locX;
            this.y = this.entityLiving.locY;
            this.z = this.entityLiving.locZ;

            return true;
        }
    }

    @Override
    public boolean b() {
        return !this.entityInsentient.getNavigation().m() && this.entityLiving.h(this.entityInsentient) < (double) (this.distance * this.distance);
    }

    @Override
    public void c() {
        this.entityInsentient.getNavigation().a(this.x, this.y, this.z, this.speed);
    }

    @Override
    public void d() {
        this.entityLiving = null;
    }
}
