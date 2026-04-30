package com.github.theredbrain.foodoverhaul.render.block.entity;

import com.github.theredbrain.foodoverhaul.block.entity.FoodDisplayBlockEntity;
import com.github.theredbrain.foodoverhaul.render.block.entity.state.FoodDisplayBlockEntityRendererState;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.renderer.SubmitNodeCollector;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.client.renderer.feature.ModelFeatureRenderer;
import net.minecraft.client.renderer.item.ItemModelResolver;
import net.minecraft.client.renderer.item.ItemStackRenderState;
import net.minecraft.client.renderer.state.level.CameraRenderState;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.world.entity.ItemOwner;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.phys.Vec3;
import org.jspecify.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;

@Environment(EnvType.CLIENT)
public class FoodDisplayBlockEntityRenderer implements BlockEntityRenderer<FoodDisplayBlockEntity, FoodDisplayBlockEntityRendererState> {
	private final ItemModelResolver itemModelManager;

	public FoodDisplayBlockEntityRenderer(BlockEntityRendererProvider.Context ctx) {
		this.itemModelManager = ctx.itemModelResolver();
	}

	public FoodDisplayBlockEntityRendererState createRenderState() {
		return new FoodDisplayBlockEntityRendererState();
	}

	@Override
	public void extractRenderState(final FoodDisplayBlockEntity blockEntity, final FoodDisplayBlockEntityRendererState state, final float partialTicks, final Vec3 cameraPosition, final ModelFeatureRenderer.@Nullable CrumblingOverlay breakProgress) {
		BlockEntityRenderer.super.extractRenderState(blockEntity, state, partialTicks, cameraPosition, breakProgress);
		int i = (int) blockEntity.getBlockPos().asLong();
		state.displayedItemStates = new ArrayList<>();

		for (int j = 0; j < blockEntity.getDisplayedItems().size(); ++j) {
			ItemStackRenderState itemRenderState = new ItemStackRenderState();
			this.itemModelManager.updateForTopItem(itemRenderState, (ItemStack) blockEntity.getDisplayedItems().get(j), ItemDisplayContext.FIXED, blockEntity.getLevel(), (ItemOwner) null, i + j);
			state.displayedItemStates.add(itemRenderState);
		}

		state.singleItemMode = blockEntity.getFoodDisplayBlockData().single_item_mode();

		state.displayedItemRotations = blockEntity.getFoodDisplayBlockData().getRotations();
	}

	@Override
	public void submit(final FoodDisplayBlockEntityRendererState state, final PoseStack poseStack, final SubmitNodeCollector submitNodeCollector, final CameraRenderState camera) {
		List<ItemStackRenderState> list = state.displayedItemStates;
		int[] itemDisplayRotations = state.displayedItemRotations;

		if (state.singleItemMode && !list.isEmpty()) {
			ItemStackRenderState itemRenderState = (ItemStackRenderState) list.getFirst();
			if (!itemRenderState.isEmpty()) {
				poseStack.pushPose();
				poseStack.translate(0.5F, 0.25F, 0.5F);
				poseStack.mulPose(Axis.YP.rotationDegrees(itemDisplayRotations[0] * -22.5F));
				itemRenderState.submit(poseStack, submitNodeCollector, state.lightCoords, OverlayTexture.NO_OVERLAY, 0);
				poseStack.popPose();
			}
		} else {
			for (int i = 0; i < list.size(); ++i) {
				ItemStackRenderState itemRenderState = (ItemStackRenderState) list.get(i);
				if (!itemRenderState.isEmpty()) {
					poseStack.pushPose();
					if (i == 0) {
						poseStack.translate(0.25F, 0.25F, 0.75F);
					} else if (i == 1) {
						poseStack.translate(0.25F, 0.25F, 0.25F);
					} else if (i == 2) {
						poseStack.translate(0.75F, 0.25F, 0.25F);
					} else if (i == 3) {
						poseStack.translate(0.75F, 0.25F, 0.75F);
					}
					poseStack.mulPose(Axis.YP.rotationDegrees(itemDisplayRotations[i] * -22.5F));
					itemRenderState.submit(poseStack, submitNodeCollector, state.lightCoords, OverlayTexture.NO_OVERLAY, 0);
					poseStack.popPose();
				}
			}
		}
	}
}
