package com.github.theredbrain.foodoverhaul.registry;

import com.github.theredbrain.foodoverhaul.FoodOverhaul;
import com.github.theredbrain.foodoverhaul.block.GenericFoodBlock;
import net.minecraft.block.Block;
import net.minecraft.block.MapColor;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.registry.RegistryKey;

public class BlockRegistry {

	public static final Block GENERIC_FOOD_BLOCK = FoodOverhaul.registerFoodBlock(
			FoodOverhaul.identifier("generic_food_block"),
			new GenericFoodBlock(Block.Settings.create().mapColor(MapColor.OAK_TAN).strength(10.0F, 3600000.0f).nonOpaque()),
			new Item.Settings(),
			null
	);

	public static void init() {
	}
}
