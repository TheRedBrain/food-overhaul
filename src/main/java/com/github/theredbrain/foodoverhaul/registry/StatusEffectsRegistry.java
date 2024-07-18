package com.github.theredbrain.foodoverhaul.registry;

import com.github.theredbrain.foodoverhaul.FoodOverhaul;
import com.github.theredbrain.foodoverhaul.effect.FoodStatusEffect;
import com.github.theredbrain.foodoverhaul.effect.RemoveFoodStatusEffect;
import com.github.theredbrain.healthregenerationoverhaul.HealthRegenerationOverhaul;
import com.github.theredbrain.manaattributes.ManaAttributes;
import com.github.theredbrain.overhauleddamage.OverhauledDamage;
import com.github.theredbrain.staminaattributes.StaminaAttributes;
import net.minecraft.entity.attribute.EntityAttributeModifier;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.registry.entry.RegistryEntry;

public class StatusEffectsRegistry {

	public static final RegistryEntry<StatusEffect> APPLE_FOOD_EFFECT = Registry.registerReference(Registries.STATUS_EFFECT, FoodOverhaul.identifier("apple_food_effect"), new FoodStatusEffect()
			.addAttributeModifier(EntityAttributes.GENERIC_MAX_HEALTH, FoodOverhaul.identifier("effect.apple_food_effect"), 5.0F, EntityAttributeModifier.Operation.ADD_VALUE)
			.addAttributeModifier(HealthRegenerationOverhaul.HEALTH_REGENERATION, FoodOverhaul.identifier("effect.apple_food_effect"), 1.0F, EntityAttributeModifier.Operation.ADD_VALUE)
			.addAttributeModifier(StaminaAttributes.MAX_STAMINA, FoodOverhaul.identifier("effect.apple_food_effect"), 5.0F, EntityAttributeModifier.Operation.ADD_VALUE)
			.addAttributeModifier(StaminaAttributes.STAMINA_REGENERATION, FoodOverhaul.identifier("effect.apple_food_effect"), 1.0F, EntityAttributeModifier.Operation.ADD_VALUE)
	);

	public static final RegistryEntry<StatusEffect> BAKED_POTATO_FOOD_EFFECT = Registry.registerReference(Registries.STATUS_EFFECT, FoodOverhaul.identifier("baked_potato_food_effect"), new FoodStatusEffect()
			.addAttributeModifier(EntityAttributes.GENERIC_MAX_HEALTH, FoodOverhaul.identifier("effect.baked_potato_food_effect"), 10.0F, EntityAttributeModifier.Operation.ADD_VALUE)
			.addAttributeModifier(HealthRegenerationOverhaul.HEALTH_REGENERATION, FoodOverhaul.identifier("effect.baked_potato_food_effect"), 1.0F, EntityAttributeModifier.Operation.ADD_VALUE)
			.addAttributeModifier(StaminaAttributes.MAX_STAMINA, FoodOverhaul.identifier("effect.baked_potato_food_effect"), 7.0F, EntityAttributeModifier.Operation.ADD_VALUE)
			.addAttributeModifier(StaminaAttributes.STAMINA_REGENERATION, FoodOverhaul.identifier("effect.baked_potato_food_effect"), 1.0F, EntityAttributeModifier.Operation.ADD_VALUE)
	);

	public static final RegistryEntry<StatusEffect> BEEF_FOOD_EFFECT = Registry.registerReference(Registries.STATUS_EFFECT, FoodOverhaul.identifier("beef_food_effect"), new FoodStatusEffect()
			.addAttributeModifier(EntityAttributes.GENERIC_MAX_HEALTH, FoodOverhaul.identifier("effect.beef_food_effect"), 5.0F, EntityAttributeModifier.Operation.ADD_VALUE)
			.addAttributeModifier(HealthRegenerationOverhaul.HEALTH_REGENERATION, FoodOverhaul.identifier("effect.beef_food_effect"), 0.5F, EntityAttributeModifier.Operation.ADD_VALUE)
			.addAttributeModifier(StaminaAttributes.MAX_STAMINA, FoodOverhaul.identifier("effect.beef_food_effect"), 2.0F, EntityAttributeModifier.Operation.ADD_VALUE)
			.addAttributeModifier(StaminaAttributes.STAMINA_REGENERATION, FoodOverhaul.identifier("effect.beef_food_effect"), 2.0F, EntityAttributeModifier.Operation.ADD_VALUE)
	);

	public static final RegistryEntry<StatusEffect> BEETROOT_FOOD_EFFECT = Registry.registerReference(Registries.STATUS_EFFECT, FoodOverhaul.identifier("beetroot_food_effect"), new FoodStatusEffect()
			.addAttributeModifier(EntityAttributes.GENERIC_MAX_HEALTH, FoodOverhaul.identifier("effect.beetroot_food_effect"), 1.0F, EntityAttributeModifier.Operation.ADD_VALUE)
			.addAttributeModifier(HealthRegenerationOverhaul.HEALTH_REGENERATION, FoodOverhaul.identifier("effect.beetroot_food_effect"), 1.0F, EntityAttributeModifier.Operation.ADD_VALUE)
			.addAttributeModifier(StaminaAttributes.MAX_STAMINA, FoodOverhaul.identifier("effect.beetroot_food_effect"), 5.0F, EntityAttributeModifier.Operation.ADD_VALUE)
			.addAttributeModifier(StaminaAttributes.STAMINA_REGENERATION, FoodOverhaul.identifier("effect.beetroot_food_effect"), 2.0F, EntityAttributeModifier.Operation.ADD_VALUE)
	);

	public static final RegistryEntry<StatusEffect> BEETROOT_SOUP_FOOD_EFFECT = Registry.registerReference(Registries.STATUS_EFFECT, FoodOverhaul.identifier("beetroot_soup_food_effect"), new FoodStatusEffect()
			.addAttributeModifier(EntityAttributes.GENERIC_MAX_HEALTH, FoodOverhaul.identifier("effect.beetroot_soup_food_effect"), 15.0F, EntityAttributeModifier.Operation.ADD_VALUE)
			.addAttributeModifier(HealthRegenerationOverhaul.HEALTH_REGENERATION, FoodOverhaul.identifier("effect.beetroot_soup_food_effect"), 1.0F, EntityAttributeModifier.Operation.ADD_VALUE)
			.addAttributeModifier(StaminaAttributes.MAX_STAMINA, FoodOverhaul.identifier("effect.beetroot_soup_food_effect"), 10.0F, EntityAttributeModifier.Operation.ADD_VALUE)
			.addAttributeModifier(StaminaAttributes.STAMINA_REGENERATION, FoodOverhaul.identifier("effect.beetroot_soup_food_effect"), 2.0F, EntityAttributeModifier.Operation.ADD_VALUE)
			.addAttributeModifier(OverhauledDamage.FROST_RESISTANCE, FoodOverhaul.identifier("effect.beetroot_soup_food_effect"), 5.0F, EntityAttributeModifier.Operation.ADD_VALUE)
	);

	public static final RegistryEntry<StatusEffect> BREAD_FOOD_EFFECT = Registry.registerReference(Registries.STATUS_EFFECT, FoodOverhaul.identifier("bread_food_effect"), new FoodStatusEffect()
			.addAttributeModifier(EntityAttributes.GENERIC_MAX_HEALTH, FoodOverhaul.identifier("effect.bread_food_effect"), 10.0F, EntityAttributeModifier.Operation.ADD_VALUE)
			.addAttributeModifier(HealthRegenerationOverhaul.HEALTH_REGENERATION, FoodOverhaul.identifier("effect.bread_food_effect"), 1.0F, EntityAttributeModifier.Operation.ADD_VALUE)
			.addAttributeModifier(StaminaAttributes.MAX_STAMINA, FoodOverhaul.identifier("effect.bread_food_effect"), 10.0F, EntityAttributeModifier.Operation.ADD_VALUE)
			.addAttributeModifier(StaminaAttributes.STAMINA_REGENERATION, FoodOverhaul.identifier("effect.bread_food_effect"), 2.0F, EntityAttributeModifier.Operation.ADD_VALUE)
	);

	public static final RegistryEntry<StatusEffect> BROWN_MUSHROOM_FOOD_EFFECT = Registry.registerReference(Registries.STATUS_EFFECT, FoodOverhaul.identifier("brown_mushroom_food_effect"), new FoodStatusEffect()
			.addAttributeModifier(EntityAttributes.GENERIC_MAX_HEALTH, FoodOverhaul.identifier("effect.brown_mushroom_food_effect"), 5.0F, EntityAttributeModifier.Operation.ADD_VALUE)
			.addAttributeModifier(HealthRegenerationOverhaul.HEALTH_REGENERATION, FoodOverhaul.identifier("effect.brown_mushroom_food_effect"), 1.0F, EntityAttributeModifier.Operation.ADD_VALUE)
			.addAttributeModifier(StaminaAttributes.MAX_STAMINA, FoodOverhaul.identifier("effect.brown_mushroom_food_effect"), 3.0F, EntityAttributeModifier.Operation.ADD_VALUE)
			.addAttributeModifier(StaminaAttributes.STAMINA_REGENERATION, FoodOverhaul.identifier("effect.brown_mushroom_food_effect"), 2.0F, EntityAttributeModifier.Operation.ADD_VALUE)
	);

	public static final RegistryEntry<StatusEffect> CAKE_FOOD_EFFECT = Registry.registerReference(Registries.STATUS_EFFECT, FoodOverhaul.identifier("cake_food_effect"), new FoodStatusEffect()
			.addAttributeModifier(EntityAttributes.GENERIC_MAX_HEALTH, FoodOverhaul.identifier("effect.cake_food_effect"), 10.0F, EntityAttributeModifier.Operation.ADD_VALUE)
			.addAttributeModifier(HealthRegenerationOverhaul.HEALTH_REGENERATION, FoodOverhaul.identifier("effect.cake_food_effect"), 2.0F, EntityAttributeModifier.Operation.ADD_VALUE)
			.addAttributeModifier(StaminaAttributes.MAX_STAMINA, FoodOverhaul.identifier("effect.cake_food_effect"), 25.0F, EntityAttributeModifier.Operation.ADD_VALUE)
			.addAttributeModifier(StaminaAttributes.STAMINA_REGENERATION, FoodOverhaul.identifier("effect.cake_food_effect"), 1.0F, EntityAttributeModifier.Operation.ADD_VALUE)
	);

	public static final RegistryEntry<StatusEffect> CARROT_FOOD_EFFECT = Registry.registerReference(Registries.STATUS_EFFECT, FoodOverhaul.identifier("carrot_food_effect"), new FoodStatusEffect()
			.addAttributeModifier(EntityAttributes.GENERIC_MAX_HEALTH, FoodOverhaul.identifier("effect.carrot_food_effect"), 5.0F, EntityAttributeModifier.Operation.ADD_VALUE)
			.addAttributeModifier(HealthRegenerationOverhaul.HEALTH_REGENERATION, FoodOverhaul.identifier("effect.carrot_food_effect"), 1.0F, EntityAttributeModifier.Operation.ADD_VALUE)
			.addAttributeModifier(StaminaAttributes.MAX_STAMINA, FoodOverhaul.identifier("effect.carrot_food_effect"), 5.0F, EntityAttributeModifier.Operation.ADD_VALUE)
			.addAttributeModifier(StaminaAttributes.STAMINA_REGENERATION, FoodOverhaul.identifier("effect.carrot_food_effect"), 1.0F, EntityAttributeModifier.Operation.ADD_VALUE)
	);

	public static final RegistryEntry<StatusEffect> CHICKEN_FOOD_EFFECT = Registry.registerReference(Registries.STATUS_EFFECT, FoodOverhaul.identifier("chicken_food_effect"), new FoodStatusEffect()
			.addAttributeModifier(EntityAttributes.GENERIC_MAX_HEALTH, FoodOverhaul.identifier("effect.chicken_food_effect"), 5.0F, EntityAttributeModifier.Operation.ADD_VALUE)
			.addAttributeModifier(HealthRegenerationOverhaul.HEALTH_REGENERATION, FoodOverhaul.identifier("effect.chicken_food_effect"), 0.5F, EntityAttributeModifier.Operation.ADD_VALUE)
			.addAttributeModifier(OverhauledDamage.POISON_BUILD_UP_REDUCTION, FoodOverhaul.identifier("effect.chicken_food_effect"), -2.0F, EntityAttributeModifier.Operation.ADD_VALUE)
			.addAttributeModifier(StaminaAttributes.STAMINA_REGENERATION, FoodOverhaul.identifier("effect.chicken_food_effect"), 2.0F, EntityAttributeModifier.Operation.ADD_VALUE)
	);

	public static final RegistryEntry<StatusEffect> CHORUS_FRUIT_FOOD_EFFECT = Registry.registerReference(Registries.STATUS_EFFECT, FoodOverhaul.identifier("chorus_fruit_food_effect"), new FoodStatusEffect()
			.addAttributeModifier(EntityAttributes.GENERIC_MAX_HEALTH, FoodOverhaul.identifier("effect.chorus_fruit_food_effect"), 1.0F, EntityAttributeModifier.Operation.ADD_VALUE)
			.addAttributeModifier(HealthRegenerationOverhaul.HEALTH_REGENERATION, FoodOverhaul.identifier("effect.chorus_fruit_food_effect"), 1.0F, EntityAttributeModifier.Operation.ADD_VALUE)
			.addAttributeModifier(StaminaAttributes.MAX_STAMINA, FoodOverhaul.identifier("effect.chorus_fruit_food_effect"), 2.0F, EntityAttributeModifier.Operation.ADD_VALUE)
			.addAttributeModifier(StaminaAttributes.STAMINA_REGENERATION, FoodOverhaul.identifier("effect.chorus_fruit_food_effect"), 2.0F, EntityAttributeModifier.Operation.ADD_VALUE)
	);

	public static final RegistryEntry<StatusEffect> COCOA_BEANS_FOOD_EFFECT = Registry.registerReference(Registries.STATUS_EFFECT, FoodOverhaul.identifier("cocoa_beans_food_effect"), new FoodStatusEffect()
			.addAttributeModifier(EntityAttributes.GENERIC_MAX_HEALTH, FoodOverhaul.identifier("effect.cocoa_beans_food_effect"), 5.0F, EntityAttributeModifier.Operation.ADD_VALUE)
			.addAttributeModifier(HealthRegenerationOverhaul.HEALTH_REGENERATION, FoodOverhaul.identifier("effect.cocoa_beans_food_effect"), 2.0F, EntityAttributeModifier.Operation.ADD_VALUE)
			.addAttributeModifier(StaminaAttributes.MAX_STAMINA, FoodOverhaul.identifier("effect.cocoa_beans_food_effect"), 3.0F, EntityAttributeModifier.Operation.ADD_VALUE)
			.addAttributeModifier(StaminaAttributes.STAMINA_REGENERATION, FoodOverhaul.identifier("effect.cocoa_beans_food_effect"), 1.0F, EntityAttributeModifier.Operation.ADD_VALUE)
	);

	public static final RegistryEntry<StatusEffect> COD_FOOD_EFFECT = Registry.registerReference(Registries.STATUS_EFFECT, FoodOverhaul.identifier("cod_food_effect"), new FoodStatusEffect()
			.addAttributeModifier(EntityAttributes.GENERIC_MAX_HEALTH, FoodOverhaul.identifier("effect.cod_food_effect"), 5.0F, EntityAttributeModifier.Operation.ADD_VALUE)
			.addAttributeModifier(HealthRegenerationOverhaul.HEALTH_REGENERATION, FoodOverhaul.identifier("effect.cod_food_effect"), 0.5F, EntityAttributeModifier.Operation.ADD_VALUE)
			.addAttributeModifier(OverhauledDamage.POISON_BUILD_UP_REDUCTION, FoodOverhaul.identifier("effect.cod_food_effect"), -2.0F, EntityAttributeModifier.Operation.ADD_VALUE)
			.addAttributeModifier(StaminaAttributes.MAX_STAMINA, FoodOverhaul.identifier("effect.cod_food_effect"), 2.0F, EntityAttributeModifier.Operation.ADD_VALUE)
			.addAttributeModifier(StaminaAttributes.STAMINA_REGENERATION, FoodOverhaul.identifier("effect.cod_food_effect"), 2.0F, EntityAttributeModifier.Operation.ADD_VALUE)
	);

	public static final RegistryEntry<StatusEffect> COOKED_BEEF_FOOD_EFFECT = Registry.registerReference(Registries.STATUS_EFFECT, FoodOverhaul.identifier("cooked_beef_food_effect"), new FoodStatusEffect()
			.addAttributeModifier(EntityAttributes.GENERIC_MAX_HEALTH, FoodOverhaul.identifier("effect.cooked_beef_food_effect"), 10.0F, EntityAttributeModifier.Operation.ADD_VALUE)
			.addAttributeModifier(HealthRegenerationOverhaul.HEALTH_REGENERATION, FoodOverhaul.identifier("effect.cooked_beef_food_effect"), 1.0F, EntityAttributeModifier.Operation.ADD_VALUE)
			.addAttributeModifier(StaminaAttributes.MAX_STAMINA, FoodOverhaul.identifier("effect.cooked_beef_food_effect"), 5.0F, EntityAttributeModifier.Operation.ADD_VALUE)
			.addAttributeModifier(StaminaAttributes.STAMINA_REGENERATION, FoodOverhaul.identifier("effect.cooked_beef_food_effect"), 2.0F, EntityAttributeModifier.Operation.ADD_VALUE)
	);

	public static final RegistryEntry<StatusEffect> COOKED_CHICKEN_FOOD_EFFECT = Registry.registerReference(Registries.STATUS_EFFECT, FoodOverhaul.identifier("cooked_chicken_food_effect"), new FoodStatusEffect()
			.addAttributeModifier(EntityAttributes.GENERIC_MAX_HEALTH, FoodOverhaul.identifier("effect.cooked_chicken_food_effect"), 10.0F, EntityAttributeModifier.Operation.ADD_VALUE)
			.addAttributeModifier(HealthRegenerationOverhaul.HEALTH_REGENERATION, FoodOverhaul.identifier("effect.cooked_chicken_food_effect"), 1.0F, EntityAttributeModifier.Operation.ADD_VALUE)
			.addAttributeModifier(StaminaAttributes.MAX_STAMINA, FoodOverhaul.identifier("effect.cooked_chicken_food_effect"), 5.0F, EntityAttributeModifier.Operation.ADD_VALUE)
			.addAttributeModifier(StaminaAttributes.STAMINA_REGENERATION, FoodOverhaul.identifier("effect.cooked_chicken_food_effect"), 3.0F, EntityAttributeModifier.Operation.ADD_VALUE)
	);

	public static final RegistryEntry<StatusEffect> COOKED_COD_FOOD_EFFECT = Registry.registerReference(Registries.STATUS_EFFECT, FoodOverhaul.identifier("cooked_cod_food_effect"), new FoodStatusEffect()
			.addAttributeModifier(EntityAttributes.GENERIC_MAX_HEALTH, FoodOverhaul.identifier("effect.cooked_cod_food_effect"), 10.0F, EntityAttributeModifier.Operation.ADD_VALUE)
			.addAttributeModifier(HealthRegenerationOverhaul.HEALTH_REGENERATION, FoodOverhaul.identifier("effect.cooked_cod_food_effect"), 1.0F, EntityAttributeModifier.Operation.ADD_VALUE)
			.addAttributeModifier(StaminaAttributes.MAX_STAMINA, FoodOverhaul.identifier("effect.cooked_cod_food_effect"), 5.0F, EntityAttributeModifier.Operation.ADD_VALUE)
			.addAttributeModifier(StaminaAttributes.STAMINA_REGENERATION, FoodOverhaul.identifier("effect.cooked_cod_food_effect"), 3.0F, EntityAttributeModifier.Operation.ADD_VALUE)
	);

	public static final RegistryEntry<StatusEffect> COOKED_MUTTON_FOOD_EFFECT = Registry.registerReference(Registries.STATUS_EFFECT, FoodOverhaul.identifier("cooked_mutton_food_effect"), new FoodStatusEffect()
			.addAttributeModifier(EntityAttributes.GENERIC_MAX_HEALTH, FoodOverhaul.identifier("effect.cooked_mutton_food_effect"), 10.0F, EntityAttributeModifier.Operation.ADD_VALUE)
			.addAttributeModifier(HealthRegenerationOverhaul.HEALTH_REGENERATION, FoodOverhaul.identifier("effect.cooked_mutton_food_effect"), 1.0F, EntityAttributeModifier.Operation.ADD_VALUE)
			.addAttributeModifier(StaminaAttributes.MAX_STAMINA, FoodOverhaul.identifier("effect.cooked_mutton_food_effect"), 5.0F, EntityAttributeModifier.Operation.ADD_VALUE)
			.addAttributeModifier(StaminaAttributes.STAMINA_REGENERATION, FoodOverhaul.identifier("effect.cooked_mutton_food_effect"), 3.0F, EntityAttributeModifier.Operation.ADD_VALUE)
	);

	public static final RegistryEntry<StatusEffect> COOKED_PORKCHOP_FOOD_EFFECT = Registry.registerReference(Registries.STATUS_EFFECT, FoodOverhaul.identifier("cooked_porkchop_food_effect"), new FoodStatusEffect()
			.addAttributeModifier(EntityAttributes.GENERIC_MAX_HEALTH, FoodOverhaul.identifier("effect.cooked_porkchop_food_effect"), 10.0F, EntityAttributeModifier.Operation.ADD_VALUE)
			.addAttributeModifier(HealthRegenerationOverhaul.HEALTH_REGENERATION, FoodOverhaul.identifier("effect.cooked_porkchop_food_effect"), 1.0F, EntityAttributeModifier.Operation.ADD_VALUE)
			.addAttributeModifier(StaminaAttributes.MAX_STAMINA, FoodOverhaul.identifier("effect.cooked_porkchop_food_effect"), 5.0F, EntityAttributeModifier.Operation.ADD_VALUE)
			.addAttributeModifier(StaminaAttributes.STAMINA_REGENERATION, FoodOverhaul.identifier("effect.cooked_porkchop_food_effect"), 3.0F, EntityAttributeModifier.Operation.ADD_VALUE)
	);

	public static final RegistryEntry<StatusEffect> COOKED_RABBIT_FOOD_EFFECT = Registry.registerReference(Registries.STATUS_EFFECT, FoodOverhaul.identifier("cooked_rabbit_food_effect"), new FoodStatusEffect()
			.addAttributeModifier(EntityAttributes.GENERIC_MAX_HEALTH, FoodOverhaul.identifier("effect.cooked_rabbit_food_effect"), 10.0F, EntityAttributeModifier.Operation.ADD_VALUE)
			.addAttributeModifier(HealthRegenerationOverhaul.HEALTH_REGENERATION, FoodOverhaul.identifier("effect.cooked_rabbit_food_effect"), 1.0F, EntityAttributeModifier.Operation.ADD_VALUE)
			.addAttributeModifier(StaminaAttributes.MAX_STAMINA, FoodOverhaul.identifier("effect.cooked_rabbit_food_effect"), 5.0F, EntityAttributeModifier.Operation.ADD_VALUE)
			.addAttributeModifier(StaminaAttributes.STAMINA_REGENERATION, FoodOverhaul.identifier("effect.cooked_rabbit_food_effect"), 3.0F, EntityAttributeModifier.Operation.ADD_VALUE)
	);

	public static final RegistryEntry<StatusEffect> COOKED_SALMON_FOOD_EFFECT = Registry.registerReference(Registries.STATUS_EFFECT, FoodOverhaul.identifier("cooked_salmon_food_effect"), new FoodStatusEffect()
			.addAttributeModifier(EntityAttributes.GENERIC_MAX_HEALTH, FoodOverhaul.identifier("effect.cooked_salmon_food_effect"), 10.0F, EntityAttributeModifier.Operation.ADD_VALUE)
			.addAttributeModifier(HealthRegenerationOverhaul.HEALTH_REGENERATION, FoodOverhaul.identifier("effect.cooked_salmon_food_effect"), 1.0F, EntityAttributeModifier.Operation.ADD_VALUE)
			.addAttributeModifier(StaminaAttributes.MAX_STAMINA, FoodOverhaul.identifier("effect.cooked_salmon_food_effect"), 5.0F, EntityAttributeModifier.Operation.ADD_VALUE)
			.addAttributeModifier(StaminaAttributes.STAMINA_REGENERATION, FoodOverhaul.identifier("effect.cooked_salmon_food_effect"), 3.0F, EntityAttributeModifier.Operation.ADD_VALUE)
	);

	public static final RegistryEntry<StatusEffect> COOKIE_FOOD_EFFECT = Registry.registerReference(Registries.STATUS_EFFECT, FoodOverhaul.identifier("cookie_food_effect"), new FoodStatusEffect()
			.addAttributeModifier(EntityAttributes.GENERIC_MAX_HEALTH, FoodOverhaul.identifier("effect.cookie_food_effect"), 5.0F, EntityAttributeModifier.Operation.ADD_VALUE)
			.addAttributeModifier(HealthRegenerationOverhaul.HEALTH_REGENERATION, FoodOverhaul.identifier("effect.cookie_food_effect"), 3.0F, EntityAttributeModifier.Operation.ADD_VALUE)
			.addAttributeModifier(StaminaAttributes.MAX_STAMINA, FoodOverhaul.identifier("effect.cookie_food_effect"), 10.0F, EntityAttributeModifier.Operation.ADD_VALUE)
			.addAttributeModifier(StaminaAttributes.STAMINA_REGENERATION, FoodOverhaul.identifier("effect.cookie_food_effect"), 3.0F, EntityAttributeModifier.Operation.ADD_VALUE)
	);

	public static final RegistryEntry<StatusEffect> DRIED_KELP_FOOD_EFFECT = Registry.registerReference(Registries.STATUS_EFFECT, FoodOverhaul.identifier("dried_kelp_food_effect"), new FoodStatusEffect()
			.addAttributeModifier(EntityAttributes.GENERIC_MAX_HEALTH, FoodOverhaul.identifier("effect.dried_kelp_food_effect"), 1.0F, EntityAttributeModifier.Operation.ADD_VALUE)
			.addAttributeModifier(HealthRegenerationOverhaul.HEALTH_REGENERATION, FoodOverhaul.identifier("effect.dried_kelp_food_effect"), 3.0F, EntityAttributeModifier.Operation.ADD_VALUE)
			.addAttributeModifier(StaminaAttributes.STAMINA_REGENERATION_DELAY_THRESHOLD, FoodOverhaul.identifier("effect.dried_kelp_food_effect"), -30.0F, EntityAttributeModifier.Operation.ADD_VALUE)
			.addAttributeModifier(StaminaAttributes.STAMINA_TICK_THRESHOLD, FoodOverhaul.identifier("effect.dried_kelp_food_effect"), -10.0F, EntityAttributeModifier.Operation.ADD_VALUE)
	);

	public static final RegistryEntry<StatusEffect> ENCHANTED_GOLDEN_APPLE_FOOD_EFFECT = Registry.registerReference(Registries.STATUS_EFFECT, FoodOverhaul.identifier("enchanted_golden_apple_food_effect"), new FoodStatusEffect()
			.addAttributeModifier(EntityAttributes.GENERIC_MAX_HEALTH, FoodOverhaul.identifier("effect.enchanted_golden_apple_food_effect"), 15.0F, EntityAttributeModifier.Operation.ADD_VALUE)
			.addAttributeModifier(HealthRegenerationOverhaul.HEALTH_REGENERATION, FoodOverhaul.identifier("effect.enchanted_golden_apple_food_effect"), 3.0F, EntityAttributeModifier.Operation.ADD_VALUE)
			.addAttributeModifier(StaminaAttributes.MAX_STAMINA, FoodOverhaul.identifier("effect.enchanted_golden_apple_food_effect"), 30.0F, EntityAttributeModifier.Operation.ADD_VALUE)
			.addAttributeModifier(StaminaAttributes.STAMINA_REGENERATION, FoodOverhaul.identifier("effect.enchanted_golden_apple_food_effect"), 3.0F, EntityAttributeModifier.Operation.ADD_VALUE)
	);

	public static final RegistryEntry<StatusEffect> FERMENTED_SPIDER_EYE_FOOD_EFFECT = Registry.registerReference(Registries.STATUS_EFFECT, FoodOverhaul.identifier("fermented_spider_eye_food_effect"), new FoodStatusEffect()
//            .addAttributeModifier(EntityAttributes.GENERIC_MAX_HEALTH, FoodOverhaul.identifier("effect.fermented_spider_eye_food_effect"), 30.0F, EntityAttributeModifier.Operation.ADD_VALUE)
//            .addAttributeModifier(EntityAttributesRegistry.HEALTH_REGENERATION, FoodOverhaul.identifier("effect.fermented_spider_eye_food_effect"), 2.0F, EntityAttributeModifier.Operation.ADD_VALUE)
					.addAttributeModifier(ManaAttributes.MAX_MANA, FoodOverhaul.identifier("effect.fermented_spider_eye_food_effect"), 10.0F, EntityAttributeModifier.Operation.ADD_VALUE)
					.addAttributeModifier(ManaAttributes.MANA_REGENERATION, FoodOverhaul.identifier("effect.fermented_spider_eye_food_effect"), 1.0F, EntityAttributeModifier.Operation.ADD_VALUE)
	);

	public static final RegistryEntry<StatusEffect> GLOW_BERRIES_FOOD_EFFECT = Registry.registerReference(Registries.STATUS_EFFECT, FoodOverhaul.identifier("glow_berries_food_effect"), new FoodStatusEffect()
			.addAttributeModifier(EntityAttributes.GENERIC_MAX_HEALTH, FoodOverhaul.identifier("effect.glow_berries_food_effect"), 5.0F, EntityAttributeModifier.Operation.ADD_VALUE)
			.addAttributeModifier(StaminaAttributes.MAX_STAMINA, FoodOverhaul.identifier("effect.glow_berries_food_effect"), 5.0F, EntityAttributeModifier.Operation.ADD_VALUE)
			.addAttributeModifier(StaminaAttributes.STAMINA_REGENERATION, FoodOverhaul.identifier("effect.glow_berries_food_effect"), 1.0F, EntityAttributeModifier.Operation.ADD_VALUE)
			.addAttributeModifier(ManaAttributes.MAX_MANA, FoodOverhaul.identifier("effect.glow_berries_food_effect"), 10.0F, EntityAttributeModifier.Operation.ADD_VALUE)
			.addAttributeModifier(ManaAttributes.MANA_REGENERATION, FoodOverhaul.identifier("effect.glow_berries_food_effect"), 1.0F, EntityAttributeModifier.Operation.ADD_VALUE)
	);

	public static final RegistryEntry<StatusEffect> GOLDEN_APPLE_FOOD_EFFECT = Registry.registerReference(Registries.STATUS_EFFECT, FoodOverhaul.identifier("golden_apple_food_effect"), new FoodStatusEffect()
			.addAttributeModifier(EntityAttributes.GENERIC_MAX_HEALTH, FoodOverhaul.identifier("effect.golden_apple_food_effect"), 10.0F, EntityAttributeModifier.Operation.ADD_VALUE)
			.addAttributeModifier(HealthRegenerationOverhaul.HEALTH_REGENERATION, FoodOverhaul.identifier("effect.golden_apple_food_effect"), 3.0F, EntityAttributeModifier.Operation.ADD_VALUE)
			.addAttributeModifier(StaminaAttributes.MAX_STAMINA, FoodOverhaul.identifier("effect.golden_apple_food_effect"), 50.0F, EntityAttributeModifier.Operation.ADD_VALUE)
			.addAttributeModifier(StaminaAttributes.STAMINA_REGENERATION, FoodOverhaul.identifier("effect.golden_apple_food_effect"), 3.0F, EntityAttributeModifier.Operation.ADD_VALUE)
	);

	public static final RegistryEntry<StatusEffect> GOLDEN_CARROT_FOOD_EFFECT = Registry.registerReference(Registries.STATUS_EFFECT, FoodOverhaul.identifier("golden_carrot_food_effect"), new FoodStatusEffect()
			.addAttributeModifier(EntityAttributes.GENERIC_MAX_HEALTH, FoodOverhaul.identifier("effect.golden_carrot_food_effect"), 10.0F, EntityAttributeModifier.Operation.ADD_VALUE)
			.addAttributeModifier(HealthRegenerationOverhaul.HEALTH_REGENERATION, FoodOverhaul.identifier("effect.golden_carrot_food_effect"), 3.0F, EntityAttributeModifier.Operation.ADD_VALUE)
			.addAttributeModifier(StaminaAttributes.MAX_STAMINA, FoodOverhaul.identifier("effect.golden_carrot_food_effect"), 50.0F, EntityAttributeModifier.Operation.ADD_VALUE)
			.addAttributeModifier(StaminaAttributes.STAMINA_REGENERATION, FoodOverhaul.identifier("effect.golden_carrot_food_effect"), 3.0F, EntityAttributeModifier.Operation.ADD_VALUE)
	);

	public static final RegistryEntry<StatusEffect> HONEY_BOTTLE_FOOD_EFFECT = Registry.registerReference(Registries.STATUS_EFFECT, FoodOverhaul.identifier("honey_bottle_food_effect"), new FoodStatusEffect()
//            .addAttributeModifier(EntityAttributes.GENERIC_MAX_HEALTH, FoodOverhaul.identifier("effect.honey_bottle_food_effect"), 60.0F, EntityAttributeModifier.Operation.ADD_VALUE)
//            .addAttributeModifier(EntityAttributesRegistry.HEALTH_REGENERATION, FoodOverhaul.identifier("effect.honey_bottle_food_effect"), 3.0F, EntityAttributeModifier.Operation.ADD_VALUE)
					.addAttributeModifier(OverhauledDamage.MAX_POISON_BUILD_UP, FoodOverhaul.identifier("effect.honey_bottle_food_effect"), 10.0F, EntityAttributeModifier.Operation.ADD_VALUE)
					.addAttributeModifier(OverhauledDamage.POISON_BUILD_UP_REDUCTION, FoodOverhaul.identifier("effect.honey_bottle_food_effect"), 3.0F, EntityAttributeModifier.Operation.ADD_VALUE)
	);

	public static final RegistryEntry<StatusEffect> MELON_SLICE_FOOD_EFFECT = Registry.registerReference(Registries.STATUS_EFFECT, FoodOverhaul.identifier("melon_slice_food_effect"), new FoodStatusEffect()
			.addAttributeModifier(EntityAttributes.GENERIC_MAX_HEALTH, FoodOverhaul.identifier("effect.melon_slice_food_effect"), 3.0F, EntityAttributeModifier.Operation.ADD_VALUE)
			.addAttributeModifier(HealthRegenerationOverhaul.HEALTH_REGENERATION, FoodOverhaul.identifier("effect.melon_slice_food_effect"), 3.0F, EntityAttributeModifier.Operation.ADD_VALUE)
			.addAttributeModifier(StaminaAttributes.MAX_STAMINA, FoodOverhaul.identifier("effect.melon_slice_food_effect"), 3.0F, EntityAttributeModifier.Operation.ADD_VALUE)
			.addAttributeModifier(StaminaAttributes.STAMINA_REGENERATION, FoodOverhaul.identifier("effect.melon_slice_food_effect"), 3.0F, EntityAttributeModifier.Operation.ADD_VALUE)
	);

	public static final RegistryEntry<StatusEffect> MUSHROOM_STEW_FOOD_EFFECT = Registry.registerReference(Registries.STATUS_EFFECT, FoodOverhaul.identifier("mushroom_stew_food_effect"), new FoodStatusEffect()
			.addAttributeModifier(EntityAttributes.GENERIC_MAX_HEALTH, FoodOverhaul.identifier("effect.mushroom_stew_food_effect"), 10.0F, EntityAttributeModifier.Operation.ADD_VALUE)
			.addAttributeModifier(HealthRegenerationOverhaul.HEALTH_REGENERATION, FoodOverhaul.identifier("effect.mushroom_stew_food_effect"), 3.0F, EntityAttributeModifier.Operation.ADD_VALUE)
			.addAttributeModifier(StaminaAttributes.MAX_STAMINA, FoodOverhaul.identifier("effect.mushroom_stew_food_effect"), 15.0F, EntityAttributeModifier.Operation.ADD_VALUE)
			.addAttributeModifier(StaminaAttributes.STAMINA_REGENERATION, FoodOverhaul.identifier("effect.mushroom_stew_food_effect"), 3.0F, EntityAttributeModifier.Operation.ADD_VALUE)
	);

	public static final RegistryEntry<StatusEffect> MUTTON_FOOD_EFFECT = Registry.registerReference(Registries.STATUS_EFFECT, FoodOverhaul.identifier("mutton_food_effect"), new FoodStatusEffect()
			.addAttributeModifier(EntityAttributes.GENERIC_MAX_HEALTH, FoodOverhaul.identifier("effect.mutton_food_effect"), 5.0F, EntityAttributeModifier.Operation.ADD_VALUE)
			.addAttributeModifier(HealthRegenerationOverhaul.HEALTH_REGENERATION, FoodOverhaul.identifier("effect.mutton_food_effect"), 0.5F, EntityAttributeModifier.Operation.ADD_VALUE)
			.addAttributeModifier(StaminaAttributes.MAX_STAMINA, FoodOverhaul.identifier("effect.mutton_food_effect"), 5.0F, EntityAttributeModifier.Operation.ADD_VALUE)
			.addAttributeModifier(StaminaAttributes.STAMINA_REGENERATION, FoodOverhaul.identifier("effect.mutton_food_effect"), 3.0F, EntityAttributeModifier.Operation.ADD_VALUE)
	);

	public static final RegistryEntry<StatusEffect> POISONOUS_POTATO_FOOD_EFFECT = Registry.registerReference(Registries.STATUS_EFFECT, FoodOverhaul.identifier("poisonous_potato_food_effect"), new FoodStatusEffect()
//            .addAttributeModifier(EntityAttributes.GENERIC_MAX_HEALTH, FoodOverhaul.identifier("effect.poisonous_potato_food_effect"), 60.0F, EntityAttributeModifier.Operation.ADD_VALUE)
					.addAttributeModifier(OverhauledDamage.POISON_BUILD_UP_REDUCTION, FoodOverhaul.identifier("effect.poisonous_potato_food_effect"), -3.0F, EntityAttributeModifier.Operation.ADD_VALUE)
//            .addAttributeModifier(StaminaAttributes.MAX_STAMINA, FoodOverhaul.identifier("effect.poisonous_potato_food_effect"), 50.0F, EntityAttributeModifier.Operation.ADD_VALUE)
//            .addAttributeModifier(StaminaAttributes.STAMINA_REGENERATION, FoodOverhaul.identifier("effect.poisonous_potato_food_effect"), 3.0F, EntityAttributeModifier.Operation.ADD_VALUE)
	);

	public static final RegistryEntry<StatusEffect> PORKCHOP_FOOD_EFFECT = Registry.registerReference(Registries.STATUS_EFFECT, FoodOverhaul.identifier("porkchop_porkchop_food_effect"), new FoodStatusEffect()
					.addAttributeModifier(EntityAttributes.GENERIC_MAX_HEALTH, FoodOverhaul.identifier("effect.porkchop_porkchop_food_effect"), 5.0F, EntityAttributeModifier.Operation.ADD_VALUE)
					.addAttributeModifier(HealthRegenerationOverhaul.HEALTH_REGENERATION, FoodOverhaul.identifier("effect.porkchop_porkchop_food_effect"), 0.5F, EntityAttributeModifier.Operation.ADD_VALUE)
					.addAttributeModifier(OverhauledDamage.POISON_BUILD_UP_REDUCTION, FoodOverhaul.identifier("effect.porkchop_porkchop_food_effect"), -2.0F, EntityAttributeModifier.Operation.ADD_VALUE)
//            .addAttributeModifier(StaminaAttributes.MAX_STAMINA, FoodOverhaul.identifier("effect.porkchop_porkchop_food_effect"), 50.0F, EntityAttributeModifier.Operation.ADD_VALUE)
//            .addAttributeModifier(StaminaAttributes.STAMINA_REGENERATION, FoodOverhaul.identifier("effect.porkchop_porkchop_food_effect"), 3.0F, EntityAttributeModifier.Operation.ADD_VALUE)
	);

	public static final RegistryEntry<StatusEffect> POTATO_FOOD_EFFECT = Registry.registerReference(Registries.STATUS_EFFECT, FoodOverhaul.identifier("potato_food_effect"), new FoodStatusEffect()
			.addAttributeModifier(EntityAttributes.GENERIC_MAX_HEALTH, FoodOverhaul.identifier("effect.potato_food_effect"), 5.0F, EntityAttributeModifier.Operation.ADD_VALUE)
			.addAttributeModifier(HealthRegenerationOverhaul.HEALTH_REGENERATION, FoodOverhaul.identifier("effect.potato_food_effect"), 3.0F, EntityAttributeModifier.Operation.ADD_VALUE)
			.addAttributeModifier(StaminaAttributes.MAX_STAMINA, FoodOverhaul.identifier("effect.potato_food_effect"), 5.0F, EntityAttributeModifier.Operation.ADD_VALUE)
			.addAttributeModifier(StaminaAttributes.STAMINA_REGENERATION, FoodOverhaul.identifier("effect.potato_food_effect"), 3.0F, EntityAttributeModifier.Operation.ADD_VALUE)
	);

	public static final RegistryEntry<StatusEffect> PUFFERFISH_FOOD_EFFECT = Registry.registerReference(Registries.STATUS_EFFECT, FoodOverhaul.identifier("pufferfish_food_effect"), new FoodStatusEffect()
//            .addAttributeModifier(EntityAttributes.GENERIC_MAX_HEALTH, FoodOverhaul.identifier("effect.pufferfish_food_effect"), 60.0F, EntityAttributeModifier.Operation.ADD_VALUE)
					.addAttributeModifier(OverhauledDamage.POISON_BUILD_UP_REDUCTION, FoodOverhaul.identifier("effect.pufferfish_food_effect"), -5.0F, EntityAttributeModifier.Operation.ADD_VALUE)
//            .addAttributeModifier(StaminaAttributes.MAX_STAMINA, FoodOverhaul.identifier("effect.pufferfish_food_effect"), 50.0F, EntityAttributeModifier.Operation.ADD_VALUE)
//            .addAttributeModifier(StaminaAttributes.STAMINA_REGENERATION, FoodOverhaul.identifier("effect.pufferfish_food_effect"), 3.0F, EntityAttributeModifier.Operation.ADD_VALUE)
	);

	public static final RegistryEntry<StatusEffect> PUMPKIN_PIE_FOOD_EFFECT = Registry.registerReference(Registries.STATUS_EFFECT, FoodOverhaul.identifier("pumpkin_pie_food_effect"), new FoodStatusEffect()
			.addAttributeModifier(EntityAttributes.GENERIC_MAX_HEALTH, FoodOverhaul.identifier("effect.pumpkin_pie_food_effect"), 10.0F, EntityAttributeModifier.Operation.ADD_VALUE)
			.addAttributeModifier(HealthRegenerationOverhaul.HEALTH_REGENERATION, FoodOverhaul.identifier("effect.pumpkin_pie_food_effect"), 3.0F, EntityAttributeModifier.Operation.ADD_VALUE)
			.addAttributeModifier(StaminaAttributes.MAX_STAMINA, FoodOverhaul.identifier("effect.pumpkin_pie_food_effect"), 15.0F, EntityAttributeModifier.Operation.ADD_VALUE)
			.addAttributeModifier(StaminaAttributes.STAMINA_REGENERATION, FoodOverhaul.identifier("effect.pumpkin_pie_food_effect"), 3.0F, EntityAttributeModifier.Operation.ADD_VALUE)
	);

	public static final RegistryEntry<StatusEffect> RABBIT_FOOD_EFFECT = Registry.registerReference(Registries.STATUS_EFFECT, FoodOverhaul.identifier("rabbit_food_effect"), new FoodStatusEffect()
			.addAttributeModifier(EntityAttributes.GENERIC_MAX_HEALTH, FoodOverhaul.identifier("effect.rabbit_food_effect"), 5.0F, EntityAttributeModifier.Operation.ADD_VALUE)
			.addAttributeModifier(HealthRegenerationOverhaul.HEALTH_REGENERATION, FoodOverhaul.identifier("effect.rabbit_food_effect"), 0.5F, EntityAttributeModifier.Operation.ADD_VALUE)
			.addAttributeModifier(StaminaAttributes.MAX_STAMINA, FoodOverhaul.identifier("effect.rabbit_food_effect"), 20.0F, EntityAttributeModifier.Operation.ADD_VALUE)
			.addAttributeModifier(StaminaAttributes.STAMINA_REGENERATION, FoodOverhaul.identifier("effect.rabbit_food_effect"), 3.0F, EntityAttributeModifier.Operation.ADD_VALUE)
	);

	public static final RegistryEntry<StatusEffect> RABBIT_STEW_FOOD_EFFECT = Registry.registerReference(Registries.STATUS_EFFECT, FoodOverhaul.identifier("rabbit_stew_food_effect"), new FoodStatusEffect()
			.addAttributeModifier(EntityAttributes.GENERIC_MAX_HEALTH, FoodOverhaul.identifier("effect.rabbit_stew_food_effect"), 15.0F, EntityAttributeModifier.Operation.ADD_VALUE)
			.addAttributeModifier(HealthRegenerationOverhaul.HEALTH_REGENERATION, FoodOverhaul.identifier("effect.rabbit_stew_food_effect"), 1.0F, EntityAttributeModifier.Operation.ADD_VALUE)
			.addAttributeModifier(StaminaAttributes.MAX_STAMINA, FoodOverhaul.identifier("effect.rabbit_stew_food_effect"), 7.0F, EntityAttributeModifier.Operation.ADD_VALUE)
			.addAttributeModifier(StaminaAttributes.STAMINA_REGENERATION, FoodOverhaul.identifier("effect.rabbit_stew_food_effect"), 1.0F, EntityAttributeModifier.Operation.ADD_VALUE)
	);

	public static final RegistryEntry<StatusEffect> RED_MUSHROOM_FOOD_EFFECT = Registry.registerReference(Registries.STATUS_EFFECT, FoodOverhaul.identifier("red_mushroom_food_effect"), new FoodStatusEffect()
//            .addAttributeModifier(EntityAttributes.GENERIC_MAX_HEALTH, FoodOverhaul.identifier("effect.red_mushroom_food_effect"), 35.0F, EntityAttributeModifier.Operation.ADD_VALUE)
//            .addAttributeModifier(StaminaAttributes.MAX_STAMINA, FoodOverhaul.identifier("effect.red_mushroom_food_effect"), 35.0F, EntityAttributeModifier.Operation.ADD_VALUE)
//            .addAttributeModifier(EntityAttributesRegistry.MAX_MANA, FoodOverhaul.identifier("effect.red_mushroom_food_effect"), 35.0F, EntityAttributeModifier.Operation.ADD_VALUE)
					.addAttributeModifier(OverhauledDamage.POISON_BUILD_UP_REDUCTION, FoodOverhaul.identifier("effect.red_mushroom_food_effect"), -4.0F, EntityAttributeModifier.Operation.ADD_VALUE)
					.addAttributeModifier(ManaAttributes.MAX_MANA, FoodOverhaul.identifier("effect.red_mushroom_food_effect"), 5.0F, EntityAttributeModifier.Operation.ADD_VALUE)
	);

	public static final RegistryEntry<StatusEffect> ROTTEN_FLESH_FOOD_EFFECT = Registry.registerReference(Registries.STATUS_EFFECT, FoodOverhaul.identifier("rotten_flesh_food_effect"), new RemoveFoodStatusEffect());

	public static final RegistryEntry<StatusEffect> SALMON_FOOD_EFFECT = Registry.registerReference(Registries.STATUS_EFFECT, FoodOverhaul.identifier("salmon_food_effect"), new FoodStatusEffect()
					.addAttributeModifier(EntityAttributes.GENERIC_MAX_HEALTH, FoodOverhaul.identifier("effect.salmon_food_effect"), 5.0F, EntityAttributeModifier.Operation.ADD_VALUE)
					.addAttributeModifier(HealthRegenerationOverhaul.HEALTH_REGENERATION, FoodOverhaul.identifier("effect.salmon_food_effect"), 0.5F, EntityAttributeModifier.Operation.ADD_VALUE)
					.addAttributeModifier(OverhauledDamage.POISON_BUILD_UP_REDUCTION, FoodOverhaul.identifier("effect.salmon_food_effect"), -2.0F, EntityAttributeModifier.Operation.ADD_VALUE)
//            .addAttributeModifier(StaminaAttributes.MAX_STAMINA, FoodOverhaul.identifier("effect.salmon_food_effect"), 50.0F, EntityAttributeModifier.Operation.ADD_VALUE)
//            .addAttributeModifier(StaminaAttributes.STAMINA_REGENERATION, FoodOverhaul.identifier("effect.salmon_food_effect"), 3.0F, EntityAttributeModifier.Operation.ADD_VALUE)
	);

	public static final RegistryEntry<StatusEffect> SPIDER_EYE_FOOD_EFFECT = Registry.registerReference(Registries.STATUS_EFFECT, FoodOverhaul.identifier("spider_eye_food_effect"), new FoodStatusEffect()
			.addAttributeModifier(OverhauledDamage.POISON_BUILD_UP_REDUCTION, FoodOverhaul.identifier("effect.spider_eye_food_effect"), -3.0F, EntityAttributeModifier.Operation.ADD_VALUE)
	);

	public static final RegistryEntry<StatusEffect> SUGAR_FOOD_EFFECT = Registry.registerReference(Registries.STATUS_EFFECT, FoodOverhaul.identifier("sugar_food_effect"), new FoodStatusEffect()
			.addAttributeModifier(EntityAttributes.GENERIC_MAX_HEALTH, FoodOverhaul.identifier("effect.sugar_food_effect"), 5.0F, EntityAttributeModifier.Operation.ADD_VALUE)
			.addAttributeModifier(HealthRegenerationOverhaul.HEALTH_REGENERATION, FoodOverhaul.identifier("effect.sugar_food_effect"), 2.0F, EntityAttributeModifier.Operation.ADD_VALUE)
			.addAttributeModifier(StaminaAttributes.MAX_STAMINA, FoodOverhaul.identifier("effect.sugar_food_effect"), 25.0F, EntityAttributeModifier.Operation.ADD_VALUE)
			.addAttributeModifier(StaminaAttributes.STAMINA_REGENERATION, FoodOverhaul.identifier("effect.sugar_food_effect"), 4.0F, EntityAttributeModifier.Operation.ADD_VALUE)
	);

	public static final RegistryEntry<StatusEffect> SUSPICIOUS_STEW_FOOD_EFFECT = Registry.registerReference(Registries.STATUS_EFFECT, FoodOverhaul.identifier("suspicious_stew_food_effect"), new FoodStatusEffect()
			.addAttributeModifier(EntityAttributes.GENERIC_MAX_HEALTH, FoodOverhaul.identifier("effect.suspicious_stew_food_effect"), 5.0F, EntityAttributeModifier.Operation.ADD_VALUE)
			.addAttributeModifier(HealthRegenerationOverhaul.HEALTH_REGENERATION, FoodOverhaul.identifier("effect.suspicious_stew_food_effect"), 0.5F, EntityAttributeModifier.Operation.ADD_VALUE)
			.addAttributeModifier(StaminaAttributes.MAX_STAMINA, FoodOverhaul.identifier("effect.suspicious_stew_food_effect"), 5.0F, EntityAttributeModifier.Operation.ADD_VALUE)
			.addAttributeModifier(ManaAttributes.MAX_MANA, FoodOverhaul.identifier("effect.suspicious_stew_food_effect"), 5.0F, EntityAttributeModifier.Operation.ADD_VALUE)
	);

	public static final RegistryEntry<StatusEffect> SWEET_BERRIES_FOOD_EFFECT = Registry.registerReference(Registries.STATUS_EFFECT, FoodOverhaul.identifier("sweet_berries_food_effect"), new FoodStatusEffect()
			.addAttributeModifier(EntityAttributes.GENERIC_MAX_HEALTH, FoodOverhaul.identifier("effect.sweet_berries_food_effect"), 5.0F, EntityAttributeModifier.Operation.ADD_VALUE)
			.addAttributeModifier(HealthRegenerationOverhaul.HEALTH_REGENERATION, FoodOverhaul.identifier("effect.sweet_berries_food_effect"), 2.0F, EntityAttributeModifier.Operation.ADD_VALUE)
			.addAttributeModifier(StaminaAttributes.MAX_STAMINA, FoodOverhaul.identifier("effect.sweet_berries_food_effect"), 5.0F, EntityAttributeModifier.Operation.ADD_VALUE)
			.addAttributeModifier(StaminaAttributes.STAMINA_REGENERATION, FoodOverhaul.identifier("effect.sweet_berries_food_effect"), 1.0F, EntityAttributeModifier.Operation.ADD_VALUE)
	);

	public static final RegistryEntry<StatusEffect> TROPICAL_FISH_FOOD_EFFECT = Registry.registerReference(Registries.STATUS_EFFECT, FoodOverhaul.identifier("tropical_fish_food_effect"), new FoodStatusEffect()
					.addAttributeModifier(EntityAttributes.GENERIC_MAX_HEALTH, FoodOverhaul.identifier("effect.tropical_fish_food_effect"), 5.0F, EntityAttributeModifier.Operation.ADD_VALUE)
					.addAttributeModifier(HealthRegenerationOverhaul.HEALTH_REGENERATION, FoodOverhaul.identifier("effect.tropical_fish_food_effect"), 0.5F, EntityAttributeModifier.Operation.ADD_VALUE)
					.addAttributeModifier(OverhauledDamage.POISON_BUILD_UP_REDUCTION, FoodOverhaul.identifier("effect.tropical_fish_food_effect"), -2.0F, EntityAttributeModifier.Operation.ADD_VALUE)
//            .addAttributeModifier(StaminaAttributes.MAX_STAMINA, FoodOverhaul.identifier("effect.tropical_fish_food_effect"), 25.0F, EntityAttributeModifier.Operation.ADD_VALUE)
//            .addAttributeModifier(StaminaAttributes.STAMINA_REGENERATION, FoodOverhaul.identifier("effect.tropical_fish_food_effect"), 1.0F, EntityAttributeModifier.Operation.ADD_VALUE)
	);

	public static void init() {
	}
}
