package com.sjkz1.lets_camping.renderer.entity;

import com.sjkz1.lets_camping.entity.MatEntity;
import net.minecraft.client.renderer.culling.Frustum;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.resources.ResourceLocation;

public class MatEntityRenderer extends EntityRenderer<MatEntity> {
    public MatEntityRenderer(EntityRendererProvider.Context context) {
        super(context);
    }

    @Override
    public ResourceLocation getTextureLocation(MatEntity entity) {
        return null;
    }

    @Override
    public boolean shouldRender(MatEntity entity, Frustum frustum, double d, double e, double f) {
        return true;
    }
}
