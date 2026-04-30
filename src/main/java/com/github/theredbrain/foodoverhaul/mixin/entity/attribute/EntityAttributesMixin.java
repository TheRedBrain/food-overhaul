package com.github.theredbrain.foodoverhaul.mixin.entity.attribute;

import com.github.theredbrain.foodoverhaul.FoodOverhaul;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.attributes.RangedAttribute;
import org.spongepowered.asm.mixin.Mixin;

@Mixin(Attributes.class)
public class EntityAttributesMixin {
	static {
		FoodOverhaul.MAX_FOOD_EFFECTS = Registry.registerForHolder(BuiltInRegistries.ATTRIBUTE, FoodOverhaul.identifier("max_food_effects"), new RangedAttribute("attribute.name.max_food_effects", 0.0, 0.0, 1024.0).setSyncable(true));
	}
}
