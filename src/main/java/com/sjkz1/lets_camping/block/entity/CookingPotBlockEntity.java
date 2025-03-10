package com.sjkz1.lets_camping.block.entity;

import com.sjkz1.lets_camping.registry.LCBlockEntityTypes;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;

public class CookingPotBlockEntity extends BlockEntity {
    public CookingPotBlockEntity(BlockPos blockPos, BlockState blockState) {
        super(LCBlockEntityTypes.COOKING_POT, blockPos, blockState);
    }
}
