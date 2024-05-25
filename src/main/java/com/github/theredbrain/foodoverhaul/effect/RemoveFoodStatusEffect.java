package com.github.theredbrain.foodoverhaul.effect;

import com.github.theredbrain.foodoverhaul.FoodOverhaul;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.InstantStatusEffect;
import net.minecraft.entity.effect.StatusEffectCategory;
import net.minecraft.entity.effect.StatusEffectInstance;
import org.jetbrains.annotations.Nullable;

public class RemoveFoodStatusEffect extends InstantStatusEffect {
    public RemoveFoodStatusEffect() {
        super(StatusEffectCategory.HARMFUL, 3381504);
    }

    @Override
    public void applyUpdateEffect(LivingEntity entity, int amplifier) {
        this.removeAllFoodEffects(entity);
    }

    @Override
    public void applyInstantEffect(@Nullable Entity source, @Nullable Entity attacker, LivingEntity target, int amplifier, double proximity) {
        this.removeAllFoodEffects(target);
    }

    private void removeAllFoodEffects(LivingEntity livingEntity) {
        FoodOverhaul.LOGGER.info("removeAllFoodEffects");
        for (StatusEffectInstance currentEffect : livingEntity.getStatusEffects().stream().toList()) {
            if (currentEffect.getEffectType() instanceof FoodStatusEffect) {
                livingEntity.removeStatusEffect(currentEffect.getEffectType());
            }
        }
    }
}
