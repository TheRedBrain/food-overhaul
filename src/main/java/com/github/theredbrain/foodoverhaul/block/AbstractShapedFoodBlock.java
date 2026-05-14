package com.github.theredbrain.foodoverhaul.block;

import com.github.theredbrain.foodoverhaul.block.entity.FoodBlockEntity;
import com.mojang.serialization.MapCodec;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.ScheduledTickAccess;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.phys.shapes.BooleanOp;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.jspecify.annotations.Nullable;

import java.util.ArrayList;
import java.util.Collections;
import java.util.EnumMap;
import java.util.IdentityHashMap;
import java.util.List;
import java.util.Map;

public abstract class AbstractShapedFoodBlock extends GenericFoodBlock {
	private static final Map<VoxelShape[], VoxelShape[][]> PLATED_SHAPE_CACHE = new IdentityHashMap<>();
	private static final Map<VoxelShape[], VoxelShape[][]> ROTATED_SHAPE_CACHE = new IdentityHashMap<>();

	protected final VoxelShape[][] combinedShapes;

	@Override
	protected abstract MapCodec<? extends AbstractShapedFoodBlock> codec();

	public AbstractShapedFoodBlock(Properties settings, VoxelShape[] foodShapes, @Nullable VoxelShape containerShape) {
		super(settings);
		this.combinedShapes = containerShape == null ? buildRotatedFoodShapes(foodShapes) : buildPlatedFoodShapes(foodShapes, containerShape);
	}

	@Override
	public VoxelShape getShape(BlockState state, BlockGetter level, BlockPos pos, CollisionContext context) {
		return this.combinedShapes[getMaxBites() - this.getBites(state)][state.getValue(FACING).get2DDataValue()];
	}

	@Override
	protected void onSuccessfulInteraction(LevelAccessor levelAccessor, BlockPos pos, BlockState state, FoodBlockEntity foodBlockEntity, Player player) {

		super.onSuccessfulInteraction(levelAccessor, pos, state, foodBlockEntity, player);

		int i = this.getBites(state);
		levelAccessor.gameEvent(player, GameEvent.EAT, pos);
		if (foodBlockEntity.getFoodBlockData().reduce_uses()) {
			if (i < this.getMaxBites() - 1) {
				levelAccessor.setBlock(pos, this.setBites(state, i + 1), Block.UPDATE_ALL);
			} else {
				levelAccessor.removeBlock(pos, false);
				levelAccessor.gameEvent(player, GameEvent.BLOCK_DESTROY, pos);
			}
		}
	}

	@Override
	protected void onSuccessfulItemInteraction(LevelAccessor levelAccessor, BlockPos pos, BlockState state, FoodBlockEntity foodBlockEntity, Player player) {

		super.onSuccessfulItemInteraction(levelAccessor, pos, state, foodBlockEntity, player);

		int i = this.getBites(state);
		levelAccessor.playSound(player, pos, SoundEvents.WOOL_BREAK, SoundSource.BLOCKS, 1.0F, 1.0F);
		levelAccessor.gameEvent(player, GameEvent.BLOCK_CHANGE, pos);
		if (foodBlockEntity.getFoodBlockData().reduce_uses()) {
			if (i < this.getMaxBites() - 1) {
				levelAccessor.setBlock(pos, this.setBites(state, i + 1), Block.UPDATE_ALL);
			} else {
				levelAccessor.removeBlock(pos, false);
				levelAccessor.gameEvent(player, GameEvent.BLOCK_DESTROY, pos);
			}
		}
	}

	@Override
	protected boolean isBlockInteractable(LevelAccessor levelAccessor, BlockPos pos, BlockState state, FoodBlockEntity foodBlockEntity, Player player) {
		return super.isBlockInteractable(levelAccessor, pos, state, foodBlockEntity, player) && foodBlockEntity.getFoodBlockData().consume_last_use() || this.getBites(state) < this.getMaxBites() - 1;
	}

	protected boolean providesEffectsOnInteraction(LevelAccessor levelAccessor, BlockPos pos, BlockState state, FoodBlockEntity foodBlockEntity) {
		return this.getBites(state) < this.getMaxBites() - 1 || foodBlockEntity.getFoodBlockData().last_use_provides_effects();
	}

	@Override
	protected void onRecoveryTick(Level world, BlockPos pos, BlockState state) {

		int i = this.getBites(state);
		if (i > 0) {
			world.setBlock(pos, this.setBites(state, i - 1), Block.UPDATE_ALL);
		}
	}

	@Override
	protected int getAnalogOutputSignal(BlockState state, Level world, BlockPos pos, Direction direction) {
		return this.getMaxBites() - this.getBites(state);
	}

	@Override
	protected BlockState updateShape(BlockState state, LevelReader world, ScheduledTickAccess tickView, BlockPos pos, Direction direction, BlockPos neighborPos, BlockState neighborState, RandomSource random) {
		return direction == Direction.DOWN && !state.canSurvive(world, pos) ? Blocks.AIR.defaultBlockState() : super.updateShape(state, world, tickView, pos, direction, neighborPos, neighborState, random);
	}

	@Override
	protected boolean canSurvive(BlockState state, LevelReader level, BlockPos pos) {
		return canSupportRigidBlock(level, pos.below());
	}

	public abstract int getMaxBites();

	protected abstract int getBites(BlockState state);

	protected abstract BlockState setBites(BlockState state, int value);

	/**
	 * Code by VectorWing as implemented in Farmer's Delight
	 */
	// region shapes
	private static VoxelShape rotateY(VoxelShape shape, int rotation) {
		List<VoxelShape> rotatedShapes = new ArrayList<>();
		shape.forAllBoxes((x1, y1, z1, x2, y2, z2) -> {
			x1 = x1 * 16.0F - 8.0F;
			x2 = x2 * 16.0F - 8.0F;
			y1 *= 16.0F;
			y2 *= 16.0F;
			z1 = z1 * 16.0F - 8.0F;
			z2 = z2 * 16.0F - 8.0F;
			double nx1;
			double nz1;
			double nx2;
			double nz2;
			switch (rotation) {
				case 0:
					nx1 = 8.0F - z1;
					nz1 = 8.0F + x1;
					nx2 = 8.0F - z2;
					nz2 = 8.0F + x2;
					break;
				case 1:
					nx1 = 8.0F - x1;
					nz1 = 8.0F - z1;
					nx2 = 8.0F - x2;
					nz2 = 8.0F - z2;
					break;
				case 2:
					nx1 = 8.0F + z1;
					nz1 = 8.0F - x1;
					nx2 = 8.0F + z2;
					nz2 = 8.0F - x2;
					break;
				default:
					throw new IllegalArgumentException("Unexpected rotation: " + rotation);
			}

			rotatedShapes.add(blockBox(nx1, y1, nz1, nx2, y2, nz2));
		});
		return mergeShapes(rotatedShapes);
	}

	private static VoxelShape blockBox(double x1, double y1, double z1, double x2, double y2, double z2) {
		return Block.box(Math.min(x1, x2), Math.min(y1, y2), Math.min(z1, z2), Math.max(x1, x2), Math.max(y1, y2), Math.max(z1, z2));
	}

	private static VoxelShape mergeShapes(List<VoxelShape> shapes) {
		return shapes.stream().reduce((a, b) -> Shapes.join(a, b, BooleanOp.OR)).orElse(Block.box(0.0F, 0.0F, 0.0F, 16.0F, 16.0F, 16.0F));
	}

	private static Map<Direction, VoxelShape> getShapesRotatedFromNorth(VoxelShape shapeOnNorth) {
		EnumMap<Direction, VoxelShape> map = new EnumMap<>(Direction.class);
		map.put(Direction.NORTH, shapeOnNorth);
		map.put(Direction.EAST, rotateY(shapeOnNorth, 0));
		map.put(Direction.SOUTH, rotateY(shapeOnNorth, 1));
		map.put(Direction.WEST, rotateY(shapeOnNorth, 2));
		return Collections.unmodifiableMap(map);
	}

	public static VoxelShape[][] buildPlatedFoodShapes(VoxelShape[] dishShapes, VoxelShape plateShape) {
		return PLATED_SHAPE_CACHE.computeIfAbsent(dishShapes, (shapes) -> {
			VoxelShape[][] result = new VoxelShape[shapes.length + 1][4];

			for (int j = 0; j < 4; ++j) {
				result[0][j] = plateShape;
			}

			for (int i = 0; i < shapes.length; ++i) {
				Map<Direction, VoxelShape> rotatedRoast = getShapesRotatedFromNorth(shapes[i]);

				for (Map.Entry<Direction, VoxelShape> entry : rotatedRoast.entrySet()) {
					result[i + 1][entry.getKey().get2DDataValue()] = Shapes.join(plateShape, entry.getValue(), BooleanOp.OR);
				}
			}

			return result;
		});
	}

	public static VoxelShape[][] buildRotatedFoodShapes(VoxelShape[] dishShapes) {
		return ROTATED_SHAPE_CACHE.computeIfAbsent(dishShapes, (shapes) -> {
			VoxelShape[][] result = new VoxelShape[shapes.length][4];

			for (int i = 0; i < shapes.length; ++i) {
				Map<Direction, VoxelShape> rotated = getShapesRotatedFromNorth(shapes[i]);

				for (Map.Entry<Direction, VoxelShape> entry : rotated.entrySet()) {
					result[i][entry.getKey().get2DDataValue()] = entry.getValue();
				}
			}

			return result;
		});
	}
	// endregion shapes
}
