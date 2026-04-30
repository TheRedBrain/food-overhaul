package com.github.theredbrain.foodoverhaul.block;

import com.github.theredbrain.foodoverhaul.block.entity.FoodDisplayBlockEntity;
import com.github.theredbrain.foodoverhaul.entity.player.DuckPlayerEntityMixin;
import com.mojang.serialization.MapCodec;
import net.minecraft.resources.Identifier;
import org.jetbrains.annotations.Nullable;

import java.util.Optional;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Holder;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.tags.TagKey;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.BaseEntityBlock;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.RenderShape;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;

public class FoodDisplayBlock extends BaseEntityBlock {
	public static final MapCodec<FoodDisplayBlock> CODEC = simpleCodec(FoodDisplayBlock::new);

	public static final BooleanProperty IS_EMPTY = BooleanProperty.create("is_empty");

	protected static final VoxelShape SHAPE;

	@Override
	protected MapCodec<? extends FoodDisplayBlock> codec() {
		return CODEC;
	}

	public FoodDisplayBlock(Properties settings) {
		super(settings);
		this.registerDefaultState(this.stateDefinition.any().setValue(IS_EMPTY, true));
	}

	@Nullable
	@Override
	public BlockEntity newBlockEntity(BlockPos pos, BlockState state) {
		return new FoodDisplayBlockEntity(pos, state);
	}

	@Override
	protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
		super.createBlockStateDefinition(builder);
		builder.add(IS_EMPTY);
	}

	@Override
	public RenderShape getRenderShape(BlockState state) {
		return RenderShape.MODEL;
	}

	@Override
	public VoxelShape getShape(BlockState state, BlockGetter level, BlockPos pos, CollisionContext context) {
		return SHAPE;
	}

	@Override
	protected InteractionResult useWithoutItem(BlockState state, Level world, BlockPos pos, Player player, BlockHitResult hit) {
		BlockEntity blockEntity = world.getBlockEntity(pos);
		if (blockEntity instanceof FoodDisplayBlockEntity foodDisplayBlockEntity && state.getBlock() instanceof FoodDisplayBlock foodDisplayBlock) {
			int index;
			if (foodDisplayBlockEntity.getFoodDisplayBlockData().single_item_mode()) {
				index = 0;
			} else {
				index = FoodDisplayBlockEntity.getIndex(hit.getLocation(), pos);
			}
			boolean canPlayerModify = foodDisplayBlock.canPlayerModify(world, pos, state, foodDisplayBlockEntity, player);

			if (player.isShiftKeyDown() && canPlayerModify) {

				if (!foodDisplayBlockEntity.getDisplayedItems().get(index).isEmpty()) {
					return foodDisplayBlockEntity.rotateItem(index);
				} else if (player.isCreative()) {
					((DuckPlayerEntityMixin) player).foodoverhaul$openFoodDisplayBlockScreen(foodDisplayBlockEntity);
					return InteractionResult.SUCCESS;
				}
			} else if (foodDisplayBlock.canPlayerEat(world, pos, state, foodDisplayBlockEntity, player)) {
				return foodDisplayBlockEntity.consumeItem(world, player, index);

			}
			return InteractionResult.FAIL;
		}
		return super.useWithoutItem(state, world, pos, player, hit);
	}

	@Override
	protected InteractionResult useItemOn(ItemStack stack, BlockState state, Level world, BlockPos pos, Player player, InteractionHand hand, BlockHitResult hit) {
		if (world.getBlockEntity(pos) instanceof FoodDisplayBlockEntity foodDisplayBlockEntity && state.getBlock() instanceof FoodDisplayBlock foodDisplayBlock && foodDisplayBlock.canPlayerModify(world, pos, state, foodDisplayBlockEntity, player)) {
			InteractionResult result;
			String viableItemsTagIdentifier = foodDisplayBlockEntity.getFoodDisplayBlockData().viable_items_tag_identifier();
			if (viableItemsTagIdentifier.isEmpty() || stack.is(TagKey.create(Registries.ITEM, Identifier.parse(viableItemsTagIdentifier)))) {
				if (foodDisplayBlockEntity.getFoodDisplayBlockData().single_item_mode()) {
					result = foodDisplayBlockEntity.addNewItem(world, stack, player, 0);
				} else {
					int index = FoodDisplayBlockEntity.getIndex(hit.getLocation(), pos);
					result = foodDisplayBlockEntity.addNewItem(world, stack, player, index);
				}
				if (result.consumesAction()) {
					return InteractionResult.SUCCESS;
				}
			}
		}

		return InteractionResult.TRY_WITH_EMPTY_HAND;
	}

	protected boolean canPlayerModify(LevelAccessor world, BlockPos pos, BlockState state, FoodDisplayBlockEntity foodDisplayBlockEntity, Player player) {
		boolean canPlayerInteract = true;
		String enableModificationStatusEffectIdentifier = foodDisplayBlockEntity.getFoodDisplayBlockData().enables_modification_status_effect_identifier();
		if (!enableModificationStatusEffectIdentifier.isEmpty()) {
			Optional<Holder.Reference<MobEffect>> optional_status_effect = BuiltInRegistries.MOB_EFFECT.get(Identifier.parse(enableModificationStatusEffectIdentifier));
			if (optional_status_effect.isPresent()) {
				canPlayerInteract = player.hasEffect(optional_status_effect.get());
			}
		}
		return canPlayerInteract || player.isCreative();
	}

	protected boolean canPlayerEat(LevelAccessor world, BlockPos pos, BlockState state, FoodDisplayBlockEntity foodDisplayBlockEntity, Player player) {
		boolean canPlayerInteract = true;
		String usePreventingStatusEffectIdentifier = foodDisplayBlockEntity.getFoodDisplayBlockData().use_preventing_status_effect_identifier();
		if (!usePreventingStatusEffectIdentifier.isEmpty()) {
			Optional<Holder.Reference<MobEffect>> optional_status_effect = BuiltInRegistries.MOB_EFFECT.get(Identifier.parse(usePreventingStatusEffectIdentifier));
			if (optional_status_effect.isPresent()) {
				canPlayerInteract = !player.hasEffect(optional_status_effect.get());
			}
		}
		return canPlayerInteract;
	}

	static {
		SHAPE = Block.box(1.0, 0.0, 1.0, 15.0, 1.0, 15.0);
	}

}
