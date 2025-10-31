package com.github.theredbrain.foodoverhaul.render.block.entity;

import com.github.theredbrain.foodoverhaul.block.entity.FoodDisplayBlockEntity;
import com.github.theredbrain.foodoverhaul.render.block.entity.state.FoodDisplayBlockEntityRendererState;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.item.ItemModelManager;
import net.minecraft.client.render.OverlayTexture;
import net.minecraft.client.render.block.entity.BlockEntityRenderer;
import net.minecraft.client.render.block.entity.BlockEntityRendererFactory;
import net.minecraft.client.render.command.ModelCommandRenderer;
import net.minecraft.client.render.command.OrderedRenderCommandQueue;
import net.minecraft.client.render.item.ItemRenderState;
import net.minecraft.client.render.state.CameraRenderState;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.item.ItemDisplayContext;
import net.minecraft.item.ItemStack;
import net.minecraft.util.HeldItemContext;
import net.minecraft.util.math.RotationAxis;
import net.minecraft.util.math.Vec3d;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;

@Environment(EnvType.CLIENT)
public class FoodDisplayBlockEntityRenderer implements BlockEntityRenderer<FoodDisplayBlockEntity, FoodDisplayBlockEntityRendererState> {
	private final ItemModelManager itemModelManager;

	public FoodDisplayBlockEntityRenderer(BlockEntityRendererFactory.Context ctx) {
		this.itemModelManager = ctx.itemModelManager();
	}

	public FoodDisplayBlockEntityRendererState createRenderState() {
		return new FoodDisplayBlockEntityRendererState();
	}

	public void updateRenderState(FoodDisplayBlockEntity foodDisplayBlockEntity, FoodDisplayBlockEntityRendererState foodDisplayBlockEntityRendererState, float f, Vec3d vec3d, @Nullable ModelCommandRenderer.CrumblingOverlayCommand crumblingOverlayCommand) {
		BlockEntityRenderer.super.updateRenderState(foodDisplayBlockEntity, foodDisplayBlockEntityRendererState, f, vec3d, crumblingOverlayCommand);
		int i = (int) foodDisplayBlockEntity.getPos().asLong();
		foodDisplayBlockEntityRendererState.displayedItemStates = new ArrayList<>();

		for (int j = 0; j < foodDisplayBlockEntity.getDisplayedItems().size(); ++j) {
			ItemRenderState itemRenderState = new ItemRenderState();
			this.itemModelManager.clearAndUpdate(itemRenderState, (ItemStack) foodDisplayBlockEntity.getDisplayedItems().get(j), ItemDisplayContext.FIXED, foodDisplayBlockEntity.getWorld(), (HeldItemContext) null, i + j);
			foodDisplayBlockEntityRendererState.displayedItemStates.add(itemRenderState);
		}

		foodDisplayBlockEntityRendererState.singleItemMode = foodDisplayBlockEntity.getFoodDisplayBlockData().single_item_mode();

		foodDisplayBlockEntityRendererState.displayedItemRotations = foodDisplayBlockEntity.getFoodDisplayBlockData().getRotations();
	}

	public void render(FoodDisplayBlockEntityRendererState foodDisplayBlockEntityRenderState, MatrixStack matrixStack, OrderedRenderCommandQueue orderedRenderCommandQueue, CameraRenderState cameraRenderState) {
		List<ItemRenderState> list = foodDisplayBlockEntityRenderState.displayedItemStates;
		int[] itemDisplayRotations = foodDisplayBlockEntityRenderState.displayedItemRotations;

		if (foodDisplayBlockEntityRenderState.singleItemMode) {
			ItemRenderState itemRenderState = (ItemRenderState) list.get(0);
			if (!itemRenderState.isEmpty()) {
				matrixStack.push();
				matrixStack.translate(0.5F, 0.25F, 0.5F);
				matrixStack.multiply(RotationAxis.POSITIVE_Y.rotationDegrees(itemDisplayRotations[0] * -22.5F));
				itemRenderState.render(matrixStack, orderedRenderCommandQueue, foodDisplayBlockEntityRenderState.lightmapCoordinates, OverlayTexture.DEFAULT_UV, 0);
				matrixStack.pop();
			}
		} else {
			for (int i = 0; i < list.size(); ++i) {
				ItemRenderState itemRenderState = (ItemRenderState) list.get(i);
				if (!itemRenderState.isEmpty()) {
					matrixStack.push();
					if (i == 0) {
						matrixStack.translate(0.25F, 0.25F, 0.75F);
					} else if (i == 1) {
						matrixStack.translate(0.25F, 0.25F, 0.25F);
					} else if (i == 2) {
						matrixStack.translate(0.75F, 0.25F, 0.25F);
					} else if (i == 3) {
						matrixStack.translate(0.75F, 0.25F, 0.75F);
					}
					matrixStack.multiply(RotationAxis.POSITIVE_Y.rotationDegrees(itemDisplayRotations[i] * -22.5F));
					itemRenderState.render(matrixStack, orderedRenderCommandQueue, foodDisplayBlockEntityRenderState.lightmapCoordinates, OverlayTexture.DEFAULT_UV, 0);
					matrixStack.pop();
				}
			}
		}
	}
}
