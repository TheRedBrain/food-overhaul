package com.github.theredbrain.foodoverhaul.network.packet;

import com.github.theredbrain.foodoverhaul.FoodOverhaul;
import com.github.theredbrain.foodoverhaul.block.entity.FoodDisplayBlockEntity;
import net.minecraft.network.RegistryByteBuf;
import net.minecraft.network.codec.PacketCodec;
import net.minecraft.network.packet.CustomPayload;
import net.minecraft.util.math.BlockPos;

public record UpdateFoodDisplayBlockPacket(
		BlockPos foodDisplayBlockPosition,
		FoodDisplayBlockEntity.FoodDisplayBlockData foodDisplayBlockData
) implements CustomPayload {
	public static final Id<UpdateFoodDisplayBlockPacket> PACKET_ID = new Id<>(FoodOverhaul.identifier("update_food_display_block"));
	public static final PacketCodec<RegistryByteBuf, UpdateFoodDisplayBlockPacket> PACKET_CODEC = PacketCodec.of(UpdateFoodDisplayBlockPacket::write, UpdateFoodDisplayBlockPacket::new);

	public UpdateFoodDisplayBlockPacket(RegistryByteBuf registryByteBuf) {
		this(
				registryByteBuf.readBlockPos(),
				new FoodDisplayBlockEntity.FoodDisplayBlockData(registryByteBuf)
		);
	}

	private void write(RegistryByteBuf registryByteBuf) {
		registryByteBuf.writeBlockPos(this.foodDisplayBlockPosition);
		foodDisplayBlockData.write(registryByteBuf);
	}

	@Override
	public Id<? extends CustomPayload> getId() {
		return PACKET_ID;
	}
}
