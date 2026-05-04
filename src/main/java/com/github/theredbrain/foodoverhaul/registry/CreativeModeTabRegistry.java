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

	public static CreativeModeTab CREATIVE_MODE_TAB;

	public static void init() {
		CREATIVE_MODE_TAB = FabricCreativeModeTab.builder()
				.icon(() -> new ItemStack(Items.APPLE))
				.title(Component.translatable("itemGroup.foodoverhaul.creative_mode_tab"))
				.build();

		Registry.register(
				BuiltInRegistries.CREATIVE_MODE_TAB,
				FoodOverhaul.CREATIVE_MODE_TAB_KEY,
				CREATIVE_MODE_TAB
		);
	}
}
