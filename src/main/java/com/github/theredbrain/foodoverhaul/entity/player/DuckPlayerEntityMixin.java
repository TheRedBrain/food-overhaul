package com.github.theredbrain.foodoverhaul.entity.player;

import com.github.theredbrain.foodoverhaul.block.entity.FoodBlockEntity;
import net.minecraft.item.ItemStack;

public interface DuckPlayerEntityMixin {

	boolean foodoverhaul$canConsumeItem(ItemStack itemStack);

	float foodoverhaul$getMaxFoodEffects();

	void foodoverhaul$openFoodBlockScreen(FoodBlockEntity foodBlockEntity);

}
