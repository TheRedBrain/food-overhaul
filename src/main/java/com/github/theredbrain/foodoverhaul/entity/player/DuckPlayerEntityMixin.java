package com.github.theredbrain.foodoverhaul.entity.player;

import net.minecraft.item.ItemStack;

public interface DuckPlayerEntityMixin {

	boolean foodoverhaul$canConsumeItem(ItemStack itemStack);

	float foodoverhaul$getMaxFoodEffects();
}
