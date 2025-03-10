package com.sjkz1.lets_camping.block;

import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.decoration.ArmorStand;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.BlockHitResult;

import java.util.List;

public class MatBlock extends Block {
    public MatBlock(Properties properties) {
        super(properties);
    }

    @Override
    protected InteractionResult useWithoutItem(BlockState blockState, Level level, BlockPos blockPos, Player player, BlockHitResult blockHitResult) {
        if (!level.isClientSide()) {
            Entity entity = null;
            List<ArmorStand> entities = level.getEntities(EntityType.ARMOR_STAND, new AABB(blockPos), chair -> true);
            if (entities.isEmpty()) {
                entity = EntityType.ARMOR_STAND.spawn((ServerLevel) level, blockPos, MobSpawnType.TRIGGERED);
            } else {
                entity = entities.get(0);
            }

            player.startRiding(entity);
        }

        return InteractionResult.SUCCESS;
    }

    @Override
    protected void onRemove(BlockState blockState, Level level, BlockPos blockPos, BlockState blockState2, boolean bl) {
        List<ArmorStand> entities = level.getEntities(EntityType.ARMOR_STAND, new AABB(blockPos), chair -> true);
        if (!entities.isEmpty()) {
            entities.get(0).remove(Entity.RemovalReason.DISCARDED);
        }
    }
}
