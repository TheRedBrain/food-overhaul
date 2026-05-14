package com.github.theredbrain.foodoverhaul.block;

import com.github.theredbrain.foodoverhaul.block.entity.FoodBlockEntity;
import com.mojang.serialization.MapCodec;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;

@Deprecated
public class OverhauledPieBlock extends GenericFoodBlock {
	public static final MapCodec<OverhauledPieBlock> CODEC = simpleCodec(OverhauledPieBlock::new);

	public static final IntegerProperty BITES;
	protected static final VoxelShape SHAPE;

	@Override
	protected MapCodec<OverhauledPieBlock> codec() {
		return CODEC;
	}

	public OverhauledPieBlock(Properties settings) {
		super(settings);
		this.registerDefaultState(this.stateDefinition.any().setValue(BITES, 0));
	}

	@Override
	protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
		super.createBlockStateDefinition(builder);
		builder.add(BITES);
	}

	@Override
	public VoxelShape getShape(BlockState state, BlockGetter level, BlockPos pos, CollisionContext context) {
		return SHAPE;
	}

	@Override
	protected void onSuccessfulInteraction(LevelAccessor world, BlockPos pos, BlockState state, FoodBlockEntity foodBlockEntity, Player player) {

		super.onSuccessfulInteraction(world, pos, state, foodBlockEntity, player);

		int i = state.getValue(BITES);
		world.gameEvent(player, GameEvent.EAT, pos);
		if (!foodBlockEntity.getFoodBlockData().reduce_uses()) {
			if (i < getMaxBites() - 1) {
				world.setBlock(pos, state.setValue(BITES, i + 1), Block.UPDATE_ALL);
			} else {
				world.removeBlock(pos, false);
				world.gameEvent(player, GameEvent.BLOCK_DESTROY, pos);
			}
		}
	}

	protected void onSuccessfulItemInteraction(LevelAccessor world, BlockPos pos, BlockState state, FoodBlockEntity foodBlockEntity, Player player) {

		super.onSuccessfulItemInteraction(world, pos, state, foodBlockEntity, player);

		int i = state.getValue(BITES);
		world.playSound(player, pos, SoundEvents.WOOL_BREAK, SoundSource.BLOCKS, 1.0F, 1.0F);
		world.gameEvent(player, GameEvent.BLOCK_CHANGE, pos);
		if (!foodBlockEntity.getFoodBlockData().reduce_uses()) {
			if (i < this.getMaxBites() - 1) {
				world.setBlock(pos, state.setValue(BITES, i + 1), Block.UPDATE_ALL);
			} else {
				world.removeBlock(pos, false);
				world.gameEvent(player, GameEvent.BLOCK_DESTROY, pos);
			}
		}
	}

	@Override
	protected void onRecoveryTick(Level world, BlockPos pos, BlockState state) {

		int i = state.getValue(BITES);
		if (i > 0) {
			world.setBlock(pos, state.setValue(BITES, i - 1), Block.UPDATE_ALL);
		}
	}

	public int getMaxBites() {
		return 4;
	}

	@Override
	protected int getAnalogOutputSignal(BlockState state, Level world, BlockPos pos, Direction direction) {
		return this.getMaxBites() - (Integer) state.getValue(BITES);
	}

	static {
		BITES = IntegerProperty.create("bites", 0, 3);
		SHAPE = Block.box(2.0, 0.0, 2.0, 14.0, 4.0, 14.0);
	}
}
