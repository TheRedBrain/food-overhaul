package com.github.theredbrain.foodoverhaul.entity.player;

import com.github.theredbrain.foodoverhaul.FoodOverhaul;
import com.github.theredbrain.foodoverhaul.entity.effect.RemoveFoodStatusEffect;
import com.google.common.collect.HashMultimap;
import net.minecraft.core.Holder;
import net.minecraft.core.component.DataComponents;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.Identifier;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.food.FoodProperties;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.alchemy.PotionContents;
import net.minecraft.world.item.component.Consumable;
import net.minecraft.world.item.consume_effects.ApplyStatusEffectsConsumeEffect;
import net.minecraft.world.item.consume_effects.ConsumeEffect;

import java.util.Collection;

public class PlayerHelper {

	public static float getMaxFoodEffectsAttributeValue(Player player) {
		return (float) player.getAttributeValue(FoodOverhaul.MAX_FOOD_EFFECTS);
	}

	public static boolean canConsumeItem(Player player, ItemStack itemStack) {
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
						if (!tryEatOverhauledFood(player, instance.getEffect())) {
							return false;
						}
					}
				}
			}
			canConsume = true;
		}
		if (potionContentsComponent != null) {
			for (MobEffectInstance statusEffectInstance : potionContentsComponent.getAllEffects()) {
				if (!tryEatOverhauledFood(player, statusEffectInstance.getEffect())) {
					return false;
				}
			}
			canConsumePotion = true;
		}
		if (foodComponent != null) {
			canConsumeFood = player.getFoodData().needsFood() || foodComponent.canAlwaysEat();
		}
		return (canConsume || canConsumePotion) && canConsumeFood;
	}

	public static boolean tryEatOverhauledFood(Player player, Holder<MobEffect> mobEffectHolder) {
		if (mobEffectHolder.value() instanceof RemoveFoodStatusEffect) {
			return true;
		} else if (mobEffectHolder.is(FoodOverhaul.FOOD_EFFECTS)) {
			int currentEatenFoods = 0;
			Collection<MobEffectInstance> collection = player.getActiveEffects();
			for (MobEffectInstance currentEffect : collection) {
				if (currentEffect.getEffect() == mobEffectHolder) {
					if (currentEffect.endsWithin(FoodOverhaul.SERVER_CONFIG.food_effect_duration_threshold_to_allow_eating.get())) {
						return true;
					} else {
						player.sendOverlayMessage(Component.translatable("hud.message.food_eaten_already").append(Component.translatable(currentEffect.getDescriptionId())));
						return false;
					}
				} else if (currentEffect.getEffect().is(FoodOverhaul.FOOD_EFFECTS)) {
					currentEatenFoods++;
				}
			}
			boolean bl = currentEatenFoods < PlayerHelper.getMaxFoodEffectsAttributeValue(player);
			if (!bl) {
				player.sendOverlayMessage(Component.translatable("hud.message.max_food_eaten"));
			}
			return bl;
		}
		return true;
	}

	public static void updateNaturalAttributeModifiers(Player player) {
		HashMultimap<Holder<Attribute>, AttributeModifier> toBeAdded = HashMultimap.create();
		HashMultimap<Holder<Attribute>, AttributeModifier> toBeRemoved = HashMultimap.create();
		addAttributeModifier(toBeAdded, toBeRemoved, FoodOverhaul.MAX_FOOD_EFFECTS, FoodOverhaul.identifier("natural_maximum_food_effects_modifier"), FoodOverhaul.SERVER_CONFIG.natural_maximum_food_effects.get());
		if (!toBeRemoved.isEmpty()) {
			player.getAttributes().removeAttributeModifiers(toBeRemoved);
		}
		if (!toBeAdded.isEmpty()) {
			player.getAttributes().addTransientAttributeModifiers(toBeAdded);
		}
	}

	private static void addAttributeModifier(
			HashMultimap<Holder<Attribute>, AttributeModifier> toBeAdded,
			HashMultimap<Holder<Attribute>, AttributeModifier> toBeRemoved,
			Holder<Attribute> attributeHolder,
			Identifier identifier,
			double amount
	) {
		AttributeModifier attributeModifier = new AttributeModifier(identifier, amount, AttributeModifier.Operation.ADD_VALUE);
		if (amount == 0) {
			toBeRemoved.put(attributeHolder, attributeModifier);
		} else {
			toBeAdded.put(attributeHolder, attributeModifier);
		}
	}

}
