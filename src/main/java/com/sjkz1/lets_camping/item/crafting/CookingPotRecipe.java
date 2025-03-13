package com.sjkz1.lets_camping.item.crafting;

import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.*;
import org.jetbrains.annotations.NotNull;

public class CookingPotRecipe extends AbstractCookingRecipe {

    public CookingPotRecipe(String group, CookingBookCategory category, Ingredient ingredient, ItemStack result, float experience, int cookingTime) {
        super(Type.INSTANCE, group, category, ingredient, result, experience, cookingTime);
    }

    @Override
    public @NotNull ItemStack getToastSymbol() {
        return new ItemStack(Items.SWEET_BERRIES);
    }

    @Override
    public RecipeSerializer<?> getSerializer() {
        return Serializer.INSTANCE;
    }

    public static class Type implements RecipeType<CookingPotRecipe> {
        public static final Type INSTANCE = new Type();
    }

    public static class Serializer {
        public static final RecipeSerializer<CookingPotRecipe> INSTANCE = new SimpleCookingSerializer<>(CookingPotRecipe::new, 50);
    }
}
