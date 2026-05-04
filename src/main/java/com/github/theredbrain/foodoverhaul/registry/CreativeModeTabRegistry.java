package com.github.theredbrain.foodoverhaul.registry;

import com.github.theredbrain.foodoverhaul.FoodOverhaul;
import net.fabricmc.fabric.api.creativetab.v1.FabricCreativeModeTab;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;

public class CreativeModeTabRegistry {

	public static final ResourceKey<CreativeModeTab> FOOD_OVERHAUL_KEY = ResourceKey.create(Registries.CREATIVE_MODE_TAB, FoodOverhaul.identifier("food_overhaul"));
	public static CreativeModeTab FOOD_OVERHAUL;

	public static void init() {
		FOOD_OVERHAUL = FabricCreativeModeTab.builder()
				.icon(() -> new ItemStack(Items.APPLE))
				.title(Component.translatable("itemGroup.foodoverhaul.food_overhaul"))
				.build();

		Registry.register(
				BuiltInRegistries.CREATIVE_MODE_TAB,
				FOOD_OVERHAUL_KEY,
				FOOD_OVERHAUL
		);
	}
}
