package com.sjkz1.lets_camping;

import com.sjkz1.lets_camping.item.crafting.LCRecipeType;
import com.sjkz1.lets_camping.registry.LCBlockEntityTypes;
import com.sjkz1.lets_camping.registry.LCBlocks;
import com.sjkz1.lets_camping.registry.LCItems;
import com.sjkz1.lets_camping.registry.LCEntityTypes;
import net.fabricmc.api.ModInitializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class LetsCamping implements ModInitializer {
    public static final String MOD_ID = "lets_camping";

    public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

    @Override
    public void onInitialize() {
        LCItems.init();
        LCBlocks.init();
        LCBlockEntityTypes.init();
        LCRecipeType.init();
        LCEntityTypes.init();
        LOGGER.info("Hello Fabric world!");
    }
}