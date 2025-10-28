package com.github.theredbrain.foodoverhaul.block;

import com.github.theredbrain.foodoverhaul.FoodOverhaul;
import com.github.theredbrain.foodoverhaul.block.entity.FoodBlockEntity;
import com.github.theredbrain.foodoverhaul.entity.player.DuckPlayerEntityMixin;
import com.github.theredbrain.foodoverhaul.registry.EntityRegistry;
import com.mojang.serialization.MapCodec;
import net.minecraft.advancement.AdvancementEntry;
import net.minecraft.block.Block;
import net.minecraft.block.BlockRenderType;
import net.minecraft.block.BlockState;
import net.minecraft.block.BlockWithEntity;
import net.minecraft.block.Blocks;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.BlockEntityTicker;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.entity.ai.pathing.NavigationType;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.registry.Registries;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.registry.tag.TagKey;
import net.minecraft.server.ServerAdvancementLoader;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.stat.Stats;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.Properties;
import net.minecraft.state.property.Property;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.Identifier;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.World;
import net.minecraft.world.WorldAccess;
import net.minecraft.world.WorldView;
import net.minecraft.world.tick.ScheduledTickView;
import org.jetbrains.annotations.Nullable;

import java.util.Optional;

public class GenericFoodBlock extends BlockWithEntity {
	public static final MapCodec<GenericFoodBlock> CODEC = createCodec(GenericFoodBlock::new);

	public static final Property<Direction> FACING;

	@Override
	protected MapCodec<? extends GenericFoodBlock> getCodec() {
		return CODEC;
	}

	public GenericFoodBlock(Settings settings) {
		super(settings);
	}

	@Override
	protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
		super.appendProperties(builder);
		builder.add(FACING);
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
			if (FoodOverhaul.SERVER_CONFIG.enable_food_block_config_screen.get() && player.isCreativeLevelTwoOp() && player.isSneaking()) {
				((DuckPlayerEntityMixin) player).foodoverhaul$openFoodBlockScreen(foodBlockEntity);
				return ActionResult.SUCCESS;
			} else {
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
		}
		return super.onUse(state, world, pos, player, hit);
	}

	@Override
	protected ActionResult onUseWithItem(ItemStack stack, BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, BlockHitResult hit) {
		BlockEntity blockEntity = world.getBlockEntity(pos);
		if (blockEntity instanceof FoodBlockEntity foodBlockEntity && state.getBlock() instanceof GenericFoodBlock genericFoodBlock) {

			FoodBlockEntity.FoodBlockData foodBlockData = foodBlockEntity.getFoodBlockData();

			if (genericFoodBlock.canPlayerInteract(world, pos, state, foodBlockEntity, player)) {

				Item interactionResultItem = Registries.ITEM.get(Identifier.of(foodBlockData.interaction_result_item_identifier()));

				if (interactionResultItem != Items.AIR) {
					Item interactionToolItem = Items.AIR;
					TagKey<Item> tag = null;
					String interaction_tool_item_identifier = foodBlockData.interaction_tool_item_identifier();
					if (interaction_tool_item_identifier.startsWith("#")) {
						String tagIdentifier = interaction_tool_item_identifier.replaceFirst("#", "");
						tag = TagKey.of(RegistryKeys.ITEM, Identifier.of(tagIdentifier));
					} else {
						interactionToolItem = Registries.ITEM.get(Identifier.of(interaction_tool_item_identifier));
					}

					if ((interactionToolItem != Items.AIR && stack.isOf(interactionToolItem)) || (tag != null && stack.isIn(tag))) {
						if (stack.getMaxCount() == 1) {
//							world.playSound(player, player.getX(), player.getY(), player.getZ(), SoundEvents.ITEM_BOTTLE_FILL, SoundCategory.BLOCKS, 1.0F, 1.0F); // TODO custom id
							stack.damage(1, player, hand.getEquipmentSlot());
//							world.emitGameEvent(player, GameEvent.FLUID_PICKUP, pos); // TODO custom id
						} else {

							if (!player.isCreative()) {
								stack.decrement(1);
							}
//							world.playSound(player, player.getX(), player.getY(), player.getZ(), SoundEvents.ITEM_BOTTLE_FILL, SoundCategory.BLOCKS, 1.0F, 1.0F); // TODO custom id
							player.setStackInHand(hand, stack.isEmpty() ? ItemStack.EMPTY : stack);
//							world.emitGameEvent(player, GameEvent.FLUID_PICKUP, pos); // TODO custom id
						}
						player.getInventory().offerOrDrop(interactionResultItem.getDefaultStack());
						if (!world.isClient()) {
							player.incrementStat(Stats.USED.getOrCreateStat(interactionToolItem));
						}
						this.onSuccessfulItemInteraction(world, pos, state, foodBlockEntity, player);
						return ActionResult.SUCCESS;
					}
				}
			}
		}
		return super.onUseWithItem(stack, state, world, pos, player, hand, hit);
	}

	protected static ActionResult tryEat(WorldAccess world, BlockPos pos, BlockState state, PlayerEntity player, FoodBlockEntity foodBlockEntity) {
		if (state.getBlock() instanceof GenericFoodBlock genericFoodBlock) {
			FoodBlockEntity.FoodBlockData foodBlockData = foodBlockEntity.getFoodBlockData();
			Optional<RegistryEntry.Reference<StatusEffect>> optional_status_effect = Registries.STATUS_EFFECT.getEntry(Identifier.of(foodBlockData.applied_status_effect_identifier()));
			if (optional_status_effect.isPresent()) {
				if (FoodOverhaul.tryEatOverhauledFood(player, optional_status_effect.get())) {
					if (!world.isClient()) {
						foodBlockEntity.setRecoveryTimer(0);
						player.addStatusEffect(new StatusEffectInstance(
								optional_status_effect.get(),
								foodBlockData.applied_status_effect_duration(),
								foodBlockData.applied_status_effect_amplifier(),
								foodBlockData.applied_status_effect_ambient(),
								foodBlockData.applied_status_effect_show_particles(),
								foodBlockData.applied_status_effect_show_icon()
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
		FoodBlockEntity.FoodBlockData foodBlockData = foodBlockEntity.getFoodBlockData();
		String usePreventingStatusEffectIdentifier = foodBlockData.use_preventing_status_effect_identifier();
		if (!usePreventingStatusEffectIdentifier.isEmpty()) {
			Optional<RegistryEntry.Reference<StatusEffect>> optional_status_effect = Registries.STATUS_EFFECT.getEntry(Identifier.of(usePreventingStatusEffectIdentifier));
			if (optional_status_effect.isPresent()) {
				canPlayerInteract = !player.hasStatusEffect(optional_status_effect.get());
			}
		}
		String requiredAdvancementIdentifier = foodBlockData.required_advancement_identifier();
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

	protected void onSuccessfulItemInteraction(WorldAccess world, BlockPos pos, BlockState state, FoodBlockEntity foodBlockEntity, PlayerEntity player) {
	}

	public static void recoveryTick(World world, BlockPos pos, BlockState state) {
		if (state.getBlock() instanceof GenericFoodBlock genericFoodBlock) {
			genericFoodBlock.onRecoveryTick(world, pos, state);
		}
	}

	protected void onRecoveryTick(World world, BlockPos pos, BlockState state) {
	}

	@Override
	public BlockState getPlacementState(ItemPlacementContext context) {
		return this.getDefaultState().with(FACING, context.getHorizontalPlayerFacing());
	}

	@Override
	protected BlockState getStateForNeighborUpdate(BlockState state, WorldView world, ScheduledTickView tickView, BlockPos pos, Direction direction, BlockPos neighborPos, BlockState neighborState, Random random) {
		return direction == Direction.DOWN && !state.canPlaceAt(world, pos) ? Blocks.AIR.getDefaultState() : super.getStateForNeighborUpdate(state, world, tickView, pos, direction, neighborPos, neighborState, random);
	}

	@Override
	protected boolean canPlaceAt(BlockState state, WorldView world, BlockPos pos) {
		return world.getBlockState(pos.down()).isSolid();
	}

	@Override
	protected boolean hasComparatorOutput(BlockState state) {
		return true;
	}

	@Override
	protected boolean canPathfindThrough(BlockState state, NavigationType type) {
		return false;
	}

	static {
		FACING = Properties.HORIZONTAL_FACING;
	}
}
