package com.sjkz1.lets_camping.item.crafting;

import net.minecraft.advancements.Advancement;
import net.minecraft.advancements.AdvancementRequirements;
import net.minecraft.advancements.AdvancementRewards;
import net.minecraft.advancements.Criterion;
import net.minecraft.advancements.critereon.RecipeUnlockedTrigger;
import net.minecraft.core.component.DataComponents;
import net.minecraft.data.recipes.RecipeBuilder;
import net.minecraft.data.recipes.RecipeCategory;
import net.minecraft.data.recipes.RecipeOutput;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.AbstractCookingRecipe;
import net.minecraft.world.item.crafting.CookingBookCategory;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.level.ItemLike;
import org.jetbrains.annotations.Nullable;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Objects;

public class CookingPotRecipeBuilder implements RecipeBuilder {
    private final RecipeCategory category;
    private final CookingBookCategory bookCategory;
    private final Item result;
    private final Ingredient ingredient;
    private final float experience;
    private final int cookingTime;
    private final Map<String, Criterion<?>> criteria = new LinkedHashMap();
    private final AbstractCookingRecipe.Factory<?> factory;
    @Nullable
    private String group;

    private CookingPotRecipeBuilder(
            RecipeCategory recipeCategory,
            CookingBookCategory cookingBookCategory,
            ItemLike itemLike,
            Ingredient ingredient,
            float f,
            int i,
            AbstractCookingRecipe.Factory<?> factory
    ) {
        this.category = recipeCategory;
        this.bookCategory = cookingBookCategory;
        this.result = itemLike.asItem();
        this.ingredient = ingredient;
        this.experience = f;
        this.cookingTime = i;
        this.factory = factory;
    }

    public static <T extends AbstractCookingRecipe> CookingPotRecipeBuilder generic(
            Ingredient ingredient,
            RecipeCategory recipeCategory,
            ItemLike itemLike,
            float f,
            int i,
            RecipeSerializer<T> recipeSerializer,
            AbstractCookingRecipe.Factory<T> factory
    ) {
        return new CookingPotRecipeBuilder(recipeCategory, determineRecipeCategory(recipeSerializer, itemLike), itemLike, ingredient, f, i, factory);
    }

    private static CookingBookCategory determineSmeltingRecipeCategory(ItemLike itemLike) {
        if (itemLike.asItem().components().has(DataComponents.FOOD)) {
            return CookingBookCategory.FOOD;
        } else {
            return itemLike.asItem() instanceof BlockItem ? CookingBookCategory.BLOCKS : CookingBookCategory.MISC;
        }
    }

    private static CookingBookCategory determineBlastingRecipeCategory(ItemLike itemLike) {
        return itemLike.asItem() instanceof BlockItem ? CookingBookCategory.BLOCKS : CookingBookCategory.MISC;
    }

    private static CookingBookCategory determineRecipeCategory(RecipeSerializer<? extends AbstractCookingRecipe> recipeSerializer, ItemLike itemLike) {
//        if (recipeSerializer == RecipeSerializer.SMELTING_RECIPE) {
//            return determineSmeltingRecipeCategory(itemLike);
//        } else if (recipeSerializer == RecipeSerializer.BLASTING_RECIPE) {
//            return determineBlastingRecipeCategory(itemLike);
//        } else
        if (recipeSerializer != CookingPotRecipe.Serializer.INSTANCE) {
            throw new IllegalStateException("Unknown cooking recipe type");
        } else {
            return CookingBookCategory.FOOD;
        }
    }

    public CookingPotRecipeBuilder unlockedBy(String string, Criterion<?> criterion) {
        this.criteria.put(string, criterion);
        return this;
    }

    public CookingPotRecipeBuilder group(@Nullable String string) {
        this.group = string;
        return this;
    }

    @Override
    public Item getResult() {
        return this.result;
    }

    @Override
    public void save(RecipeOutput recipeOutput, ResourceLocation resourceLocation) {
        this.ensureValid(resourceLocation);
        Advancement.Builder builder = recipeOutput.advancement()
                .addCriterion("has_the_recipe", RecipeUnlockedTrigger.unlocked(resourceLocation))
                .rewards(AdvancementRewards.Builder.recipe(resourceLocation))
                .requirements(AdvancementRequirements.Strategy.OR);
        this.criteria.forEach(builder::addCriterion);
        AbstractCookingRecipe abstractCookingRecipe = this.factory
                .create(
                        (String) Objects.requireNonNullElse(this.group, ""), this.bookCategory, this.ingredient, new ItemStack(this.result), this.experience, this.cookingTime
                );
        recipeOutput.accept(resourceLocation, abstractCookingRecipe, builder.build(resourceLocation.withPrefix("recipes/" + this.category.getFolderName() + "/")));
    }

    private void ensureValid(ResourceLocation resourceLocation) {
        if (this.criteria.isEmpty()) {
            throw new IllegalStateException("No way of obtaining recipe " + resourceLocation);
        }
    }
}
