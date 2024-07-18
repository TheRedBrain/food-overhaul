package com.github.theredbrain.foodoverhaul;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;

public class FoodOverhaulClient implements ClientModInitializer {
	@Override
	public void onInitializeClient() {

		// Packets
		ClientPlayNetworking.registerGlobalReceiver(FoodOverhaul.ServerConfigSyncPacket.PACKET_ID, (payload, context) -> {
			FoodOverhaul.serverConfig = payload.serverConfig();
		});
	}
}