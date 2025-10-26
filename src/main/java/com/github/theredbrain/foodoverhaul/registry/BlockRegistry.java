package com.github.theredbrain.foodoverhaul.registry;

import com.github.theredbrain.foodoverhaul.FoodOverhaul;
import com.github.theredbrain.foodoverhaul.block.GenericFoodBlock;
import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;
import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;

import java.util.List;

public class BlockRegistry {

	public static RegistryKey<Block> GENERIC_FOOD_BLOCK_BLOCK_KEY = RegistryKey.of(RegistryKeys.BLOCK, FoodOverhaul.identifier("generic_food_block"));
	public static RegistryKey<Item> GENERIC_FOOD_BLOCK_ITEM_KEY = RegistryKey.of(RegistryKeys.ITEM, FoodOverhaul.identifier("generic_food_block"));
	public static final Block GENERIC_FOOD_BLOCK = registerBlock(GENERIC_FOOD_BLOCK_BLOCK_KEY, GENERIC_FOOD_BLOCK_ITEM_KEY, new GenericFoodBlock(AbstractBlock.Settings.create().registryKey(GENERIC_FOOD_BLOCK_BLOCK_KEY)), List.of());

	private static Block registerBlock(RegistryKey<Block> block_key, RegistryKey<Item> item_key, Block block, List<RegistryKey<ItemGroup>> itemGroupList) {
		Registry.register(Registries.ITEM, item_key, new BlockItem(block, new Item.Settings().registryKey(item_key)));
		for (RegistryKey<ItemGroup> itemGroup : itemGroupList) {
			ItemGroupEvents.modifyEntriesEvent(itemGroup).register(content -> content.add(block));
		}
		return Registry.register(Registries.BLOCK, block_key, block);
	}

	public static void init() {
	}
}
