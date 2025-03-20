package com.sjkz1.lets_camping.registry;

import com.sjkz1.lets_camping.LetsCamping;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;

public class LCItems {

    public static void init() {

    }

    private static void register(Item item, String id) {
        Registry.register(BuiltInRegistries.ITEM, ResourceLocation.tryBuild(LetsCamping.MOD_ID, id), item);
    }
}
