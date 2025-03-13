package com.sjkz1.lets_camping.registry;

import com.sjkz1.lets_camping.LetsCamping;
import com.sjkz1.lets_camping.block.entity.CookingPotBlockEntity;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.entity.BlockEntityType;

public class LCBlockEntityTypes {
    public static <T extends BlockEntityType<?>> T register(String path, T blockEntityType) {
        return Registry.register(BuiltInRegistries.BLOCK_ENTITY_TYPE, ResourceLocation.fromNamespaceAndPath(LetsCamping.MOD_ID, path), blockEntityType);
    }

    public static void init() {
    }

    public static final BlockEntityType<CookingPotBlockEntity> COOKING_POT = register(
            "cooking_pot",
            BlockEntityType.Builder.of(CookingPotBlockEntity::new, LCBlocks.COOKING_POT).build()
    );


}
