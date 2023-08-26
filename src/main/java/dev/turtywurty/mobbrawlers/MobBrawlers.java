package dev.turtywurty.mobbrawlers;

import com.mojang.logging.LogUtils;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraftforge.event.entity.EntityAttributeModificationEvent;
import net.minecraftforge.eventbus.api.EventPriority;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ConfigTracker;
import net.minecraftforge.fml.config.ModConfig;
import net.minecraftforge.fml.loading.FMLPaths;
import org.slf4j.Logger;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.nio.file.Path;
import java.util.EnumMap;
import java.util.Map;
import java.util.Set;

@Mod(MobBrawlers.MOD_ID)
public class MobBrawlers {
    public static final String MOD_ID = "mobbrawlers";
    public static final Logger LOGGER = LogUtils.getLogger();

    public MobBrawlers() {
        LOGGER.info("Hello from Mob Brawlers!");

        ModLoadingContext.get().registerConfig(ModConfig.Type.COMMON, CommonConfig.SPEC);

        ModConfig commonConfig = ConfigTracker.INSTANCE.configSets().get(ModConfig.Type.COMMON)
                .stream()
                .filter(modConfig -> modConfig.getModId().equals(MOD_ID))
                .findFirst()
                .orElseThrow(IllegalStateException::new);
        Method openConfig;
        try {
            openConfig = ConfigTracker.INSTANCE.getClass().getDeclaredMethod("openConfig", ModConfig.class, Path.class);
            openConfig.setAccessible(true);
            openConfig.invoke(ConfigTracker.INSTANCE, commonConfig, FMLPaths.CONFIGDIR.get());
            openConfig.setAccessible(false);
        } catch (NoSuchMethodException | IllegalAccessException | InvocationTargetException exception) {
            throw new RuntimeException(exception);
        }
    }

    @Mod.EventBusSubscriber(modid = MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD)
    public static class ModEvents {
        @SubscribeEvent(priority = EventPriority.HIGHEST)
        public static void entityAttributeModificationEvent(EntityAttributeModificationEvent event) {
            LOGGER.info("Entity Attribute Creation Event");
            for (EntityType<? extends LivingEntity> entityType : event.getTypes()) {
                event.add(entityType, Attributes.FOLLOW_RANGE, CommonConfig.getSightDistance());
            }
        }
    }
}
