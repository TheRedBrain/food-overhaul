package com.github.theredbrain.foodoverhaul.registry;

import com.github.theredbrain.foodoverhaul.FoodOverhaul;
import com.github.theredbrain.foodoverhaul.component.type.FoodBlockDataComponent;
import com.github.theredbrain.foodoverhaul.component.type.FoodDisplayBlockDataComponent;
import net.minecraft.core.Registry;
import net.minecraft.core.component.DataComponentType;
import net.minecraft.core.registries.BuiltInRegistries;

public class DataComponentRegistry {

	static {
		FoodOverhaul.FOOD_BLOCK_DATA = Registry.register(
				BuiltInRegistries.DATA_COMPONENT_TYPE,
				FoodOverhaul.identifier("food_block_data"),
				DataComponentType.<FoodBlockDataComponent>builder().persistent(FoodBlockDataComponent.CODEC).networkSynchronized(FoodBlockDataComponent.PACKET_CODEC).cacheEncoding().build()
		);
		FoodOverhaul.FOOD_DISPLAY_BLOCK_DATA = Registry.register(
				BuiltInRegistries.DATA_COMPONENT_TYPE,
				FoodOverhaul.identifier("food_display_block_data"),
				DataComponentType.<FoodDisplayBlockDataComponent>builder().persistent(FoodDisplayBlockDataComponent.CODEC).networkSynchronized(FoodDisplayBlockDataComponent.PACKET_CODEC).cacheEncoding().build()
		);
	}

	public static void init() {
	}
}
