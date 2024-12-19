package com.github.theredbrain.foodoverhaul.effect;

import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffectCategory;

public class FoodStatusEffect extends StatusEffect {
	public FoodStatusEffect(StatusEffectCategory category, int color) {
		super(category, color);
	}

	public FoodStatusEffect() {
		this(StatusEffectCategory.BENEFICIAL, 3381504);
	}
}
