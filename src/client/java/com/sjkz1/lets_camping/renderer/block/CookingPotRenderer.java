package com.sjkz1.lets_camping.renderer.block;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import com.sjkz1.lets_camping.block.CookingPotBlock;
import com.sjkz1.lets_camping.block.entity.CookingPotBlockEntity;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.client.renderer.entity.ItemRenderer;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.core.Direction;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import org.jetbrains.annotations.NotNull;

public class CookingPotRenderer implements BlockEntityRenderer<CookingPotBlockEntity> {

    public CookingPotRenderer(BlockEntityRendererProvider.Context context) {

    }

    @Override
    public void render(@NotNull CookingPotBlockEntity blockEntity, float f, PoseStack poseStack, MultiBufferSource multiBufferSource, int i, int j) {
        Direction direction = (Direction) blockEntity.getBlockState().getValue(CookingPotBlock.FACING);
        ItemRenderer itemRenderer = Minecraft.getInstance().getItemRenderer();
        for (ItemStack itemStack : blockEntity.getItems()) {
            poseStack.pushPose();
            if (direction == Direction.NORTH) {
                poseStack.translate(0.5f, 1.0f, 0.4f);
            }
            if (direction == Direction.SOUTH) {
                poseStack.translate(0.5f, 1.0f, 0.6f);
            }
            if (direction == Direction.WEST) {
                poseStack.translate(0.4f, 1.0f, 0.5f);
            }
            if (direction == Direction.EAST) {
                poseStack.translate(0.6f, 1.0f, 0.5f);
            }
            poseStack.scale(0.35f, 0.35f, 0.35f);
            Direction direction2 = Direction.from2DDataValue((direction.get2DDataValue()));
            float g = -direction2.toYRot();
            poseStack.mulPose(Axis.YP.rotationDegrees(g));
            poseStack.mulPose(Axis.XP.rotationDegrees(90.0F));
            itemRenderer.renderStatic(new ItemStack(Items.SWEET_BERRIES), ItemDisplayContext.GUI, i, OverlayTexture.NO_OVERLAY, poseStack, multiBufferSource, blockEntity.getLevel(), 1);
            poseStack.popPose();
        }
    }
}
