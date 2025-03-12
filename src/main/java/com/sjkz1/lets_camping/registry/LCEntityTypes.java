package com.sjkz1.lets_camping.registry;

import com.sjkz1.lets_camping.LetsCamping;
import com.sjkz1.lets_camping.entity.MatEntity;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;

public class LCEntityTypes {
    public static final EntityType<MatEntity> MAT = Registry.register(BuiltInRegistries.ENTITY_TYPE,
            ResourceLocation.tryBuild(LetsCamping.MOD_ID, "mat"),
            EntityType.Builder.of(MatEntity::new, MobCategory.MISC)
                    .sized(0.5f, 0.5f).build());


    public static void init() {
    }
}
