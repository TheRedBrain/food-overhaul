package com.github.theredbrain.foodoverhaul.network.packet;

import com.github.theredbrain.foodoverhaul.block.entity.FoodBlockEntity;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;

public class UpdateFoodBlockPacketReceiver implements ServerPlayNetworking.PlayPayloadHandler<UpdateFoodBlockPacket> {
	@Override
	public void receive(UpdateFoodBlockPacket payload, ServerPlayNetworking.Context context) {

		ServerPlayer serverPlayerEntity = context.player();

		if (!serverPlayerEntity.canUseGameMasterBlocks()) {
			return;
		}

		BlockPos foodBlockPosition = payload.foodBlockPosition();

		FoodBlockEntity.FoodBlockData foodBlockData = payload.foodBlockData();

		Level world = serverPlayerEntity.level();

		BlockEntity blockEntity = world.getBlockEntity(foodBlockPosition);
		BlockState blockState = world.getBlockState(foodBlockPosition);

		if (blockEntity instanceof FoodBlockEntity triggeredBeaconBlockEntity) {
			triggeredBeaconBlockEntity.setFoodBlockData(foodBlockData);
			serverPlayerEntity.sendOverlayMessage(Component.translatable("hud.message.food_block.update_successful"));
			triggeredBeaconBlockEntity.setChanged();
			world.sendBlockUpdated(foodBlockPosition, blockState, blockState, Block.UPDATE_ALL);
		}
	}
}
