package com.sjkz1.lets_camping.mixin.block;

import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import com.llamalad7.mixinextras.sugar.Local;
import com.sjkz1.lets_camping.block.CookingPotBlock;
import net.minecraft.world.item.FlintAndSteelItem;
import net.minecraft.world.level.block.CandleCakeBlock;
import net.minecraft.world.level.block.state.BlockState;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(FlintAndSteelItem.class)
public class FlintAndSteelItemMixin {
    @ModifyExpressionValue(method = "useOn", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/level/block/CandleCakeBlock;canLight(Lnet/minecraft/world/level/block/state/BlockState;)Z"))
    public boolean useOn(boolean original, @Local BlockState blockState) {
        return !CandleCakeBlock.canLight(blockState) || !CookingPotBlock.canLight(blockState);
    }
}
