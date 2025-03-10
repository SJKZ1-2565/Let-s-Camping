package com.sjkz1.lets_camping.registry;

import com.sjkz1.lets_camping.LetsCamping;
import com.sjkz1.lets_camping.block.CookingPotBlock;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockBehaviour;

import java.util.function.ToIntFunction;

public class LCBlocks {

    public static final Block COOKING_POT = new CookingPotBlock(false, BlockBehaviour.Properties.of().lightLevel(Blocks.litBlockEmission(15)).noOcclusion());

    public static void init() {
        register(COOKING_POT, "cooking_pot", true);
    }

    private static void register(Block block, String id, boolean item) {
        Registry.register(BuiltInRegistries.BLOCK, ResourceLocation.tryBuild(LetsCamping.MOD_ID, id), block);
        if (item) {
            Registry.register(BuiltInRegistries.ITEM, ResourceLocation.tryBuild(LetsCamping.MOD_ID, id), new BlockItem(block, new Item.Properties()));
        }
    }
}
