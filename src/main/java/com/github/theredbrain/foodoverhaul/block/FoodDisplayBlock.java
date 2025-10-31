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
import net.minecraft.block.entity.CampfireBlockEntity;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.registry.Registries;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.registry.tag.TagKey;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.BooleanProperty;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.Identifier;
import net.minecraft.util.ItemActionResult;
import net.minecraft.util.ItemScatterer;
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
			int index;
			if (foodDisplayBlockEntity.getFoodDisplayBlockData().single_item_mode()) {
				index = 0;
			} else {
				index = FoodDisplayBlockEntity.getIndex(hit.getPos(), pos);
			}
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
	protected ItemActionResult onUseWithItem(ItemStack stack, BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, BlockHitResult hit) {
		if (world.getBlockEntity(pos) instanceof FoodDisplayBlockEntity foodDisplayBlockEntity && state.getBlock() instanceof FoodDisplayBlock foodDisplayBlock && foodDisplayBlock.canPlayerModify(world, pos, state, foodDisplayBlockEntity, player)) {
			ActionResult result;
			String viableItemsTagIdentifier = foodDisplayBlockEntity.getFoodDisplayBlockData().viable_items_tag_identifier();
			if (viableItemsTagIdentifier.isEmpty() || stack.isIn(TagKey.of(RegistryKeys.ITEM, Identifier.of(viableItemsTagIdentifier)))) {
				if (foodDisplayBlockEntity.getFoodDisplayBlockData().single_item_mode()) {
					result = foodDisplayBlockEntity.addNewItem(world, stack, player, 0);
				} else {
					int index = FoodDisplayBlockEntity.getIndex(hit.getPos(), pos);
					result = foodDisplayBlockEntity.addNewItem(world, stack, player, index);
				}
				if (result.isAccepted()) {
					return ItemActionResult.SUCCESS;
				}
			}
		}

		return ItemActionResult.PASS_TO_DEFAULT_BLOCK_INTERACTION;
	}

	@Override
	protected void onStateReplaced(BlockState state, World world, BlockPos pos, BlockState newState, boolean moved) {
		if (!state.isOf(newState.getBlock())) {
			BlockEntity blockEntity = world.getBlockEntity(pos);
			if (blockEntity instanceof FoodDisplayBlockEntity) {
				ItemScatterer.spawn(world, pos, ((FoodDisplayBlockEntity)blockEntity).getDisplayedItems());
			}

			super.onStateReplaced(state, world, pos, newState, moved);
		}
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
		return canPlayerInteract || player.isCreative();
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
