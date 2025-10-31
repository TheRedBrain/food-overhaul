package com.github.theredbrain.foodoverhaul.component.type;

import com.github.theredbrain.foodoverhaul.block.entity.FoodBlockEntity;
import com.mojang.serialization.Codec;
import net.minecraft.network.RegistryByteBuf;
import net.minecraft.network.codec.PacketCodec;

public record FoodBlockDataComponent(FoodBlockEntity.FoodBlockData food_block_data) {
	public static final Codec<FoodBlockDataComponent> CODEC = FoodBlockEntity.FoodBlockData.CODEC.xmap(FoodBlockDataComponent::new, FoodBlockDataComponent::food_block_data);
	public static final PacketCodec<RegistryByteBuf, FoodBlockDataComponent> PACKET_CODEC = FoodBlockEntity.FoodBlockData.PACKET_CODEC
			.xmap(FoodBlockDataComponent::new, FoodBlockDataComponent::food_block_data);
	public static final FoodBlockDataComponent DEFAULT = new FoodBlockDataComponent(FoodBlockEntity.FoodBlockData.DEFAULT);
}
