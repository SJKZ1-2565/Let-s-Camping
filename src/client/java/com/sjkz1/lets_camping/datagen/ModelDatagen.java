package com.sjkz1.lets_camping.datagen;


import com.sjkz1.lets_camping.LetsCamping;
import com.sjkz1.lets_camping.block.MatBlock;
import com.sjkz1.lets_camping.block.MatType;
import com.sjkz1.lets_camping.registry.LCBlocks;
import com.sjkz1.lets_camping.registry.LCItems;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricModelProvider;
import net.minecraft.data.models.BlockModelGenerators;
import net.minecraft.data.models.ItemModelGenerators;
import net.minecraft.data.models.blockstates.MultiVariantGenerator;
import net.minecraft.data.models.blockstates.PropertyDispatch;
import net.minecraft.data.models.blockstates.Variant;
import net.minecraft.data.models.blockstates.VariantProperties;
import net.minecraft.data.models.model.*;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;

import java.util.Optional;

public class ModelDatagen extends FabricModelProvider {

    public static final TextureSlot STICK = TextureSlot.create("stick");
    public static final TextureSlot POT = TextureSlot.create("pot");
    public static final TextureSlot GROUD_MAT = TextureSlot.create("groud_mat");

    public static final ModelTemplate COOKING_POT = create("template_cooking_pot", TextureSlot.FIRE, TextureSlot.LIT_LOG, STICK, POT);
    public static final ModelTemplate MAT = create("template_mat", GROUD_MAT);

    public ModelDatagen(FabricDataOutput output) {
        super(output);
    }

    private static ModelTemplate create(String string, TextureSlot... textureSlots) {
        return new ModelTemplate(Optional.of(ResourceLocation.tryBuild(LetsCamping.MOD_ID, "block/" + string)), Optional.empty(), textureSlots);
    }

    public static TextureMapping cooking(Block block) {
        return new TextureMapping().put(TextureSlot.LIT_LOG, TextureMapping.getBlockTexture(Blocks.CAMPFIRE, "_log_lit")).put(TextureSlot.FIRE, TextureMapping.getBlockTexture(Blocks.CAMPFIRE, "_fire")).put(STICK, TextureMapping.getBlockTexture(Blocks.OAK_LOG, "")).put(POT, TextureMapping.getBlockTexture(Blocks.CAULDRON, "_side"));
    }

    public static TextureMapping mat(Block block) {
        return new TextureMapping().put(GROUD_MAT, TextureMapping.getBlockTexture(block, ""));
    }

    @Override
    public void generateBlockStateModels(BlockModelGenerators blockModelGenerators) {
        createCookingPot(blockModelGenerators, LCBlocks.COOKING_POT);
        createMat(blockModelGenerators, LCBlocks.GROUD_MAT, LCBlocks.ORANGE_GROUD_MAT, LCBlocks.MAGENTA_GROUD_MAT, LCBlocks.LIGHT_BLUE_GROUD_MAT, LCBlocks.YELLOW_GROUD_MAT, LCBlocks.LIME_GROUD_MAT, LCBlocks.PINK_GROUD_MAT, LCBlocks.GRAY_GROUD_MAT, LCBlocks.LIGHT_GRAY_GROUD_MAT, LCBlocks.CYAN_GROUD_MAT, LCBlocks.PURPLE_GROUD_MAT, LCBlocks.BLUE_GROUD_MAT, LCBlocks.BROWN_GROUD_MAT, LCBlocks
                .GREEN_GROUD_MAT, LCBlocks.RED_GROUD_MAT, LCBlocks.BLACK_GROUD_MAT);
    }

    @Override
    public void generateItemModels(ItemModelGenerators itemModelGenerators) {
        itemModelGenerators.generateFlatItem(LCItems.JAM_BOTTLE, ModelTemplates.FLAT_ITEM);
        itemModelGenerators.generateFlatItem(LCItems.BERRY_JAM, ModelTemplates.FLAT_ITEM);
    }

    public final void createCookingPot(BlockModelGenerators blockModelGenerators, Block block) {
        ResourceLocation resourceLocation = ModelLocationUtils.getModelLocation(LCBlocks.COOKING_POT, "_campfire_off");
        ResourceLocation resourceLocation2 = COOKING_POT.create(block, cooking(block), blockModelGenerators.modelOutput);
        blockModelGenerators.blockStateOutput
                .accept(
                        MultiVariantGenerator.multiVariant(block).with(BlockModelGenerators.createBooleanModelDispatch(BlockStateProperties.LIT, resourceLocation2, resourceLocation))
                                .with(BlockModelGenerators.createHorizontalFacingDispatchAlt())
                );
    }

    public final void createMat(BlockModelGenerators blockModelGenerators, Block... blocks) {
        for (Block block : blocks) {
            ResourceLocation resourceLocation1 = ModelLocationUtils.getModelLocation(LCBlocks.GROUD_MAT, "_slab");
            ResourceLocation resourceLocation2 = ModelLocationUtils.getModelLocation(LCBlocks.GROUD_MAT, "_stair");
            ResourceLocation resourceLocation = MAT.create(block, mat(block), blockModelGenerators.modelOutput);
            blockModelGenerators.blockStateOutput.accept(MultiVariantGenerator.multiVariant(block, Variant.variant().with(VariantProperties.MODEL, resourceLocation)).with(PropertyDispatch.property(MatBlock.TYPE)
                    .select(MatType.FULL, Variant.variant().with(VariantProperties.MODEL, resourceLocation))
                    .select(MatType.SLAB, Variant.variant().with(VariantProperties.MODEL, resourceLocation1))
                    .select(MatType.STAIR, Variant.variant().with(VariantProperties.MODEL, resourceLocation2))));
        }
    }

}
