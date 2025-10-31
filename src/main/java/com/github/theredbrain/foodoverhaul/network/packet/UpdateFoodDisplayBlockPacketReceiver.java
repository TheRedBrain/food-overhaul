package com.github.theredbrain.foodoverhaul.network.packet;

import com.github.theredbrain.foodoverhaul.block.entity.FoodBlockEntity;
import com.github.theredbrain.foodoverhaul.block.entity.FoodDisplayBlockEntity;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.Text;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class UpdateFoodDisplayBlockPacketReceiver implements ServerPlayNetworking.PlayPayloadHandler<UpdateFoodDisplayBlockPacket> {
	@Override
	public void receive(UpdateFoodDisplayBlockPacket payload, ServerPlayNetworking.Context context) {

		ServerPlayerEntity serverPlayerEntity = context.player();

		if (!serverPlayerEntity.isCreativeLevelTwoOp()) {
			return;
		}

		BlockPos foodBlockPosition = payload.foodDisplayBlockPosition();

		FoodDisplayBlockEntity.FoodDisplayBlockData foodDisplayBlockData = payload.foodDisplayBlockData();

		World world = serverPlayerEntity.getEntityWorld();

		BlockEntity blockEntity = world.getBlockEntity(foodBlockPosition);
		BlockState blockState = world.getBlockState(foodBlockPosition);

		if (blockEntity instanceof FoodDisplayBlockEntity foodDisplayBlockEntity) {
			foodDisplayBlockEntity.setFoodDisplayBlockData(foodDisplayBlockData);
			serverPlayerEntity.sendMessage(Text.translatable("hud.message.food_display_block.update_successful"), true);
			foodDisplayBlockEntity.markDirty();
			world.updateListeners(foodBlockPosition, blockState, blockState, Block.NOTIFY_ALL);
		}
	}
}
