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

		String appliedStatusEffectIdentifier = payload.appliedStatusEffectIdentifier();
		int appliedStatusEffectAmplifier = payload.appliedStatusEffectAmplifier();
		int appliedStatusEffectDuration = payload.appliedStatusEffectDuration();
		boolean appliedStatusEffectAmbient = payload.appliedStatusEffectAmbient();
		boolean appliedStatusEffectShowParticles = payload.appliedStatusEffectShowParticles();
		boolean appliedStatusEffectShowIcon = payload.appliedStatusEffectShowIcon();

		String interactionResultItemIdentifier = payload.interactionResultItemIdentifier();
		String interactionToolItemIdentifier = payload.interactionToolItemIdentifier();
		String usePreventingStatusEffectIdentifier = payload.usePreventingStatusEffectIdentifier();
		String requiredAdvancementIdentifier = payload.requiredAdvancementIdentifier();
		int recoveryTimerThreshold = payload.recoveryTimerThreshold();
		boolean infiniteUses = payload.infiniteUses();

		World world = serverPlayerEntity.getEntityWorld();

		boolean updateSuccessful = true;

		BlockEntity blockEntity = world.getBlockEntity(foodBlockPosition);
		BlockState blockState = world.getBlockState(foodBlockPosition);

		if (blockEntity instanceof FoodBlockEntity triggeredBeaconBlockEntity) {
			if (!triggeredBeaconBlockEntity.setAppliedStatusEffectIdentifier(appliedStatusEffectIdentifier)) {
				serverPlayerEntity.sendMessage(Text.translatable("food_block.appliedStatusEffectIdentifier.invalid"), false);
				updateSuccessful = false;
			}
			if (!triggeredBeaconBlockEntity.setAppliedStatusEffectAmplifier(appliedStatusEffectAmplifier)) {
				serverPlayerEntity.sendMessage(Text.translatable("food_block.appliedStatusEffectAmplifier.invalid"), false);
				updateSuccessful = false;
			}
			triggeredBeaconBlockEntity.setAppliedStatusEffectDuration(appliedStatusEffectDuration);
			triggeredBeaconBlockEntity.setAppliedStatusEffectAmbient(appliedStatusEffectAmbient);
			triggeredBeaconBlockEntity.setAppliedStatusEffectShowParticles(appliedStatusEffectShowParticles);
			triggeredBeaconBlockEntity.setAppliedStatusEffectShowIcon(appliedStatusEffectShowIcon);


			triggeredBeaconBlockEntity.setInteractionResultItemIdentifier(interactionResultItemIdentifier);
			triggeredBeaconBlockEntity.setInteractionToolItemIdentifier(interactionToolItemIdentifier);
			triggeredBeaconBlockEntity.setUsePreventingStatusEffectIdentifier(usePreventingStatusEffectIdentifier);
			triggeredBeaconBlockEntity.setRequiredAdvancementIdentifier(requiredAdvancementIdentifier);
			triggeredBeaconBlockEntity.setRecoveryTimerThreshold(recoveryTimerThreshold);
			triggeredBeaconBlockEntity.setInfiniteUses(infiniteUses);

			if (updateSuccessful) {
				serverPlayerEntity.sendMessage(Text.translatable("hud.message.food_block.update_successful"), true);
			}
			triggeredBeaconBlockEntity.markDirty();
			world.updateListeners(foodBlockPosition, blockState, blockState, Block.NOTIFY_ALL);
		}
	}
}
