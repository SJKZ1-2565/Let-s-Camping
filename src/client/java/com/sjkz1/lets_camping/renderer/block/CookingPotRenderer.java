package com.sjkz1.lets_camping.renderer.block;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import com.sjkz1.lets_camping.LetsCamping;
import com.sjkz1.lets_camping.block.entity.CookingPotBlockEntity;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.client.renderer.entity.ItemRenderer;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import org.jetbrains.annotations.NotNull;

public class CookingPotRenderer implements BlockEntityRenderer<CookingPotBlockEntity> {

    public CookingPotRenderer(BlockEntityRendererProvider.Context context) {

    }

    @Override
    public void render(@NotNull CookingPotBlockEntity blockEntity, float f, PoseStack poseStack, MultiBufferSource multiBufferSource, int i, int j) {
        ItemRenderer itemRenderer = Minecraft.getInstance().getItemRenderer();
        for (ItemStack itemStack : blockEntity.getItems()) {
            LetsCamping.LOGGER.info(itemStack.getDisplayName().getString());
            poseStack.pushPose();
            poseStack.translate(0.5f, 1.0f, 0.6f);
            poseStack.scale(0.45f, 0.45f, 0.45f);
            poseStack.mulPose(Axis.XP.rotationDegrees(270F));
            itemRenderer.renderStatic(new ItemStack(Items.SWEET_BERRIES), ItemDisplayContext.GUI, i, OverlayTexture.NO_OVERLAY, poseStack, multiBufferSource, blockEntity.getLevel(), 1);
            poseStack.popPose();
        }
    }
}
