package com.sjkz1.lets_camping.registry;

import com.sjkz1.lets_camping.LetsCamping;
import com.sjkz1.lets_camping.block.CookingPotBlock;
import com.sjkz1.lets_camping.block.MatBlock;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockBehaviour;

public class LCBlocks {

    public static final Block COOKING_POT = new CookingPotBlock(BlockBehaviour.Properties.of().lightLevel(Blocks.litBlockEmission(15)).noOcclusion());
    public static final Block WHITE_GROUD_MAT = new MatBlock(BlockBehaviour.Properties.of());
    public static final Block ORANGE_GROUD_MAT = new MatBlock(BlockBehaviour.Properties.of());
    public static final Block MAGENTA_GROUD_MAT = new MatBlock(BlockBehaviour.Properties.of());
    public static final Block LIGHT_BLUE_GROUD_MAT = new MatBlock(BlockBehaviour.Properties.of());
    public static final Block YELLOW_GROUD_MAT = new MatBlock(BlockBehaviour.Properties.of());
    public static final Block LIME_GROUD_MAT = new MatBlock(BlockBehaviour.Properties.of());
    public static final Block PINK_GROUD_MAT = new MatBlock(BlockBehaviour.Properties.of());
    public static final Block GRAY_GROUD_MAT = new MatBlock(BlockBehaviour.Properties.of());
    public static final Block LIGHT_GRAY_GROUD_MAT = new MatBlock(BlockBehaviour.Properties.of());
    public static final Block CYAN_GROUD_MAT = new MatBlock(BlockBehaviour.Properties.of());
    public static final Block PURPLE_GROUD_MAT = new MatBlock(BlockBehaviour.Properties.of());
    public static final Block BLUE_GROUD_MAT = new MatBlock(BlockBehaviour.Properties.of());
    public static final Block BROWN_GROUD_MAT = new MatBlock(BlockBehaviour.Properties.of());
    public static final Block GREEN_GROUD_MAT = new MatBlock(BlockBehaviour.Properties.of());
    public static final Block RED_GROUD_MAT = new MatBlock(BlockBehaviour.Properties.of());
    public static final Block BLACK_GROUD_MAT = new MatBlock(BlockBehaviour.Properties.of());

    public static void init() {
        register(COOKING_POT, "cooking_pot", true);
        register(WHITE_GROUD_MAT, "white_groud_mat", true);
        register(ORANGE_GROUD_MAT, "orange_groud_mat", true);
        register(MAGENTA_GROUD_MAT, "magenta_groud_mat", true);
        register(LIGHT_BLUE_GROUD_MAT, "light_blue_groud_mat", true);
        register(YELLOW_GROUD_MAT, "yellow_groud_mat", true);
        register(LIME_GROUD_MAT, "lime_groud_mat", true);
        register(PINK_GROUD_MAT, "pink_groud_mat", true);
        register(GRAY_GROUD_MAT, "gray_groud_mat", true);
        register(LIGHT_GRAY_GROUD_MAT, "light_gray_groud_mat", true);
        register(CYAN_GROUD_MAT, "cyan_groud_mat", true);
        register(PURPLE_GROUD_MAT, "purple_groud_mat", true);
        register(BLUE_GROUD_MAT, "blue_groud_mat", true);
        register(BROWN_GROUD_MAT, "brown_groud_mat", true);
        register(GREEN_GROUD_MAT, "green_groud_mat", true);
        register(RED_GROUD_MAT, "red_groud_mat", true);
        register(BLACK_GROUD_MAT, "black_groud_mat", true);
    }

    private static void register(Block block, String id, boolean item) {
        Registry.register(BuiltInRegistries.BLOCK, ResourceLocation.tryBuild(LetsCamping.MOD_ID, id), block);
        if (item) {
            Registry.register(BuiltInRegistries.ITEM, ResourceLocation.tryBuild(LetsCamping.MOD_ID, id), new BlockItem(block, new Item.Properties()));
        }
    }
}
