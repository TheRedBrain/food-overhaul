package com.github.theredbrain.foodoverhaul.network.packet;

import com.github.theredbrain.foodoverhaul.block.entity.FoodDisplayBlockEntity;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;

public class UpdateFoodDisplayBlockPacketReceiver implements ServerPlayNetworking.PlayPayloadHandler<UpdateFoodDisplayBlockPacket> {
	@Override
	public void receive(UpdateFoodDisplayBlockPacket payload, ServerPlayNetworking.Context context) {

		ServerPlayer serverPlayerEntity = context.player();

		if (!serverPlayerEntity.canUseGameMasterBlocks()) {
			return;
		}

		BlockPos foodBlockPosition = payload.foodDisplayBlockPosition();

		FoodDisplayBlockEntity.FoodDisplayBlockData foodDisplayBlockData = payload.foodDisplayBlockData();

		Level world = serverPlayerEntity.level();

		BlockEntity blockEntity = world.getBlockEntity(foodBlockPosition);
		BlockState blockState = world.getBlockState(foodBlockPosition);

		if (blockEntity instanceof FoodDisplayBlockEntity foodDisplayBlockEntity) {
			foodDisplayBlockEntity.setFoodDisplayBlockData(foodDisplayBlockData);
			serverPlayerEntity.sendOverlayMessage(Component.translatable("hud.message.food_display_block.update_successful"));
			foodDisplayBlockEntity.setChanged();
			world.sendBlockUpdated(foodBlockPosition, blockState, blockState, Block.UPDATE_ALL);
		}
	}
}
