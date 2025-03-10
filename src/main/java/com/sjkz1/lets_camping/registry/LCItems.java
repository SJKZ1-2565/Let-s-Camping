package com.sjkz1.lets_camping.registry;

import com.sjkz1.lets_camping.LetsCamping;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;

public class LCItems {

    public static final Item BERRY_JAM = new Item(new Item.Properties().stacksTo(1));
    public static final Item JAM_BOTTLE = new Item(new Item.Properties().stacksTo(1));
    public static final TagKey<Item> JAMS = TagKey.create(Registries.ITEM, ResourceLocation.tryBuild(LetsCamping.MOD_ID, "jams"));


    public static void init() {
        register(BERRY_JAM, "berry_jam");
        register(JAM_BOTTLE, "jam_bottle");
    }

    private static void register(Item item, String id) {
        Registry.register(BuiltInRegistries.ITEM, ResourceLocation.tryBuild(LetsCamping.MOD_ID, id), item);
    }
}
