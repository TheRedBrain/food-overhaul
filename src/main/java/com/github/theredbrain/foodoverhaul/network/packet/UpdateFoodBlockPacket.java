package com.github.theredbrain.foodoverhaul.network.packet;

import com.github.theredbrain.foodoverhaul.FoodOverhaul;
import com.github.theredbrain.foodoverhaul.block.entity.FoodBlockEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;

public record UpdateFoodBlockPacket(
		BlockPos foodBlockPosition,
		FoodBlockEntity.FoodBlockData foodBlockData
) implements CustomPacketPayload {
	public static final Type<UpdateFoodBlockPacket> PACKET_ID = new Type<>(FoodOverhaul.identifier("update_food_block"));
	public static final StreamCodec<RegistryFriendlyByteBuf, UpdateFoodBlockPacket> PACKET_CODEC = StreamCodec.ofMember(UpdateFoodBlockPacket::write, UpdateFoodBlockPacket::new);

	public UpdateFoodBlockPacket(RegistryFriendlyByteBuf registryByteBuf) {
		this(
				registryByteBuf.readBlockPos(),
				new FoodBlockEntity.FoodBlockData(registryByteBuf)
		);
	}

	private void write(RegistryFriendlyByteBuf registryByteBuf) {
		registryByteBuf.writeBlockPos(this.foodBlockPosition);
		foodBlockData.write(registryByteBuf);
	}

	@Override
	public Type<? extends CustomPacketPayload> type() {
		return PACKET_ID;
	}
}
