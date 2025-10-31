package com.github.theredbrain.foodoverhaul.registry;

import com.github.theredbrain.foodoverhaul.FoodOverhaul;
import com.github.theredbrain.foodoverhaul.block.entity.FoodBlockEntity;
import com.github.theredbrain.foodoverhaul.block.entity.FoodDisplayBlockEntity;
import net.fabricmc.fabric.api.object.builder.v1.block.entity.FabricBlockEntityTypeBuilder;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;

public class EntityRegistry {

	public static final BlockEntityType<FoodBlockEntity> FOOD_BLOCK_ENTITY = Registry.register(Registries.BLOCK_ENTITY_TYPE, FoodOverhaul.identifier("food_block"), FabricBlockEntityTypeBuilder.create(FoodBlockEntity::new, BlockRegistry.GENERIC_FOOD_BLOCK).build());

	public static final BlockEntityType<FoodDisplayBlockEntity> FOOD_DISPLAY_BLOCK_ENTITY = Registry.register(Registries.BLOCK_ENTITY_TYPE, FoodOverhaul.identifier("food_display_block"), FabricBlockEntityTypeBuilder.create(FoodDisplayBlockEntity::new, BlockRegistry.FOOD_DISPLAY_BLOCK).build());

	public static void init() {
	}

}
