package xyz.trixkz.bedwarspractice.customentities;

import net.minecraft.server.v1_8_R3.*;
import org.bukkit.ChatColor;
import org.bukkit.World;
import org.bukkit.craftbukkit.v1_8_R3.CraftWorld;
import org.bukkit.craftbukkit.v1_8_R3.inventory.CraftItemStack;
import xyz.trixkz.bedwarspractice.Main;
import xyz.trixkz.bedwarspractice.customentities.path.PathfinderGoalZombie;
import xyz.trixkz.bedwarspractice.managers.user.User;
import xyz.trixkz.bedwarspractice.utils.ItemBuilder;
import xyz.trixkz.bedwarspractice.utils.Utils;

import java.util.List;

public class CustomZombie extends EntityZombie {

    public CustomZombie(World world, User user) {
        super(((CraftWorld) world).getHandle());

        List goalB = (List) Main.getInstance().getUtils().getPrivateField("b", PathfinderGoalSelector.class, this.goalSelector);
        goalB.clear();

        List goalC = (List) Main.getInstance().getUtils().getPrivateField("c", PathfinderGoalSelector.class, this.goalSelector);
        goalC.clear();

        List targetB = (List) Main.getInstance().getUtils().getPrivateField("b", PathfinderGoalSelector.class, this.targetSelector);
        targetB.clear();

        List targetC = (List) Main.getInstance().getUtils().getPrivateField("c", PathfinderGoalSelector.class, this.targetSelector);
        targetC.clear();

        ((Navigation) this.getNavigation()).b(true);

        this.goalSelector.a(0, new PathfinderGoalFloat(this));
        this.goalSelector.a(2, new PathfinderGoalMeleeAttack(this, EntityHuman.class, 1.0D, false));
        this.goalSelector.a(5, new PathfinderGoalMoveTowardsRestriction(this, 1.0D));
        this.goalSelector.a(7, new PathfinderGoalRandomStroll(this, 1.0D));
        this.goalSelector.a(8, new PathfinderGoalLookAtPlayer(this, EntityHuman.class, 8.0F));
        this.goalSelector.a(8, new PathfinderGoalRandomLookaround(this));
        this.goalSelector.a(1, new PathfinderGoalZombie(this, user, 1, 20));

        this.goalSelector.a(4, new PathfinderGoalMeleeAttack(this, EntityIronGolem.class, 1.0D, true));
        this.goalSelector.a(6, new PathfinderGoalMoveThroughVillage(this, 1.0D, false));
        this.targetSelector.a(1, new PathfinderGoalHurtByTarget(this, true, new Class[]{EntityPigZombie.class}));
        this.targetSelector.a(2, new PathfinderGoalNearestAttackableTarget(this, EntityHuman.class, true));

        this.setCustomName(ChatColor.GREEN + user.getPlayerFromUniqueId().getName() + " Opponent");
        this.setCustomNameVisible(true);

        this.setSprinting(true);

        net.minecraft.server.v1_8_R3.ItemStack nmsSword = CraftItemStack.asNMSCopy(new ItemBuilder(org.bukkit.Material.DIAMOND_SWORD).enchantment(org.bukkit.enchantments.Enchantment.DAMAGE_ALL, 4).build());

        setEquipment(0, nmsSword);

        getAttributeInstance(GenericAttributes.maxHealth).setValue(1020);
        getAttributeInstance(GenericAttributes.ATTACK_DAMAGE).setValue(10);
        getAttributeInstance(GenericAttributes.FOLLOW_RANGE).setValue(30);
    }
}
