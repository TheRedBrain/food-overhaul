package com.github.theredbrain.foodoverhaul.render.block.entity;

import com.github.theredbrain.foodoverhaul.block.entity.FoodDisplayBlockEntity;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.block.entity.BlockEntityRenderer;
import net.minecraft.client.render.block.entity.BlockEntityRendererFactory;
import net.minecraft.client.render.item.ItemRenderer;
import net.minecraft.client.render.model.json.ModelTransformationMode;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.item.ItemStack;
import net.minecraft.util.collection.DefaultedList;
import net.minecraft.util.math.RotationAxis;

@Environment(EnvType.CLIENT)
public class FoodDisplayBlockEntityRenderer implements BlockEntityRenderer<FoodDisplayBlockEntity> {
	private final ItemRenderer itemRenderer;

	public FoodDisplayBlockEntityRenderer(BlockEntityRendererFactory.Context ctx) {
		this.itemRenderer = ctx.getItemRenderer();
	}

	public void render(FoodDisplayBlockEntity foodDisplayBlockEntity, float f, MatrixStack matrixStack, VertexConsumerProvider vertexConsumerProvider, int i, int j) {
		DefaultedList<ItemStack> defaultedList = foodDisplayBlockEntity.getDisplayedItems();
		int[] itemDisplayRotations = foodDisplayBlockEntity.getFoodDisplayBlockData().getRotations();
		int k = (int) foodDisplayBlockEntity.getPos().asLong();

		if (foodDisplayBlockEntity.getFoodDisplayBlockData().single_item_mode()) {
			ItemStack itemStack = (ItemStack) defaultedList.get(0);
			if (!itemStack.isEmpty()) {
				matrixStack.push();
				matrixStack.translate(0.5F, 0.25F, 0.5F);
				matrixStack.multiply(RotationAxis.POSITIVE_Y.rotationDegrees(itemDisplayRotations[0] * -22.5F));
				this.itemRenderer.renderItem(itemStack, ModelTransformationMode.FIXED, i, j, matrixStack, vertexConsumerProvider, foodDisplayBlockEntity.getWorld(), k);
				matrixStack.pop();
			}
		} else {
			for (int l = 0; l < defaultedList.size(); ++l) {
				ItemStack itemStack = (ItemStack) defaultedList.get(l);
				if (!itemStack.isEmpty()) {
					matrixStack.push();
					if (l == 0) {
						matrixStack.translate(0.25F, 0.25F, 0.75F);
					} else if (l == 1) {
						matrixStack.translate(0.25F, 0.25F, 0.25F);
					} else if (l == 2) {
						matrixStack.translate(0.75F, 0.25F, 0.25F);
					} else if (l == 3) {
						matrixStack.translate(0.75F, 0.25F, 0.75F);
					}
					matrixStack.multiply(RotationAxis.POSITIVE_Y.rotationDegrees(itemDisplayRotations[l] * -22.5F));
					this.itemRenderer.renderItem(itemStack, ModelTransformationMode.FIXED, i, j, matrixStack, vertexConsumerProvider, foodDisplayBlockEntity.getWorld(), k + l);
					matrixStack.pop();
				}
			}
		}
	}
}
