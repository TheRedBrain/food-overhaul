package com.github.theredbrain.foodoverhaul.registry;

import com.github.theredbrain.foodoverhaul.FoodOverhaul;
import com.github.theredbrain.foodoverhaul.component.type.FoodBlockDataComponent;
import net.minecraft.component.ComponentType;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;

public class DataComponentRegistry {

	public static void init() {

		FoodOverhaul.FOOD_BLOCK_DATA = Registry.register(
				Registries.DATA_COMPONENT_TYPE,
				FoodOverhaul.identifier("food_block_data"),
				ComponentType.<FoodBlockDataComponent>builder().codec(FoodBlockDataComponent.CODEC).packetCodec(FoodBlockDataComponent.PACKET_CODEC).cache().build()
		);

	}
}
