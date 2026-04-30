package com.github.theredbrain.foodoverhaul.mixin.component.type;

import com.github.theredbrain.foodoverhaul.FoodOverhaul;
import com.github.theredbrain.foodoverhaul.entity.player.PlayerHelper;
import com.llamalad7.mixinextras.injector.wrapmethod.WrapMethod;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

import java.util.List;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.component.Consumable;
import net.minecraft.world.item.consume_effects.ApplyStatusEffectsConsumeEffect;
import net.minecraft.world.item.consume_effects.ConsumeEffect;

@Mixin(Consumable.class)
public class ConsumableComponentMixin {

	@Shadow
	@Final
	private List<ConsumeEffect> onConsumeEffects;

	@WrapMethod(method = "canConsume")
	public boolean foodoverhaul$canConsume(LivingEntity user, ItemStack stack, Operation<Boolean> original) {
		boolean canConsume = true;
		boolean appliesFoodEffect = false;
		for (ConsumeEffect effect : this.onConsumeEffects) {
			if (effect instanceof ApplyStatusEffectsConsumeEffect applyEffectsConsumeEffect) {
				for (MobEffectInstance instance : applyEffectsConsumeEffect.effects()) {
					if (instance.getEffect().is(FoodOverhaul.FOOD_EFFECTS)) {
						appliesFoodEffect = true;
						break;
					}
				}
			}
		}
		if (appliesFoodEffect && user instanceof Player playerEntity) {
			canConsume = PlayerHelper.canConsumeItem(playerEntity, stack);
		}
		return canConsume && original.call(user, stack);
	}

}
