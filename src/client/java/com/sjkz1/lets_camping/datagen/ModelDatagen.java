package com.sjkz1.lets_camping.datagen;


import com.sjkz1.lets_camping.LetsCamping;
import com.sjkz1.lets_camping.registry.LCBlocks;
import com.sjkz1.lets_camping.registry.LCItems;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricModelProvider;
import net.minecraft.data.models.BlockModelGenerators;
import net.minecraft.data.models.ItemModelGenerators;
import net.minecraft.data.models.blockstates.MultiVariantGenerator;
import net.minecraft.data.models.model.*;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;

import java.util.Optional;

public class ModelDatagen extends FabricModelProvider {

    public static final TextureSlot STICK = TextureSlot.create("stick");
    public static final TextureSlot POT = TextureSlot.create("pot");

    public static final ModelTemplate COOKING_POT = create("template_cooking_pot", TextureSlot.FIRE, TextureSlot.LIT_LOG, STICK, POT);

    public ModelDatagen(FabricDataOutput output) {
        super(output);
    }

    @Override
    public void generateBlockStateModels(BlockModelGenerators blockModelGenerators) {
        createCookingPot(blockModelGenerators, LCBlocks.COOKING_POT);

    }

    @Override
    public void generateItemModels(ItemModelGenerators itemModelGenerators) {
        itemModelGenerators.generateFlatItem(LCItems.JAM_BOTTLE, ModelTemplates.FLAT_ITEM);
        itemModelGenerators.generateFlatItem(LCItems.BERRY_JAM, ModelTemplates.FLAT_ITEM);
    }

    public final void createCookingPot(BlockModelGenerators blockModelGenerators, Block block) {
        ResourceLocation resourceLocation = ModelLocationUtils.getModelLocation(LCBlocks.COOKING_POT, "_campfire_off");
        ResourceLocation resourceLocation2 = COOKING_POT.create(block, cooking(block), blockModelGenerators.modelOutput);
        blockModelGenerators.createSimpleFlatItemModel(block.asItem());
        blockModelGenerators.blockStateOutput
                .accept(
                        MultiVariantGenerator.multiVariant(block).with(BlockModelGenerators.createBooleanModelDispatch(BlockStateProperties.LIT, resourceLocation2, resourceLocation))
                                .with(BlockModelGenerators.createHorizontalFacingDispatchAlt())
                );
    }


    private static ModelTemplate create(String string, TextureSlot... textureSlots) {
        return new ModelTemplate(Optional.of(ResourceLocation.tryBuild(LetsCamping.MOD_ID, "block/" + string)), Optional.empty(), textureSlots);
    }

    public static TextureMapping cooking(Block block) {
        return new TextureMapping().put(TextureSlot.LIT_LOG, TextureMapping.getBlockTexture(Blocks.CAMPFIRE, "_log_lit")).put(TextureSlot.FIRE, TextureMapping.getBlockTexture(Blocks.CAMPFIRE, "_fire")).put(STICK, TextureMapping.getBlockTexture(Blocks.OAK_LOG, "")).put(POT, TextureMapping.getBlockTexture(Blocks.CAULDRON, "_side"));
    }
}
