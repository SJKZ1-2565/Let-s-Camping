package com.sjkz1.lets_camping.datagen;

import net.fabricmc.fabric.api.datagen.v1.DataGeneratorEntrypoint;
import net.fabricmc.fabric.api.datagen.v1.FabricDataGenerator;

public class LCDatagen implements DataGeneratorEntrypoint {
    @Override
    public void onInitializeDataGenerator(FabricDataGenerator fabricDataGenerator) {
        FabricDataGenerator.Pack pack = fabricDataGenerator.createPack();
        pack.addProvider(ItemTagDatagen::new);
        pack.addProvider(LangDatagen::new);
        pack.addProvider(LangDatagen.ThaiLangDataGen::new);
        pack.addProvider(ModelDatagen::new);
    }
}
