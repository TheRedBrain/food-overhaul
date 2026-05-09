package com.github.theredbrain.foodoverhaul.registry;

import com.github.theredbrain.foodoverhaul.network.packet.UpdateFoodBlockPacket;
import com.github.theredbrain.foodoverhaul.network.packet.UpdateFoodBlockPacketReceiver;
import com.github.theredbrain.foodoverhaul.network.packet.UpdateFoodDisplayBlockPacket;
import com.github.theredbrain.foodoverhaul.network.packet.UpdateFoodDisplayBlockPacketReceiver;
import net.fabricmc.fabric.api.networking.v1.PayloadTypeRegistry;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;

public class FoodOverhaulServerPackets {

	public static void init() {

		PayloadTypeRegistry.serverboundPlay().register(UpdateFoodBlockPacket.PACKET_ID, UpdateFoodBlockPacket.PACKET_CODEC);
		ServerPlayNetworking.registerGlobalReceiver(UpdateFoodBlockPacket.PACKET_ID, new UpdateFoodBlockPacketReceiver());

		PayloadTypeRegistry.serverboundPlay().register(UpdateFoodDisplayBlockPacket.PACKET_ID, UpdateFoodDisplayBlockPacket.PACKET_CODEC);
		ServerPlayNetworking.registerGlobalReceiver(UpdateFoodDisplayBlockPacket.PACKET_ID, new UpdateFoodDisplayBlockPacketReceiver());

	}

}
