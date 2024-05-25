package com.github.theredbrain.foodoverhaul.registry;

import com.github.theredbrain.foodoverhaul.FoodOverhaul;
import com.github.theredbrain.foodoverhaul.effect.FoodStatusEffect;
import com.github.theredbrain.foodoverhaul.effect.RemoveFoodStatusEffect;
import com.github.theredbrain.foodoverhaul.util.AttributeModifierUUIDs;
import com.github.theredbrain.manaattributes.ManaAttributes;
import com.github.theredbrain.overhauleddamage.OverhauledDamage;
import com.github.theredbrain.staminaattributes.StaminaAttributes;
import net.minecraft.entity.attribute.EntityAttributeModifier;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;

public class StatusEffectsRegistry {

    public static final StatusEffect APPLE_FOOD_EFFECT = new FoodStatusEffect()
            .addAttributeModifier(EntityAttributes.GENERIC_MAX_HEALTH, AttributeModifierUUIDs.APPLE_FOOD_EFFECT, 5.0F, EntityAttributeModifier.Operation.ADDITION)
            .addAttributeModifier(HealthRegenerationOverhaul.HEALTH_REGENERATION, AttributeModifierUUIDs.APPLE_FOOD_EFFECT, 1.0F, EntityAttributeModifier.Operation.ADDITION)
            .addAttributeModifier(StaminaAttributes.MAX_STAMINA, AttributeModifierUUIDs.APPLE_FOOD_EFFECT, 5.0F, EntityAttributeModifier.Operation.ADDITION)
            .addAttributeModifier(StaminaAttributes.STAMINA_REGENERATION, AttributeModifierUUIDs.APPLE_FOOD_EFFECT, 1.0F, EntityAttributeModifier.Operation.ADDITION)
            ;

    public static final StatusEffect BAKED_POTATO_FOOD_EFFECT = new FoodStatusEffect()
            .addAttributeModifier(EntityAttributes.GENERIC_MAX_HEALTH, AttributeModifierUUIDs.BAKED_POTATO_FOOD_EFFECT, 10.0F, EntityAttributeModifier.Operation.ADDITION)
            .addAttributeModifier(HealthRegenerationOverhaul.HEALTH_REGENERATION, AttributeModifierUUIDs.BAKED_POTATO_FOOD_EFFECT, 1.0F, EntityAttributeModifier.Operation.ADDITION)
            .addAttributeModifier(StaminaAttributes.MAX_STAMINA, AttributeModifierUUIDs.BAKED_POTATO_FOOD_EFFECT, 7.0F, EntityAttributeModifier.Operation.ADDITION)
            .addAttributeModifier(StaminaAttributes.STAMINA_REGENERATION, AttributeModifierUUIDs.BAKED_POTATO_FOOD_EFFECT, 1.0F, EntityAttributeModifier.Operation.ADDITION)
            ;

    public static final StatusEffect BEEF_FOOD_EFFECT = new FoodStatusEffect()
            .addAttributeModifier(EntityAttributes.GENERIC_MAX_HEALTH, AttributeModifierUUIDs.BEEF_FOOD_EFFECT, 5.0F, EntityAttributeModifier.Operation.ADDITION)
            .addAttributeModifier(HealthRegenerationOverhaul.HEALTH_REGENERATION, AttributeModifierUUIDs.BEEF_FOOD_EFFECT, 0.5F, EntityAttributeModifier.Operation.ADDITION)
            .addAttributeModifier(StaminaAttributes.MAX_STAMINA, AttributeModifierUUIDs.BEEF_FOOD_EFFECT, 2.0F, EntityAttributeModifier.Operation.ADDITION)
            .addAttributeModifier(StaminaAttributes.STAMINA_REGENERATION, AttributeModifierUUIDs.BEEF_FOOD_EFFECT, 2.0F, EntityAttributeModifier.Operation.ADDITION)
            ;

    public static final StatusEffect BEETROOT_FOOD_EFFECT = new FoodStatusEffect()
            .addAttributeModifier(EntityAttributes.GENERIC_MAX_HEALTH, AttributeModifierUUIDs.BEETROOT_FOOD_EFFECT, 1.0F, EntityAttributeModifier.Operation.ADDITION)
            .addAttributeModifier(HealthRegenerationOverhaul.HEALTH_REGENERATION, AttributeModifierUUIDs.BEETROOT_FOOD_EFFECT, 1.0F, EntityAttributeModifier.Operation.ADDITION)
            .addAttributeModifier(StaminaAttributes.MAX_STAMINA, AttributeModifierUUIDs.BEETROOT_FOOD_EFFECT, 5.0F, EntityAttributeModifier.Operation.ADDITION)
            .addAttributeModifier(StaminaAttributes.STAMINA_REGENERATION, AttributeModifierUUIDs.BEETROOT_FOOD_EFFECT, 2.0F, EntityAttributeModifier.Operation.ADDITION)
            ;

    public static final StatusEffect BEETROOT_SOUP_FOOD_EFFECT = new FoodStatusEffect()
            .addAttributeModifier(EntityAttributes.GENERIC_MAX_HEALTH, AttributeModifierUUIDs.BEETROOT_SOUP_FOOD_EFFECT, 15.0F, EntityAttributeModifier.Operation.ADDITION)
            .addAttributeModifier(HealthRegenerationOverhaul.HEALTH_REGENERATION, AttributeModifierUUIDs.BEETROOT_SOUP_FOOD_EFFECT, 1.0F, EntityAttributeModifier.Operation.ADDITION)
            .addAttributeModifier(StaminaAttributes.MAX_STAMINA, AttributeModifierUUIDs.BEETROOT_SOUP_FOOD_EFFECT, 10.0F, EntityAttributeModifier.Operation.ADDITION)
            .addAttributeModifier(StaminaAttributes.STAMINA_REGENERATION, AttributeModifierUUIDs.BEETROOT_SOUP_FOOD_EFFECT, 2.0F, EntityAttributeModifier.Operation.ADDITION)
            .addAttributeModifier(OverhauledDamage.FROST_RESISTANCE, AttributeModifierUUIDs.BEETROOT_SOUP_FOOD_EFFECT, 5.0F, EntityAttributeModifier.Operation.ADDITION)
            ;

    public static final StatusEffect BREAD_FOOD_EFFECT = new FoodStatusEffect()
            .addAttributeModifier(EntityAttributes.GENERIC_MAX_HEALTH, AttributeModifierUUIDs.BREAD_FOOD_EFFECT, 10.0F, EntityAttributeModifier.Operation.ADDITION)
            .addAttributeModifier(HealthRegenerationOverhaul.HEALTH_REGENERATION, AttributeModifierUUIDs.BREAD_FOOD_EFFECT, 1.0F, EntityAttributeModifier.Operation.ADDITION)
            .addAttributeModifier(StaminaAttributes.MAX_STAMINA, AttributeModifierUUIDs.BREAD_FOOD_EFFECT, 10.0F, EntityAttributeModifier.Operation.ADDITION)
            .addAttributeModifier(StaminaAttributes.STAMINA_REGENERATION, AttributeModifierUUIDs.BREAD_FOOD_EFFECT, 2.0F, EntityAttributeModifier.Operation.ADDITION)
            ;

    public static final StatusEffect BROWN_MUSHROOM_FOOD_EFFECT = new FoodStatusEffect()
            .addAttributeModifier(EntityAttributes.GENERIC_MAX_HEALTH, AttributeModifierUUIDs.BROWN_MUSHROOM_FOOD_EFFECT, 5.0F, EntityAttributeModifier.Operation.ADDITION)
            .addAttributeModifier(HealthRegenerationOverhaul.HEALTH_REGENERATION, AttributeModifierUUIDs.BROWN_MUSHROOM_FOOD_EFFECT, 1.0F, EntityAttributeModifier.Operation.ADDITION)
            .addAttributeModifier(StaminaAttributes.MAX_STAMINA, AttributeModifierUUIDs.BROWN_MUSHROOM_FOOD_EFFECT, 3.0F, EntityAttributeModifier.Operation.ADDITION)
            .addAttributeModifier(StaminaAttributes.STAMINA_REGENERATION, AttributeModifierUUIDs.BROWN_MUSHROOM_FOOD_EFFECT, 2.0F, EntityAttributeModifier.Operation.ADDITION)
            ;

    public static final StatusEffect CAKE_FOOD_EFFECT = new FoodStatusEffect()
            .addAttributeModifier(EntityAttributes.GENERIC_MAX_HEALTH, AttributeModifierUUIDs.CAKE_FOOD_EFFECT, 10.0F, EntityAttributeModifier.Operation.ADDITION)
            .addAttributeModifier(HealthRegenerationOverhaul.HEALTH_REGENERATION, AttributeModifierUUIDs.CAKE_FOOD_EFFECT, 2.0F, EntityAttributeModifier.Operation.ADDITION)
            .addAttributeModifier(StaminaAttributes.MAX_STAMINA, AttributeModifierUUIDs.CAKE_FOOD_EFFECT, 25.0F, EntityAttributeModifier.Operation.ADDITION)
            .addAttributeModifier(StaminaAttributes.STAMINA_REGENERATION, AttributeModifierUUIDs.CAKE_FOOD_EFFECT, 1.0F, EntityAttributeModifier.Operation.ADDITION)
            ;

    public static final StatusEffect CARROT_FOOD_EFFECT = new FoodStatusEffect()
            .addAttributeModifier(EntityAttributes.GENERIC_MAX_HEALTH, AttributeModifierUUIDs.CARROT_FOOD_EFFECT, 5.0F, EntityAttributeModifier.Operation.ADDITION)
            .addAttributeModifier(HealthRegenerationOverhaul.HEALTH_REGENERATION, AttributeModifierUUIDs.CARROT_FOOD_EFFECT, 1.0F, EntityAttributeModifier.Operation.ADDITION)
            .addAttributeModifier(StaminaAttributes.MAX_STAMINA, AttributeModifierUUIDs.CARROT_FOOD_EFFECT, 5.0F, EntityAttributeModifier.Operation.ADDITION)
            .addAttributeModifier(StaminaAttributes.STAMINA_REGENERATION, AttributeModifierUUIDs.CARROT_FOOD_EFFECT, 1.0F, EntityAttributeModifier.Operation.ADDITION)
            ;

    public static final StatusEffect CHICKEN_FOOD_EFFECT = new FoodStatusEffect()
            .addAttributeModifier(EntityAttributes.GENERIC_MAX_HEALTH, AttributeModifierUUIDs.CHICKEN_FOOD_EFFECT, 5.0F, EntityAttributeModifier.Operation.ADDITION)
            .addAttributeModifier(HealthRegenerationOverhaul.HEALTH_REGENERATION, AttributeModifierUUIDs.CHICKEN_FOOD_EFFECT, 0.5F, EntityAttributeModifier.Operation.ADDITION)
            .addAttributeModifier(OverhauledDamage.POISON_BUILD_UP_REDUCTION, AttributeModifierUUIDs.CHICKEN_FOOD_EFFECT, -2.0F, EntityAttributeModifier.Operation.ADDITION)
            .addAttributeModifier(StaminaAttributes.STAMINA_REGENERATION, AttributeModifierUUIDs.CHICKEN_FOOD_EFFECT, 2.0F, EntityAttributeModifier.Operation.ADDITION)
            ;

    public static final StatusEffect CHORUS_FRUIT_FOOD_EFFECT = new FoodStatusEffect()
            .addAttributeModifier(EntityAttributes.GENERIC_MAX_HEALTH, AttributeModifierUUIDs.CHORUS_FRUIT_FOOD_EFFECT, 1.0F, EntityAttributeModifier.Operation.ADDITION)
            .addAttributeModifier(HealthRegenerationOverhaul.HEALTH_REGENERATION, AttributeModifierUUIDs.CHORUS_FRUIT_FOOD_EFFECT, 1.0F, EntityAttributeModifier.Operation.ADDITION)
            .addAttributeModifier(StaminaAttributes.MAX_STAMINA, AttributeModifierUUIDs.CHORUS_FRUIT_FOOD_EFFECT, 2.0F, EntityAttributeModifier.Operation.ADDITION)
            .addAttributeModifier(StaminaAttributes.STAMINA_REGENERATION, AttributeModifierUUIDs.CHORUS_FRUIT_FOOD_EFFECT, 2.0F, EntityAttributeModifier.Operation.ADDITION)
            ;

    public static final StatusEffect COCOA_BEANS_FOOD_EFFECT = new FoodStatusEffect()
            .addAttributeModifier(EntityAttributes.GENERIC_MAX_HEALTH, AttributeModifierUUIDs.COCOA_BEANS_FOOD_EFFECT, 5.0F, EntityAttributeModifier.Operation.ADDITION)
            .addAttributeModifier(HealthRegenerationOverhaul.HEALTH_REGENERATION, AttributeModifierUUIDs.COCOA_BEANS_FOOD_EFFECT, 2.0F, EntityAttributeModifier.Operation.ADDITION)
            .addAttributeModifier(StaminaAttributes.MAX_STAMINA, AttributeModifierUUIDs.COCOA_BEANS_FOOD_EFFECT, 3.0F, EntityAttributeModifier.Operation.ADDITION)
            .addAttributeModifier(StaminaAttributes.STAMINA_REGENERATION, AttributeModifierUUIDs.COCOA_BEANS_FOOD_EFFECT, 1.0F, EntityAttributeModifier.Operation.ADDITION)
            ;

    public static final StatusEffect COD_FOOD_EFFECT = new FoodStatusEffect()
            .addAttributeModifier(EntityAttributes.GENERIC_MAX_HEALTH, AttributeModifierUUIDs.COD_FOOD_EFFECT, 5.0F, EntityAttributeModifier.Operation.ADDITION)
            .addAttributeModifier(HealthRegenerationOverhaul.HEALTH_REGENERATION, AttributeModifierUUIDs.COD_FOOD_EFFECT, 0.5F, EntityAttributeModifier.Operation.ADDITION)
            .addAttributeModifier(OverhauledDamage.POISON_BUILD_UP_REDUCTION, AttributeModifierUUIDs.COD_FOOD_EFFECT, -2.0F, EntityAttributeModifier.Operation.ADDITION)
            .addAttributeModifier(StaminaAttributes.MAX_STAMINA, AttributeModifierUUIDs.COD_FOOD_EFFECT, 2.0F, EntityAttributeModifier.Operation.ADDITION)
            .addAttributeModifier(StaminaAttributes.STAMINA_REGENERATION, AttributeModifierUUIDs.COD_FOOD_EFFECT, 2.0F, EntityAttributeModifier.Operation.ADDITION)
            ;

    public static final StatusEffect COOKED_BEEF_FOOD_EFFECT = new FoodStatusEffect()
            .addAttributeModifier(EntityAttributes.GENERIC_MAX_HEALTH, AttributeModifierUUIDs.COOKED_BEEF_FOOD_EFFECT, 10.0F, EntityAttributeModifier.Operation.ADDITION)
            .addAttributeModifier(HealthRegenerationOverhaul.HEALTH_REGENERATION, AttributeModifierUUIDs.COOKED_BEEF_FOOD_EFFECT, 1.0F, EntityAttributeModifier.Operation.ADDITION)
            .addAttributeModifier(StaminaAttributes.MAX_STAMINA, AttributeModifierUUIDs.COOKED_BEEF_FOOD_EFFECT, 4.0F, EntityAttributeModifier.Operation.ADDITION)
            .addAttributeModifier(StaminaAttributes.STAMINA_REGENERATION, AttributeModifierUUIDs.COOKED_BEEF_FOOD_EFFECT, 2.0F, EntityAttributeModifier.Operation.ADDITION)
            ;

    public static final StatusEffect COOKED_CHICKEN_FOOD_EFFECT = new FoodStatusEffect()
            .addAttributeModifier(EntityAttributes.GENERIC_MAX_HEALTH, AttributeModifierUUIDs.COOKED_CHICKEN_FOOD_EFFECT, 10.0F, EntityAttributeModifier.Operation.ADDITION)
            .addAttributeModifier(HealthRegenerationOverhaul.HEALTH_REGENERATION, AttributeModifierUUIDs.COOKED_CHICKEN_FOOD_EFFECT, 1.0F, EntityAttributeModifier.Operation.ADDITION)
            .addAttributeModifier(StaminaAttributes.MAX_STAMINA, AttributeModifierUUIDs.COOKED_CHICKEN_FOOD_EFFECT, 50.0F, EntityAttributeModifier.Operation.ADDITION)
            .addAttributeModifier(StaminaAttributes.STAMINA_REGENERATION, AttributeModifierUUIDs.COOKED_CHICKEN_FOOD_EFFECT, 3.0F, EntityAttributeModifier.Operation.ADDITION)
            ;

    public static final StatusEffect COOKED_COD_FOOD_EFFECT = new FoodStatusEffect()
            .addAttributeModifier(EntityAttributes.GENERIC_MAX_HEALTH, AttributeModifierUUIDs.COOKED_COD_FOOD_EFFECT, 10.0F, EntityAttributeModifier.Operation.ADDITION)
            .addAttributeModifier(HealthRegenerationOverhaul.HEALTH_REGENERATION, AttributeModifierUUIDs.COOKED_COD_FOOD_EFFECT, 1.0F, EntityAttributeModifier.Operation.ADDITION)
            .addAttributeModifier(StaminaAttributes.MAX_STAMINA, AttributeModifierUUIDs.COOKED_COD_FOOD_EFFECT, 50.0F, EntityAttributeModifier.Operation.ADDITION)
            .addAttributeModifier(StaminaAttributes.STAMINA_REGENERATION, AttributeModifierUUIDs.COOKED_COD_FOOD_EFFECT, 3.0F, EntityAttributeModifier.Operation.ADDITION)
            ;

    public static final StatusEffect COOKED_MUTTON_FOOD_EFFECT = new FoodStatusEffect()
            .addAttributeModifier(EntityAttributes.GENERIC_MAX_HEALTH, AttributeModifierUUIDs.COOKED_MUTTON_FOOD_EFFECT, 10.0F, EntityAttributeModifier.Operation.ADDITION)
            .addAttributeModifier(HealthRegenerationOverhaul.HEALTH_REGENERATION, AttributeModifierUUIDs.COOKED_MUTTON_FOOD_EFFECT, 1.0F, EntityAttributeModifier.Operation.ADDITION)
            .addAttributeModifier(StaminaAttributes.MAX_STAMINA, AttributeModifierUUIDs.COOKED_MUTTON_FOOD_EFFECT, 50.0F, EntityAttributeModifier.Operation.ADDITION)
            .addAttributeModifier(StaminaAttributes.STAMINA_REGENERATION, AttributeModifierUUIDs.COOKED_MUTTON_FOOD_EFFECT, 3.0F, EntityAttributeModifier.Operation.ADDITION)
            ;

    public static final StatusEffect COOKED_PORKCHOP_FOOD_EFFECT = new FoodStatusEffect()
            .addAttributeModifier(EntityAttributes.GENERIC_MAX_HEALTH, AttributeModifierUUIDs.COOKED_PORKCHOP_FOOD_EFFECT, 10.0F, EntityAttributeModifier.Operation.ADDITION)
            .addAttributeModifier(HealthRegenerationOverhaul.HEALTH_REGENERATION, AttributeModifierUUIDs.COOKED_PORKCHOP_FOOD_EFFECT, 1.0F, EntityAttributeModifier.Operation.ADDITION)
            .addAttributeModifier(StaminaAttributes.MAX_STAMINA, AttributeModifierUUIDs.COOKED_PORKCHOP_FOOD_EFFECT, 50.0F, EntityAttributeModifier.Operation.ADDITION)
            .addAttributeModifier(StaminaAttributes.STAMINA_REGENERATION, AttributeModifierUUIDs.COOKED_PORKCHOP_FOOD_EFFECT, 3.0F, EntityAttributeModifier.Operation.ADDITION)
            ;

    public static final StatusEffect COOKED_RABBIT_FOOD_EFFECT = new FoodStatusEffect()
            .addAttributeModifier(EntityAttributes.GENERIC_MAX_HEALTH, AttributeModifierUUIDs.COOKED_RABBIT_FOOD_EFFECT, 10.0F, EntityAttributeModifier.Operation.ADDITION)
            .addAttributeModifier(HealthRegenerationOverhaul.HEALTH_REGENERATION, AttributeModifierUUIDs.COOKED_RABBIT_FOOD_EFFECT, 1.0F, EntityAttributeModifier.Operation.ADDITION)
            .addAttributeModifier(StaminaAttributes.MAX_STAMINA, AttributeModifierUUIDs.COOKED_RABBIT_FOOD_EFFECT, 50.0F, EntityAttributeModifier.Operation.ADDITION)
            .addAttributeModifier(StaminaAttributes.STAMINA_REGENERATION, AttributeModifierUUIDs.COOKED_RABBIT_FOOD_EFFECT, 3.0F, EntityAttributeModifier.Operation.ADDITION)
            ;

    public static final StatusEffect COOKED_SALMON_FOOD_EFFECT = new FoodStatusEffect()
            .addAttributeModifier(EntityAttributes.GENERIC_MAX_HEALTH, AttributeModifierUUIDs.COOKED_SALMON_FOOD_EFFECT, 10.0F, EntityAttributeModifier.Operation.ADDITION)
            .addAttributeModifier(HealthRegenerationOverhaul.HEALTH_REGENERATION, AttributeModifierUUIDs.COOKED_SALMON_FOOD_EFFECT, 1.0F, EntityAttributeModifier.Operation.ADDITION)
            .addAttributeModifier(StaminaAttributes.MAX_STAMINA, AttributeModifierUUIDs.COOKED_SALMON_FOOD_EFFECT, 50.0F, EntityAttributeModifier.Operation.ADDITION)
            .addAttributeModifier(StaminaAttributes.STAMINA_REGENERATION, AttributeModifierUUIDs.COOKED_SALMON_FOOD_EFFECT, 3.0F, EntityAttributeModifier.Operation.ADDITION)
            ;

    public static final StatusEffect COOKIE_FOOD_EFFECT = new FoodStatusEffect()
            .addAttributeModifier(EntityAttributes.GENERIC_MAX_HEALTH, AttributeModifierUUIDs.COOKIE_FOOD_EFFECT, 5.0F, EntityAttributeModifier.Operation.ADDITION)
            .addAttributeModifier(HealthRegenerationOverhaul.HEALTH_REGENERATION, AttributeModifierUUIDs.COOKIE_FOOD_EFFECT, 3.0F, EntityAttributeModifier.Operation.ADDITION)
            .addAttributeModifier(StaminaAttributes.MAX_STAMINA, AttributeModifierUUIDs.COOKIE_FOOD_EFFECT, 10.0F, EntityAttributeModifier.Operation.ADDITION)
            .addAttributeModifier(StaminaAttributes.STAMINA_REGENERATION, AttributeModifierUUIDs.COOKIE_FOOD_EFFECT, 3.0F, EntityAttributeModifier.Operation.ADDITION)
            ;

    public static final StatusEffect DRIED_KELP_FOOD_EFFECT = new FoodStatusEffect()
            .addAttributeModifier(EntityAttributes.GENERIC_MAX_HEALTH, AttributeModifierUUIDs.DRIED_KELP_FOOD_EFFECT, 1.0F, EntityAttributeModifier.Operation.ADDITION)
            .addAttributeModifier(HealthRegenerationOverhaul.HEALTH_REGENERATION, AttributeModifierUUIDs.DRIED_KELP_FOOD_EFFECT, 3.0F, EntityAttributeModifier.Operation.ADDITION)
            .addAttributeModifier(StaminaAttributes.STAMINA_REGENERATION_DELAY_THRESHOLD, AttributeModifierUUIDs.DRIED_KELP_FOOD_EFFECT, -30.0F, EntityAttributeModifier.Operation.ADDITION)
            .addAttributeModifier(StaminaAttributes.STAMINA_TICK_THRESHOLD, AttributeModifierUUIDs.DRIED_KELP_FOOD_EFFECT, -10.0F, EntityAttributeModifier.Operation.ADDITION)
            ;

    public static final StatusEffect ENCHANTED_GOLDEN_APPLE_FOOD_EFFECT = new FoodStatusEffect()
            .addAttributeModifier(EntityAttributes.GENERIC_MAX_HEALTH, AttributeModifierUUIDs.ENCHANTED_GOLDEN_APPLE_FOOD_EFFECT, 15.0F, EntityAttributeModifier.Operation.ADDITION)
            .addAttributeModifier(HealthRegenerationOverhaul.HEALTH_REGENERATION, AttributeModifierUUIDs.ENCHANTED_GOLDEN_APPLE_FOOD_EFFECT, 3.0F, EntityAttributeModifier.Operation.ADDITION)
            .addAttributeModifier(StaminaAttributes.MAX_STAMINA, AttributeModifierUUIDs.ENCHANTED_GOLDEN_APPLE_FOOD_EFFECT, 30.0F, EntityAttributeModifier.Operation.ADDITION)
            .addAttributeModifier(StaminaAttributes.STAMINA_REGENERATION, AttributeModifierUUIDs.ENCHANTED_GOLDEN_APPLE_FOOD_EFFECT, 3.0F, EntityAttributeModifier.Operation.ADDITION)
            ;

    public static final StatusEffect FERMENTED_SPIDER_EYE_FOOD_EFFECT = new FoodStatusEffect()
//            .addAttributeModifier(EntityAttributes.GENERIC_MAX_HEALTH, AttributeModifierUUIDs.FERMENTED_SPIDER_EYE_FOOD_EFFECT, 30.0F, EntityAttributeModifier.Operation.ADDITION)
//            .addAttributeModifier(EntityAttributesRegistry.HEALTH_REGENERATION, AttributeModifierUUIDs.FERMENTED_SPIDER_EYE_FOOD_EFFECT, 2.0F, EntityAttributeModifier.Operation.ADDITION)
            .addAttributeModifier(ManaAttributes.MAX_MANA, AttributeModifierUUIDs.FERMENTED_SPIDER_EYE_FOOD_EFFECT, 10.0F, EntityAttributeModifier.Operation.ADDITION)
            .addAttributeModifier(ManaAttributes.MANA_REGENERATION, AttributeModifierUUIDs.FERMENTED_SPIDER_EYE_FOOD_EFFECT, 1.0F, EntityAttributeModifier.Operation.ADDITION);

    public static final StatusEffect GLOW_BERRIES_FOOD_EFFECT = new FoodStatusEffect()
            .addAttributeModifier(EntityAttributes.GENERIC_MAX_HEALTH, AttributeModifierUUIDs.GLOW_BERRIES_FOOD_EFFECT, 5.0F, EntityAttributeModifier.Operation.ADDITION)
            .addAttributeModifier(StaminaAttributes.MAX_STAMINA, AttributeModifierUUIDs.GLOW_BERRIES_FOOD_EFFECT, 5.0F, EntityAttributeModifier.Operation.ADDITION)
            .addAttributeModifier(StaminaAttributes.STAMINA_REGENERATION, AttributeModifierUUIDs.GLOW_BERRIES_FOOD_EFFECT, 1.0F, EntityAttributeModifier.Operation.ADDITION)
            ;

    public static final StatusEffect GOLDEN_APPLE_FOOD_EFFECT = new FoodStatusEffect()
            .addAttributeModifier(EntityAttributes.GENERIC_MAX_HEALTH, AttributeModifierUUIDs.GOLDEN_APPLE_FOOD_EFFECT, 10.0F, EntityAttributeModifier.Operation.ADDITION)
            .addAttributeModifier(HealthRegenerationOverhaul.HEALTH_REGENERATION, AttributeModifierUUIDs.GOLDEN_APPLE_FOOD_EFFECT, 3.0F, EntityAttributeModifier.Operation.ADDITION)
            .addAttributeModifier(StaminaAttributes.MAX_STAMINA, AttributeModifierUUIDs.GOLDEN_APPLE_FOOD_EFFECT, 50.0F, EntityAttributeModifier.Operation.ADDITION)
            .addAttributeModifier(StaminaAttributes.STAMINA_REGENERATION, AttributeModifierUUIDs.GOLDEN_APPLE_FOOD_EFFECT, 3.0F, EntityAttributeModifier.Operation.ADDITION)
            ;

    public static final StatusEffect GOLDEN_CARROT_FOOD_EFFECT = new FoodStatusEffect()
            .addAttributeModifier(EntityAttributes.GENERIC_MAX_HEALTH, AttributeModifierUUIDs.GOLDEN_CARROT_FOOD_EFFECT, 10.0F, EntityAttributeModifier.Operation.ADDITION)
            .addAttributeModifier(HealthRegenerationOverhaul.HEALTH_REGENERATION, AttributeModifierUUIDs.GOLDEN_CARROT_FOOD_EFFECT, 3.0F, EntityAttributeModifier.Operation.ADDITION)
            .addAttributeModifier(StaminaAttributes.MAX_STAMINA, AttributeModifierUUIDs.GOLDEN_CARROT_FOOD_EFFECT, 50.0F, EntityAttributeModifier.Operation.ADDITION)
            .addAttributeModifier(StaminaAttributes.STAMINA_REGENERATION, AttributeModifierUUIDs.GOLDEN_CARROT_FOOD_EFFECT, 3.0F, EntityAttributeModifier.Operation.ADDITION)
            ;

    public static final StatusEffect HONEY_BOTTLE_FOOD_EFFECT = new FoodStatusEffect()
//            .addAttributeModifier(EntityAttributes.GENERIC_MAX_HEALTH, AttributeModifierUUIDs.HONEY_BOTTLE_FOOD_EFFECT, 60.0F, EntityAttributeModifier.Operation.ADDITION)
//            .addAttributeModifier(EntityAttributesRegistry.HEALTH_REGENERATION, AttributeModifierUUIDs.HONEY_BOTTLE_FOOD_EFFECT, 3.0F, EntityAttributeModifier.Operation.ADDITION)
            .addAttributeModifier(OverhauledDamage.MAX_POISON_BUILD_UP, AttributeModifierUUIDs.HONEY_BOTTLE_FOOD_EFFECT, 10.0F, EntityAttributeModifier.Operation.ADDITION)
            .addAttributeModifier(OverhauledDamage.POISON_BUILD_UP_REDUCTION, AttributeModifierUUIDs.HONEY_BOTTLE_FOOD_EFFECT, 3.0F, EntityAttributeModifier.Operation.ADDITION)
            ;

    public static final StatusEffect MELON_SLICE_FOOD_EFFECT = new FoodStatusEffect()
            .addAttributeModifier(EntityAttributes.GENERIC_MAX_HEALTH, AttributeModifierUUIDs.MELON_SLICE_FOOD_EFFECT, 3.0F, EntityAttributeModifier.Operation.ADDITION)
            .addAttributeModifier(HealthRegenerationOverhaul.HEALTH_REGENERATION, AttributeModifierUUIDs.MELON_SLICE_FOOD_EFFECT, 3.0F, EntityAttributeModifier.Operation.ADDITION)
            .addAttributeModifier(StaminaAttributes.MAX_STAMINA, AttributeModifierUUIDs.MELON_SLICE_FOOD_EFFECT, 3.0F, EntityAttributeModifier.Operation.ADDITION)
            .addAttributeModifier(StaminaAttributes.STAMINA_REGENERATION, AttributeModifierUUIDs.MELON_SLICE_FOOD_EFFECT, 3.0F, EntityAttributeModifier.Operation.ADDITION)
            ;

    public static final StatusEffect MUSHROOM_STEW_FOOD_EFFECT = new FoodStatusEffect()
            .addAttributeModifier(EntityAttributes.GENERIC_MAX_HEALTH, AttributeModifierUUIDs.MUSHROOM_STEW_FOOD_EFFECT, 10.0F, EntityAttributeModifier.Operation.ADDITION)
            .addAttributeModifier(HealthRegenerationOverhaul.HEALTH_REGENERATION, AttributeModifierUUIDs.MUSHROOM_STEW_FOOD_EFFECT, 3.0F, EntityAttributeModifier.Operation.ADDITION)
            .addAttributeModifier(StaminaAttributes.MAX_STAMINA, AttributeModifierUUIDs.MUSHROOM_STEW_FOOD_EFFECT, 50.0F, EntityAttributeModifier.Operation.ADDITION)
            .addAttributeModifier(StaminaAttributes.STAMINA_REGENERATION, AttributeModifierUUIDs.MUSHROOM_STEW_FOOD_EFFECT, 3.0F, EntityAttributeModifier.Operation.ADDITION)
            ;

    public static final StatusEffect MUTTON_FOOD_EFFECT = new FoodStatusEffect()
            .addAttributeModifier(EntityAttributes.GENERIC_MAX_HEALTH, AttributeModifierUUIDs.MUTTON_FOOD_EFFECT, 5.0F, EntityAttributeModifier.Operation.ADDITION)
            .addAttributeModifier(HealthRegenerationOverhaul.HEALTH_REGENERATION, AttributeModifierUUIDs.MUTTON_FOOD_EFFECT, 0.5F, EntityAttributeModifier.Operation.ADDITION)
            .addAttributeModifier(StaminaAttributes.MAX_STAMINA, AttributeModifierUUIDs.MUTTON_FOOD_EFFECT, 50.0F, EntityAttributeModifier.Operation.ADDITION)
            .addAttributeModifier(StaminaAttributes.STAMINA_REGENERATION, AttributeModifierUUIDs.MUTTON_FOOD_EFFECT, 3.0F, EntityAttributeModifier.Operation.ADDITION)
            ;

    public static final StatusEffect POISONOUS_POTATO_FOOD_EFFECT = new FoodStatusEffect()
//            .addAttributeModifier(EntityAttributes.GENERIC_MAX_HEALTH, AttributeModifierUUIDs.POISONOUS_POTATO_FOOD_EFFECT, 60.0F, EntityAttributeModifier.Operation.ADDITION)
            .addAttributeModifier(OverhauledDamage.POISON_BUILD_UP_REDUCTION, AttributeModifierUUIDs.POISONOUS_POTATO_FOOD_EFFECT, -3.0F, EntityAttributeModifier.Operation.ADDITION)
//            .addAttributeModifier(StaminaAttributes.MAX_STAMINA, AttributeModifierUUIDs.POISONOUS_POTATO_FOOD_EFFECT, 50.0F, EntityAttributeModifier.Operation.ADDITION)
//            .addAttributeModifier(StaminaAttributes.STAMINA_REGENERATION, AttributeModifierUUIDs.POISONOUS_POTATO_FOOD_EFFECT, 3.0F, EntityAttributeModifier.Operation.ADDITION)
            ;

    public static final StatusEffect PORKCHOP_FOOD_EFFECT = new FoodStatusEffect()
            .addAttributeModifier(EntityAttributes.GENERIC_MAX_HEALTH, AttributeModifierUUIDs.PORKCHOP_FOOD_EFFECT, 5.0F, EntityAttributeModifier.Operation.ADDITION)
            .addAttributeModifier(HealthRegenerationOverhaul.HEALTH_REGENERATION, AttributeModifierUUIDs.PORKCHOP_FOOD_EFFECT, 0.5F, EntityAttributeModifier.Operation.ADDITION)
            .addAttributeModifier(OverhauledDamage.POISON_BUILD_UP_REDUCTION, AttributeModifierUUIDs.PORKCHOP_FOOD_EFFECT, -2.0F, EntityAttributeModifier.Operation.ADDITION)
//            .addAttributeModifier(StaminaAttributes.MAX_STAMINA, AttributeModifierUUIDs.PORKCHOP_FOOD_EFFECT, 50.0F, EntityAttributeModifier.Operation.ADDITION)
//            .addAttributeModifier(StaminaAttributes.STAMINA_REGENERATION, AttributeModifierUUIDs.PORKCHOP_FOOD_EFFECT, 3.0F, EntityAttributeModifier.Operation.ADDITION)
            ;

    public static final StatusEffect POTATO_FOOD_EFFECT = new FoodStatusEffect()
            .addAttributeModifier(EntityAttributes.GENERIC_MAX_HEALTH, AttributeModifierUUIDs.POTATO_FOOD_EFFECT, 5.0F, EntityAttributeModifier.Operation.ADDITION)
            .addAttributeModifier(HealthRegenerationOverhaul.HEALTH_REGENERATION, AttributeModifierUUIDs.POTATO_FOOD_EFFECT, 3.0F, EntityAttributeModifier.Operation.ADDITION)
            .addAttributeModifier(StaminaAttributes.MAX_STAMINA, AttributeModifierUUIDs.POTATO_FOOD_EFFECT, 5.0F, EntityAttributeModifier.Operation.ADDITION)
            .addAttributeModifier(StaminaAttributes.STAMINA_REGENERATION, AttributeModifierUUIDs.POTATO_FOOD_EFFECT, 3.0F, EntityAttributeModifier.Operation.ADDITION)
            ;

    public static final StatusEffect PUFFERFISH_FOOD_EFFECT = new FoodStatusEffect()
//            .addAttributeModifier(EntityAttributes.GENERIC_MAX_HEALTH, AttributeModifierUUIDs.PUFFERFISH_FOOD_EFFECT, 60.0F, EntityAttributeModifier.Operation.ADDITION)
            .addAttributeModifier(OverhauledDamage.POISON_BUILD_UP_REDUCTION, AttributeModifierUUIDs.PUFFERFISH_FOOD_EFFECT, -5.0F, EntityAttributeModifier.Operation.ADDITION)
//            .addAttributeModifier(StaminaAttributes.MAX_STAMINA, AttributeModifierUUIDs.PUFFERFISH_FOOD_EFFECT, 50.0F, EntityAttributeModifier.Operation.ADDITION)
//            .addAttributeModifier(StaminaAttributes.STAMINA_REGENERATION, AttributeModifierUUIDs.PUFFERFISH_FOOD_EFFECT, 3.0F, EntityAttributeModifier.Operation.ADDITION)
            ;

    public static final StatusEffect PUMPKIN_PIE_FOOD_EFFECT = new FoodStatusEffect()
            .addAttributeModifier(EntityAttributes.GENERIC_MAX_HEALTH, AttributeModifierUUIDs.PUMPKIN_PIE_FOOD_EFFECT, 10.0F, EntityAttributeModifier.Operation.ADDITION)
            .addAttributeModifier(HealthRegenerationOverhaul.HEALTH_REGENERATION, AttributeModifierUUIDs.PUMPKIN_PIE_FOOD_EFFECT, 3.0F, EntityAttributeModifier.Operation.ADDITION)
            .addAttributeModifier(StaminaAttributes.MAX_STAMINA, AttributeModifierUUIDs.PUMPKIN_PIE_FOOD_EFFECT, 50.0F, EntityAttributeModifier.Operation.ADDITION)
            .addAttributeModifier(StaminaAttributes.STAMINA_REGENERATION, AttributeModifierUUIDs.PUMPKIN_PIE_FOOD_EFFECT, 3.0F, EntityAttributeModifier.Operation.ADDITION)
            ;

    public static final StatusEffect RABBIT_FOOD_EFFECT = new FoodStatusEffect()
            .addAttributeModifier(EntityAttributes.GENERIC_MAX_HEALTH, AttributeModifierUUIDs.RABBIT_FOOD_EFFECT, 5.0F, EntityAttributeModifier.Operation.ADDITION)
            .addAttributeModifier(HealthRegenerationOverhaul.HEALTH_REGENERATION, AttributeModifierUUIDs.RABBIT_FOOD_EFFECT, 0.5F, EntityAttributeModifier.Operation.ADDITION)
            .addAttributeModifier(HealthRegenerationOverhaul.HEALTH_REGENERATION, AttributeModifierUUIDs.RABBIT_FOOD_EFFECT, 3.0F, EntityAttributeModifier.Operation.ADDITION)
            .addAttributeModifier(StaminaAttributes.MAX_STAMINA, AttributeModifierUUIDs.RABBIT_FOOD_EFFECT, 50.0F, EntityAttributeModifier.Operation.ADDITION)
            .addAttributeModifier(StaminaAttributes.STAMINA_REGENERATION, AttributeModifierUUIDs.RABBIT_FOOD_EFFECT, 3.0F, EntityAttributeModifier.Operation.ADDITION)
            ;

    public static final StatusEffect RABBIT_STEW_FOOD_EFFECT = new FoodStatusEffect()
            .addAttributeModifier(EntityAttributes.GENERIC_MAX_HEALTH, AttributeModifierUUIDs.RABBIT_STEW_FOOD_EFFECT, 10.0F, EntityAttributeModifier.Operation.ADDITION)
            .addAttributeModifier(HealthRegenerationOverhaul.HEALTH_REGENERATION, AttributeModifierUUIDs.RABBIT_STEW_FOOD_EFFECT, 1.0F, EntityAttributeModifier.Operation.ADDITION)
            .addAttributeModifier(StaminaAttributes.MAX_STAMINA, AttributeModifierUUIDs.RABBIT_STEW_FOOD_EFFECT, 6.0F, EntityAttributeModifier.Operation.ADDITION)
            .addAttributeModifier(StaminaAttributes.STAMINA_REGENERATION, AttributeModifierUUIDs.RABBIT_STEW_FOOD_EFFECT, 1.0F, EntityAttributeModifier.Operation.ADDITION)
            ;

    public static final StatusEffect RED_MUSHROOM_FOOD_EFFECT = new FoodStatusEffect()
//            .addAttributeModifier(EntityAttributes.GENERIC_MAX_HEALTH, AttributeModifierUUIDs.RED_MUSHROOM_FOOD_EFFECT, 35.0F, EntityAttributeModifier.Operation.ADDITION)
//            .addAttributeModifier(StaminaAttributes.MAX_STAMINA, AttributeModifierUUIDs.RED_MUSHROOM_FOOD_EFFECT, 35.0F, EntityAttributeModifier.Operation.ADDITION)
//            .addAttributeModifier(EntityAttributesRegistry.MAX_MANA, AttributeModifierUUIDs.RED_MUSHROOM_FOOD_EFFECT, 35.0F, EntityAttributeModifier.Operation.ADDITION)
            .addAttributeModifier(OverhauledDamage.POISON_BUILD_UP_REDUCTION, AttributeModifierUUIDs.RED_MUSHROOM_FOOD_EFFECT, -4.0F, EntityAttributeModifier.Operation.ADDITION)
            .addAttributeModifier(ManaAttributes.MAX_MANA, AttributeModifierUUIDs.RED_MUSHROOM_FOOD_EFFECT, 5.0F, EntityAttributeModifier.Operation.ADDITION)
            ;

    public static final StatusEffect ROTTEN_FLESH_FOOD_EFFECT = new RemoveFoodStatusEffect();

    public static final StatusEffect SALMON_FOOD_EFFECT = new FoodStatusEffect()
            .addAttributeModifier(EntityAttributes.GENERIC_MAX_HEALTH, AttributeModifierUUIDs.SALMON_FOOD_EFFECT, 5.0F, EntityAttributeModifier.Operation.ADDITION)
            .addAttributeModifier(HealthRegenerationOverhaul.HEALTH_REGENERATION, AttributeModifierUUIDs.SALMON_FOOD_EFFECT, 0.5F, EntityAttributeModifier.Operation.ADDITION)
            .addAttributeModifier(OverhauledDamage.POISON_BUILD_UP_REDUCTION, AttributeModifierUUIDs.SALMON_FOOD_EFFECT, -2.0F, EntityAttributeModifier.Operation.ADDITION)
            .addAttributeModifier(StaminaAttributes.MAX_STAMINA, AttributeModifierUUIDs.SALMON_FOOD_EFFECT, 50.0F, EntityAttributeModifier.Operation.ADDITION)
            .addAttributeModifier(StaminaAttributes.STAMINA_REGENERATION, AttributeModifierUUIDs.SALMON_FOOD_EFFECT, 3.0F, EntityAttributeModifier.Operation.ADDITION)
            ;

    public static final StatusEffect SPIDER_EYE_FOOD_EFFECT = new FoodStatusEffect()
            .addAttributeModifier(OverhauledDamage.POISON_BUILD_UP_REDUCTION, AttributeModifierUUIDs.SPIDER_EYE_FOOD_EFFECT, -3.0F, EntityAttributeModifier.Operation.ADDITION)
            ;

    public static final StatusEffect SUGAR_FOOD_EFFECT = new FoodStatusEffect()
            .addAttributeModifier(EntityAttributes.GENERIC_MAX_HEALTH, AttributeModifierUUIDs.SUGAR_FOOD_EFFECT, 5.0F, EntityAttributeModifier.Operation.ADDITION)
            .addAttributeModifier(HealthRegenerationOverhaul.HEALTH_REGENERATION, AttributeModifierUUIDs.SUGAR_FOOD_EFFECT, 2.0F, EntityAttributeModifier.Operation.ADDITION)
            .addAttributeModifier(StaminaAttributes.MAX_STAMINA, AttributeModifierUUIDs.SUGAR_FOOD_EFFECT, 25.0F, EntityAttributeModifier.Operation.ADDITION)
            .addAttributeModifier(StaminaAttributes.STAMINA_REGENERATION, AttributeModifierUUIDs.SUGAR_FOOD_EFFECT, 4.0F, EntityAttributeModifier.Operation.ADDITION)
            ;

    public static final StatusEffect SUSPICIOUS_STEW_FOOD_EFFECT = new FoodStatusEffect()
            .addAttributeModifier(EntityAttributes.GENERIC_MAX_HEALTH, AttributeModifierUUIDs.SUSPICIOUS_STEW_FOOD_EFFECT, 5.0F, EntityAttributeModifier.Operation.ADDITION)
            .addAttributeModifier(HealthRegenerationOverhaul.HEALTH_REGENERATION, AttributeModifierUUIDs.SUSPICIOUS_STEW_FOOD_EFFECT, 0.5F, EntityAttributeModifier.Operation.ADDITION)
            .addAttributeModifier(StaminaAttributes.MAX_STAMINA, AttributeModifierUUIDs.SUSPICIOUS_STEW_FOOD_EFFECT, 5.0F, EntityAttributeModifier.Operation.ADDITION)
            .addAttributeModifier(ManaAttributes.MAX_MANA, AttributeModifierUUIDs.SUSPICIOUS_STEW_FOOD_EFFECT, 5.0F, EntityAttributeModifier.Operation.ADDITION)
            ;

    public static final StatusEffect SWEET_BERRIES_FOOD_EFFECT = new FoodStatusEffect()
            .addAttributeModifier(EntityAttributes.GENERIC_MAX_HEALTH, AttributeModifierUUIDs.SWEET_BERRIES_FOOD_EFFECT, 5.0F, EntityAttributeModifier.Operation.ADDITION)
            .addAttributeModifier(HealthRegenerationOverhaul.HEALTH_REGENERATION, AttributeModifierUUIDs.SWEET_BERRIES_FOOD_EFFECT, 2.0F, EntityAttributeModifier.Operation.ADDITION)
            .addAttributeModifier(StaminaAttributes.MAX_STAMINA, AttributeModifierUUIDs.SWEET_BERRIES_FOOD_EFFECT, 5.0F, EntityAttributeModifier.Operation.ADDITION)
            .addAttributeModifier(StaminaAttributes.STAMINA_REGENERATION, AttributeModifierUUIDs.SWEET_BERRIES_FOOD_EFFECT, 1.0F, EntityAttributeModifier.Operation.ADDITION)
            ;

    public static final StatusEffect TROPICAL_FISH_FOOD_EFFECT = new FoodStatusEffect()
            .addAttributeModifier(EntityAttributes.GENERIC_MAX_HEALTH, AttributeModifierUUIDs.TROPICAL_FISH_FOOD_EFFECT, 5.0F, EntityAttributeModifier.Operation.ADDITION)
            .addAttributeModifier(HealthRegenerationOverhaul.HEALTH_REGENERATION, AttributeModifierUUIDs.TROPICAL_FISH_FOOD_EFFECT, 0.5F, EntityAttributeModifier.Operation.ADDITION)
            .addAttributeModifier(OverhauledDamage.POISON_BUILD_UP_REDUCTION, AttributeModifierUUIDs.TROPICAL_FISH_FOOD_EFFECT, -2.0F, EntityAttributeModifier.Operation.ADDITION)
            .addAttributeModifier(StaminaAttributes.MAX_STAMINA, AttributeModifierUUIDs.TROPICAL_FISH_FOOD_EFFECT, 25.0F, EntityAttributeModifier.Operation.ADDITION)
            .addAttributeModifier(StaminaAttributes.STAMINA_REGENERATION, AttributeModifierUUIDs.TROPICAL_FISH_FOOD_EFFECT, 1.0F, EntityAttributeModifier.Operation.ADDITION)
            ;

    
    public static void registerEffects() {

        // Vanilla
        Registry.register(Registries.STATUS_EFFECT, FoodOverhaul.identifier("apple_food_effect"), APPLE_FOOD_EFFECT);
        Registry.register(Registries.STATUS_EFFECT, FoodOverhaul.identifier("baked_potato_food_effect"), BAKED_POTATO_FOOD_EFFECT);
        Registry.register(Registries.STATUS_EFFECT, FoodOverhaul.identifier("beef_food_effect"), BEEF_FOOD_EFFECT);
        Registry.register(Registries.STATUS_EFFECT, FoodOverhaul.identifier("beetroot_food_effect"), BEETROOT_FOOD_EFFECT);
        Registry.register(Registries.STATUS_EFFECT, FoodOverhaul.identifier("beetroot_soup_food_effect"), BEETROOT_SOUP_FOOD_EFFECT);
        Registry.register(Registries.STATUS_EFFECT, FoodOverhaul.identifier("bread_food_effect"), BREAD_FOOD_EFFECT);
        Registry.register(Registries.STATUS_EFFECT, FoodOverhaul.identifier("brown_mushroom_food_effect"), BROWN_MUSHROOM_FOOD_EFFECT);
        Registry.register(Registries.STATUS_EFFECT, FoodOverhaul.identifier("cake_food_effect"), CAKE_FOOD_EFFECT);
        Registry.register(Registries.STATUS_EFFECT, FoodOverhaul.identifier("carrot_food_effect"), CARROT_FOOD_EFFECT);
        Registry.register(Registries.STATUS_EFFECT, FoodOverhaul.identifier("chicken_food_effect"), CHICKEN_FOOD_EFFECT);
        Registry.register(Registries.STATUS_EFFECT, FoodOverhaul.identifier("chorus_fruit_food_effect"), CHORUS_FRUIT_FOOD_EFFECT);
        Registry.register(Registries.STATUS_EFFECT, FoodOverhaul.identifier("cocoa_beans_food_effect"), COCOA_BEANS_FOOD_EFFECT);
        Registry.register(Registries.STATUS_EFFECT, FoodOverhaul.identifier("cod_food_effect"), COD_FOOD_EFFECT);
        Registry.register(Registries.STATUS_EFFECT, FoodOverhaul.identifier("cooked_beef_food_effect"), COOKED_BEEF_FOOD_EFFECT);
        Registry.register(Registries.STATUS_EFFECT, FoodOverhaul.identifier("cooked_chicken_food_effect"), COOKED_CHICKEN_FOOD_EFFECT);
        Registry.register(Registries.STATUS_EFFECT, FoodOverhaul.identifier("cooked_cod_food_effect"), COOKED_COD_FOOD_EFFECT);
        Registry.register(Registries.STATUS_EFFECT, FoodOverhaul.identifier("cooked_mutton_food_effect"), COOKED_MUTTON_FOOD_EFFECT);
        Registry.register(Registries.STATUS_EFFECT, FoodOverhaul.identifier("cooked_porkchop_food_effect"), COOKED_PORKCHOP_FOOD_EFFECT);
        Registry.register(Registries.STATUS_EFFECT, FoodOverhaul.identifier("cooked_rabbit_food_effect"), COOKED_RABBIT_FOOD_EFFECT);
        Registry.register(Registries.STATUS_EFFECT, FoodOverhaul.identifier("cooked_salmon_food_effect"), COOKED_SALMON_FOOD_EFFECT);
        Registry.register(Registries.STATUS_EFFECT, FoodOverhaul.identifier("cookie_food_effect"), COOKIE_FOOD_EFFECT);
        Registry.register(Registries.STATUS_EFFECT, FoodOverhaul.identifier("dried_kelp_food_effect"), DRIED_KELP_FOOD_EFFECT);
        Registry.register(Registries.STATUS_EFFECT, FoodOverhaul.identifier("enchanted_golden_apple_food_effect"), ENCHANTED_GOLDEN_APPLE_FOOD_EFFECT);
        Registry.register(Registries.STATUS_EFFECT, FoodOverhaul.identifier("fermented_spider_eye_food_effect"), FERMENTED_SPIDER_EYE_FOOD_EFFECT);
        Registry.register(Registries.STATUS_EFFECT, FoodOverhaul.identifier("glow_berries_food_effect"), GLOW_BERRIES_FOOD_EFFECT);
        Registry.register(Registries.STATUS_EFFECT, FoodOverhaul.identifier("golden_apple_food_effect"), GOLDEN_APPLE_FOOD_EFFECT);
        Registry.register(Registries.STATUS_EFFECT, FoodOverhaul.identifier("golden_carrot_food_effect"), GOLDEN_CARROT_FOOD_EFFECT);
        Registry.register(Registries.STATUS_EFFECT, FoodOverhaul.identifier("honey_bottle_food_effect"), HONEY_BOTTLE_FOOD_EFFECT);
        Registry.register(Registries.STATUS_EFFECT, FoodOverhaul.identifier("melon_slice_food_effect"), MELON_SLICE_FOOD_EFFECT);
        Registry.register(Registries.STATUS_EFFECT, FoodOverhaul.identifier("mushroom_stew_food_effect"), MUSHROOM_STEW_FOOD_EFFECT);
        Registry.register(Registries.STATUS_EFFECT, FoodOverhaul.identifier("mutton_food_effect"), MUTTON_FOOD_EFFECT);
        Registry.register(Registries.STATUS_EFFECT, FoodOverhaul.identifier("poisonous_potato_food_effect"), POISONOUS_POTATO_FOOD_EFFECT);
        Registry.register(Registries.STATUS_EFFECT, FoodOverhaul.identifier("porkchop_food_effect"), PORKCHOP_FOOD_EFFECT);
        Registry.register(Registries.STATUS_EFFECT, FoodOverhaul.identifier("potato_food_effect"), POTATO_FOOD_EFFECT);
        Registry.register(Registries.STATUS_EFFECT, FoodOverhaul.identifier("pufferfish_food_effect"), PUFFERFISH_FOOD_EFFECT);
        Registry.register(Registries.STATUS_EFFECT, FoodOverhaul.identifier("pumpkin_pie_food_effect"), PUMPKIN_PIE_FOOD_EFFECT);
        Registry.register(Registries.STATUS_EFFECT, FoodOverhaul.identifier("rabbit_food_effect"), RABBIT_FOOD_EFFECT);
        Registry.register(Registries.STATUS_EFFECT, FoodOverhaul.identifier("rabbit_stew_food_effect"), RABBIT_STEW_FOOD_EFFECT);
        Registry.register(Registries.STATUS_EFFECT, FoodOverhaul.identifier("red_mushroom_food_effect"), RED_MUSHROOM_FOOD_EFFECT);
        Registry.register(Registries.STATUS_EFFECT, FoodOverhaul.identifier("rotten_flesh_food_effect"), ROTTEN_FLESH_FOOD_EFFECT);
        Registry.register(Registries.STATUS_EFFECT, FoodOverhaul.identifier("salmon_food_effect"), SALMON_FOOD_EFFECT);
        Registry.register(Registries.STATUS_EFFECT, FoodOverhaul.identifier("spider_eye_food_effect"), SPIDER_EYE_FOOD_EFFECT);
        Registry.register(Registries.STATUS_EFFECT, FoodOverhaul.identifier("sugar_food_effect"), SUGAR_FOOD_EFFECT);
        Registry.register(Registries.STATUS_EFFECT, FoodOverhaul.identifier("suspicious_stew_food_effect"), SUSPICIOUS_STEW_FOOD_EFFECT);
        Registry.register(Registries.STATUS_EFFECT, FoodOverhaul.identifier("sweet_berries_food_effect"), SWEET_BERRIES_FOOD_EFFECT);
        Registry.register(Registries.STATUS_EFFECT, FoodOverhaul.identifier("tropical_fish_food_effect"), TROPICAL_FISH_FOOD_EFFECT);
    }
}
