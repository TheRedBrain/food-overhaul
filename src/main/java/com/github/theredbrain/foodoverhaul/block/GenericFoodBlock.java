package com.github.theredbrain.foodoverhaul.block;

import com.github.theredbrain.foodoverhaul.FoodOverhaul;
import com.github.theredbrain.foodoverhaul.block.entity.FoodBlockEntity;
import com.github.theredbrain.foodoverhaul.registry.EntityRegistry;
import com.mojang.serialization.MapCodec;
import net.minecraft.advancement.AdvancementEntry;
import net.minecraft.block.BlockRenderType;
import net.minecraft.block.BlockState;
import net.minecraft.block.BlockWithEntity;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.BlockEntityTicker;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.registry.Registries;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.server.ServerAdvancementLoader;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.Identifier;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.WorldAccess;
import org.jetbrains.annotations.Nullable;

import java.util.Optional;

public class GenericFoodBlock extends BlockWithEntity {
	public static final MapCodec<GenericFoodBlock> CODEC = createCodec(GenericFoodBlock::new);

	public GenericFoodBlock(Settings settings) {
		super(settings);
	}

	@Override
	protected MapCodec<? extends GenericFoodBlock> getCodec() {
		return CODEC;
	}

	@Nullable
	@Override
	public BlockEntity createBlockEntity(BlockPos pos, BlockState state) {
		return new FoodBlockEntity(pos, state);
	}

	@Override
	public BlockRenderType getRenderType(BlockState state) {
		return BlockRenderType.MODEL;
	}

	@Override
	@Nullable
	public <T extends BlockEntity> BlockEntityTicker<T> getTicker(World world, BlockState state, BlockEntityType<T> type) {
		return validateTicker(type, EntityRegistry.FOOD_BLOCK_ENTITY, FoodBlockEntity::tick);
	}

	@Override
	protected ActionResult onUse(BlockState state, World world, BlockPos pos, PlayerEntity player, BlockHitResult hit) {
		BlockEntity blockEntity = world.getBlockEntity(pos);
		if (blockEntity instanceof FoodBlockEntity foodBlockEntity) {
			if (world.isClient()) {
				if (tryEat(world, pos, state, player, foodBlockEntity).isAccepted()) {
					return ActionResult.SUCCESS;
				}

				if (player.getStackInHand(Hand.MAIN_HAND).isEmpty()) {
					return ActionResult.CONSUME;
				}
			}

			return tryEat(world, pos, state, player, foodBlockEntity);
		}
		return super.onUse(state, world, pos, player, hit);
	}

	protected static ActionResult tryEat(WorldAccess world, BlockPos pos, BlockState state, PlayerEntity player, FoodBlockEntity foodBlockEntity) {
		if (state.getBlock() instanceof GenericFoodBlock genericFoodBlock) {
			Optional<RegistryEntry.Reference<StatusEffect>> optional_status_effect = Registries.STATUS_EFFECT.getEntry(Identifier.of(foodBlockEntity.getAppliedStatusEffectIdentifier()));
			if (optional_status_effect.isPresent()) {
				if (genericFoodBlock.canPlayerInteract(world, pos, state, foodBlockEntity, player) && FoodOverhaul.tryEatOverhauledFood(player, optional_status_effect.get())) {
					if (!world.isClient()) {
						foodBlockEntity.setRecoveryTimer(0);
						player.addStatusEffect(new StatusEffectInstance(
								optional_status_effect.get(),
								foodBlockEntity.getDuration(),
								foodBlockEntity.getAmplifier(),
								foodBlockEntity.getAmbient(),
								foodBlockEntity.getShowParticles(),
								foodBlockEntity.getShowIcon()
						));
					}
					genericFoodBlock.onSuccessfulInteraction(world, pos, state, foodBlockEntity, player);
					return ActionResult.SUCCESS_SERVER;
				}
			}
		}
		return ActionResult.PASS;
	}

	protected boolean canPlayerInteract(WorldAccess world, BlockPos pos, BlockState state, FoodBlockEntity foodBlockEntity, PlayerEntity player) {
		boolean canPlayerInteract = true;
		String usePreventingStatusEffectIdentifier = foodBlockEntity.getUsePreventingStatusEffectIdentifier();
		if (!usePreventingStatusEffectIdentifier.isEmpty()) {
			Optional<RegistryEntry.Reference<StatusEffect>> optional_status_effect = Registries.STATUS_EFFECT.getEntry(Identifier.of(usePreventingStatusEffectIdentifier));
			if (optional_status_effect.isPresent()) {
				canPlayerInteract = player.hasStatusEffect(optional_status_effect.get());
			}
		}
		String requiredAdvancementIdentifier = foodBlockEntity.getRequiredAdvancementIdentifier();
		if (canPlayerInteract && !requiredAdvancementIdentifier.isEmpty() && world.getServer() != null && player instanceof ServerPlayerEntity serverPlayerEntity) {
			ServerAdvancementLoader advancementLoader = world.getServer().getAdvancementLoader();
			AdvancementEntry advancementEntry = advancementLoader.get(Identifier.of(usePreventingStatusEffectIdentifier));
			if (advancementEntry != null) {
				canPlayerInteract = serverPlayerEntity.getAdvancementTracker().getProgress(advancementEntry).isDone();
			}
		}
		return canPlayerInteract;
	}

	protected void onSuccessfulInteraction(WorldAccess world, BlockPos pos, BlockState state, FoodBlockEntity foodBlockEntity, PlayerEntity player) {
	}

	public static void recoveryTick(World world, BlockPos pos, BlockState state) {
		if (state.getBlock() instanceof GenericFoodBlock genericFoodBlock) {
			genericFoodBlock.onRecoveryTick(world, pos, state);
		}
	}

	protected void onRecoveryTick(World world, BlockPos pos, BlockState state) {
	}

}
