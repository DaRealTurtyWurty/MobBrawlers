package dev.turtywurty.mobbrawlers.util;

import dev.turtywurty.mobbrawlers.CommonConfig;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.OwnableEntity;
import net.minecraft.world.entity.TamableAnimal;

import java.util.function.Predicate;

public class AttackPredicate implements Predicate<LivingEntity> {
    private final Mob mob;

    public AttackPredicate(Mob mob) {
        this.mob = mob;
    }

    @Override
    public boolean test(LivingEntity entity) {
        return entity != null
                && (CommonConfig.canAttackSameType() || entity.getClass() != this.mob.getClass())
                && entity.isAlive();
    }
}