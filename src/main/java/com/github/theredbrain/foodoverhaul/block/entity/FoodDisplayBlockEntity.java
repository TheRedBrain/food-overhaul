package com.github.theredbrain.foodoverhaul.block.entity;

import com.github.theredbrain.foodoverhaul.FoodOverhaul;
import com.github.theredbrain.foodoverhaul.block.FoodDisplayBlock;
import com.github.theredbrain.foodoverhaul.component.type.FoodDisplayBlockDataComponent;
import com.github.theredbrain.foodoverhaul.entity.player.DuckPlayerEntityMixin;
import com.github.theredbrain.foodoverhaul.registry.EntityRegistry;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.component.ComponentMap;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.Inventories;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.nbt.NbtOps;
import net.minecraft.network.RegistryByteBuf;
import net.minecraft.network.codec.PacketCodec;
import net.minecraft.network.packet.s2c.play.BlockEntityUpdateS2CPacket;
import net.minecraft.registry.RegistryWrapper;
import net.minecraft.util.ActionResult;
import net.minecraft.util.ItemScatterer;
import net.minecraft.util.collection.DefaultedList;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;

public class FoodDisplayBlockEntity extends BlockEntity {

	private final DefaultedList<ItemStack> displayedItems = DefaultedList.ofSize(4, ItemStack.EMPTY);

	private FoodDisplayBlockData foodDisplayBlockData = FoodDisplayBlockData.DEFAULT;

	public FoodDisplayBlockEntity(BlockPos pos, BlockState state) {
		super(EntityRegistry.FOOD_DISPLAY_BLOCK_ENTITY, pos, state);
	}

	@Override
	protected void writeNbt(NbtCompound nbt, RegistryWrapper.WrapperLookup registryLookup) {

		super.writeNbt(nbt, registryLookup);

		Inventories.writeNbt(nbt, this.displayedItems, registryLookup);

		nbt.put("foodDisplayBlockData", FoodDisplayBlockData.CODEC.encodeStart(NbtOps.INSTANCE, this.foodDisplayBlockData).getOrThrow());

	}

	@Override
	protected void readNbt(NbtCompound nbt, RegistryWrapper.WrapperLookup registryLookup) {

		super.readNbt(nbt, registryLookup);

		this.displayedItems.clear();
		Inventories.readNbt(nbt, this.displayedItems, registryLookup);

		if (nbt.contains("foodDisplayBlockData")) {
			this.foodDisplayBlockData = FoodDisplayBlockData.CODEC.parse(NbtOps.INSTANCE, nbt.get("foodDisplayBlockData")).resultOrPartial().orElse(FoodDisplayBlockData.DEFAULT);
		}

	}

	@Override
	public BlockEntityUpdateS2CPacket toUpdatePacket() {
		return BlockEntityUpdateS2CPacket.create(this);
	}

	@Override
	public NbtCompound toInitialChunkDataNbt(RegistryWrapper.WrapperLookup registryLookup) {
		return this.createComponentlessNbt(registryLookup);
	}

	public ActionResult rotateItem(int index) {
		if (this.getWorld() != null) {
			int[] newRotations = this.foodDisplayBlockData.getRotations();
			newRotations[index] = newRotations[index] + 1;
			this.setFoodDisplayBlockData(new FoodDisplayBlockData.Builder(this.foodDisplayBlockData).rotations(newRotations[0], newRotations[1], newRotations[2], newRotations[3]).build());
			this.markDirty();
			this.getWorld().updateListeners(this.getPos(), this.getCachedState(), this.getCachedState(), Block.NOTIFY_ALL);
			return ActionResult.SUCCESS;
		}
		return ActionResult.PASS;
	}

	public static int getIndex(Vec3d hitPos, BlockPos blockPos) {
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

	public ActionResult consumeItem(World world, PlayerEntity player, int index) {
		ItemStack consumedStack = this.displayedItems.get(index).copy();
		if (!consumedStack.isEmpty()) {

			if (player.getStackInHand(player.getActiveHand()).isEmpty()) {

				if (((DuckPlayerEntityMixin) player).foodoverhaul$canConsumeItem(consumedStack)) {
					ItemStack remainingStack = consumedStack.finishUsing(player.getEntityWorld(), player);

					if (!this.foodDisplayBlockData.infinite_uses) {
						this.displayedItems.set(index, remainingStack);
						BlockState oldState = world.getBlockState(pos);
						BlockState newState = this.updateEmptyState(oldState);
						this.markDirty();
						world.setBlockState(pos, newState, Block.NOTIFY_ALL);
					}
					return ActionResult.SUCCESS;
				}
			} else {
				if (!player.isCreative()) {
					player.getInventory().offerOrDrop(consumedStack);
				}
				if (!this.foodDisplayBlockData.infinite_uses) {
					this.displayedItems.set(index, ItemStack.EMPTY);
					BlockState oldState = world.getBlockState(pos);
					BlockState newState = this.updateEmptyState(oldState);
					this.markDirty();
					world.setBlockState(pos, newState, Block.NOTIFY_ALL);
				}
				return ActionResult.SUCCESS;
			}
		}
		return ActionResult.PASS;
	}

	public ActionResult addNewItem(World world, ItemStack itemStack, PlayerEntity player, int index) {
		BlockState oldState = world.getBlockState(this.pos);

		if (this.displayedItems.get(index).isEmpty() && !itemStack.isEmpty()) {
			this.displayedItems.set(index, itemStack.splitUnlessCreative(1, player));
			BlockState newState = this.updateEmptyState(oldState);
			this.markDirty();
			world.setBlockState(this.pos, newState, Block.NOTIFY_ALL);
			return ActionResult.SUCCESS;
		}
		return ActionResult.FAIL;
	}

	public BlockState updateEmptyState(BlockState oldState) {
		for (int i = 0; i < 4; i++) {
			if (!this.displayedItems.get(i).isEmpty()) {
				return oldState.with(FoodDisplayBlock.IS_EMPTY, false);
			}
		}
		return oldState.with(FoodDisplayBlock.IS_EMPTY, true);
	}

	public FoodDisplayBlockData getFoodDisplayBlockData() {
		return this.foodDisplayBlockData;
	}

	public void setFoodDisplayBlockData(FoodDisplayBlockData foodDisplayBlockData) {
		this.foodDisplayBlockData = foodDisplayBlockData;
		if (this.foodDisplayBlockData.single_item_mode) {
			if (this.world != null) {
				for (int i = 1; i < 4; i++) {
					ItemScatterer.spawn(this.world, pos.getX(), pos.getY(), pos.getZ(), this.getDisplayedItems().get(i));
				}
			}
		}
	}

	public DefaultedList<ItemStack> getDisplayedItems() {
		return this.displayedItems;
	}

	@Override
	protected void readComponents(ComponentsAccess components) {
		super.readComponents(components);
		this.foodDisplayBlockData = components.getOrDefault(FoodOverhaul.FOOD_DISPLAY_BLOCK_DATA, FoodDisplayBlockDataComponent.DEFAULT).food_display_block_data();
	}

	@Override
	protected void addComponents(ComponentMap.Builder builder) {
		super.addComponents(builder);
		builder.add(FoodOverhaul.FOOD_DISPLAY_BLOCK_DATA, new FoodDisplayBlockDataComponent(this.foodDisplayBlockData));
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

		public static final PacketCodec<RegistryByteBuf, FoodDisplayBlockData> PACKET_CODEC = PacketCodec.of(FoodDisplayBlockData::write, FoodDisplayBlockData::new);

		public FoodDisplayBlockData(RegistryByteBuf registryByteBuf) {
			this(
					registryByteBuf.readString(),
					registryByteBuf.readString(),
					registryByteBuf.readString(),
					registryByteBuf.readBoolean(),
					registryByteBuf.readInt(),
					registryByteBuf.readInt(),
					registryByteBuf.readInt(),
					registryByteBuf.readInt(),
					registryByteBuf.readBoolean()
			);
		}

		public void write(RegistryByteBuf registryByteBuf) {
			registryByteBuf.writeString(this.use_preventing_status_effect_identifier);
			registryByteBuf.writeString(this.enables_modification_status_effect_identifier);
			registryByteBuf.writeString(this.viable_items_tag_identifier);
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

			public FoodDisplayBlockData.Builder rotations(int rotation_1, int rotation_2, int rotation_3, int rotation_4) {
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
