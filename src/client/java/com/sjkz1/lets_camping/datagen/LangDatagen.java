package com.sjkz1.lets_camping.datagen;

import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricLanguageProvider;
import net.minecraft.core.HolderLookup;

import java.util.concurrent.CompletableFuture;

public class LangDatagen extends FabricLanguageProvider {
    protected LangDatagen(FabricDataOutput dataOutput, CompletableFuture<HolderLookup.Provider> registryLookup) {
        super(dataOutput, registryLookup);
    }

    @Override
    public void generateTranslations(HolderLookup.Provider provider, TranslationBuilder translationBuilder) {
        translationBuilder.add("item.lets_camping.jam_bottle","Jam Bottle");
        translationBuilder.add("item.lets_camping.berry_jam","Berry Jam");
        translationBuilder.add("block.lets_camping.cooking_pot","Cooking Pot");

    }

    static class ThaiLangDataGen extends FabricLanguageProvider {
        protected ThaiLangDataGen(FabricDataOutput dataOutput, CompletableFuture<HolderLookup.Provider> registryLookup) {
            super(dataOutput,"th_th",registryLookup);
        }

        @Override
        public void generateTranslations(HolderLookup.Provider provider, TranslationBuilder translationBuilder) {
            translationBuilder.add("item.lets_camping.jam_bottle","ขวดแยม");
            translationBuilder.add("item.lets_camping.berry_jam","แยมเบอร์รี");
            translationBuilder.add("block.lets_camping.cooking_pot","Cooking Pot");
        }
    }
}
