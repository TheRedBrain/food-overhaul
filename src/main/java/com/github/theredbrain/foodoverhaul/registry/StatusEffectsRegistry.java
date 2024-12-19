package com.github.theredbrain.foodoverhaul.registry;

import com.github.theredbrain.foodoverhaul.FoodOverhaul;
import com.github.theredbrain.foodoverhaul.effect.RemoveFoodStatusEffect;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.registry.entry.RegistryEntry;

public class StatusEffectsRegistry {

	public static final RegistryEntry<StatusEffect> REMOVE_FOOD_EFFECTS_EFFECT = Registry.registerReference(Registries.STATUS_EFFECT, FoodOverhaul.identifier("remove_food_effects_effect"), new RemoveFoodStatusEffect());

	public static void init() {
	}
}
