package com.github.theredbrain.foodoverhaul;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;

public class FoodOverhaulClient implements ClientModInitializer {
	@Override
	public void onInitializeClient() {
		// TODO 1.20.6
//		PayloadTypeRegistry.playS2C().register(ConfigSyncPacket.PACKET_ID, ConfigSyncPacket.PACKET_CODEC);
//		ClientPlayNetworking.registerGlobalReceiver(ConfigSyncPacket.PACKET_ID, ((payload, context) -> {
//			FoodOverhaul.serverConfig = payload.config();
//		}));
		ClientPlayNetworking.registerGlobalReceiver(FoodOverhaul.ServerConfigSync.ID, (client, handler, buf, responseSender) -> {
			FoodOverhaul.serverConfig = FoodOverhaul.ServerConfigSync.read(buf);
		});
	}
}