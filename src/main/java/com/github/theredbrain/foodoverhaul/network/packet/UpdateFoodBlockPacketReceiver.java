package com.github.theredbrain.foodoverhaul.network.packet;

import com.github.theredbrain.foodoverhaul.block.entity.FoodBlockEntity;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.Text;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class UpdateFoodBlockPacketReceiver implements ServerPlayNetworking.PlayPayloadHandler<UpdateFoodBlockPacket> {
	@Override
	public void receive(UpdateFoodBlockPacket payload, ServerPlayNetworking.Context context) {

		ServerPlayerEntity serverPlayerEntity = context.player();

		if (!serverPlayerEntity.isCreativeLevelTwoOp()) {
			return;
		}

		BlockPos foodBlockPosition = payload.foodBlockPosition();

		FoodBlockEntity.FoodBlockData foodBlockData = payload.foodBlockData();

		World world = serverPlayerEntity.getEntityWorld();

		BlockEntity blockEntity = world.getBlockEntity(foodBlockPosition);
		BlockState blockState = world.getBlockState(foodBlockPosition);

		if (blockEntity instanceof FoodBlockEntity triggeredBeaconBlockEntity) {
			triggeredBeaconBlockEntity.setFoodBlockData(foodBlockData);
			serverPlayerEntity.sendMessage(Text.translatable("hud.message.food_block.update_successful"), true);
			triggeredBeaconBlockEntity.markDirty();
			world.updateListeners(foodBlockPosition, blockState, blockState, Block.NOTIFY_ALL);
		}
	}
}
