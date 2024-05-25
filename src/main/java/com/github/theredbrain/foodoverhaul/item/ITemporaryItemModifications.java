package com.github.theredbrain.foodoverhaul.item;

import net.minecraft.item.FoodComponent;
import net.minecraft.item.Item;

// TODO 1.20.6 use fabric API
public interface ITemporaryItemModifications {
    ITemporaryItemModifications INSTANCE = new TemporaryItemModifications();
    void setFoodComponent(Item item, FoodComponent foodComponent);
}
