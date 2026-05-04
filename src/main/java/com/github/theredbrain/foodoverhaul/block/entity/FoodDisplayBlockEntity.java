package com.github.theredbrain.foodoverhaul.block.entity;

import com.github.theredbrain.foodoverhaul.FoodOverhaul;
import com.github.theredbrain.foodoverhaul.block.FoodDisplayBlock;
import com.github.theredbrain.foodoverhaul.component.type.FoodDisplayBlockDataComponent;
import com.github.theredbrain.foodoverhaul.entity.player.PlayerHelper;
import com.github.theredbrain.foodoverhaul.registry.EntityRegistry;
import com.github.theredbrain.foodoverhaul.registry.FoodOverhaulDataComponents;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.BlockPos;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.NonNullList;
import net.minecraft.core.component.DataComponentGetter;
import net.minecraft.core.component.DataComponentMap;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
import net.minecraft.world.ContainerHelper;
import net.minecraft.world.Containers;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.storage.ValueInput;
import net.minecraft.world.level.storage.ValueOutput;
import net.minecraft.world.phys.Vec3;

public class FoodDisplayBlockEntity extends BlockEntity {

	private final NonNullList<ItemStack> displayedItems = NonNullList.withSize(4, ItemStack.EMPTY);

	private FoodDisplayBlockData foodDisplayBlockData = FoodDisplayBlockData.DEFAULT;

	public FoodDisplayBlockEntity(BlockPos pos, BlockState state) {
		super(EntityRegistry.FOOD_DISPLAY_BLOCK_ENTITY, pos, state);
	}

	@Override
	protected void saveAdditional(ValueOutput view) {

		super.saveAdditional(view);

		ContainerHelper.saveAllItems(view, this.displayedItems, true);

		view.store("foodDisplayBlockData", FoodDisplayBlockData.CODEC, this.foodDisplayBlockData);

	}

	@Override
	protected void loadAdditional(ValueInput view) {

		super.loadAdditional(view);

		this.displayedItems.clear();
		ContainerHelper.loadAllItems(view, this.displayedItems);

		this.foodDisplayBlockData = view.read("foodDisplayBlockData", FoodDisplayBlockData.CODEC).orElse(FoodDisplayBlockData.DEFAULT);

	}

	@Override
	public ClientboundBlockEntityDataPacket getUpdatePacket() {
		return ClientboundBlockEntityDataPacket.create(this);
	}

	@Override
	public CompoundTag getUpdateTag(HolderLookup.Provider registryLookup) {
		return this.saveCustomOnly(registryLookup);
	}

	@Override
	public void preRemoveSideEffects(BlockPos pos, BlockState oldState) {
		if (this.level != null) {
			Containers.dropContents(this.level, pos, this.getDisplayedItems());
		}
	}

	public InteractionResult rotateItem(int index) {
		if (this.getLevel() != null) {
			int[] newRotations = this.foodDisplayBlockData.getRotations();
			newRotations[index] = newRotations[index] + 1;
			this.setFoodDisplayBlockData(new FoodDisplayBlockData.Builder(this.foodDisplayBlockData).rotations(newRotations[0], newRotations[1], newRotations[2], newRotations[3]).build());
			this.setChanged();
			this.getLevel().sendBlockUpdated(this.getBlockPos(), this.getBlockState(), this.getBlockState(), Block.UPDATE_ALL);
			return InteractionResult.SUCCESS;
		}
		return InteractionResult.PASS;
	}

	public static int getIndex(Vec3 hitPos, BlockPos blockPos) {
		boolean xPositive = hitPos.x - blockPos.getX() > 0.5;
		boolean zPositive = hitPos.z - blockPos.getZ() > 0.5;
		int index;
		if (xPositive && zPositive) {
			index = 3;
		} else if (!xPositive && zPositive) {
			index = 0;
		} else if (!xPositive && !zPositive) {
			index = 1;
		} else {
			index = 2;
		}
		return index;
	}

	public InteractionResult consumeItem(Level level, Player player, int index) {
		ItemStack consumedStack = this.displayedItems.get(index).copy();
		if (!consumedStack.isEmpty()) {

			if (player.getItemInHand(player.getUsedItemHand()).isEmpty()) {

				if (PlayerHelper.canConsumeItem(player, consumedStack)) {
					ItemStack remainingStack = consumedStack.finishUsingItem(player.level(), player);

					if (!this.foodDisplayBlockData.infinite_uses) {
						this.displayedItems.set(index, remainingStack);
						BlockState oldState = level.getBlockState(worldPosition);
						BlockState newState = this.updateEmptyState(oldState);
						this.setChanged();
						level.setBlock(worldPosition, newState, Block.UPDATE_ALL);
					}
					return InteractionResult.SUCCESS;
				}
			} else {
				if (!player.isCreative()) {
					player.getInventory().placeItemBackInInventory(consumedStack);
				}
				if (!this.foodDisplayBlockData.infinite_uses) {
					this.displayedItems.set(index, ItemStack.EMPTY);
					BlockState oldState = level.getBlockState(worldPosition);
					BlockState newState = this.updateEmptyState(oldState);
					this.setChanged();
					level.setBlock(worldPosition, newState, Block.UPDATE_ALL);
				}
				return InteractionResult.SUCCESS;
			}
		}
		return InteractionResult.PASS;
	}

	public InteractionResult addNewItem(Level world, ItemStack itemStack, Player player, int index) {
		BlockState oldState = world.getBlockState(this.worldPosition);

		if (this.displayedItems.get(index).isEmpty() && !itemStack.isEmpty()) {
			this.displayedItems.set(index, itemStack.consumeAndReturn(1, player));
			BlockState newState = this.updateEmptyState(oldState);
			this.setChanged();
			world.setBlock(this.worldPosition, newState, Block.UPDATE_ALL);
			return InteractionResult.SUCCESS;
		}
		return InteractionResult.FAIL;
	}

	public BlockState updateEmptyState(BlockState oldState) {
		for (int i = 0; i < 4; i++) {
			if (!this.displayedItems.get(i).isEmpty()) {
				return oldState.setValue(FoodDisplayBlock.IS_EMPTY, false);
			}
		}
		return oldState.setValue(FoodDisplayBlock.IS_EMPTY, true);
	}

	public FoodDisplayBlockData getFoodDisplayBlockData() {
		return this.foodDisplayBlockData;
	}

	public void setFoodDisplayBlockData(FoodDisplayBlockData foodDisplayBlockData) {
		this.foodDisplayBlockData = foodDisplayBlockData;
		if (this.foodDisplayBlockData.single_item_mode) {
			if (this.level != null) {
				for (int i = 1; i < 4; i++) {
					Containers.dropItemStack(this.level, worldPosition.getX(), worldPosition.getY(), worldPosition.getZ(), this.getDisplayedItems().get(i));
				}
			}
		}
	}

	public NonNullList<ItemStack> getDisplayedItems() {
		return this.displayedItems;
	}

	@Override
	protected void applyImplicitComponents(DataComponentGetter components) {
		super.applyImplicitComponents(components);
		this.foodDisplayBlockData = components.getOrDefault(FoodOverhaulDataComponents.FOOD_DISPLAY_BLOCK_DATA, FoodDisplayBlockDataComponent.DEFAULT).food_display_block_data();
	}

	@Override
	protected void collectImplicitComponents(DataComponentMap.Builder builder) {
		super.collectImplicitComponents(builder);
		builder.set(FoodOverhaulDataComponents.FOOD_DISPLAY_BLOCK_DATA, new FoodDisplayBlockDataComponent(this.foodDisplayBlockData));
	}

	public record FoodDisplayBlockData(
			String use_preventing_status_effect_identifier,
			String enables_modification_status_effect_identifier,
			String viable_items_tag_identifier,
			boolean single_item_mode,
			int rotation_1,
			int rotation_2,
			int rotation_3,
			int rotation_4,
			boolean infinite_uses
	) {

		public static final FoodDisplayBlockData DEFAULT = new FoodDisplayBlockData(
				"",
				"",
				"",
				false,
				0,
				0,
				0,
				0,
				false
		);

		public static final Codec<FoodDisplayBlockData> CODEC = RecordCodecBuilder.create(
				instance -> instance.group(
								Codec.STRING.fieldOf("use_preventing_status_effect_identifier").forGetter(FoodDisplayBlockData::use_preventing_status_effect_identifier),
								Codec.STRING.fieldOf("enables_modification_status_effect_identifier").forGetter(FoodDisplayBlockData::enables_modification_status_effect_identifier),
								Codec.STRING.fieldOf("viable_items_tag_identifier").forGetter(FoodDisplayBlockData::viable_items_tag_identifier),
								Codec.BOOL.fieldOf("single_item_mode").forGetter(FoodDisplayBlockData::single_item_mode),
								Codec.INT.fieldOf("rotation_1").forGetter(FoodDisplayBlockData::rotation_1),
								Codec.INT.fieldOf("rotation_2").forGetter(FoodDisplayBlockData::rotation_2),
								Codec.INT.fieldOf("rotation_3").forGetter(FoodDisplayBlockData::rotation_3),
								Codec.INT.fieldOf("rotation_4").forGetter(FoodDisplayBlockData::rotation_4),
								Codec.BOOL.fieldOf("infinite_uses").forGetter(FoodDisplayBlockData::infinite_uses)
						)
						.apply(instance, FoodDisplayBlockData::new)
		);

		public static final StreamCodec<RegistryFriendlyByteBuf, FoodDisplayBlockData> PACKET_CODEC = StreamCodec.ofMember(FoodDisplayBlockData::write, FoodDisplayBlockData::new);

		public FoodDisplayBlockData(RegistryFriendlyByteBuf registryByteBuf) {
			this(
					registryByteBuf.readUtf(),
					registryByteBuf.readUtf(),
					registryByteBuf.readUtf(),
					registryByteBuf.readBoolean(),
					registryByteBuf.readInt(),
					registryByteBuf.readInt(),
					registryByteBuf.readInt(),
					registryByteBuf.readInt(),
					registryByteBuf.readBoolean()
			);
		}

		public void write(RegistryFriendlyByteBuf registryByteBuf) {
			registryByteBuf.writeUtf(this.use_preventing_status_effect_identifier);
			registryByteBuf.writeUtf(this.enables_modification_status_effect_identifier);
			registryByteBuf.writeUtf(this.viable_items_tag_identifier);
			registryByteBuf.writeBoolean(this.single_item_mode);
			registryByteBuf.writeInt(this.rotation_1 % 16);
			registryByteBuf.writeInt(this.rotation_2 % 16);
			registryByteBuf.writeInt(this.rotation_3 % 16);
			registryByteBuf.writeInt(this.rotation_4 % 16);
			registryByteBuf.writeBoolean(this.infinite_uses);
		}

		public int[] getRotations() {
			return new int[]{this.rotation_1, this.rotation_2, this.rotation_3, this.rotation_4};
		}

		static class Builder {
			private String use_preventing_status_effect_identifier;
			private String enables_modification_status_effect_identifier;
			private String viable_items_tag_identifier;
			private boolean single_item_mode;
			private int rotation_1;
			private int rotation_2;
			private int rotation_3;
			private int rotation_4;
			private boolean infinite_uses;

			public Builder(FoodDisplayBlockData base) {
				this.use_preventing_status_effect_identifier = base.use_preventing_status_effect_identifier;
				this.enables_modification_status_effect_identifier = base.enables_modification_status_effect_identifier;
				this.viable_items_tag_identifier = base.viable_items_tag_identifier;
				this.single_item_mode = base.single_item_mode;
				this.rotation_1 = base.rotation_1;
				this.rotation_2 = base.rotation_2;
				this.rotation_3 = base.rotation_3;
				this.rotation_4 = base.rotation_4;
				this.infinite_uses = base.infinite_uses;
			}

			public Builder rotations(int rotation_1, int rotation_2, int rotation_3, int rotation_4) {
				this.rotation_1 = rotation_1 % 16;
				this.rotation_2 = rotation_2 % 16;
				this.rotation_3 = rotation_3 % 16;
				this.rotation_4 = rotation_4 % 16;
				return this;
			}

			public FoodDisplayBlockData build() {
				return new FoodDisplayBlockData(
						this.use_preventing_status_effect_identifier,
						this.enables_modification_status_effect_identifier,
						this.viable_items_tag_identifier,
						this.single_item_mode,
						this.rotation_1,
						this.rotation_2,
						this.rotation_3,
						this.rotation_4,
						this.infinite_uses
				);
			}
		}
	}

}
