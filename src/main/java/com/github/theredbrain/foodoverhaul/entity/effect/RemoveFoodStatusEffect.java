package com.github.theredbrain.foodoverhaul.entity.effect;

import com.github.theredbrain.foodoverhaul.FoodOverhaul;
import net.minecraft.core.Holder;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.LivingEntity;

public class RemoveFoodStatusEffect extends MobEffect {
	public RemoveFoodStatusEffect(MobEffectCategory category, int color) {
		super(category, color);
	}

	public RemoveFoodStatusEffect() {
		this(MobEffectCategory.HARMFUL, 3381504);
	}

	@Override
	public void onEffectStarted(LivingEntity entity, int amplifier) {
		this.removeEffects(entity);
	}

	private void removeEffects(LivingEntity livingEntity) {
		for (MobEffectInstance currentEffect : livingEntity.getActiveEffects().stream().toList()) {
			Holder<MobEffect> statusEffectRegistryEntry = currentEffect.getEffect();
			if (statusEffectRegistryEntry.value() instanceof RemoveFoodStatusEffect || statusEffectRegistryEntry.is(FoodOverhaul.FOOD_EFFECTS)) {
				livingEntity.removeEffect(statusEffectRegistryEntry);
			}
		}
	}
}