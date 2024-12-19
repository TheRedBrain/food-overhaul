package com.github.theredbrain.foodoverhaul.registry;

import com.github.theredbrain.foodoverhaul.FoodOverhaul;
import com.github.theredbrain.foodoverhaul.component.type.OverhauledFoodComponent;
import net.minecraft.component.ComponentType;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;

public class ItemComponentRegistry {
	static {
		FoodOverhaul.OVERHAULED_FOOD_COMPONENT_TYPE = Registry.register(
				Registries.DATA_COMPONENT_TYPE,
				FoodOverhaul.identifier("overhauled_food"),
				ComponentType.<OverhauledFoodComponent>builder().codec(OverhauledFoodComponent.CODEC).packetCodec(OverhauledFoodComponent.PACKET_CODEC).build()
		);
	}

	public static void init() {
	}
}
