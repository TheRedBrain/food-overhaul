package com.github.theredbrain.foodoverhaul.entity.effect;

import net.minecraft.core.Holder;
import net.minecraft.tags.TagKey;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.LivingEntity;

public class RemoveEffectsInTagStatusEffect extends MobEffect {

	private final TagKey<MobEffect> effectsToBeRemoved;

	public RemoveEffectsInTagStatusEffect(TagKey<MobEffect> effectsToBeRemoved, MobEffectCategory category, int color) {
		super(category, color);
		this.effectsToBeRemoved = effectsToBeRemoved;
	}

	public RemoveEffectsInTagStatusEffect(TagKey<MobEffect> effectsToBeRemoved) {
		this(effectsToBeRemoved, MobEffectCategory.HARMFUL, 3381504);
	}

	@Override
	public void onEffectStarted(LivingEntity entity, int amplifier) {
		this.removeEffects(entity);
	}

	private void removeEffects(LivingEntity livingEntity) {
		for (MobEffectInstance currentEffect : livingEntity.getActiveEffects().stream().toList()) {
			Holder<MobEffect> statusEffectRegistryEntry = currentEffect.getEffect();
			if (statusEffectRegistryEntry.value() instanceof RemoveEffectsInTagStatusEffect || statusEffectRegistryEntry.is(this.effectsToBeRemoved)) {
				livingEntity.removeEffect(statusEffectRegistryEntry);
			}
		}
	}
}