package com.github.theredbrain.foodoverhaul.registry;

import com.github.theredbrain.foodoverhaul.FoodOverhaul;
import com.github.theredbrain.foodoverhaul.block.FoodDisplayBlock;
import com.github.theredbrain.foodoverhaul.block.GenericFoodBlock;
import com.github.theredbrain.foodoverhaul.block.entity.FoodBlockEntity;
import com.github.theredbrain.foodoverhaul.component.type.FoodBlockDataComponent;
import net.fabricmc.fabric.api.creativetab.v1.CreativeModeTabEvents;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockBehaviour;

import java.util.List;

public class BlockRegistry {

	public static ResourceKey<Block> GENERIC_FOOD_BLOCK_BLOCK_KEY = ResourceKey.create(Registries.BLOCK, FoodOverhaul.identifier("generic_food_block"));
	public static ResourceKey<Item> GENERIC_FOOD_BLOCK_ITEM_KEY = ResourceKey.create(Registries.ITEM, FoodOverhaul.identifier("generic_food_block"));
	public static FoodBlockEntity.FoodBlockData GENERIC_FOOD_BLOCK_DATA = new FoodBlockEntity.FoodBlockData(
			"minecraft:glowing",
			600,
			0,
			false,
			false,
			true,
			"minecraft:cookie",
			"minecraft:iron_sword",
			"",
			"",
			0,
			false
	);
	public static final Block GENERIC_FOOD_BLOCK = registerBlockWithFoodBlockData(GENERIC_FOOD_BLOCK_DATA, GENERIC_FOOD_BLOCK_BLOCK_KEY, GENERIC_FOOD_BLOCK_ITEM_KEY, new GenericFoodBlock(BlockBehaviour.Properties.of().setId(GENERIC_FOOD_BLOCK_BLOCK_KEY)), List.of());

	public static ResourceKey<Block> FOOD_DISPLAY_BLOCK_BLOCK_KEY = ResourceKey.create(Registries.BLOCK, FoodOverhaul.identifier("food_display_block"));
	public static ResourceKey<Item> FOOD_DISPLAY_BLOCK_ITEM_KEY = ResourceKey.create(Registries.ITEM, FoodOverhaul.identifier("food_display_block"));
	public static final Block FOOD_DISPLAY_BLOCK = registerBlock(FOOD_DISPLAY_BLOCK_BLOCK_KEY, FOOD_DISPLAY_BLOCK_ITEM_KEY, new FoodDisplayBlock(BlockBehaviour.Properties.of().setId(FOOD_DISPLAY_BLOCK_BLOCK_KEY).strength(0.3F).noOcclusion().isValidSpawn(Blocks::never).isRedstoneConductor(Blocks::never).isSuffocating(Blocks::never).isViewBlocking(Blocks::never)), List.of());

	private static Block registerBlockWithFoodBlockData(FoodBlockEntity.FoodBlockData foodBlockData, ResourceKey<Block> block_key, ResourceKey<Item> item_key, Block block, List<ResourceKey<CreativeModeTab>> itemGroupList) {
		Registry.register(BuiltInRegistries.ITEM, item_key, new BlockItem(block, new Item.Properties().setId(item_key).component(FoodOverhaul.FOOD_BLOCK_DATA, new FoodBlockDataComponent(foodBlockData)).stacksTo(1)));
		for (ResourceKey<CreativeModeTab> itemGroup : itemGroupList) {
			CreativeModeTabEvents.modifyOutputEvent(itemGroup).register(content -> content.accept(block));
		}
		return Registry.register(BuiltInRegistries.BLOCK, block_key, block);
	}

	private static Block registerBlock(ResourceKey<Block> block_key, ResourceKey<Item> item_key, Block block, List<ResourceKey<CreativeModeTab>> itemGroupList) {
		Registry.register(BuiltInRegistries.ITEM, item_key, new BlockItem(block, new Item.Properties().setId(item_key)));
		for (ResourceKey<CreativeModeTab> itemGroup : itemGroupList) {
			CreativeModeTabEvents.modifyOutputEvent(itemGroup).register(content -> content.accept(block));
		}
		return Registry.register(BuiltInRegistries.BLOCK, block_key, block);
	}

	public static void init() {
	}
}
