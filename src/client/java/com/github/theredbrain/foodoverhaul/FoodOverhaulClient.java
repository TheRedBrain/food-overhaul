package com.github.theredbrain.foodoverhaul;

import com.github.theredbrain.foodoverhaul.registry.EntityRegistry;
import com.github.theredbrain.foodoverhaul.render.block.entity.FoodDisplayBlockEntityRenderer;
import net.fabricmc.api.ClientModInitializer;
import net.minecraft.client.render.block.entity.BlockEntityRendererFactories;

public class FoodOverhaulClient implements ClientModInitializer {
	@Override
	public void onInitializeClient() {
		registerBlockEntityRenderer();
	}

	private void registerBlockEntityRenderer() {
		BlockEntityRendererFactories.register(EntityRegistry.FOOD_DISPLAY_BLOCK_ENTITY, FoodDisplayBlockEntityRenderer::new);
	}
}