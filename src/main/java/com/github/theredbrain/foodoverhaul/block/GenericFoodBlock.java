package com.github.theredbrain.foodoverhaul.block;

import com.github.theredbrain.foodoverhaul.FoodOverhaul;
import com.github.theredbrain.foodoverhaul.block.entity.FoodBlockEntity;
import com.github.theredbrain.foodoverhaul.entity.player.DuckPlayerEntityMixin;
import com.github.theredbrain.foodoverhaul.registry.EntityRegistry;
import com.mojang.serialization.MapCodec;
import net.minecraft.resources.Identifier;

import java.util.Optional;
import net.minecraft.advancements.AdvancementHolder;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.Holder;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.server.ServerAdvancementManager;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.stats.Stats;
import net.minecraft.tags.TagKey;
import net.minecraft.util.RandomSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.ScheduledTickAccess;
import net.minecraft.world.level.block.BaseEntityBlock;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.RenderShape;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.Property;
import net.minecraft.world.level.pathfinder.PathComputationType;
import net.minecraft.world.phys.BlockHitResult;
import org.jspecify.annotations.Nullable;

public class GenericFoodBlock extends BaseEntityBlock {
	public static final MapCodec<GenericFoodBlock> CODEC = simpleCodec(GenericFoodBlock::new);

	public static final Property<Direction> FACING;

	@Override
	protected MapCodec<? extends GenericFoodBlock> codec() {
		return CODEC;
	}

	public GenericFoodBlock(Properties settings) {
		super(settings);
	}

	@Override
	protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
		super.createBlockStateDefinition(builder);
		builder.add(FACING);
	}

	@Nullable
	@Override
	public BlockEntity newBlockEntity(BlockPos pos, BlockState state) {
		return new FoodBlockEntity(pos, state);
	}

	@Override
	public RenderShape getRenderShape(BlockState state) {
		return RenderShape.MODEL;
	}

	@Override
	@Nullable
	public <T extends BlockEntity> BlockEntityTicker<T> getTicker(Level world, BlockState state, BlockEntityType<T> type) {
		return createTickerHelper(type, EntityRegistry.FOOD_BLOCK_ENTITY, FoodBlockEntity::tick);
	}

	@Override
	protected InteractionResult useWithoutItem(BlockState state, Level world, BlockPos pos, Player player, BlockHitResult hit) {
		BlockEntity blockEntity = world.getBlockEntity(pos);
		if (blockEntity instanceof FoodBlockEntity foodBlockEntity && state.getBlock() instanceof GenericFoodBlock genericFoodBlock) {
			if (/*FoodOverhaul.SERVER_CONFIG.enable_food_block_config_screen.get() && */player.isCreative() && player.isShiftKeyDown()) {
				((DuckPlayerEntityMixin) player).foodoverhaul$openFoodBlockScreen(foodBlockEntity);
				return InteractionResult.SUCCESS;
			} else if (genericFoodBlock.canPlayerInteract(world, pos, state, foodBlockEntity, player)) {
				if (world.isClientSide()) {
					if (tryEat(world, pos, state, player, foodBlockEntity).consumesAction()) {
						return InteractionResult.SUCCESS;
					}

					if (player.getItemInHand(InteractionHand.MAIN_HAND).isEmpty()) {
						return InteractionResult.CONSUME;
					}
				}

				return tryEat(world, pos, state, player, foodBlockEntity);
			}
		}
		return super.useWithoutItem(state, world, pos, player, hit);
	}

	@Override
	protected InteractionResult useItemOn(ItemStack stack, BlockState state, Level world, BlockPos pos, Player player, InteractionHand hand, BlockHitResult hit) {
		BlockEntity blockEntity = world.getBlockEntity(pos);
		if (blockEntity instanceof FoodBlockEntity foodBlockEntity && state.getBlock() instanceof GenericFoodBlock genericFoodBlock) {

			FoodBlockEntity.FoodBlockData foodBlockData = foodBlockEntity.getFoodBlockData();

			if (genericFoodBlock.canPlayerInteract(world, pos, state, foodBlockEntity, player)) {

				Item interactionResultItem = BuiltInRegistries.ITEM.getValue(Identifier.parse(foodBlockData.interaction_result_item_identifier()));

				if (interactionResultItem != Items.AIR) {
					Item interactionToolItem = Items.AIR;
					TagKey<Item> tag = null;
					String interaction_tool_item_identifier = foodBlockData.interaction_tool_item_identifier();
					if (interaction_tool_item_identifier.startsWith("#")) {
						String tagIdentifier = interaction_tool_item_identifier.replaceFirst("#", "");
						tag = TagKey.create(Registries.ITEM, Identifier.parse(tagIdentifier));
					} else {
						interactionToolItem = BuiltInRegistries.ITEM.getValue(Identifier.parse(interaction_tool_item_identifier));
					}

					if ((interactionToolItem != Items.AIR && stack.is(interactionToolItem)) || (tag != null && stack.is(tag))) {
						if (stack.getMaxStackSize() == 1) {
//							world.playSound(player, player.getX(), player.getY(), player.getZ(), SoundEvents.ITEM_BOTTLE_FILL, SoundCategory.BLOCKS, 1.0F, 1.0F); // TODO custom id
							stack.hurtAndBreak(1, player, hand.asEquipmentSlot());
//							world.emitGameEvent(player, GameEvent.FLUID_PICKUP, pos); // TODO custom id
						} else {

							if (!player.isCreative()) {
								stack.shrink(1);
							}
//							world.playSound(player, player.getX(), player.getY(), player.getZ(), SoundEvents.ITEM_BOTTLE_FILL, SoundCategory.BLOCKS, 1.0F, 1.0F); // TODO custom id
							player.setItemInHand(hand, stack.isEmpty() ? ItemStack.EMPTY : stack);
//							world.emitGameEvent(player, GameEvent.FLUID_PICKUP, pos); // TODO custom id
						}
						player.getInventory().placeItemBackInInventory(interactionResultItem.getDefaultInstance());
						if (!world.isClientSide()) {
							player.awardStat(Stats.ITEM_USED.get(interactionToolItem));
						}
						this.onSuccessfulItemInteraction(world, pos, state, foodBlockEntity, player);
						return InteractionResult.SUCCESS;
					}
				}
			}
		}
		return super.useItemOn(stack, state, world, pos, player, hand, hit);
	}

	protected static InteractionResult tryEat(LevelAccessor world, BlockPos pos, BlockState state, Player player, FoodBlockEntity foodBlockEntity) {
		if (state.getBlock() instanceof GenericFoodBlock genericFoodBlock) {
			FoodBlockEntity.FoodBlockData foodBlockData = foodBlockEntity.getFoodBlockData();
			Optional<Holder.Reference<MobEffect>> optional_status_effect = BuiltInRegistries.MOB_EFFECT.get(Identifier.parse(foodBlockData.applied_status_effect_identifier()));
			if (optional_status_effect.isPresent()) {
				if (FoodOverhaul.tryEatOverhauledFood(player, optional_status_effect.get())) {
					if (!world.isClientSide()) {
						foodBlockEntity.setRecoveryTimer(0);
						player.addEffect(new MobEffectInstance(
								optional_status_effect.get(),
								foodBlockData.applied_status_effect_duration(),
								foodBlockData.applied_status_effect_amplifier(),
								foodBlockData.applied_status_effect_ambient(),
								foodBlockData.applied_status_effect_show_particles(),
								foodBlockData.applied_status_effect_show_icon()
						));
					}
					genericFoodBlock.onSuccessfulInteraction(world, pos, state, foodBlockEntity, player);
					return InteractionResult.SUCCESS_SERVER;
				}
			}
		}
		return InteractionResult.PASS;
	}

	protected boolean canPlayerInteract(LevelAccessor world, BlockPos pos, BlockState state, FoodBlockEntity foodBlockEntity, Player player) {
		boolean canPlayerInteract = true;
		FoodBlockEntity.FoodBlockData foodBlockData = foodBlockEntity.getFoodBlockData();
		String usePreventingStatusEffectIdentifier = foodBlockData.use_preventing_status_effect_identifier();
		if (!usePreventingStatusEffectIdentifier.isEmpty()) {
			Optional<Holder.Reference<MobEffect>> optional_status_effect = BuiltInRegistries.MOB_EFFECT.get(Identifier.parse(usePreventingStatusEffectIdentifier));
			if (optional_status_effect.isPresent()) {
				canPlayerInteract = !player.hasEffect(optional_status_effect.get());
			}
		}
		String requiredAdvancementIdentifier = foodBlockData.required_advancement_identifier();
		if (canPlayerInteract && !requiredAdvancementIdentifier.isEmpty() && world.getServer() != null && player instanceof ServerPlayer serverPlayerEntity) {
			ServerAdvancementManager advancementLoader = world.getServer().getAdvancements();
			AdvancementHolder advancementEntry = advancementLoader.get(Identifier.parse(usePreventingStatusEffectIdentifier));
			if (advancementEntry != null) {
				canPlayerInteract = serverPlayerEntity.getAdvancements().getOrStartProgress(advancementEntry).isDone();
			}
		}
		return canPlayerInteract;
	}

	protected void onSuccessfulInteraction(LevelAccessor world, BlockPos pos, BlockState state, FoodBlockEntity foodBlockEntity, Player player) {
	}

	protected void onSuccessfulItemInteraction(LevelAccessor world, BlockPos pos, BlockState state, FoodBlockEntity foodBlockEntity, Player player) {
	}

	public static void recoveryTick(Level world, BlockPos pos, BlockState state) {
		if (state.getBlock() instanceof GenericFoodBlock genericFoodBlock) {
			genericFoodBlock.onRecoveryTick(world, pos, state);
		}
	}

	protected void onRecoveryTick(Level world, BlockPos pos, BlockState state) {
	}

	@Override
	public BlockState getStateForPlacement(BlockPlaceContext context) {
		return this.defaultBlockState().setValue(FACING, context.getHorizontalDirection());
	}

	@Override
	protected BlockState updateShape(BlockState state, LevelReader world, ScheduledTickAccess tickView, BlockPos pos, Direction direction, BlockPos neighborPos, BlockState neighborState, RandomSource random) {
		return direction == Direction.DOWN && !state.canSurvive(world, pos) ? Blocks.AIR.defaultBlockState() : super.updateShape(state, world, tickView, pos, direction, neighborPos, neighborState, random);
	}

	@Override
	protected boolean canSurvive(BlockState state, LevelReader world, BlockPos pos) {
		return world.getBlockState(pos.below()).isSolid();
	}

	@Override
	protected boolean hasAnalogOutputSignal(BlockState state) {
		return true;
	}

	@Override
	protected boolean isPathfindable(BlockState state, PathComputationType type) {
		return false;
	}

	static {
		FACING = BlockStateProperties.HORIZONTAL_FACING;
	}
}
