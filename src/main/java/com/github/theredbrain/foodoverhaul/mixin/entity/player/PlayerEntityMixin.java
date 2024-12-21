package com.github.theredbrain.foodoverhaul.mixin.entity.player;

import com.github.theredbrain.foodoverhaul.FoodOverhaul;
import com.github.theredbrain.foodoverhaul.effect.FoodStatusEffect;
import com.github.theredbrain.foodoverhaul.effect.RemoveFoodStatusEffect;
import com.github.theredbrain.foodoverhaul.entity.player.DuckPlayerEntityMixin;
import net.minecraft.component.DataComponentTypes;
import net.minecraft.component.type.FoodComponent;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.attribute.DefaultAttributeContainer;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.player.ItemCooldownManager;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.text.Text;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.Collection;

@Mixin(PlayerEntity.class)
public abstract class PlayerEntityMixin extends LivingEntity implements DuckPlayerEntityMixin {

	@Shadow
	public abstract void sendMessage(Text message, boolean overlay);

	@Shadow
	public abstract ItemStack getEquippedStack(EquipmentSlot slot);

	@Shadow
	public abstract ItemCooldownManager getItemCooldownManager();

	protected PlayerEntityMixin(EntityType<? extends LivingEntity> entityType, World world) {
		super(entityType, world);
	}

	@Inject(method = "createPlayerAttributes", at = @At("RETURN"))
	private static void foodoverhaul$createPlayerAttributes(CallbackInfoReturnable<DefaultAttributeContainer.Builder> cir) {
		cir.getReturnValue()
				.add(FoodOverhaul.MAX_FOOD_EFFECTS, 3)
		;
	}

	@Inject(method = "eatFood", at = @At(value = "RETURN"))
	public void foodoverhaul$eatFood(World world, ItemStack stack, FoodComponent foodComponent, CallbackInfoReturnable<ItemStack> cir) {
		this.getItemCooldownManager().set(stack.getItem(), FoodOverhaul.SERVER_CONFIG.item_cooldown_after_eating.get());
	}

	@Unique
	public boolean foodoverhaul$canConsumeItem(ItemStack itemStack) {
		FoodComponent foodComponent = itemStack.get(DataComponentTypes.FOOD);
		if (foodComponent != null) {
			for (FoodComponent.StatusEffectEntry statusEffectEntry : foodComponent.effects()) {
				if (getWorld().isClient) continue;
				return foodoverhaul$tryEatOverhauledFood(statusEffectEntry.effect());
			}
		}
		return false;
	}

	@Unique
	public boolean foodoverhaul$tryEatOverhauledFood(StatusEffectInstance statusEffectInstance) {
		if (this.getStatusEffects().isEmpty() || statusEffectInstance.getEffectType().value() instanceof RemoveFoodStatusEffect) {
			return true;
		} else if (statusEffectInstance.getEffectType().value() instanceof FoodStatusEffect) {
			int currentEatenFoods = 0;
			Collection<StatusEffectInstance> collection = this.getStatusEffects();
			for (StatusEffectInstance currentEffect : collection) {
				if (currentEffect.getEffectType() == statusEffectInstance.getEffectType()) {
					if (currentEffect.isDurationBelow(FoodOverhaul.SERVER_CONFIG.food_effect_duration_threshold_to_allow_eating.get())) {
						return true;
					} else {
						this.sendMessage(Text.translatable("hud.message.foodEatenAlready").append(Text.translatable(currentEffect.getTranslationKey())), true);
						return false;
					}
				} else if (currentEffect.getEffectType().value() instanceof FoodStatusEffect) {
					currentEatenFoods++;
				}
			}
			boolean bl = currentEatenFoods < this.foodoverhaul$getMaxFoodEffects();
			if (!bl) {
				this.sendMessage(Text.translatable("hud.message.maxFoodEaten"), true);
			}
			return bl;
		}
		return true;
	}

	@Override
	public float foodoverhaul$getMaxFoodEffects() {
		return (float) this.getAttributeValue(FoodOverhaul.MAX_FOOD_EFFECTS);
	}
}
