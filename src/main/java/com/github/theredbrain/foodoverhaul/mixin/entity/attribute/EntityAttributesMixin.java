package com.github.theredbrain.foodoverhaul.mixin.entity.attribute;

import com.github.theredbrain.foodoverhaul.FoodOverhaul;
import net.minecraft.entity.attribute.ClampedEntityAttribute;
import net.minecraft.entity.attribute.EntityAttribute;
import net.minecraft.entity.attribute.EntityAttributes;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

@Mixin(EntityAttributes.class)
public class EntityAttributesMixin {
    @Shadow
    private static EntityAttribute register(String id, EntityAttribute attribute) {
        throw new AssertionError();
    }

    static {
        FoodOverhaul.MAX_FOOD_EFFECTS = register(FoodOverhaul.MOD_ID + ":generic.max_food_effects", new ClampedEntityAttribute("attribute.name.generic.max_food_effects", 0.0, 0.0, 1024.0).setTracked(true));
    }
}
