package com.github.theredbrain.foodoverhaul.block;

import com.github.theredbrain.foodoverhaul.block.entity.FoodBlockEntity;
import com.mojang.serialization.MapCodec;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.ShapeContext;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.pathing.NavigationType;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.item.ItemStack;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.stat.Stats;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.IntProperty;
import net.minecraft.state.property.Properties;
import net.minecraft.state.property.Property;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.random.Random;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;
import net.minecraft.world.WorldAccess;
import net.minecraft.world.WorldView;
import net.minecraft.world.event.GameEvent;
import net.minecraft.world.tick.ScheduledTickView;
import org.jetbrains.annotations.Nullable;

public class OverhauledPieBlock extends GenericFoodBlock {
	public static final MapCodec<OverhauledPieBlock> CODEC = createCodec(OverhauledPieBlock::new);

	public static final Property<Direction> FACING;
	public static final IntProperty BITES;
	protected static final VoxelShape SHAPE;

	@Override
	protected MapCodec<OverhauledPieBlock> getCodec() {
		return CODEC;
	}

	public OverhauledPieBlock(Settings settings) {
		super(settings);
		this.setDefaultState((BlockState) ((BlockState) this.stateManager.getDefaultState()).with(BITES, 0));
	}

	@Override
	protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
		builder.add(FACING, BITES);
	}

	@Override
	public VoxelShape getOutlineShape(BlockState state, BlockView level, BlockPos pos, ShapeContext context) {
		return SHAPE;
	}

	@Override
	public BlockState getPlacementState(ItemPlacementContext context) {
		return (BlockState)this.getDefaultState().with(FACING, context.getHorizontalPlayerFacing());
	}

	@Override
	public void onPlaced(World world, BlockPos pos, BlockState state, @Nullable LivingEntity placer, ItemStack itemStack) {
		if (!world.isClient()) {
			BlockEntity blockEntity = world.getBlockEntity(pos);
			if (blockEntity instanceof FoodBlockEntity foodBlockEntity) {
				foodBlockEntity.setAppliedStatusEffectIdentifier("foodoverhaulvanillafoods:cake_food_effect");
				foodBlockEntity.setAppliedStatusEffectDuration(12000);
				foodBlockEntity.setInteractionResultItemIdentifier("farmersdelight:cake_slice");
				foodBlockEntity.setInteractionToolItemIdentifier("#farmersdelight:tools/knives");
				foodBlockEntity.markDirty();
				world.updateListeners(pos, state, state, Block.NOTIFY_ALL);
			}
		}
	}

	@Override
	protected void onSuccessfulInteraction(WorldAccess world, BlockPos pos, BlockState state, FoodBlockEntity foodBlockEntity, PlayerEntity player) {

		super.onSuccessfulInteraction(world, pos, state, foodBlockEntity, player);

		player.incrementStat(Stats.EAT_CAKE_SLICE);
		int i = state.get(BITES);
		world.emitGameEvent(player, GameEvent.EAT, pos);
		if (!foodBlockEntity.getInfiniteUses()) {
			if (i < 6) {
				world.setBlockState(pos, state.with(BITES, i + 1), Block.NOTIFY_ALL);
			} else {
				world.removeBlock(pos, false);
				world.emitGameEvent(player, GameEvent.BLOCK_DESTROY, pos);
			}
		}
	}

	protected void onSuccessfulItemInteraction(WorldAccess world, BlockPos pos, BlockState state, FoodBlockEntity foodBlockEntity, PlayerEntity player) {

		super.onSuccessfulItemInteraction(world, pos, state, foodBlockEntity, player);

		player.incrementStat(Stats.EAT_CAKE_SLICE);
		int i = state.get(BITES);
		world.playSound(player, pos, SoundEvents.BLOCK_WOOL_BREAK, SoundCategory.BLOCKS, 1.0F, 1.0F);
		world.emitGameEvent(player, GameEvent.EAT, pos);
		if (!foodBlockEntity.getInfiniteUses()) {
			if (i < this.getMaxBites() - 1) {
				world.setBlockState(pos, state.with(BITES, i + 1), Block.NOTIFY_ALL);
			} else {
				world.removeBlock(pos, false);
				world.emitGameEvent(player, GameEvent.BLOCK_DESTROY, pos);
			}
		}
	}

	@Override
	protected void onRecoveryTick(World world, BlockPos pos, BlockState state) {

		int i = state.get(BITES);
		if (i > 0) {
			world.setBlockState(pos, state.with(BITES, i - 1), Block.NOTIFY_ALL);
		}
	}

	@Override
	protected BlockState getStateForNeighborUpdate(BlockState state, WorldView world, ScheduledTickView tickView, BlockPos pos, Direction direction, BlockPos neighborPos, BlockState neighborState, Random random) {
		return direction == Direction.DOWN && !state.canPlaceAt(world, pos) ? Blocks.AIR.getDefaultState() : super.getStateForNeighborUpdate(state, world, tickView, pos, direction, neighborPos, neighborState, random);
	}

	@Override
	protected boolean canPlaceAt(BlockState state, WorldView world, BlockPos pos) {
		return world.getBlockState(pos.down()).isSolid();
	}

	public int getMaxBites() {
		return 4;
	}

	@Override
	protected int getComparatorOutput(BlockState state, World world, BlockPos pos, Direction direction) {
		return this.getMaxBites() - (Integer)state.get(BITES);
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
		BITES = IntProperty.of("bites", 0, 3);
		SHAPE = Block.createCuboidShape(2.0, 0.0, 2.0, 14.0, 4.0, 14.0);
	}
}
