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

public class CookingPotRenderer implements BlockEntityRenderer<CookingPotBlockEntity> {

    public CookingPotRenderer(BlockEntityRendererProvider.Context context) {

    }

//    @Override
//    public void render(GemEmpoweringStationBlockEntity entity, float tickDelta, MatrixStack matrices,
//                       VertexConsumerProvider vertexConsumers, int light, int overlay) {
//        ItemRenderer itemRenderer = MinecraftClient.getInstance().getItemRenderer();
//        ItemStack itemStack = entity.getRenderStack();
//        matrices.push();
//        matrices.translate(0.5f, 0.75f, 0.5f);
//        matrices.scale(0.35f, 0.35f, 0.35f);
//        matrices.multiply(RotationAxis.NEGATIVE_Y.rotationDegrees(entity.getCachedState().get(GemEmpoweringStationBlock.FACING).asRotation()));
//        matrices.multiply(RotationAxis.POSITIVE_X.rotationDegrees(270));
//
//        itemRenderer.renderItem(itemStack, ModelTransformationMode.GUI, getLightLevel(entity.getWorld(),
//                entity.getPos()), OverlayTexture.DEFAULT_UV, matrices, vertexConsumers, entity.getWorld(), 1);
//        matrices.pop();
//    }
//
//    private int getLightLevel(World world, BlockPos pos) {
//        int bLight = world.getLightLevel(LightType.BLOCK, pos);
//        int sLight = world.getLightLevel(LightType.SKY, pos);
//        return LightmapTextureManager.pack(bLight, sLight);
//    }

    @Override
    public void render(CookingPotBlockEntity blockEntity, float f, PoseStack poseStack, MultiBufferSource multiBufferSource, int i, int j) {
        ItemRenderer itemRenderer = Minecraft.getInstance().getItemRenderer();
        for (ItemStack itemStack : blockEntity.getItems()) {
            LetsCamping.LOGGER.info(itemStack.getDisplayName().getString());
            poseStack.pushPose();
            poseStack.translate(0.5f, 1.0f, 0.6f);
            poseStack.scale(0.45f, 0.45f, 0.45f);
//            poseStack.mulPose(Axis.YN.rotationDegrees(blockEntity.getBlockState().get.get(GemEmpoweringStationBlock.FACING).asRotation()));
            poseStack.mulPose(Axis.XP.rotationDegrees(270F));
            itemRenderer.renderStatic(new ItemStack(Items.SWEET_BERRIES), ItemDisplayContext.GUI, i, OverlayTexture.NO_OVERLAY, poseStack, multiBufferSource, blockEntity.getLevel(), 1);
            poseStack.popPose();
        }
    }
}
