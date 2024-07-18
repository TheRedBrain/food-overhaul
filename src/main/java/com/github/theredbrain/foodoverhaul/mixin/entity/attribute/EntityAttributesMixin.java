package com.github.theredbrain.foodoverhaul.mixin.entity.attribute;

import com.github.theredbrain.foodoverhaul.FoodOverhaul;
import net.minecraft.entity.attribute.ClampedEntityAttribute;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import org.spongepowered.asm.mixin.Mixin;

@Mixin(EntityAttributes.class)
public class EntityAttributesMixin {
	static {
		FoodOverhaul.MAX_FOOD_EFFECTS = Registry.registerReference(Registries.ATTRIBUTE, FoodOverhaul.identifier("generic.max_food_effects"), new ClampedEntityAttribute("attribute.name.generic.max_food_effects", 0.0, 0.0, 1024.0).setTracked(true));
	}
}
