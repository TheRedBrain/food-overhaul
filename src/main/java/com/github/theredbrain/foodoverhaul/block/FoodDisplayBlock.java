package com.github.theredbrain.foodoverhaul.block;

import com.github.theredbrain.foodoverhaul.block.entity.FoodDisplayBlockEntity;
import com.github.theredbrain.foodoverhaul.entity.player.DuckPlayerEntityMixin;
import com.mojang.serialization.MapCodec;
import net.minecraft.block.Block;
import net.minecraft.block.BlockRenderType;
import net.minecraft.block.BlockState;
import net.minecraft.block.BlockWithEntity;
import net.minecraft.block.ShapeContext;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.registry.Registries;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.BooleanProperty;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.Identifier;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;
import net.minecraft.world.WorldAccess;
import org.jetbrains.annotations.Nullable;

import java.util.Optional;

public class FoodDisplayBlock extends BlockWithEntity {
	public static final MapCodec<FoodDisplayBlock> CODEC = createCodec(FoodDisplayBlock::new);

	public static final BooleanProperty IS_EMPTY = BooleanProperty.of("is_empty");

	protected static final VoxelShape SHAPE;

	@Override
	protected MapCodec<? extends FoodDisplayBlock> getCodec() {
		return CODEC;
	}

	public FoodDisplayBlock(Settings settings) {
		super(settings);
		this.setDefaultState(this.stateManager.getDefaultState().with(IS_EMPTY, true));
	}

	@Nullable
	@Override
	public BlockEntity createBlockEntity(BlockPos pos, BlockState state) {
		return new FoodDisplayBlockEntity(pos, state);
	}

	@Override
	protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
		super.appendProperties(builder);
		builder.add(IS_EMPTY);
	}

	@Override
	public BlockRenderType getRenderType(BlockState state) {
		return BlockRenderType.MODEL;
	}

	@Override
	public VoxelShape getOutlineShape(BlockState state, BlockView level, BlockPos pos, ShapeContext context) {
		return SHAPE;
	}

	@Override
	protected ActionResult onUse(BlockState state, World world, BlockPos pos, PlayerEntity player, BlockHitResult hit) {
		BlockEntity blockEntity = world.getBlockEntity(pos);
		if (blockEntity instanceof FoodDisplayBlockEntity foodDisplayBlockEntity && state.getBlock() instanceof FoodDisplayBlock foodDisplayBlock) {
			int index = FoodDisplayBlockEntity.getIndex(hit.getPos(), pos);
			boolean canPlayerModify = foodDisplayBlock.canPlayerModify(world, pos, state, foodDisplayBlockEntity, player);

			if (player.isSneaking() && canPlayerModify) {

				if (!foodDisplayBlockEntity.getDisplayedItems().get(index).isEmpty()) {
					return foodDisplayBlockEntity.rotateItem(index);
				} else if (player.isCreative()) {
					((DuckPlayerEntityMixin) player).foodoverhaul$openFoodDisplayBlockScreen(foodDisplayBlockEntity);
					return ActionResult.SUCCESS;
				}
			} else if (foodDisplayBlock.canPlayerEat(world, pos, state, foodDisplayBlockEntity, player)) {
				return foodDisplayBlockEntity.consumeItem(world, player, index);

			}
			return ActionResult.FAIL;
		}
		return super.onUse(state, world, pos, player, hit);
	}

	@Override
	protected ActionResult onUseWithItem(ItemStack stack, BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, BlockHitResult hit) {
		if (world.getBlockEntity(pos) instanceof FoodDisplayBlockEntity foodDisplayBlockEntity && state.getBlock() instanceof FoodDisplayBlock foodDisplayBlock && foodDisplayBlock.canPlayerModify(world, pos, state, foodDisplayBlockEntity, player)) {
			int index = FoodDisplayBlockEntity.getIndex(hit.getPos(), pos);
			ActionResult result = foodDisplayBlockEntity.addNewItem(world, stack, player, index);

			if (result.isAccepted()) {
				return ActionResult.SUCCESS;
			}
		}

		return ActionResult.PASS_TO_DEFAULT_BLOCK_ACTION;
	}

	protected boolean canPlayerModify(WorldAccess world, BlockPos pos, BlockState state, FoodDisplayBlockEntity foodDisplayBlockEntity, PlayerEntity player) {
		boolean canPlayerInteract = true;
		String enableModificationStatusEffectIdentifier = foodDisplayBlockEntity.getFoodDisplayBlockData().enables_modification_status_effect_identifier();
		if (!enableModificationStatusEffectIdentifier.isEmpty()) {
			Optional<RegistryEntry.Reference<StatusEffect>> optional_status_effect = Registries.STATUS_EFFECT.getEntry(Identifier.of(enableModificationStatusEffectIdentifier));
			if (optional_status_effect.isPresent()) {
				canPlayerInteract = player.hasStatusEffect(optional_status_effect.get());
			}
		}
		return canPlayerInteract;
	}

	protected boolean canPlayerEat(WorldAccess world, BlockPos pos, BlockState state, FoodDisplayBlockEntity foodDisplayBlockEntity, PlayerEntity player) {
		boolean canPlayerInteract = true;
		String usePreventingStatusEffectIdentifier = foodDisplayBlockEntity.getFoodDisplayBlockData().use_preventing_status_effect_identifier();
		if (!usePreventingStatusEffectIdentifier.isEmpty()) {
			Optional<RegistryEntry.Reference<StatusEffect>> optional_status_effect = Registries.STATUS_EFFECT.getEntry(Identifier.of(usePreventingStatusEffectIdentifier));
			if (optional_status_effect.isPresent()) {
				canPlayerInteract = !player.hasStatusEffect(optional_status_effect.get());
			}
		}
		return canPlayerInteract;
	}

	static {
		SHAPE = Block.createCuboidShape(1.0, 0.0, 1.0, 15.0, 1.0, 15.0);
	}

}
