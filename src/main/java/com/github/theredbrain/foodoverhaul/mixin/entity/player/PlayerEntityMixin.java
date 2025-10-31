package com.github.theredbrain.foodoverhaul.mixin.entity.player;

import com.github.theredbrain.foodoverhaul.FoodOverhaul;
import com.github.theredbrain.foodoverhaul.block.entity.FoodBlockEntity;
import com.github.theredbrain.foodoverhaul.block.entity.FoodDisplayBlockEntity;
import com.github.theredbrain.foodoverhaul.entity.player.DuckPlayerEntityMixin;
import com.google.common.collect.HashMultimap;
import net.minecraft.component.DataComponentTypes;
import net.minecraft.component.type.ConsumableComponent;
import net.minecraft.component.type.FoodComponent;
import net.minecraft.component.type.PotionContentsComponent;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.attribute.EntityAttribute;
import net.minecraft.entity.attribute.EntityAttributeModifier;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.player.HungerManager;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.consume.ApplyEffectsConsumeEffect;
import net.minecraft.item.consume.ConsumeEffect;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(PlayerEntity.class)
public abstract class PlayerEntityMixin extends LivingEntity implements DuckPlayerEntityMixin {

	@Shadow
	public abstract HungerManager getHungerManager();

	protected PlayerEntityMixin(EntityType<? extends LivingEntity> entityType, World world) {
		super(entityType, world);
	}

	@Inject(method = "tick", at = @At("TAIL"))
	public void foodoverhaul$tick(CallbackInfo ci) {
		this.getAttributes().addTemporaryModifiers(getNaturalAttributeModifiers(this.getEntityWorld()));
	}

	@Override
	public boolean foodoverhaul$canConsumeItem(ItemStack itemStack) {
		boolean canConsume = false;
		boolean canConsumePotion = false;
		boolean canConsumeFood = true;
		ConsumableComponent consumableComponent = itemStack.get(DataComponentTypes.CONSUMABLE);
		PotionContentsComponent potionContentsComponent = itemStack.get(DataComponentTypes.POTION_CONTENTS);
		FoodComponent foodComponent = itemStack.get(DataComponentTypes.FOOD);
		if (consumableComponent != null) {
			for (ConsumeEffect effect : consumableComponent.onConsumeEffects()) {
				if (effect instanceof ApplyEffectsConsumeEffect applyEffectsConsumeEffect) {
					for (StatusEffectInstance instance : applyEffectsConsumeEffect.effects()) {
						if (!FoodOverhaul.tryEatOverhauledFood(((PlayerEntity) (Object) this), instance.getEffectType())) {
							return false;
						}
					}
				}
			}
			canConsume = true;
		}
		if (potionContentsComponent != null) {
			for (StatusEffectInstance statusEffectInstance : potionContentsComponent.getEffects()) {
				if (!FoodOverhaul.tryEatOverhauledFood(((PlayerEntity) (Object) this), statusEffectInstance.getEffectType())) {
					return false;
				}
			}
			canConsumePotion = true;
		}
		if (foodComponent != null) {
			canConsumeFood = this.getHungerManager().isNotFull() || foodComponent.canAlwaysEat();
		}
		return (canConsume || canConsumePotion) && canConsumeFood;
	}

	@Override
	public float foodoverhaul$getMaxFoodEffects() {
		return (float) this.getAttributeValue(FoodOverhaul.MAX_FOOD_EFFECTS);
	}

	@Unique
	private HashMultimap<RegistryEntry<EntityAttribute>, EntityAttributeModifier> getNaturalAttributeModifiers(World world) {
		HashMultimap<RegistryEntry<EntityAttribute>, EntityAttributeModifier> hashMultimap = HashMultimap.create();
		hashMultimap.put(FoodOverhaul.MAX_FOOD_EFFECTS, new EntityAttributeModifier(FoodOverhaul.identifier("natural_maximum_food_effects_modifier"), FoodOverhaul.SERVER_CONFIG.natural_maximum_food_effects.get(), EntityAttributeModifier.Operation.ADD_VALUE));
		return hashMultimap;
	}

	@Override
	public void foodoverhaul$openFoodBlockScreen(FoodBlockEntity foodBlockEntity) {
	}

	@Override
	public void foodoverhaul$openFoodDisplayBlockScreen(FoodDisplayBlockEntity foodDisplayBlockEntity) {
	}

}
