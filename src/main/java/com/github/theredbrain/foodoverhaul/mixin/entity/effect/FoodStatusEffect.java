package com.github.theredbrain.foodoverhaul.mixin.entity.effect;

import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffectCategory;

public class FoodStatusEffect extends StatusEffect {

	public FoodStatusEffect() {
		super(StatusEffectCategory.BENEFICIAL, 16262179);
	}
}
