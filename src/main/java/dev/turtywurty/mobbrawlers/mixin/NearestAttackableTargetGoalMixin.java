package dev.turtywurty.mobbrawlers.mixin;

import dev.turtywurty.mobbrawlers.CommonConfig;
import dev.turtywurty.mobbrawlers.MobBrawlers;
import dev.turtywurty.mobbrawlers.util.AttackPredicate;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.ai.goal.target.TargetGoal;
import net.minecraft.world.entity.ai.targeting.TargetingConditions;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Mutable;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.function.Predicate;

@Mixin(NearestAttackableTargetGoal.class)
public abstract class NearestAttackableTargetGoalMixin extends TargetGoal {
    @Mutable
    @Final
    @Shadow
    protected Class<?> targetType;

    @Shadow
    protected TargetingConditions targetConditions;

    private NearestAttackableTargetGoalMixin(Mob pMob, boolean pMustSee) {
        super(pMob, pMustSee);
    }

    @Inject(
            method = "<init>(Lnet/minecraft/world/entity/Mob;Ljava/lang/Class;IZZLjava/util/function/Predicate;)V",
            at = @At("RETURN")
    )
    private void mobbrawlers$init(Mob pMob, Class<?> pTargetType, int pRandomInterval, boolean pMustSee, boolean pMustReach, Predicate<?> pTargetPredicate, CallbackInfo ci) {
        this.targetType = LivingEntity.class;
        this.targetConditions = TargetingConditions.forCombat()
                .range(CommonConfig.getSightDistance())
                .selector(new AttackPredicate(this.mob));
    }
}
