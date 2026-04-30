package com.github.theredbrain.foodoverhaul.component.type;

import com.github.theredbrain.foodoverhaul.block.entity.FoodDisplayBlockEntity;
import com.mojang.serialization.Codec;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;

public record FoodDisplayBlockDataComponent(FoodDisplayBlockEntity.FoodDisplayBlockData food_display_block_data) {
	public static final Codec<FoodDisplayBlockDataComponent> CODEC = FoodDisplayBlockEntity.FoodDisplayBlockData.CODEC.xmap(FoodDisplayBlockDataComponent::new, FoodDisplayBlockDataComponent::food_display_block_data);
	public static final StreamCodec<RegistryFriendlyByteBuf, FoodDisplayBlockDataComponent> PACKET_CODEC = FoodDisplayBlockEntity.FoodDisplayBlockData.PACKET_CODEC
			.map(FoodDisplayBlockDataComponent::new, FoodDisplayBlockDataComponent::food_display_block_data);
	public static final FoodDisplayBlockDataComponent DEFAULT = new FoodDisplayBlockDataComponent(FoodDisplayBlockEntity.FoodDisplayBlockData.DEFAULT);
}
