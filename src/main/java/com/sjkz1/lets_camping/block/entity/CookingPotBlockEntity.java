package com.sjkz1.lets_camping.block.entity;

import com.sjkz1.lets_camping.item.crafting.CookingPotRecipe;
import com.sjkz1.lets_camping.registry.LCBlockEntityTypes;
import net.minecraft.core.BlockPos;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.NonNullList;
import net.minecraft.core.component.DataComponentMap;
import net.minecraft.core.component.DataComponents;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
import net.minecraft.util.Mth;
import net.minecraft.world.Clearable;
import net.minecraft.world.ContainerHelper;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.component.ItemContainerContents;
import net.minecraft.world.item.crafting.RecipeHolder;
import net.minecraft.world.item.crafting.RecipeManager;
import net.minecraft.world.item.crafting.SingleRecipeInput;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.CampfireBlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.gameevent.GameEvent;
import org.jetbrains.annotations.Nullable;

import java.util.Optional;

public class CookingPotBlockEntity extends BlockEntity implements Clearable {

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
                        cookingPotBlockEntity.items.set(i, itemStack2);
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
        boolean bl = false;
        for (int i = 0; i < cookingPotBlockEntity.items.size(); ++i) {
            if (cookingPotBlockEntity.cookingProgress[i] <= 0) continue;
            bl = true;
            cookingPotBlockEntity.cookingProgress[i] = Mth.clamp(cookingPotBlockEntity.cookingProgress[i] - 2, 0, cookingPotBlockEntity.cookingTime[i]);
        }
        if (bl) {
            CampfireBlockEntity.setChanged(level, blockPos, blockState);
        }
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

    @Override
    protected void loadAdditional(CompoundTag compoundTag, HolderLookup.Provider provider) {
        super.loadAdditional(compoundTag, provider);
        this.items.clear();
        ContainerHelper.loadAllItems(compoundTag, this.items, provider);
        if (compoundTag.contains("CookingTimes", 11)) {
            int[] is = compoundTag.getIntArray("CookingTimes");
            System.arraycopy(is, 0, this.cookingProgress, 0, Math.min(this.cookingTime.length, is.length));
        }

        if (compoundTag.contains("CookingTotalTimes", 11)) {
            int[] is = compoundTag.getIntArray("CookingTotalTimes");
            System.arraycopy(is, 0, this.cookingTime, 0, Math.min(this.cookingTime.length, is.length));
        }
    }

    @Override
    protected void saveAdditional(CompoundTag compoundTag, HolderLookup.Provider provider) {
        super.saveAdditional(compoundTag, provider);
        ContainerHelper.saveAllItems(compoundTag, this.items, true, provider);
        compoundTag.putIntArray("CookingTimes", this.cookingProgress);
        compoundTag.putIntArray("CookingTotalTimes", this.cookingTime);
    }

    public NonNullList<ItemStack> getItems() {
        return this.items;
    }

    @Override
    public CompoundTag getUpdateTag(HolderLookup.Provider provider) {
        CompoundTag compoundTag = new CompoundTag();
        ContainerHelper.saveAllItems(compoundTag, this.items, true, provider);
        return compoundTag;
    }

    private void markUpdated() {
        this.setChanged();
        this.getLevel().sendBlockUpdated(this.getBlockPos(), this.getBlockState(), this.getBlockState(), 3);
    }

    @Override
    protected void applyImplicitComponents(BlockEntity.DataComponentInput dataComponentInput) {
        super.applyImplicitComponents(dataComponentInput);
        dataComponentInput.getOrDefault(DataComponents.CONTAINER, ItemContainerContents.EMPTY).copyInto(this.getItems());
    }

    @Override
    protected void collectImplicitComponents(DataComponentMap.Builder builder) {
        super.collectImplicitComponents(builder);
        builder.set(DataComponents.CONTAINER, ItemContainerContents.fromItems(this.getItems()));
    }

    @Override
    public void removeComponentsFromTag(CompoundTag compoundTag) {
        compoundTag.remove("Items");
    }

    @Override
    public void clearContent() {
        this.items.clear();
    }

    @Override
    public @Nullable Packet<ClientGamePacketListener> getUpdatePacket() {
        return ClientboundBlockEntityDataPacket.create(this);
    }
}
