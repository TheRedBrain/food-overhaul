package com.github.theredbrain.foodoverhaul.mixin.entity;

import com.github.theredbrain.foodoverhaul.FoodOverhaul;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(LivingEntity.class)
public abstract class LivingEntityMixin {

	@Inject(method = "createLivingAttributes", at = @At("RETURN"))
	private static void foodoverhaul$createLivingAttributes(CallbackInfoReturnable<AttributeSupplier.Builder> cir) {
		cir.getReturnValue()
				.add(FoodOverhaul.MAX_FOOD_EFFECTS)
		;
	}
}
