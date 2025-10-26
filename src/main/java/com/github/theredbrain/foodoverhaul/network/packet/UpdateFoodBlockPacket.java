package com.github.theredbrain.foodoverhaul.network.packet;

import com.github.theredbrain.foodoverhaul.FoodOverhaul;
import net.minecraft.network.RegistryByteBuf;
import net.minecraft.network.codec.PacketCodec;
import net.minecraft.network.packet.CustomPayload;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3i;

public record UpdateFoodBlockPacket(
		BlockPos foodBlockPosition,
		String appliedStatusEffectIdentifier,
		int appliedStatusEffectAmplifier,
		int appliedStatusEffectDuration,
		boolean appliedStatusEffectAmbient,
		boolean appliedStatusEffectShowParticles,
		boolean appliedStatusEffectShowIcon,
		String interactionResultItemIdentifier,
		String interactionToolItemIdentifier,
		String usePreventingStatusEffectIdentifier,
		String requiredAdvancementIdentifier,
		int recoveryTimerThreshold,
		boolean infiniteUses
) implements CustomPayload {
	public static final Id<UpdateFoodBlockPacket> PACKET_ID = new Id<>(FoodOverhaul.identifier("update_food_block"));
	public static final PacketCodec<RegistryByteBuf, UpdateFoodBlockPacket> PACKET_CODEC = PacketCodec.of(UpdateFoodBlockPacket::write, UpdateFoodBlockPacket::new);

	public UpdateFoodBlockPacket(RegistryByteBuf registryByteBuf) {
		this(
				registryByteBuf.readBlockPos(),
				registryByteBuf.readString(),
				registryByteBuf.readInt(),
				registryByteBuf.readInt(),
				registryByteBuf.readBoolean(),
				registryByteBuf.readBoolean(),
				registryByteBuf.readBoolean(),
				registryByteBuf.readString(),
				registryByteBuf.readString(),
				registryByteBuf.readString(),
				registryByteBuf.readString(),
				registryByteBuf.readInt(),
				registryByteBuf.readBoolean()
		);
	}

	private void write(RegistryByteBuf registryByteBuf) {
		registryByteBuf.writeBlockPos(this.foodBlockPosition);
		registryByteBuf.writeString(this.appliedStatusEffectIdentifier);
		registryByteBuf.writeInt(this.appliedStatusEffectAmplifier);
		registryByteBuf.writeInt(this.appliedStatusEffectDuration);
		registryByteBuf.writeBoolean(this.appliedStatusEffectAmbient);
		registryByteBuf.writeBoolean(this.appliedStatusEffectShowParticles);
		registryByteBuf.writeBoolean(this.appliedStatusEffectShowIcon);
		registryByteBuf.writeString(this.interactionToolItemIdentifier);
		registryByteBuf.writeString(this.interactionResultItemIdentifier);
		registryByteBuf.writeString(this.usePreventingStatusEffectIdentifier);
		registryByteBuf.writeString(this.requiredAdvancementIdentifier);
		registryByteBuf.writeInt(this.recoveryTimerThreshold);
		registryByteBuf.writeBoolean(this.infiniteUses);
	}

	@Override
	public Id<? extends CustomPayload> getId() {
		return PACKET_ID;
	}
}
