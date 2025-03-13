package com.sjkz1.lets_camping;

import com.sjkz1.lets_camping.registry.LCBlockEntityTypes;
import com.sjkz1.lets_camping.registry.LCBlocks;
import com.sjkz1.lets_camping.registry.LCEntityTypes;
import com.sjkz1.lets_camping.renderer.block.CookingPotRenderer;
import com.sjkz1.lets_camping.renderer.entity.MatEntityRenderer;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.blockrenderlayer.v1.BlockRenderLayerMap;
import net.fabricmc.fabric.api.client.rendering.v1.EntityRendererRegistry;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderers;

public class LetsCampingClient implements ClientModInitializer {
    @Override
    public void onInitializeClient() {
        BlockRenderLayerMap.INSTANCE.putBlock(LCBlocks.COOKING_POT, RenderType.cutout());
        EntityRendererRegistry.register(LCEntityTypes.MAT, MatEntityRenderer::new);
        BlockEntityRenderers.register(LCBlockEntityTypes.COOKING_POT, CookingPotRenderer::new);
    }
}