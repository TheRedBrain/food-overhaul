package com.github.theredbrain.foodoverhaul.entity.player;

import com.github.theredbrain.foodoverhaul.block.entity.FoodBlockEntity;
import com.github.theredbrain.foodoverhaul.block.entity.FoodDisplayBlockEntity;
import net.minecraft.world.item.ItemStack;

public interface DuckPlayerEntityMixin {

	boolean foodoverhaul$canConsumeItem(ItemStack itemStack);

	float foodoverhaul$getMaxFoodEffects();

	default void foodoverhaul$openFoodBlockScreen(FoodBlockEntity foodBlockEntity) {
	}

	default void foodoverhaul$openFoodDisplayBlockScreen(FoodDisplayBlockEntity foodDisplayBlockEntity) {
	}

}
