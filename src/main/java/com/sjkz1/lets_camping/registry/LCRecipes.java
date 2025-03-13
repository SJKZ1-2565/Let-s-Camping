package com.sjkz1.lets_camping.registry;

import com.sjkz1.lets_camping.LetsCamping;
import com.sjkz1.lets_camping.item.crafting.CookingPotRecipe;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;

public class LCRecipes {

    public static void init() {
        Registry.register(BuiltInRegistries.RECIPE_SERIALIZER, ResourceLocation.tryBuild(LetsCamping.MOD_ID, "cooking_pot_recipe"),
                CookingPotRecipe.Serializer.INSTANCE);
        Registry.register(BuiltInRegistries.RECIPE_TYPE, ResourceLocation.tryBuild(LetsCamping.MOD_ID, "cooking_pot_cooking"),
                CookingPotRecipe.Type.INSTANCE);
    }


}
