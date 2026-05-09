package com.github.theredbrain.foodoverhaul;

import com.github.theredbrain.foodoverhaul.registry.FoodOverhaulEntities;
import com.github.theredbrain.foodoverhaul.render.block.entity.FoodDisplayBlockEntityRenderer;
import net.fabricmc.api.ClientModInitializer;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderers;

public class FoodOverhaulClient implements ClientModInitializer {
	@Override
	public void onInitializeClient() {
		registerBlockEntityRenderer();
	}

	private void registerBlockEntityRenderer() {
		BlockEntityRenderers.register(FoodOverhaulEntities.FOOD_DISPLAY_BLOCK_ENTITY, FoodDisplayBlockEntityRenderer::new);
	}
}