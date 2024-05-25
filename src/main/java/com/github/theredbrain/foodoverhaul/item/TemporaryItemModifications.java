package com.github.theredbrain.foodoverhaul.item;

import com.github.theredbrain.foodoverhaul.mixin.item.TemporaryItemAccessor;
import net.minecraft.item.FoodComponent;
import net.minecraft.item.Item;
import net.minecraft.item.Items;

// TODO remove when data-driven items are in vanilla
public class TemporaryItemModifications implements ITemporaryItemModifications {
    @Override
    public void setFoodComponent(Item item, FoodComponent foodComponent) {
        ((TemporaryItemAccessor) item).foodoverhaul$setFoodComponent(foodComponent);
    }

    public static void applyFoodComponentModifications() {

        // modified vanilla items
        ITemporaryItemModifications.INSTANCE.setFoodComponent(Items.APPLE, OverhauledFoodComponents.APPLE);
        ITemporaryItemModifications.INSTANCE.setFoodComponent(Items.BAKED_POTATO, OverhauledFoodComponents.BAKED_POTATO);
        ITemporaryItemModifications.INSTANCE.setFoodComponent(Items.BEEF, OverhauledFoodComponents.BEEF);
        ITemporaryItemModifications.INSTANCE.setFoodComponent(Items.BEETROOT, OverhauledFoodComponents.BEETROOT);
        ITemporaryItemModifications.INSTANCE.setFoodComponent(Items.BEETROOT_SOUP, OverhauledFoodComponents.BEETROOT_SOUP);
        ITemporaryItemModifications.INSTANCE.setFoodComponent(Items.BREAD, OverhauledFoodComponents.BREAD);
        ITemporaryItemModifications.INSTANCE.setFoodComponent(Items.CARROT, OverhauledFoodComponents.CARROT);
        ITemporaryItemModifications.INSTANCE.setFoodComponent(Items.CHICKEN, OverhauledFoodComponents.CHICKEN);
        ITemporaryItemModifications.INSTANCE.setFoodComponent(Items.CHORUS_FRUIT, OverhauledFoodComponents.CHORUS_FRUIT);
        ITemporaryItemModifications.INSTANCE.setFoodComponent(Items.COD, OverhauledFoodComponents.COD);
        ITemporaryItemModifications.INSTANCE.setFoodComponent(Items.COOKED_BEEF, OverhauledFoodComponents.COOKED_BEEF);
        ITemporaryItemModifications.INSTANCE.setFoodComponent(Items.COOKED_CHICKEN, OverhauledFoodComponents.COOKED_CHICKEN);
        ITemporaryItemModifications.INSTANCE.setFoodComponent(Items.COOKED_COD, OverhauledFoodComponents.COOKED_COD);
        ITemporaryItemModifications.INSTANCE.setFoodComponent(Items.COOKED_MUTTON, OverhauledFoodComponents.COOKED_MUTTON);
        ITemporaryItemModifications.INSTANCE.setFoodComponent(Items.COOKED_PORKCHOP, OverhauledFoodComponents.COOKED_PORKCHOP);
        ITemporaryItemModifications.INSTANCE.setFoodComponent(Items.COOKED_RABBIT, OverhauledFoodComponents.COOKED_RABBIT);
        ITemporaryItemModifications.INSTANCE.setFoodComponent(Items.COOKED_SALMON, OverhauledFoodComponents.COOKED_SALMON);
        ITemporaryItemModifications.INSTANCE.setFoodComponent(Items.COOKIE, OverhauledFoodComponents.COOKIE);
        ITemporaryItemModifications.INSTANCE.setFoodComponent(Items.DRIED_KELP, OverhauledFoodComponents.DRIED_KELP);
        ITemporaryItemModifications.INSTANCE.setFoodComponent(Items.ENCHANTED_GOLDEN_APPLE, OverhauledFoodComponents.ENCHANTED_GOLDEN_APPLE);
        ITemporaryItemModifications.INSTANCE.setFoodComponent(Items.GOLDEN_APPLE, OverhauledFoodComponents.GOLDEN_APPLE);
        ITemporaryItemModifications.INSTANCE.setFoodComponent(Items.GOLDEN_CARROT, OverhauledFoodComponents.GOLDEN_CARROT);
        ITemporaryItemModifications.INSTANCE.setFoodComponent(Items.HONEY_BOTTLE, OverhauledFoodComponents.HONEY_BOTTLE);
        ITemporaryItemModifications.INSTANCE.setFoodComponent(Items.MELON_SLICE, OverhauledFoodComponents.MELON_SLICE);
        ITemporaryItemModifications.INSTANCE.setFoodComponent(Items.MUSHROOM_STEW, OverhauledFoodComponents.MUSHROOM_STEW);
        ITemporaryItemModifications.INSTANCE.setFoodComponent(Items.MUTTON, OverhauledFoodComponents.MUTTON);
        ITemporaryItemModifications.INSTANCE.setFoodComponent(Items.POISONOUS_POTATO, OverhauledFoodComponents.POISONOUS_POTATO);
        ITemporaryItemModifications.INSTANCE.setFoodComponent(Items.PORKCHOP, OverhauledFoodComponents.PORKCHOP);
        ITemporaryItemModifications.INSTANCE.setFoodComponent(Items.POTATO, OverhauledFoodComponents.POTATO);
        ITemporaryItemModifications.INSTANCE.setFoodComponent(Items.PUFFERFISH, OverhauledFoodComponents.PUFFERFISH);
        ITemporaryItemModifications.INSTANCE.setFoodComponent(Items.PUMPKIN_PIE, OverhauledFoodComponents.PUMPKIN_PIE);
        ITemporaryItemModifications.INSTANCE.setFoodComponent(Items.RABBIT, OverhauledFoodComponents.RABBIT);
        ITemporaryItemModifications.INSTANCE.setFoodComponent(Items.RABBIT_STEW, OverhauledFoodComponents.RABBIT_STEW);
        ITemporaryItemModifications.INSTANCE.setFoodComponent(Items.ROTTEN_FLESH, OverhauledFoodComponents.ROTTEN_FLESH);
        ITemporaryItemModifications.INSTANCE.setFoodComponent(Items.SALMON, OverhauledFoodComponents.SALMON);
        ITemporaryItemModifications.INSTANCE.setFoodComponent(Items.SPIDER_EYE, OverhauledFoodComponents.SPIDER_EYE);
        ITemporaryItemModifications.INSTANCE.setFoodComponent(Items.SUSPICIOUS_STEW, OverhauledFoodComponents.SUSPICIOUS_STEW);
        ITemporaryItemModifications.INSTANCE.setFoodComponent(Items.SWEET_BERRIES, OverhauledFoodComponents.SWEET_BERRIES);
        ITemporaryItemModifications.INSTANCE.setFoodComponent(Items.GLOW_BERRIES, OverhauledFoodComponents.GLOW_BERRIES);
        ITemporaryItemModifications.INSTANCE.setFoodComponent(Items.TROPICAL_FISH, OverhauledFoodComponents.TROPICAL_FISH);

        // additional vanilla items
        ITemporaryItemModifications.INSTANCE.setFoodComponent(Items.BROWN_MUSHROOM, OverhauledFoodComponents.BROWN_MUSHROOM);
        ITemporaryItemModifications.INSTANCE.setFoodComponent(Items.COCOA_BEANS, OverhauledFoodComponents.COCOA_BEANS);
        ITemporaryItemModifications.INSTANCE.setFoodComponent(Items.FERMENTED_SPIDER_EYE, OverhauledFoodComponents.FERMENTED_SPIDER_EYE);
        ITemporaryItemModifications.INSTANCE.setFoodComponent(Items.RED_MUSHROOM, OverhauledFoodComponents.RED_MUSHROOM);
        ITemporaryItemModifications.INSTANCE.setFoodComponent(Items.SUGAR, OverhauledFoodComponents.SUGAR);
    }
}
