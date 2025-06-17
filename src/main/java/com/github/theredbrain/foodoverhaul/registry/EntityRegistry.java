package com.github.theredbrain.foodoverhaul.registry;

import com.github.theredbrain.foodoverhaul.FoodOverhaul;
import com.github.theredbrain.foodoverhaul.block.entity.FoodBlockEntity;
import net.fabricmc.fabric.api.object.builder.v1.block.entity.FabricBlockEntityTypeBuilder;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;

public class EntityRegistry {

	public static BlockEntityType<FoodBlockEntity> FOOD_BLOCK_ENTITY = FabricBlockEntityTypeBuilder.create(FoodBlockEntity::new, BlockRegistry.GENERIC_FOOD_BLOCK).build();

	public static final BlockEntityType<FoodBlockEntity> GENERIC_FOOD_BLOCK_ENTITY = Registry.register(Registries.BLOCK_ENTITY_TYPE, FoodOverhaul.identifier("generic_food_block"), FOOD_BLOCK_ENTITY);

	public static void init() {}

}
