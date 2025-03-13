package com.sjkz1.lets_camping.datagen;

import com.sjkz1.lets_camping.item.crafting.CookingPotRecipe;
import com.sjkz1.lets_camping.item.crafting.CookingPotRecipeBuilder;
import com.sjkz1.lets_camping.registry.LCItems;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricRecipeProvider;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.recipes.RecipeCategory;
import net.minecraft.data.recipes.RecipeOutput;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.AbstractCookingRecipe;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.level.ItemLike;

import java.util.concurrent.CompletableFuture;

public class RecipeDatagen extends FabricRecipeProvider {
    public RecipeDatagen(FabricDataOutput output, CompletableFuture<HolderLookup.Provider> registriesFuture) {
        super(output, registriesFuture);
    }

    public static <T extends AbstractCookingRecipe> void cookPotRecipes(
            RecipeOutput recipeOutput, String string, RecipeSerializer<T> recipeSerializer, AbstractCookingRecipe.Factory<T> factory, int i
    ) {
        simpleCookingPotRecipe(recipeOutput, string, recipeSerializer, factory, i, Items.SWEET_BERRIES, LCItems.BERRY_JAM, 0.35F);
    }

    public static <T extends AbstractCookingRecipe> void simpleCookingPotRecipe(
            RecipeOutput recipeOutput,
            String string,
            RecipeSerializer<T> recipeSerializer,
            AbstractCookingRecipe.Factory<T> factory,
            int i,
            ItemLike itemLike,
            ItemLike itemLike2,
            float f
    ) {
        CookingPotRecipeBuilder.generic(Ingredient.of(itemLike), RecipeCategory.FOOD, itemLike2, f, i, recipeSerializer, factory)
                .unlockedBy(getHasName(itemLike), has(itemLike))
                .save(recipeOutput, getItemName(itemLike2) + "_from_" + string);
    }

    @Override
    public void buildRecipes(RecipeOutput recipeOutput) {
        cookPotRecipes(recipeOutput, "cooking_pot_cooking", CookingPotRecipe.Serializer.INSTANCE, CookingPotRecipe::new, 600);
    }
}
