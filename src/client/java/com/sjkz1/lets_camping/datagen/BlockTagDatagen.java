package com.sjkz1.lets_camping.datagen;

import com.sjkz1.lets_camping.registry.LCBlocks;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricTagProvider;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.level.block.Block;

import java.util.concurrent.CompletableFuture;

public class BlockTagDatagen extends FabricTagProvider<Block> {

    public BlockTagDatagen(FabricDataOutput output, CompletableFuture<HolderLookup.Provider> registriesFuture) {
        super(output, Registries.BLOCK, registriesFuture);
    }

    @Override
    protected void addTags(HolderLookup.Provider provider) {
        getOrCreateTagBuilder(LCBlocks.MATS).add(LCBlocks.GROUD_MAT, LCBlocks.ORANGE_GROUD_MAT, LCBlocks.MAGENTA_GROUD_MAT, LCBlocks.LIGHT_BLUE_GROUD_MAT, LCBlocks.YELLOW_GROUD_MAT, LCBlocks.LIME_GROUD_MAT, LCBlocks.PINK_GROUD_MAT, LCBlocks.GRAY_GROUD_MAT, LCBlocks.LIGHT_GRAY_GROUD_MAT, LCBlocks.CYAN_GROUD_MAT, LCBlocks.PURPLE_GROUD_MAT, LCBlocks.BLUE_GROUD_MAT, LCBlocks.BROWN_GROUD_MAT, LCBlocks
                .GREEN_GROUD_MAT, LCBlocks.RED_GROUD_MAT, LCBlocks.BLACK_GROUD_MAT);
    }


}
