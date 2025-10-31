package com.github.theredbrain.foodoverhaul.registry;

import com.github.theredbrain.foodoverhaul.FoodOverhaul;
import com.github.theredbrain.foodoverhaul.block.FoodDisplayBlock;
import com.github.theredbrain.foodoverhaul.block.GenericFoodBlock;
import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;
import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemGroups;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.registry.RegistryKey;

public class BlockRegistry {

	public static final Block GENERIC_FOOD_BLOCK = registerBlock("generic_food_block", new GenericFoodBlock(AbstractBlock.Settings.create()), null);

	public static final Block FOOD_DISPLAY_BLOCK = registerBlock("food_display_block", new FoodDisplayBlock(AbstractBlock.Settings.create().strength(0.3F).nonOpaque().allowsSpawning(Blocks::never).solidBlock(Blocks::never).suffocates(Blocks::never).blockVision(Blocks::never)), ItemGroups.FUNCTIONAL);

	private static Block registerBlock(String name, Block block, RegistryKey<ItemGroup> itemGroup) {
		Registry.register(Registries.ITEM, FoodOverhaul.identifier(name), new BlockItem(block, new Item.Settings()));
		ItemGroupEvents.modifyEntriesEvent(itemGroup).register(content -> content.add(block));
		return Registry.register(Registries.BLOCK, FoodOverhaul.identifier(name), block);
	}

	public static void init() {
	}
}
