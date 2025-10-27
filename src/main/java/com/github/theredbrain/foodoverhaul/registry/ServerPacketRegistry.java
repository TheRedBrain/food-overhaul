package com.github.theredbrain.foodoverhaul.registry;

import com.github.theredbrain.foodoverhaul.network.packet.UpdateFoodBlockPacket;
import com.github.theredbrain.foodoverhaul.network.packet.UpdateFoodBlockPacketReceiver;
import net.fabricmc.fabric.api.networking.v1.PayloadTypeRegistry;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;

public class ServerPacketRegistry {

	public static void init() {

		PayloadTypeRegistry.playC2S().register(UpdateFoodBlockPacket.PACKET_ID, UpdateFoodBlockPacket.PACKET_CODEC);
		ServerPlayNetworking.registerGlobalReceiver(UpdateFoodBlockPacket.PACKET_ID, new UpdateFoodBlockPacketReceiver());

	}

}
