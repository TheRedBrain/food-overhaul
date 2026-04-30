package com.github.theredbrain.foodoverhaul.network.packet;

import com.github.theredbrain.foodoverhaul.FoodOverhaul;
import com.github.theredbrain.foodoverhaul.block.entity.FoodDisplayBlockEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;

public record UpdateFoodDisplayBlockPacket(
		BlockPos foodDisplayBlockPosition,
		FoodDisplayBlockEntity.FoodDisplayBlockData foodDisplayBlockData
) implements CustomPacketPayload {
	public static final Type<UpdateFoodDisplayBlockPacket> PACKET_ID = new Type<>(FoodOverhaul.identifier("update_food_display_block"));
	public static final StreamCodec<RegistryFriendlyByteBuf, UpdateFoodDisplayBlockPacket> PACKET_CODEC = StreamCodec.ofMember(UpdateFoodDisplayBlockPacket::write, UpdateFoodDisplayBlockPacket::new);

	public UpdateFoodDisplayBlockPacket(RegistryFriendlyByteBuf registryByteBuf) {
		this(
				registryByteBuf.readBlockPos(),
				new FoodDisplayBlockEntity.FoodDisplayBlockData(registryByteBuf)
		);
	}

	private void write(RegistryFriendlyByteBuf registryByteBuf) {
		registryByteBuf.writeBlockPos(this.foodDisplayBlockPosition);
		foodDisplayBlockData.write(registryByteBuf);
	}

	@Override
	public Type<? extends CustomPacketPayload> type() {
		return PACKET_ID;
	}
}
