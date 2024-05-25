package com.github.theredbrain.foodoverhaul.entity.player;

import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.item.ItemStack;

public interface DuckPlayerEntityMixin {
    boolean foodoverhaul$canConsumeItem(ItemStack itemStack);
    boolean foodoverhaul$tryEatOverhauledFood(StatusEffectInstance statusEffectInstance);
    float foodoverhaul$getMaxFoodEffects();
}
