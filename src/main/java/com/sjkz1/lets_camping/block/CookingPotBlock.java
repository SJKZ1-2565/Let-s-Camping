package com.sjkz1.lets_camping.block;

import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import com.sjkz1.lets_camping.block.entity.CookingPotBlockEntity;
import com.sjkz1.lets_camping.item.crafting.CookingPotRecipe;
import com.sjkz1.lets_camping.registry.LCBlockEntityTypes;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.ItemInteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.alchemy.PotionContents;
import net.minecraft.world.item.alchemy.Potions;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.item.crafting.RecipeHolder;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.BaseEntityBlock;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.RenderShape;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.properties.DirectionProperty;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Optional;

public class CookingPotBlock extends BaseEntityBlock {

    public static final BooleanProperty LIT = BlockStateProperties.LIT;
    public static final IntegerProperty LEVEL = BlockStateProperties.LEVEL_CAULDRON;
    public static final DirectionProperty FACING = BlockStateProperties.HORIZONTAL_FACING;
    public static final MapCodec<CookingPotBlock> CODEC = RecordCodecBuilder.mapCodec(
            instance -> instance.group(
                            propertiesCodec()
                    )
                    .apply(instance, CookingPotBlock::new)
    );
    protected static final VoxelShape SHAPE = Block.box(0.0, 0.0, 0.0, 14.0, 5.0, 14.0);
    protected static final VoxelShape VISAUL_SHAPE = Block.box(0.0, 0.0, 0.0, 14.0, 16, 14.0);

    public CookingPotBlock(Properties properties) {
        super(properties);
        this.registerDefaultState(
                this.stateDefinition
                        .any()
                        .setValue(LIT, Boolean.valueOf(false))
                        .setValue(FACING, Direction.NORTH)
        );
    }

    public static boolean canLight(BlockState blockState) {
        return blockState.hasProperty(LIT)
                && !(Boolean) blockState.getValue(LIT);
    }

    @Override
    protected @NotNull MapCodec<CookingPotBlock> codec() {
        return CODEC;
    }

    @Override
    public @Nullable BlockEntity newBlockEntity(BlockPos blockPos, BlockState blockState) {
        return new CookingPotBlockEntity(blockPos, blockState);
    }

    @Override
    protected ItemInteractionResult useItemOn(ItemStack itemStack, BlockState blockState, Level level, BlockPos blockPos, Player player, InteractionHand interactionHand, BlockHitResult blockHitResult) {
        if (level.getBlockEntity(blockPos) instanceof CookingPotBlockEntity cookingPotBlockEntity) {
            ItemStack itemStack2 = player.getItemInHand(interactionHand);
            Optional<RecipeHolder<CookingPotRecipe>> optional = cookingPotBlockEntity.getCookableRecipe(itemStack2);
            if (optional.isPresent()) {
                if (!level.isClientSide
                        && cookingPotBlockEntity.placeFood(player, itemStack2, ((CookingPotRecipe) ((RecipeHolder) optional.get()).value()).getCookingTime())) {
                    return ItemInteractionResult.SUCCESS;
                }

                return ItemInteractionResult.CONSUME;
            }
            if (!level.isClientSide) {
                if (itemStack2.is(PotionContents.createItemStack(Items.POTION, Potions.WATER).getItem())) {
                    if (!player.hasInfiniteMaterials()) {
                        player.setItemInHand(interactionHand, new ItemStack(Items.GLASS_BOTTLE));
                    }
//                blockState.setValue(LEVEL, blockState.cycle());
                    blockState.cycle(LEVEL);
                    return ItemInteractionResult.SUCCESS;
                }
            }
        }

        return ItemInteractionResult.PASS_TO_DEFAULT_BLOCK_INTERACTION;
    }

    @Nullable
    @Override
    public BlockState getStateForPlacement(BlockPlaceContext blockPlaceContext) {
        return this.defaultBlockState()
                .setValue(LIT, false)
                .setValue(FACING, blockPlaceContext.getHorizontalDirection().getOpposite());
    }

    @Nullable
    @Override
    public <T extends BlockEntity> BlockEntityTicker<T> getTicker(Level level, BlockState blockState, BlockEntityType<T> blockEntityType) {
        if (level.isClientSide) {
            return blockState.getValue(LIT) ? createTickerHelper(blockEntityType, LCBlockEntityTypes.COOKING_POT, CookingPotBlockEntity::particleTick) : null;
        } else {
            return blockState.getValue(LIT)
                    ? createTickerHelper(blockEntityType, LCBlockEntityTypes.COOKING_POT, CookingPotBlockEntity::cookTick)
                    : createTickerHelper(blockEntityType, LCBlockEntityTypes.COOKING_POT, CookingPotBlockEntity::cooldownTick);
        }
    }

    @Override
    protected VoxelShape getShape(BlockState blockState, BlockGetter blockGetter, BlockPos blockPos, CollisionContext collisionContext) {
        return SHAPE;
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(LIT, FACING, LEVEL);
    }

    @Override
    protected @NotNull RenderShape getRenderShape(BlockState blockState) {
        return RenderShape.MODEL;
    }
}
