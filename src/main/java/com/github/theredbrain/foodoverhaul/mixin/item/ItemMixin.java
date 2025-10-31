package com.github.theredbrain.foodoverhaul.mixin.item;

import com.github.theredbrain.foodoverhaul.FoodOverhaul;
import com.github.theredbrain.foodoverhaul.entity.player.DuckPlayerEntityMixin;
import net.minecraft.component.DataComponentTypes;
import net.minecraft.component.type.FoodComponent;
import net.minecraft.component.type.PotionContentsComponent;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(Item.class)
public abstract class ItemMixin {

	@Inject(method = "use", at = @At("HEAD"), cancellable = true)
	public void foodoverhaul$use(World world, PlayerEntity user, Hand hand, CallbackInfoReturnable<TypedActionResult<ItemStack>> cir) {
		ItemStack itemStack = user.getStackInHand(hand);
		FoodComponent foodComponent = itemStack.get(DataComponentTypes.FOOD);
		PotionContentsComponent potionContentsComponent = itemStack.get(DataComponentTypes.POTION_CONTENTS);
		if ((potionContentsComponent != null && potionContentsComponent.hasEffects()) || (foodComponent != null && (!foodComponent.effects().isEmpty() || !FoodOverhaul.SERVER_CONFIG.allow_eating_food_with_no_food_effect.get()))) {
			if (((DuckPlayerEntityMixin) user).foodoverhaul$canConsumeItem(itemStack)) {
				user.setCurrentHand(hand);
				cir.setReturnValue(TypedActionResult.consume(itemStack));
			} else {
				cir.setReturnValue(TypedActionResult.fail(itemStack));
			}
			cir.cancel();
		}
	}
}
