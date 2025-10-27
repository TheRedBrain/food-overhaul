package com.github.theredbrain.foodoverhaul.network.packet;

import com.github.theredbrain.foodoverhaul.FoodOverhaul;
import com.github.theredbrain.foodoverhaul.block.entity.FoodBlockEntity;
import net.minecraft.network.RegistryByteBuf;
import net.minecraft.network.codec.PacketCodec;
import net.minecraft.network.packet.CustomPayload;
import net.minecraft.util.math.BlockPos;

public record UpdateFoodBlockPacket(
		BlockPos foodBlockPosition,
		FoodBlockEntity.FoodBlockData foodBlockData
) implements CustomPayload {
	public static final Id<UpdateFoodBlockPacket> PACKET_ID = new Id<>(FoodOverhaul.identifier("update_food_block"));
	public static final PacketCodec<RegistryByteBuf, UpdateFoodBlockPacket> PACKET_CODEC = PacketCodec.of(UpdateFoodBlockPacket::write, UpdateFoodBlockPacket::new);

	public UpdateFoodBlockPacket(RegistryByteBuf registryByteBuf) {
		this(
				registryByteBuf.readBlockPos(),
				new FoodBlockEntity.FoodBlockData(registryByteBuf)
		);
	}

	private void write(RegistryByteBuf registryByteBuf) {
		registryByteBuf.writeBlockPos(this.foodBlockPosition);
		foodBlockData.write(registryByteBuf);
	}

	@Override
	public Id<? extends CustomPayload> getId() {
		return PACKET_ID;
	}
}
