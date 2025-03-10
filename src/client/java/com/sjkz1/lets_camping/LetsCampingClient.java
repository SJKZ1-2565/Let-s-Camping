package com.sjkz1.lets_camping;

import com.sjkz1.lets_camping.registry.LCBlocks;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.blockrenderlayer.v1.BlockRenderLayerMap;
import net.minecraft.client.renderer.RenderType;

public class LetsCampingClient implements ClientModInitializer {
    @Override
    public void onInitializeClient() {
        BlockRenderLayerMap.INSTANCE.putBlock(LCBlocks.COOKING_POT, RenderType.cutout());
    }
}