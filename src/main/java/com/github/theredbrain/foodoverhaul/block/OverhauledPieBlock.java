package com.github.theredbrain.foodoverhaul.block;

import com.github.theredbrain.foodoverhaul.block.entity.FoodBlockEntity;
import com.mojang.serialization.MapCodec;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.ShapeContext;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.IntProperty;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;
import net.minecraft.world.WorldAccess;
import net.minecraft.world.event.GameEvent;

public class OverhauledPieBlock extends GenericFoodBlock {
	public static final MapCodec<OverhauledPieBlock> CODEC = createCodec(OverhauledPieBlock::new);

	public static final IntProperty BITES;
	protected static final VoxelShape SHAPE;

	@Override
	protected MapCodec<OverhauledPieBlock> getCodec() {
		return CODEC;
	}

	public OverhauledPieBlock(Settings settings) {
		super(settings);
		this.setDefaultState(this.stateManager.getDefaultState().with(BITES, 0));
	}

	@Override
	protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
		super.appendProperties(builder);
		builder.add(BITES);
	}

	@Override
	public VoxelShape getOutlineShape(BlockState state, BlockView level, BlockPos pos, ShapeContext context) {
		return SHAPE;
	}

	@Override
	protected void onSuccessfulInteraction(WorldAccess world, BlockPos pos, BlockState state, FoodBlockEntity foodBlockEntity, PlayerEntity player) {

		super.onSuccessfulInteraction(world, pos, state, foodBlockEntity, player);

		int i = state.get(BITES);
		world.emitGameEvent(player, GameEvent.EAT, pos);
		if (!foodBlockEntity.getFoodBlockData().infinite_uses()) {
			if (i < getMaxBites() - 1) {
				world.setBlockState(pos, state.with(BITES, i + 1), Block.NOTIFY_ALL);
			} else {
				world.removeBlock(pos, false);
				world.emitGameEvent(player, GameEvent.BLOCK_DESTROY, pos);
			}
		}
	}

	protected void onSuccessfulItemInteraction(WorldAccess world, BlockPos pos, BlockState state, FoodBlockEntity foodBlockEntity, PlayerEntity player) {

		super.onSuccessfulItemInteraction(world, pos, state, foodBlockEntity, player);

		int i = state.get(BITES);
		world.playSound(player, pos, SoundEvents.BLOCK_WOOL_BREAK, SoundCategory.BLOCKS, 1.0F, 1.0F);
		world.emitGameEvent(player, GameEvent.EAT, pos);
		if (!foodBlockEntity.getFoodBlockData().infinite_uses()) {
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

	public int getMaxBites() {
		return 4;
	}

	@Override
	protected int getComparatorOutput(BlockState state, World world, BlockPos pos) {
		return this.getMaxBites() - (Integer) state.get(BITES);
	}

	static {
		BITES = IntProperty.of("bites", 0, 3);
		SHAPE = Block.createCuboidShape(2.0, 0.0, 2.0, 14.0, 4.0, 14.0);
	}
}
