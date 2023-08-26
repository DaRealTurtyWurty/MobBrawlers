package dev.turtywurty.mobbrawlers;

import net.minecraftforge.common.ForgeConfigSpec;

public class CommonConfig {
    public static final ForgeConfigSpec SPEC;

    private static ForgeConfigSpec.DoubleValue sightDistance;
    private static ForgeConfigSpec.BooleanValue canAttackSameType;

    static {
        final ForgeConfigSpec.Builder builder = new ForgeConfigSpec.Builder();
        setup(builder);
        SPEC = builder.build();
    }

    private static void setup(ForgeConfigSpec.Builder builder) {
        builder.comment("General settings").push("general");

        sightDistance = builder.comment("The distance a mob can see a player from.")
                .translation(MobBrawlers.MOD_ID + ".config.sightDistance")
                .defineInRange("sightDistance", 200, 1, 10_000_000D);

        canAttackSameType = builder.comment("Whether or not mobs can attack the same type of mob.")
                .translation(MobBrawlers.MOD_ID + ".config.canAttackSameType")
                .define("canAttackSameType", false);

        builder.pop();
    }

    public static double getSightDistance() {
        return sightDistance.get();
    }

    public static boolean canAttackSameType() {
        return canAttackSameType.get();
    }
}
