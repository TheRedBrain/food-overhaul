package com.github.theredbrain.foodoverhaul.effect;

import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffectCategory;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.registry.entry.RegistryEntry;

public class RemoveFoodStatusEffect extends StatusEffect {
	public RemoveFoodStatusEffect(StatusEffectCategory category, int color) {
		super(category, color);
	}

	public RemoveFoodStatusEffect() {
		this(StatusEffectCategory.HARMFUL, 3381504);
	}

	@Override
	public void onApplied(LivingEntity entity, int amplifier) {
		this.removeEffects(entity);
	}

	private void removeEffects(LivingEntity livingEntity) {
		for (StatusEffectInstance currentEffect : livingEntity.getStatusEffects().stream().toList()) {
			RegistryEntry<StatusEffect> statusEffectRegistryEntry = currentEffect.getEffectType();
			if (statusEffectRegistryEntry.value() instanceof RemoveFoodStatusEffect || statusEffectRegistryEntry.value() instanceof FoodStatusEffect) {
				livingEntity.removeStatusEffect(statusEffectRegistryEntry);
			}
		}
	}
}
