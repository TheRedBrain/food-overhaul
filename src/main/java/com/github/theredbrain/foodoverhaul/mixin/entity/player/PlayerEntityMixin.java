package com.github.theredbrain.foodoverhaul.mixin.entity.player;

import com.github.theredbrain.foodoverhaul.FoodOverhaul;
import com.github.theredbrain.foodoverhaul.entity.player.DuckPlayerEntityMixin;
import com.google.common.collect.HashMultimap;
import net.minecraft.core.Holder;
import net.minecraft.core.component.DataComponents;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.food.FoodData;
import net.minecraft.world.food.FoodProperties;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.alchemy.PotionContents;
import net.minecraft.world.item.component.Consumable;
import net.minecraft.world.item.consume_effects.ApplyStatusEffectsConsumeEffect;
import net.minecraft.world.item.consume_effects.ConsumeEffect;
import net.minecraft.world.level.Level;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(Player.class)
public abstract class PlayerEntityMixin extends LivingEntity implements DuckPlayerEntityMixin {

	@Shadow
	protected FoodData foodData;

	protected PlayerEntityMixin(EntityType<? extends LivingEntity> entityType, Level world) {
		super(entityType, world);
	}

	@Inject(method = "tick", at = @At("TAIL"))
	public void foodoverhaul$tick(CallbackInfo ci) {
		this.getAttributes().addTransientAttributeModifiers(getNaturalAttributeModifiers(this.level()));
	}

	@Override
	public boolean foodoverhaul$canConsumeItem(ItemStack itemStack) {
		boolean canConsume = false;
		boolean canConsumePotion = false;
		boolean canConsumeFood = true;
		Consumable consumableComponent = itemStack.get(DataComponents.CONSUMABLE);
		PotionContents potionContentsComponent = itemStack.get(DataComponents.POTION_CONTENTS);
		FoodProperties foodComponent = itemStack.get(DataComponents.FOOD);
		if (consumableComponent != null) {
			for (ConsumeEffect effect : consumableComponent.onConsumeEffects()) {
				if (effect instanceof ApplyStatusEffectsConsumeEffect applyEffectsConsumeEffect) {
					for (MobEffectInstance instance : applyEffectsConsumeEffect.effects()) {
						if (!FoodOverhaul.tryEatOverhauledFood(((Player) (Object) this), instance.getEffect())) {
							return false;
						}
					}
				}
			}
			canConsume = true;
		}
		if (potionContentsComponent != null) {
			for (MobEffectInstance statusEffectInstance : potionContentsComponent.getAllEffects()) {
				if (!FoodOverhaul.tryEatOverhauledFood(((Player) (Object) this), statusEffectInstance.getEffect())) {
					return false;
				}
			}
			canConsumePotion = true;
		}
		if (foodComponent != null) {
			canConsumeFood = this.foodData.needsFood() || foodComponent.canAlwaysEat();
		}
		return (canConsume || canConsumePotion) && canConsumeFood;
	}

	@Override
	public float foodoverhaul$getMaxFoodEffects() {
		return (float) this.getAttributeValue(FoodOverhaul.MAX_FOOD_EFFECTS);
	}

	@Unique
	private HashMultimap<Holder<Attribute>, AttributeModifier> getNaturalAttributeModifiers(Level world) {
		HashMultimap<Holder<Attribute>, AttributeModifier> hashMultimap = HashMultimap.create();
		hashMultimap.put(FoodOverhaul.MAX_FOOD_EFFECTS, new AttributeModifier(FoodOverhaul.identifier("natural_maximum_food_effects_modifier"), FoodOverhaul.SERVER_CONFIG.natural_maximum_food_effects.get(), AttributeModifier.Operation.ADD_VALUE));
		return hashMultimap;
	}

}
