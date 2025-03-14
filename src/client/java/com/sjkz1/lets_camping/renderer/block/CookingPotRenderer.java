package com.sjkz1.lets_camping.renderer.block;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import com.sjkz1.lets_camping.block.CookingPotBlock;
import com.sjkz1.lets_camping.block.entity.CookingPotBlockEntity;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.client.renderer.entity.ItemRenderer;
import net.minecraft.core.Direction;
import net.minecraft.core.NonNullList;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.state.properties.Property;

public class CookingPotRenderer implements BlockEntityRenderer<CookingPotBlockEntity> {
    private final ItemRenderer itemRenderer;

    public CookingPotRenderer(BlockEntityRendererProvider.Context context) {
        this.itemRenderer = context.getItemRenderer();
    }

    @Override
    public void render(CookingPotBlockEntity blockEntity, float f, PoseStack poseStack, MultiBufferSource multiBufferSource, int i, int j) {
        Direction direction = (Direction) blockEntity.getBlockState().getValue((Property) CookingPotBlock.FACING);
        NonNullList nonNullList = blockEntity.getItems();
        int k = (int) blockEntity.getBlockPos().asLong();
        for (int l = 0; l < nonNullList.size(); ++l) {
            poseStack.pushPose();
            ItemStack itemStack = (ItemStack) nonNullList.get(l);
            float y = l * 0.0025F;
            if (direction == Direction.NORTH) {
                poseStack.translate(0.5f, 1.0F + y, 0.4f);
            }
            if (direction == Direction.SOUTH) {
                poseStack.translate(0.5f, 1.0F + y, 0.6f);
            }
            if (direction == Direction.WEST) {
                poseStack.translate(0.4f, 1.0F + y, 0.5f);
            }
            if (direction == Direction.EAST) {
                poseStack.translate(0.6f, 1.0F + y, 0.5f);
            }
            poseStack.mulPose(Axis.YP.rotation(l + 3F % 3));
            poseStack.mulPose(Axis.XP.rotationDegrees(90.0f));
            poseStack.scale(0.375f, 0.375f, 0.375f);
            this.itemRenderer.renderStatic(itemStack, ItemDisplayContext.FIXED, i, j, poseStack, multiBufferSource, blockEntity.getLevel(), k + l);
            poseStack.popPose();
        }
    }
}