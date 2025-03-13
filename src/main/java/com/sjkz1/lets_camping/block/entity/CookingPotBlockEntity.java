package com.sjkz1.lets_camping.block.entity;

import com.sjkz1.lets_camping.item.crafting.CookingPotRecipe;
import com.sjkz1.lets_camping.registry.LCBlockEntityTypes;
import net.minecraft.core.BlockPos;
import net.minecraft.core.NonNullList;
import net.minecraft.world.Containers;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.RecipeHolder;
import net.minecraft.world.item.crafting.RecipeManager;
import net.minecraft.world.item.crafting.SingleRecipeInput;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.gameevent.GameEvent;
import org.jetbrains.annotations.Nullable;

import java.util.Optional;

public class CookingPotBlockEntity extends BlockEntity {

    private final NonNullList<ItemStack> items = NonNullList.withSize(3, ItemStack.EMPTY);
    private final RecipeManager.CachedCheck<SingleRecipeInput, CookingPotRecipe> quickCheck = RecipeManager.createCheck(CookingPotRecipe.Type.INSTANCE);
    private final int[] cookingProgress = new int[4];
    private final int[] cookingTime = new int[4];

    public CookingPotBlockEntity(BlockPos blockPos, BlockState blockState) {
        super(LCBlockEntityTypes.COOKING_POT, blockPos, blockState);
    }

    public static void particleTick(Level level, BlockPos blockPos, BlockState blockState, CookingPotBlockEntity cookingPotBlockEntity) {

    }

    public static void cookTick(Level level, BlockPos blockPos, BlockState blockState, CookingPotBlockEntity cookingPotBlockEntity) {
        boolean bl = false;

        for (int i = 0; i < cookingPotBlockEntity.items.size(); i++) {
            ItemStack itemStack = cookingPotBlockEntity.items.get(i);
            if (!itemStack.isEmpty()) {
                bl = true;
                cookingPotBlockEntity.cookingProgress[i]++;
                if (cookingPotBlockEntity.cookingProgress[i] >= cookingPotBlockEntity.cookingTime[i]) {
                    SingleRecipeInput singleRecipeInput = new SingleRecipeInput(itemStack);
                    ItemStack itemStack2 = (ItemStack) cookingPotBlockEntity.quickCheck
                            .getRecipeFor(singleRecipeInput, level)
                            .map(recipeHolder -> ((CookingPotRecipe) recipeHolder.value()).assemble(singleRecipeInput, level.registryAccess()))
                            .orElse(itemStack);
                    if (itemStack2.isItemEnabled(level.enabledFeatures())) {
                        Containers.dropItemStack(level, blockPos.getX(), blockPos.getY(), blockPos.getZ(), itemStack2);
                        cookingPotBlockEntity.items.set(i, ItemStack.EMPTY);
                        level.sendBlockUpdated(blockPos, blockState, blockState, 3);
                        level.gameEvent(GameEvent.BLOCK_CHANGE, blockPos, GameEvent.Context.of(blockState));
                    }
                }
            }
        }

        if (bl) {
            setChanged(level, blockPos, blockState);
        }
    }

    public static void cooldownTick(Level level, BlockPos blockPos, BlockState blockState, CookingPotBlockEntity cookingPotBlockEntity) {
    }

    public Optional<RecipeHolder<CookingPotRecipe>> getCookableRecipe(ItemStack itemStack) {
        return this.items.stream().noneMatch(ItemStack::isEmpty) ? Optional.empty() : this.quickCheck.getRecipeFor(new SingleRecipeInput(itemStack), this.level);
    }

    public boolean placeFood(@Nullable LivingEntity livingEntity, ItemStack itemStack, int i) {
        for (int j = 0; j < this.items.size(); j++) {
            ItemStack itemStack2 = this.items.get(j);
            if (itemStack2.isEmpty()) {
                this.cookingTime[j] = i;
                this.cookingProgress[j] = 0;
                this.items.set(j, itemStack.consumeAndReturn(1, livingEntity));
                this.level.gameEvent(GameEvent.BLOCK_CHANGE, this.getBlockPos(), GameEvent.Context.of(livingEntity, this.getBlockState()));
                this.markUpdated();
                return true;
            }
        }

        return false;
    }

    public NonNullList<ItemStack> getItems() {
        return this.items;
    }


    private void markUpdated() {
        this.setChanged();
        this.getLevel().sendBlockUpdated(this.getBlockPos(), this.getBlockState(), this.getBlockState(), 3);
    }
}
