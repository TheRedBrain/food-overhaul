package com.github.theredbrain.foodoverhaul.registry;

import com.github.theredbrain.foodoverhaul.item.OverhauledFoodComponents;
import net.fabricmc.fabric.api.item.v1.DefaultItemComponentEvents;
import net.minecraft.component.DataComponentTypes;
import net.minecraft.item.Items;

public class EventsRegistry {
	public static void initializeEvents() {
		DefaultItemComponentEvents.MODIFY.register(context -> {

			//region modified vanilla items
			context.modify(Items.APPLE, builder -> {
				builder.add(DataComponentTypes.FOOD, OverhauledFoodComponents.APPLE);
			});
			context.modify(Items.BAKED_POTATO, builder -> {
				builder.add(DataComponentTypes.FOOD, OverhauledFoodComponents.BAKED_POTATO);
			});
			context.modify(Items.BEEF, builder -> {
				builder.add(DataComponentTypes.FOOD, OverhauledFoodComponents.BEEF);
			});
			context.modify(Items.BEETROOT, builder -> {
				builder.add(DataComponentTypes.FOOD, OverhauledFoodComponents.BEETROOT);
			});
			context.modify(Items.BEETROOT_SOUP, builder -> {
				builder.add(DataComponentTypes.FOOD, OverhauledFoodComponents.BEETROOT_SOUP);
			});
			context.modify(Items.BREAD, builder -> {
				builder.add(DataComponentTypes.FOOD, OverhauledFoodComponents.BREAD);
			});
			context.modify(Items.CARROT, builder -> {
				builder.add(DataComponentTypes.FOOD, OverhauledFoodComponents.CARROT);
			});
			context.modify(Items.CHICKEN, builder -> {
				builder.add(DataComponentTypes.FOOD, OverhauledFoodComponents.CHICKEN);
			});
			context.modify(Items.CHORUS_FRUIT, builder -> {
				builder.add(DataComponentTypes.FOOD, OverhauledFoodComponents.CHORUS_FRUIT);
			});
			context.modify(Items.COD, builder -> {
				builder.add(DataComponentTypes.FOOD, OverhauledFoodComponents.COD);
			});
			context.modify(Items.COOKED_BEEF, builder -> {
				builder.add(DataComponentTypes.FOOD, OverhauledFoodComponents.COOKED_BEEF);
			});
			context.modify(Items.COOKED_CHICKEN, builder -> {
				builder.add(DataComponentTypes.FOOD, OverhauledFoodComponents.COOKED_CHICKEN);
			});
			context.modify(Items.COOKED_COD, builder -> {
				builder.add(DataComponentTypes.FOOD, OverhauledFoodComponents.COOKED_COD);
			});
			context.modify(Items.COOKED_MUTTON, builder -> {
				builder.add(DataComponentTypes.FOOD, OverhauledFoodComponents.COOKED_MUTTON);
			});
			context.modify(Items.COOKED_PORKCHOP, builder -> {
				builder.add(DataComponentTypes.FOOD, OverhauledFoodComponents.COOKED_PORKCHOP);
			});
			context.modify(Items.COOKED_RABBIT, builder -> {
				builder.add(DataComponentTypes.FOOD, OverhauledFoodComponents.COOKED_RABBIT);
			});
			context.modify(Items.COOKED_SALMON, builder -> {
				builder.add(DataComponentTypes.FOOD, OverhauledFoodComponents.COOKED_SALMON);
			});
			context.modify(Items.COOKIE, builder -> {
				builder.add(DataComponentTypes.FOOD, OverhauledFoodComponents.COOKIE);
			});
			context.modify(Items.DRIED_KELP, builder -> {
				builder.add(DataComponentTypes.FOOD, OverhauledFoodComponents.DRIED_KELP);
			});
			context.modify(Items.ENCHANTED_GOLDEN_APPLE, builder -> {
				builder.add(DataComponentTypes.FOOD, OverhauledFoodComponents.ENCHANTED_GOLDEN_APPLE);
			});
			context.modify(Items.GOLDEN_APPLE, builder -> {
				builder.add(DataComponentTypes.FOOD, OverhauledFoodComponents.GOLDEN_APPLE);
			});
			context.modify(Items.GOLDEN_CARROT, builder -> {
				builder.add(DataComponentTypes.FOOD, OverhauledFoodComponents.GOLDEN_CARROT);
			});
			context.modify(Items.HONEY_BOTTLE, builder -> {
				builder.add(DataComponentTypes.FOOD, OverhauledFoodComponents.HONEY_BOTTLE);
			});
			context.modify(Items.MELON_SLICE, builder -> {
				builder.add(DataComponentTypes.FOOD, OverhauledFoodComponents.MELON_SLICE);
			});
			context.modify(Items.MUSHROOM_STEW, builder -> {
				builder.add(DataComponentTypes.FOOD, OverhauledFoodComponents.MUSHROOM_STEW);
			});
			context.modify(Items.MUTTON, builder -> {
				builder.add(DataComponentTypes.FOOD, OverhauledFoodComponents.MUTTON);
			});
			context.modify(Items.POISONOUS_POTATO, builder -> {
				builder.add(DataComponentTypes.FOOD, OverhauledFoodComponents.POISONOUS_POTATO);
			});
			context.modify(Items.PORKCHOP, builder -> {
				builder.add(DataComponentTypes.FOOD, OverhauledFoodComponents.PORKCHOP);
			});
			context.modify(Items.POTATO, builder -> {
				builder.add(DataComponentTypes.FOOD, OverhauledFoodComponents.POTATO);
			});
			context.modify(Items.PUFFERFISH, builder -> {
				builder.add(DataComponentTypes.FOOD, OverhauledFoodComponents.PUFFERFISH);
			});
			context.modify(Items.PUMPKIN_PIE, builder -> {
				builder.add(DataComponentTypes.FOOD, OverhauledFoodComponents.PUMPKIN_PIE);
			});
			context.modify(Items.RABBIT, builder -> {
				builder.add(DataComponentTypes.FOOD, OverhauledFoodComponents.RABBIT);
			});
			context.modify(Items.RABBIT_STEW, builder -> {
				builder.add(DataComponentTypes.FOOD, OverhauledFoodComponents.RABBIT_STEW);
			});
			context.modify(Items.ROTTEN_FLESH, builder -> {
				builder.add(DataComponentTypes.FOOD, OverhauledFoodComponents.ROTTEN_FLESH);
			});
			context.modify(Items.SALMON, builder -> {
				builder.add(DataComponentTypes.FOOD, OverhauledFoodComponents.SALMON);
			});
			context.modify(Items.SPIDER_EYE, builder -> {
				builder.add(DataComponentTypes.FOOD, OverhauledFoodComponents.SPIDER_EYE);
			});
			context.modify(Items.SUSPICIOUS_STEW, builder -> {
				builder.add(DataComponentTypes.FOOD, OverhauledFoodComponents.SUSPICIOUS_STEW);
			});
			context.modify(Items.SWEET_BERRIES, builder -> {
				builder.add(DataComponentTypes.FOOD, OverhauledFoodComponents.SWEET_BERRIES);
			});
			context.modify(Items.GLOW_BERRIES, builder -> {
				builder.add(DataComponentTypes.FOOD, OverhauledFoodComponents.GLOW_BERRIES);
			});
			context.modify(Items.TROPICAL_FISH, builder -> {
				builder.add(DataComponentTypes.FOOD, OverhauledFoodComponents.TROPICAL_FISH);
			});
			//endregion modified vanilla items

			//endregion additional vanilla items
			context.modify(Items.BROWN_MUSHROOM, builder -> {
				builder.add(DataComponentTypes.FOOD, OverhauledFoodComponents.BROWN_MUSHROOM);
			});
			context.modify(Items.COCOA_BEANS, builder -> {
				builder.add(DataComponentTypes.FOOD, OverhauledFoodComponents.COCOA_BEANS);
			});
			context.modify(Items.FERMENTED_SPIDER_EYE, builder -> {
				builder.add(DataComponentTypes.FOOD, OverhauledFoodComponents.FERMENTED_SPIDER_EYE);
			});
			context.modify(Items.RED_MUSHROOM, builder -> {
				builder.add(DataComponentTypes.FOOD, OverhauledFoodComponents.RED_MUSHROOM);
			});
			context.modify(Items.SUGAR, builder -> {
				builder.add(DataComponentTypes.FOOD, OverhauledFoodComponents.SUGAR);
			});
			//endregion additional vanilla items
		});

	}
}
