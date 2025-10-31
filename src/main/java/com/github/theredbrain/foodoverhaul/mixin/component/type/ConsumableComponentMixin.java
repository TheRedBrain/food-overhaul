package com.github.theredbrain.foodoverhaul.mixin.component.type;
//
//import com.github.theredbrain.foodoverhaul.FoodOverhaul;
//import com.github.theredbrain.foodoverhaul.entity.player.DuckPlayerEntityMixin;
//import com.llamalad7.mixinextras.injector.wrapmethod.WrapMethod;
//import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
//import net.minecraft.component.type.ConsumableComponent;
//import net.minecraft.entity.LivingEntity;
//import net.minecraft.entity.effect.StatusEffectInstance;
//import net.minecraft.entity.player.PlayerEntity;
//import net.minecraft.item.ItemStack;
//import net.minecraft.item.consume.ApplyEffectsConsumeEffect;
//import net.minecraft.item.consume.ConsumeEffect;
//import org.spongepowered.asm.mixin.Final;
//import org.spongepowered.asm.mixin.Mixin;
//import org.spongepowered.asm.mixin.Shadow;
//
//import java.util.List;
//
//@Mixin(ConsumableComponent.class)
//public class ConsumableComponentMixin {
//
//	@Shadow
//	@Final
//	private List<ConsumeEffect> onConsumeEffects;
//
//	@WrapMethod(method = "canConsume")
//	public boolean foodoverhaul$canConsume(LivingEntity user, ItemStack stack, Operation<Boolean> original) {
//		boolean canConsume = true;
//		boolean appliesFoodEffect = false;
//		for (ConsumeEffect effect : this.onConsumeEffects) {
//			if (effect instanceof ApplyEffectsConsumeEffect applyEffectsConsumeEffect) {
//				for (StatusEffectInstance instance : applyEffectsConsumeEffect.effects()) {
//					if (instance.getEffectType().isIn(FoodOverhaul.FOOD_EFFECTS)) {
//						appliesFoodEffect = true;
//						break;
//					}
//				}
//			}
//		}
//		if (appliesFoodEffect && user instanceof PlayerEntity playerEntity) {
//			canConsume = ((DuckPlayerEntityMixin) playerEntity).foodoverhaul$canConsumeItem(stack);
//		}
//		return canConsume && original.call(user, stack);
//	}
//
//}
